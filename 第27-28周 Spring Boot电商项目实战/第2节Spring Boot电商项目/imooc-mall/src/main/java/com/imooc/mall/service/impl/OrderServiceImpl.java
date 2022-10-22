package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.OrderItemMapper;
import com.imooc.mall.model.dao.OrderMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Order;
import com.imooc.mall.model.pojo.OrderItem;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.model.vo.OrderItemVO;
import com.imooc.mall.model.vo.OrderVO;
import com.imooc.mall.service.CartService;
import com.imooc.mall.service.OrderService;
import com.imooc.mall.util.OrderCodeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 订单Service实现类
 */
//数据库事务
@Transactional(rollbackFor = Exception.class) //含义是 遇到任何异常都会回滚
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    CartService cartService;

    @Resource
    ProductMapper productMapper;

    @Resource
    CartMapper cartMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    //返回的是一个orderNum 订单编号
    @Override
    public String create(CreateOrderReq createOrderReq) {

        //拿到用户ID
        Integer userId = UserFilter.currentUser.getId();
        //从购物车查找已经勾选的商品
        List<CartVO> cartVOList = cartService.list(userId);
        ArrayList<CartVO> cartVOListTemp = new ArrayList<>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            if (cartVO.getSelected().equals(Constant.Cart.CHECKED)) {
                cartVOListTemp.add(cartVO);
            }
        }
        cartVOList = cartVOListTemp;

        //如果购物车已勾选的为空,报错
        if (cartVOList == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CART_EMPTY);
        }

        //判断商品是否存在、上下架状态、库存
        validSaleStatusAndStock(cartVOList);
        //把购物车对象转为订单item对象
        List<OrderItem> orderItemList = cartVOListToOrderItemList(cartVOList);
        //扣库存  (商品表中的原有库存 - orderItem表里的购买数量)
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            int stock = product.getStock() - orderItem.getQuantity();
            //[加入购物车的时候有库存但是买的时候没有库存了。因为可能被别人买走了]
            if (stock < 0) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
            }
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        }
        //把购物车中的已勾选商品删除 (这里传进来的cartVOList经过过滤都是被勾选中的)
        cleanCart(cartVOList);
        //生成订单
        Order order = new Order();
        //生成订单号,有独立的规则
        String orderNo = OrderCodeFactory.getOrderCode(Long.valueOf(userId));
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice(orderItemList));
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverMobile(createOrderReq.getReceiverMobile());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAID.getCode());
        order.setPostage(0); //包邮
        order.setPaymentType(1); //付款方式设置为在线付款
        //插入到order表
        orderMapper.insertSelective(order);

        //循环保存每个商品到order_item表
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem);
//            throw new ImoocMallException(ImoocMallExceptionEnum.CART_EMPTY); 测试数据库事务时使用
        }
        //把结果返回
        return orderNo;
    }

    private Integer totalPrice(List<OrderItem> orderItemList) {
        Integer totalPrice = 0;
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            totalPrice += orderItem.getTotalPrice();
        }
        //返回的是订单下所有商品的总价
        return totalPrice;
    }

    private void cleanCart(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
//            cartMapper.deleteByPrimaryKey(cartVO.getProductId()); 这里应该是getId 是主键而不是商品id
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    private List<OrderItem> cartVOListToOrderItemList(List<CartVO> cartVOList) {
        //主要是赋值操作
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            //productId 是不会变的,但商品信息是会被修改的,所以我们作为订单一定要记录下当时的情景,
            // 这样的话,后面商品降价、升价还是改图片了对我们都不应该有影响,
            //记录商品快照信息
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setProductImg(cartVO.getProductImage());
            orderItem.setUnitPrice(cartVO.getPrice());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalPrice(cartVO.getTotalPrice());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    private void validSaleStatusAndStock(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            //判断商品是否存在、上下架状态
            if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
            }
            //判断商品库存 (买几件商品和库存进行对比)
            if (cartVO.getQuantity() > product.getStock()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
            }
        }
    }

    //新增OrderVO类和OrderItemVO类
    @Override
    public OrderVO detail(String orderNo) {
        //为了安全起见不允许暴露主键,所以不能用selectByPrimaryKey() 来查询。所以需要新写一个方法
        Order order = orderMapper.selectByOrderNo(orderNo);
        //订单不存在,则报错
        if (order == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ORDER);
        }
        //订单存在,需要判断所属  （不能拿别人的订单详情）
        Integer userId = UserFilter.currentUser.getId();
        if (!userId.equals(order.getUserId())) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_YOUR_ORDER);
        }
        OrderVO orderVO = getOrderVO(order);
        return orderVO;
    }

    private OrderVO getOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        //把order里能复制的都复制到orderVO
        BeanUtils.copyProperties(order, orderVO);
        //获取订单对应的orderItemVOList
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, orderItemVO);
            orderItemVOList.add(orderItemVO);

        }
        orderVO.setOrderItemVOList(orderItemVOList);
        //codeOf()... 通过状态码返回一个枚举的类型。这样就把一个数字的类型转化为枚举,,,,
        orderVO.setOrderStatusName(String.valueOf(Constant.OrderStatusEnum.codeOf(orderVO.getOrderStatus()).getValue()));
        return orderVO;
    }

    //订单列表前台的和后台的是不太一样的。给前台的只能查询自己的订单并且对里面的内容进行裁剪,可是给管理员看的就没有这么多限制了
    @Override
    public PageInfo listForCustomer(Integer pageNum, Integer pageSize) {
        Integer userId = UserFilter.currentUser.getId();
        PageHelper.startPage(pageNum, pageSize);
        //需要把orderList里的一个个order对象变成orderVO
        List<Order> orderList = orderMapper.listForCustomer(userId);
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        //在pageInfo去构造的时候一定是我们查出来的内容也就是mapper出来的内容。然后由于我们最终返回给前端的不是查询出来的而是经过处理的orderVOList,
        // 所以我们要给这个pageInfo设置一下,也就是说它会有一个方法setList,,
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVOList);
        return pageInfo;
    }

    private List<OrderVO> orderListToOrderVOList(List<Order> orderList) {
        List<OrderVO> orderVOList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            OrderVO orderVO = getOrderVO(order);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public void cancel(String orderNo) {
        //查询得到就可以取消,查询不到就说明订单号可能写的不对
        Order order = orderMapper.selectByOrderNo(orderNo);
        //查不到订单,报错
        if (order == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ORDER);
        }
        //验证用户身份
        //订单存在,需要判断所属  （不能拿别人的订单详情）
        Integer userId = UserFilter.currentUser.getId();
        if (!userId.equals(order.getUserId())) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_YOUR_ORDER);
        }
        //如果没有付款就可以取消订单
        if (order.getOrderStatus().equals(Constant.OrderStatusEnum.NOT_PAID.getCode())) {
            order.setOrderStatus(Constant.OrderStatusEnum.CANCELED.getCode());
            //订单完结除了发货之后的确认收货,还有就是取消订单,这两种情况都代表订单后续不会有任何的流转了,所以这里设置结束时间
            order.setEndTime(new Date());
            //更新订单
            orderMapper.updateByPrimaryKeySelective(order);
        }else{
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    //返回值是String,是生成的二维码图片地址
    public String qrcode(String orderNo){
        //生成二维码需要引入依赖 javase pom.xml
        //为了生成二维码需要新建一个工具 util下的 QRCodeGenerator
        return null;
    }
}

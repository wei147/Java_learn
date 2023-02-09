package com.wei.cloud.mall.practice.cartorder.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import com.wei.cloud.mall.practice.cartorder.feign.ProductFeignClient;
import com.wei.cloud.mall.practice.cartorder.feign.UserFeignClient;
import com.wei.cloud.mall.practice.cartorder.model.dao.CartMapper;
import com.wei.cloud.mall.practice.cartorder.model.dao.OrderItemMapper;
import com.wei.cloud.mall.practice.cartorder.model.dao.OrderMapper;
import com.wei.cloud.mall.practice.cartorder.model.pojo.Order;
import com.wei.cloud.mall.practice.cartorder.model.pojo.OrderItem;
import com.wei.cloud.mall.practice.cartorder.model.request.CreateOrderReq;
import com.wei.cloud.mall.practice.cartorder.model.vo.CartVO;
import com.wei.cloud.mall.practice.cartorder.model.vo.OrderItemVO;
import com.wei.cloud.mall.practice.cartorder.model.vo.OrderVO;
import com.wei.cloud.mall.practice.cartorder.service.CartService;
import com.wei.cloud.mall.practice.cartorder.service.OrderService;
import com.wei.cloud.mall.practice.cartorder.util.OrderCodeFactory;
import com.wei.cloud.mall.practice.categoryproduct.model.dao.ProductMapper;
import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.wei.cloud.mall.practice.user.service.UserService;
import com.wei.mall.practice.common.common.Constant;
import com.wei.mall.practice.common.exception.ImoocMallException;
import com.wei.mall.practice.common.exception.ImoocMallExceptionEnum;
import com.wei.mall.practice.common.util.QRCodeGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    ProductFeignClient productFeignClient;
//    ProductMapper productMapper;

    @Resource
    CartMapper cartMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    //配置生成二维码中的ip信息
    @Value("${file.upload.ip}")
    String ip;

    @Resource
//    UserService userService;
    UserFeignClient userFeignClient;


    //返回的是一个orderNum 订单编号
    @Override
    public String create(CreateOrderReq createOrderReq) {

        //拿到用户ID
        Integer userId = userFeignClient.getUser().getId();
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
            Product product = productFeignClient.detailForFeign(orderItem.getProductId());
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
        } else {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    //返回值是String,是生成的二维码图片地址
    @Override
    public String qrcode(String orderNo) {
        //在生成二维码之前,要知道存入的url是什么。这个url是包含http、ip还有地址在内的,最后再跟上订单号,就是完整的地址了
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //这个能获取到局域网的ip (复杂网络则不可行)
//        try {
//            ip = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
        String address = ip + ":" + request.getLocalPort(); //拿到端口号拼接ip信息
        String payUrl = "http://" + address + "/pay?orderNo=" + orderNo;
        try {
            QRCodeGenerator.generateQRCodeImage(payUrl, 350, 350, Constant.FILE_UPLOAD_DIR + orderNo + ".png");
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String pngAddress = "http://" + address + "/images/" + orderNo + ".png";
        //有了端口号之后,我们还需要一个ip信息,这个ip信息是需要我们自己来配置的,而不能简单的从request去拿,因为我们的服务上线之后它其实并不是直接暴露给外面的,
        // 无论是阿里云还是腾讯云,他们这个链接都会经过多层的转发,比如说防火墙之类的才回到我们的机器上,那么这个时候这个ip从request中拿其实是经过转发之后的内网ip,那这个是不对的,
        //所以我们应该在这里配置一下可以访问的ip   @Value("${file.upload.ip}")

        //生成二维码需要引入依赖 javase pom.xml
        //为了生成二维码需要新建一个工具 util下的 QRCodeGenerator

        //返回的结果是这个图片文件应该通过什么url可以访问到
        return pngAddress;
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectAllForAdmin();
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        //在pageInfo去构造的时候一定是我们查出来的内容也就是mapper出来的内容。然后由于我们最终返回给前端的不是查询出来的而是经过处理的orderVOList,
        // 所以我们要给这个pageInfo设置一下,也就是说它会有一个方法setList,,
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVOList);
        return pageInfo;
    }

    @Override
    public void pay(String orderNo) {
        //首先去根据orderNo把当前这个订单给找到,同样也会根据能找到和找不到这两种情况来决定这个支付成功与否
        //查不到订单,报错
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ORDER);
        }
        //支付之前的判断。 如果是未付款的状态才能允许付款
        if (order.getOrderStatus() == Constant.OrderStatusEnum.NOT_PAID.getCode()) {
            //设置为已付款
            order.setOrderStatus(Constant.OrderStatusEnum.PAID.getCode());
            order.setPayTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }


    //发货这个方法所做最主要的事情就是 改变订单的状态。(类似支付接口 pay)
    @Override
    public void deliver(String orderNo) {
        //查不到订单,报错
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ORDER);
        }
        //发货之前的判断。 如果是已付款的状态才能允许发货
        if (order.getOrderStatus() == Constant.OrderStatusEnum.PAID.getCode()) {
            //设置为已发货
            order.setOrderStatus(Constant.OrderStatusEnum.DELIVERED.getCode());
            order.setDeliveryTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    //除了状态的不一致之外,还有另外很大不同的一点是 由于这个接口有可能是管理员调用也可能是用户调用,所以我们在这里额外进行一层判断
    @Override
    public void finish(String orderNo) {
        //查不到订单,报错
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ORDER);
        }
        //如果是普通用户,就要校验订单的所属(普通用户登录进来,不能修改别人的订单) [订单中的用户id等不等于当前登录用户的id] &&两真为真,一假为假
        //巧妙的判断,导致普通用户只能修改自己的订单,而管理员则没有这个限制
        if (!userService.checkAdminRole(UserFilter.currentUser) &&
                !order.getUserId().equals(UserFilter.currentUser.getId())){
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_YOUR_ORDER);
        }
        //发货后可以完结订单
        if (order.getOrderStatus() == Constant.OrderStatusEnum.DELIVERED.getCode()) {
            //设置为已完结
            order.setOrderStatus(Constant.OrderStatusEnum.FINISHED.getCode());
            order.setEndTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }
}

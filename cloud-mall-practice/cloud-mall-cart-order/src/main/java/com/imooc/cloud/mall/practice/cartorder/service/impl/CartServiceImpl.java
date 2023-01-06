package com.imooc.cloud.mall.practice.cartorder.service.impl;

import com.imooc.cloud.mall.practice.cartorder.feign.ProductFeignClient;
import com.imooc.cloud.mall.practice.cartorder.model.dao.CartMapper;
import com.imooc.cloud.mall.practice.cartorder.model.pojo.Cart;
import com.imooc.cloud.mall.practice.cartorder.model.vo.CartVO;
import com.imooc.cloud.mall.practice.cartorder.service.CartService;
import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.wei.mall.practice.common.common.Constant;
import com.wei.mall.practice.common.exception.ImoocMallException;
import com.wei.mall.practice.common.exception.ImoocMallExceptionEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车Service实现类
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    @Resource
//    ProductMapper productMapper;
    ProductFeignClient productFeignClient;

    @Resource
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId) {
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        //CartVO中totalPrice这个字段需要自己手动设置
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            //总价 = 价格 * 数量
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    //添加商品到购物车
    //List<> 里面的对象应该是什么样的?  第一要包含商品的一些信息,比如商品的id、图片、名字、价格等,因为返回的是一个购物车列表。
    // 第二这些价格、数量、是否选中、用户信息也是需要包含的。所以现在暂时没有一个现成的对象能包含这些信息,所以需要新建一个VO类
    //这个VO就是返回给前端的,经过组装之后的对象  CartVO
    //为什么这里要返回一个List? 与其要前端再调一次接口获取最新的list数据,不如我们直接就返回最新的购物车list,可以减少延迟,提高性能的
    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        //[say] 一个良好的程序员,他第一步考虑的可能不是整体的业务逻辑,而是那些兜底的异常方案,编程经验增长,这一点会越来越明显
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProduct(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里,需要新增一个记录(添加到购物车)
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            //这个商品已经在购物车里了,则数量相加
            count = (cart.getQuantity() + count);
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED); //用户加选到购物车说明用户有一定的购买欲望,这里设置购物车记录为选中状态(不管之前状态是否选中)
            int i = cartMapper.updateByPrimaryKeySelective(cartNew);

            //2022年10月11日19:30:30 商品已经在购物车的情况中,更新不此成功 (比如在原来基础上加数量)
            System.out.println("=================  " + i);
//            if (i == 0) {
//                throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
//            }

        }
        return this.list(userId);
    }

    //这个方法最主要的作用是验证这一次添加(添加商品到购物车)是不是合法的  怎么验证? 第一步需要先把商品查出来 
    private void validProduct(Integer productId, Integer count) {
        Product product = productFeignClient.detailForFeign(productId);
        //判断商品是否存在,商品是否上架,,,
        //如果只是product.getStatus().equals(1)) 这个1 在这里代表的含义不够明确,可能我们知道1是上架,0是下架。所以用Constant类新建一个枚举常量,一目了然
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
        }
        //判断商品库存
        if (count > product.getStock()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
    }

    //更新购物车列表和添加商品到购物车区别不大
    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count) {
        //[say] 一个良好的程序员,他第一步考虑的可能不是整体的业务逻辑,而是那些兜底的异常方案,编程经验增长,这一点会越来越明显
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProduct(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里,无法更新 (更新接口,更新的是已有的购物车,)
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        } else {
            //这个商品已经在购物车里了,则更新数量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED); //用户加选到购物车说明用户有一定的购买欲望,这里设置购物车记录为选中状态(不管之前状态是否选中)
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId) {
        Cart cart = cartMapper.selectCartByUserIdAndProduct(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里,无法删除
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        } else {
            //这个商品已经在购物车里了,则可以删除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    //全选和选中一个是有很大的相似点的。(一个可以复用的逻辑) 即当只传用户id不传productId的时候代表选中全部购物车商品,然后再更新状态
    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected) {
        Cart cart = cartMapper.selectCartByUserIdAndProduct(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里,无法更新选中状态
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        } else {
            //这个商品已经在购物车里了,则可以选中/不选中
            cartMapper.selectOrNot(userId, productId, selected);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectAll(Integer userId, Integer selected) {
        //没有判空处理?
        //改变选中状态
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);
    }
}

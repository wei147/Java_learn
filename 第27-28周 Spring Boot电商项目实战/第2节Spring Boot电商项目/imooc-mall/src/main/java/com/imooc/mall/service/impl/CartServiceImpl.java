package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Cart;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车Service实现类
 */
@Service("cartService")
public class CartServiceImpl implements CartService {
    
    @Resource
    ProductMapper productMapper;

    @Resource
    CartMapper cartMapper;
    
    //添加商品到购物车
    //List<> 里面的对象应该是什么样的?  第一要包含商品的一些信息,比如商品的id、图片、名字、价格等,因为返回的是一个购物车列表。
    // 第二这些价格、数量、是否选中、用户信息也是需要包含的。所以现在暂时没有一个现成的对象能包含这些信息,所以需要新建一个VO类
    //这个VO就是返回给前端的,经过组装之后的对象  CartVO
    //为什么这里要返回一个List? 与其要前端再调一次接口获取最新的list数据,不如我们直接就返回最新的购物车list,可以减少延迟,提高性能的
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        //[say] 一个良好的程序员,他第一步考虑的可能不是整体的业务逻辑,而是那些兜底的异常方案,编程经验增长,这一点会越来越明显
        validProduct(productId,count);

        //不在购物车的情况
        cartMapper.selectByProductId();
        if ()
    }

    //这个方法最主要的作用是验证这一次添加(添加商品到购物车)是不是合法的  怎么验证? 第一步需要先把商品查出来 
    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
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
}

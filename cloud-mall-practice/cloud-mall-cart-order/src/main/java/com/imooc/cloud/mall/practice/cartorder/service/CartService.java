package com.imooc.cloud.mall.practice.cartorder.service;

import com.imooc.mall.model.vo.CartVO;

import java.util.List;

/**
 * 购物车Service
 */
public interface CartService {


    List<CartVO> list(Integer userId);

    //添加商品到购物车
    //List<> 里面的对象应该是什么样的?  第一要包含商品的一些信息,比如商品的id、图片、名字、价格等,因为返回的是一个购物车列表。
    // 第二这些价格、数量、是否选中、用户信息也是需要包含的。所以现在暂时没有一个现成的对象能包含这些信息,所以需要新建一个VO类
    //这个VO就是返回给前端的,经过组装之后的对象  CartVO
    //为什么这里要返回一个List? 与其要前端再调一次接口获取最新的list数据,不如我们直接就返回最新的购物车list,可以减少延迟,提高性能的
    List<CartVO> add(Integer userId, Integer productId, Integer count);


    List<CartVO> update(Integer userId, Integer productId, Integer count);


    List<CartVO> delete(Integer userId, Integer productId);

    //全选和选中一个是有很大的相似点的。(一个可以复用的逻辑) 即当只传用户id不传productId的时候代表选中全部购物车商品,然后再更新状态
    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected);

    List<CartVO> selectAll(Integer userId, Integer selected);
}

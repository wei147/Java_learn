package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.OrderMapper;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import com.imooc.mall.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单Service实现类
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    CartService cartService;

    //返回的是一个orderNum 订单编号
    public String create(CreateOrderReq createOrderReq){

        //拿到用户ID
        Integer userId = UserFilter.currentUser.getId();
        //从购物车查找已经勾选的商品
        List<CartVO> cartVOList = cartService.list(userId);
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            if (cartVO.getSelected()== Constant.Cart.CHECKED){

            }
        }

        //如果购物车已勾选的为空,报错

        //判断商品是否存在、上下架状态、库存
        //把购物车对象转为订单item对象

        //扣库存

        //把购物车中的已勾选商品删除

        //生成订单

        //生成订单号,有独立的规则

        //循环保存每个商品到order_item表
        //把结果返回

    }

}

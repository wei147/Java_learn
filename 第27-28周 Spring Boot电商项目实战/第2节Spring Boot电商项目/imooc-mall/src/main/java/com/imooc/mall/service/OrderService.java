package com.imooc.mall.service;

import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.OrderVO;

/**
 * 订单Service
 */
public interface OrderService {

    //返回的是一个orderNum 订单编号
    String create(CreateOrderReq createOrderReq);

    //新增OrderVO类和OrderItemVO类
    OrderVO detail(String orderNo);
}

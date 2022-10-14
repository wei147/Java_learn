package com.imooc.mall.service;

import com.imooc.mall.model.request.CreateOrderReq;

/**
 * 订单Service
 */
public interface OrderService {

    //返回的是一个orderNum 订单编号
    String create(CreateOrderReq createOrderReq);
}

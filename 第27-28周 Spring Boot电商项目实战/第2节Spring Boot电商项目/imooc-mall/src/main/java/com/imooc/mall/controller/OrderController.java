package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 订单Controller
 */
@RestController
public class OrderController {

    @Resource
    OrderService orderService;

    //因为传入的参数比较复杂,所以用一个请求类来包装,
    @PostMapping("order/create")
    public ApiRestResponse create(@Valid @RequestBody CreateOrderReq createOrderReq) {
        return null;
    }

}

package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.OrderVO;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiOperation("创建订单")
    @PostMapping("order/create")
    public ApiRestResponse create(@Valid @RequestBody CreateOrderReq createOrderReq) {
        String orderNo = orderService.create(createOrderReq);
        return ApiRestResponse.success(orderNo);
    }

    @ApiOperation("前台订单详情")
    @PostMapping("order/detail")
    public ApiRestResponse detail(@RequestParam String orderNo) {
        OrderVO orderVO = orderService.detail(orderNo);
        return ApiRestResponse.success(orderVO);

    }
}

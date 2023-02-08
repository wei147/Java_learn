package com.wei.cloud.mall.practice.cartorder.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单后台管理Controller
 * 分别有: 后台订单列表、发货和完结 一共是三个接口
 */
@RestController
public class OrderAdminController {

    @Resource
    OrderService orderService;

    @ApiOperation("后台管理员订单列表")
    @GetMapping("admin/order/list")
    public ApiRestResponse list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("支付接口")
    @GetMapping("pay")
    public ApiRestResponse pay(@RequestParam String orderNo) {
        orderService.pay(orderNo);
        return ApiRestResponse.success();
    }

    /**
     *发货。订单状态流程: 0用户已取消,10未付款,20已付款,40交易完成
     */
    @ApiOperation("管理员发货")
    @GetMapping("admin/order/delivered")
    public ApiRestResponse delivered(@RequestParam String orderNo) {
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }


    /**
     *完结订单。订单状态流程: 0用户已取消,10未付款,20已付款,40交易完成
     * 管理员和普通用户都可以调用
     */
    @ApiOperation("完结订单接口")
    @GetMapping("order/finish")
    public ApiRestResponse finish(@RequestParam String orderNo) {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
}

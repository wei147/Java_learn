package com.wei.cloud.mall.practice.cartorder.controller;

import com.github.pagehelper.PageInfo;
import com.wei.cloud.mall.practice.cartorder.service.OrderService;
import com.wei.mall.practice.common.common.ApiRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

//    这个接口普通用户也可以操作。所以不该放这里
//    @ApiOperation("支付接口")
//    @GetMapping("pay")
//    public ApiRestResponse pay(@RequestParam String orderNo) {
//        orderService.pay(orderNo);
//        return ApiRestResponse.success();
//    }

    /**
     *发货。订单状态流程: 0用户已取消,10未付款,20已付款,40交易完成
     */
    @ApiOperation("管理员发货")
    @PostMapping("admin/order/delivered")
    public ApiRestResponse delivered(@RequestParam String orderNo) {
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }


    /**
     *完结订单。订单状态流程: 0用户已取消,10未付款,20已付款,40交易完成
     * 管理员和普通用户都可以调用
     */
    @ApiOperation("完结订单接口")
    @PostMapping("order/finish")
    public ApiRestResponse finish(@RequestParam String orderNo) {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
}

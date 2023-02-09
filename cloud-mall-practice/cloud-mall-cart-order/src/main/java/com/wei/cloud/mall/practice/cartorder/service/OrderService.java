package com.wei.cloud.mall.practice.cartorder.service;

import com.github.pagehelper.PageInfo;
import com.wei.cloud.mall.practice.cartorder.model.request.CreateOrderReq;
import com.wei.cloud.mall.practice.cartorder.model.vo.OrderVO;

/**
 * 订单Service
 */
public interface OrderService {

    //返回的是一个orderNum 订单编号
    String create(CreateOrderReq createOrderReq);

    //新增OrderVO类和OrderItemVO类
    OrderVO detail(String orderNo);

    //订单列表前台的和后台的是不太一样的。给前台的只能查询自己的订单并且对里面的内容进行裁剪,可是给管理员看的就没有这么多限制了
    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    //返回值是String,是生成的二维码图片地址
    String qrcode(String orderNo);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    void pay(String orderNo);

    //发货这个方法所做最主要的事情就是 改变订单的状态
    void deliver(String orderNo);

    //除了状态的不一致之外,还有另外很大不同的一点是 由于这个接口有可能是管理员调用也可能是用户调用,所以我们在这里额外进行一层判断,,
    void finish(String orderNo);
}

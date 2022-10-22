package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
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

    //订单列表前台的和后台的是不太一样的。给前台的只能查询自己的订单并且对里面的内容进行裁剪,可是给管理员看的就没有这么多限制了
    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);
}

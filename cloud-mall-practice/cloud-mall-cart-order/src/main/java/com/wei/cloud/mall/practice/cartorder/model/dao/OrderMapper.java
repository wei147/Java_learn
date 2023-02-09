package com.wei.cloud.mall.practice.cartorder.model.dao;


import com.wei.cloud.mall.practice.cartorder.model.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderNo(String orderNo);

    List<Order> listForCustomer(Integer userId);

    //给管理员把所有的订单都查询出来
    List<Order> selectAllForAdmin();
}
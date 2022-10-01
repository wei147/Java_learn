package com.wei.oa_spring.model.dao;

import com.wei.oa_spring.model.pojo.Node;

public interface NodeMapper {
    int deleteByPrimaryKey(Long nodeId);

    int insert(Node record);

    int insertSelective(Node record);

    Node selectByPrimaryKey(Long nodeId);

    int updateByPrimaryKeySelective(Node record);

    int updateByPrimaryKey(Node record);
}
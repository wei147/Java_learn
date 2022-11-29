package com.wei.oa_spring.service;

import com.wei.oa_spring.model.pojo.Node;

import java.util.List;

public interface NodeService {

    List<Node> selectNodeByUserId(Long userId);
}

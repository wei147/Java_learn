package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.model.dao.NodeMapper;
import com.wei.oa_spring.model.pojo.Node;
import com.wei.oa_spring.service.NodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("nodeService")
public class NodeServiceImpl implements NodeService {

    @Resource
    NodeMapper nodeMapper;

    @Override
    public List<Node> selectNodeByUserId(Long userId) {
        List<Node> nodeList = nodeMapper.selectNodeByUserId(userId);
        return nodeList;

    }
}

package com.imooc.oa.dao;

import com.imooc.oa.entity.Node;
import com.imooc.oa.utils.MybatisUtils;

import java.util.List;

public class RbacDao {
    //   每一个查询结果都是一个Node节点 为什么用list，因为返回多个数据所以用list进行接收
    public List<Node> selectNodeByUserId(Long userId) {
        return  (List) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectList("rbacmapper.selectNodeByUserId", userId));

    }
}

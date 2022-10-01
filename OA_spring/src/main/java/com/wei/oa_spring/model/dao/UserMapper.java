package com.wei.oa_spring.model.dao;

import com.wei.oa_spring.model.pojo.Node;
import com.wei.oa_spring.model.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //对于MyBatis而言,如果入参只有一个的话可以不用param注解的。如果有多个入参,需要给明确的指示
    User selectLogin(@Param("username") String username, @Param("password") String password);

    List<Node> selectNodeByUserId(Long userId);
}
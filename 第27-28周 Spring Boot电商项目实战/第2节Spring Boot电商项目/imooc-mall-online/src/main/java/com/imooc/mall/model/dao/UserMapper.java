package com.imooc.mall.model.dao;

import com.imooc.mall.model.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByName(String username);

    //对于MyBatis而言,如果入参只有一个的话可以不用param注解的。如果有多个入参,需要给明确的指示
    User selectLogin(@Param("username") String username, @Param("password") String password);
}
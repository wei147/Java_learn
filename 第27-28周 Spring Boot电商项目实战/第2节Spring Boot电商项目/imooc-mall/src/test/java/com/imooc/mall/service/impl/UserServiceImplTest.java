package com.imooc.mall.service.impl;

import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserServiceImplTest {
    @Resource
    UserMapper userMapper;

    @Test
    void getUser() {
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user.getPersonalizedSignature());
    }
}
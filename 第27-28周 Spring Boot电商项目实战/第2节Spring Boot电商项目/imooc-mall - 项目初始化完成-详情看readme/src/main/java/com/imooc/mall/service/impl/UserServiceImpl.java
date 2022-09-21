package com.imooc.mall.service.impl;

import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserService 实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public User getUser() {
        User user = userMapper.selectByPrimaryKey(1);
        return user;
    }
}

package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.exception.OAException;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.dao.UserMapper;
import com.wei.oa_spring.model.pojo.Node;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.service.UserService;
import com.wei.oa_spring.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public User selectById() {
        return userMapper.selectByPrimaryKey(1L);
    }

    @Override
    public User login(String username, String password) {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMd5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            throw new OAException(OAExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public List<Node> selectNodeByUserId(Long userId){
        List<Node> nodeList =userMapper.selectNodeByUserId(userId);
        return nodeList;

    }
}

package com.wei.cloud.mall.practice.user.service.impl;


import com.wei.cloud.mall.practice.user.model.dao.UserMapper;
import com.wei.cloud.mall.practice.user.model.pojo.User;
import com.wei.cloud.mall.practice.user.service.UserService;
import com.wei.mall.practice.common.exception.ImoocMallException;
import com.wei.mall.practice.common.exception.ImoocMallExceptionEnum;
import com.wei.mall.practice.common.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

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

    @Override
    public void register(String username, String password) throws ImoocMallException {
        //查询用户名是否存在,不允许重名
        User result = userMapper.selectByName(username);
        if (result != null) {
            //由于每一层的分工是非常明确的,,不应该在Service去触碰那些和最终返回相关的内
            // 容,那些是Controller层的职责。所以我们在此用一个巧妙的方法———抛出异常的方法来解决这个问题
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //写到数据库
        User user = new User();
        user.setUsername(username);
        try {
            //对密码进行加密
            user.setPassword(MD5Utils.getMd5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //user.setPassword(password);

        int count = userMapper.insertSelective(user);//选择性插入
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public User login(String username, String password) throws ImoocMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMd5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public void updateUserInformation(User user) throws ImoocMallException {
        //更新个性签名
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount>1){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        //1是普通用户  2是管理员
        return user.getRole().equals(2);
    }
}

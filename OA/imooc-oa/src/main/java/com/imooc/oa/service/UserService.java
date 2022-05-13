package com.imooc.oa.service;

import com.imooc.oa.dao.RbacDao;
import com.imooc.oa.dao.UserDao;
import com.imooc.oa.entity.Node;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.exception.BusinessException;
import com.imooc.oa.utils.MD5Utils;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();
    private RbacDao rbacDao = new RbacDao();

    // 检查查询结果
    public User checkLogin(String username, String password) {
        //按用户名查询用户
        User user = userDao.selectByUsername(username);
        if (user == null) {
            //抛出用户不存在异常     （抛出异常会中断程序）
            throw new BusinessException("L001", "用户名不存在");
        }
        //对前台输入的密码加盐混淆后生成MD5，与保存在数据库中的MD5密码进行比对
        String password_md5 = MD5Utils.md5Digest(password, user.getSalt());
        if (!password_md5.equals(user.getPassword())) {
            throw new BusinessException("L002", "密码错误");
        }
        return user;
    }

    public List<Node> selectNodeByUserId(Long userId){
         List<Node> nodeList = rbacDao.selectNodeByUserId(userId);
         return nodeList;

    }
}

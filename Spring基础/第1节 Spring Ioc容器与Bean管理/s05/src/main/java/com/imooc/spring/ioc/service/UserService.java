package com.imooc.spring.ioc.service;

import com.imooc.spring.ioc.dao.UserDao;

public class UserService {
    private UserDao userDao;

    //创建默认的构造方法
    public UserService(){
        System.out.println("UserService已创建"+this);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        System.out.println("调用setUserDao"+userDao);
        this.userDao = userDao;
    }
}

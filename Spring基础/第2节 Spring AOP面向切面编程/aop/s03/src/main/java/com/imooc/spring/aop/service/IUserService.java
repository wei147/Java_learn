package com.imooc.spring.aop.service;

import com.imooc.spring.aop.dao.UserDao;

public interface IUserService {
    public void createUser();

    public String generateRandomPassword(String type, Integer length);

//    public UserDao getUserDao();
//    public void setUserDao(UserDao userDao);
}

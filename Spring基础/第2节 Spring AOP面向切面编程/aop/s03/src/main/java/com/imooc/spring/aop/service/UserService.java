package com.imooc.spring.aop.service;

import com.imooc.spring.aop.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务
 */
@Service
public class UserService {
    @Resource   //对属性进行注入 有两种方法：1.直接放在属性上(推荐) 2.如果有set方法，也直接放在set方法上
    private UserDao userDao;

    public void createUser(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行员工入职业务逻辑");
        userDao.insert();
    }

    public String generateRandomPassword(String type , Integer length){
        System.out.println("按" + type + "方式生成"+ length  + "位随机密码");
        return "Zxcquei1";
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

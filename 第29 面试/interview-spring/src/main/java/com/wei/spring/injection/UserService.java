package com.wei.spring.injection;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//@Service("userService")  //这里不写也行,默认就是userService
public class UserService {
//    @Resource  //优先按照beanId来加载对象
    UserDAO userDAO;

    public UserService() {
        System.out.println(this + "已创建");
    }

    //通过构造函数注入  (较为少用)
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        System.out.println(this + "正在调用构造函数注入,UserService(" + userDAO + ")");
    }

    //通过Setter方法注入
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
        System.out.println(this + "正在调用Setter方法注入,setUserDao" + userDAO + ")");
    }

    //业务方法
    public void createUser() {
        System.out.println(this + "正在调用UserService.createUser()方法");
        userDAO.insert();
    }
}

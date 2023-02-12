package com.wei.spring.injection;

import javax.annotation.Resource;

public class UserService {
//    @Resource
//    UserDAO userDAO;
    private UserDAO userDAO;

    public UserService(){
        System.out.println(this+"已创建");
    }
    //通过构造函数注入
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
        System.out.println(this+"正在调用构造函数注入,UserService");
    }

    //通过Setter方法注入
    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
        System.out.println(this+"正在调用Setter方法注入,setUserDao");
    }

    //业务方法
    public void createUser(){
        this.userDAO = userDAO;
        System.out.println(this+"正在调用UserService.createUser()方法");
        userDAO.insert();
    }
}

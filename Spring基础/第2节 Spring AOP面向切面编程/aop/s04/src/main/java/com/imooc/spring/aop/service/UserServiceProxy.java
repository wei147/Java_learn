package com.imooc.spring.aop.service;

import java.text.SimpleDateFormat;
import java.util.Date;

//静态代理是指必须手动创建代理类的代理模式使用方式

//代理类
//作为其代理类，其核心特点是:持有委托类的对象
public class UserServiceProxy implements UserService{   //作为代理类和委托类都需要实现相同的接口
    //持有委托类的对象
    private UserService userService;
    //重点:构建带参的构造方法
    public UserServiceProxy(UserService userService){   //这里需要传入具体的UserService实现。比如 UserServiceImpl
        //为内部的委托类赋值（相当于持有了委托类的对象）
        this.userService = userService;

    }

    @Override
    public void createUser() {

        System.out.println(" 我是二房东  ====="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date())+"=====");
        //因为持有了委托类的对象，我们可以发起委托类的具体的职责。重点:在方法执行前还可以扩展其他的代码
        userService.createUser();
    }
}

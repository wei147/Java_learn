package com.imooc.spring.aop.service;

public class UserServiceProxy1 implements UserService{
    private UserService userService;

    public UserServiceProxy1(UserService userService){
        this.userService = userService;
    }
    @Override
    public void createUser() {
        System.out.println("我也是租户，他是二房东");
        userService.createUser();
        System.out.println("后置扩展功能");
    }
}

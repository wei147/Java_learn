package com.imooc.spring.ioc.controller;

import com.imooc.spring.ioc.service.UserService;

public class UserController {
    //UserController 依赖于 UserService
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

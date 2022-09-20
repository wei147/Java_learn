package com.imooc.mall.controller;

import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

//用户控制器
@Controller
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/user")
    @ResponseBody
    public User personalPage() {
        return userService.getUser();
    }
}

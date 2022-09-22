package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")   //因为参数是在请求中的,所以需要加上@RequestParam
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String username, @RequestParam("password") String password) throws ImoocMallException {
        //1.校验 userName不能为空
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //2.校验 password不能为空
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASS_WORD);
        }
        //3.校验 密码长度不能少于6位(原先是不能少于8位)
        if (password.length() < 6) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_TOO_SHORT);
        }
        userService.register(username, password);
        return ApiRestResponse.success();

    }
}

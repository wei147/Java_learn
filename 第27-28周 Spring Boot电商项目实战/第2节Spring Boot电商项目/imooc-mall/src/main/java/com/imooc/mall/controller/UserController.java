package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

    @PostMapping("/login")   //因为参数是在请求中的,所以需要加上@RequestParam
    @ResponseBody
    public ApiRestResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws ImoocMallException {
        //登录时所要用的关键任务 HttpSession session对象
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //2.校验 password不能为空
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASS_WORD);
        }
        User user = userService.login(username, password);
        //保存用户信息时,不保存密码(为了安全起见这里的password设置为空,不会返回给用户)
        user.setPassword(null);
        session.setAttribute(Constant.IMOOC_MALL_USER, user);
        return ApiRestResponse.success(user);
    }

    @PostMapping("/user/info")   //因为参数是在请求中的,所以需要加上@RequestParam
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) {
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        //会通过id找到该用户将用户昵称进行更新
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.
    }
}

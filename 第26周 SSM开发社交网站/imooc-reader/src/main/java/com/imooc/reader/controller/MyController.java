package com.imooc.reader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 因为毕业论文登录页想添加一个验证码功能而鼓捣的。比较静态
 */
@Controller
public class MyController {
    @GetMapping("/login1.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/myLoginPage");
    }
}

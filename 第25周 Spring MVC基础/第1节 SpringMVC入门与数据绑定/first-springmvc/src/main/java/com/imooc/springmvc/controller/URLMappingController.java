package com.imooc.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/um")    //RequestMapping通常放在类上面 （这个注解在大多数情况下是用于进行url的全局设置的）/um/g 访问前缀
public class URLMappingController {

    //@GetMapping("/g")
    @RequestMapping(value = "/g", method = RequestMethod.GET)   //用于模拟get方法。（也可以是其他方法）
    //@RequestMapping("/g")   //作用在方法上，不再区分get/post请求
    @ResponseBody
    public String getMapping() {
        return "This is get method";
    }

    @ResponseBody
    @PostMapping("/p")
    public String postMapping(String username, Long password) {
        // 报400错误可能是前端表单校验不够严谨造成的
        System.out.println("用户名: " + username + "   密码: " + password);
        return "This is post method， 登录成功";
    }


}

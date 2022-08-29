package com.imooc.reader.controller;

import com.imooc.reader.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    @GetMapping("/t1")
    public ModelAndView test1(){
        return new ModelAndView("/test");
    }

    @GetMapping("/t2")
    @ResponseBody
    public User test2(){
        User user = new User();
        user.setName("神里绫华");
        user.setAge(20);
        return user;
    }

    @GetMapping("/wei")
    @ResponseBody
    public String wei(){
        return "I am here ,小美";
    }
}

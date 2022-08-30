package com.imooc.reader.test;

import com.imooc.reader.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyTestController {
    @GetMapping("/t")
    @ResponseBody
    public String test(){
        return "[I am 17]";
    }

    @ResponseBody
    @GetMapping("/user")
    public User jsonTest(){
        User user = new User();
        user.setName("神里绫华");
        user.setAge(20);

        return user;
    }

    @GetMapping("/t1")
    public ModelAndView ftlTest(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/test");
        return mav;
    }
}

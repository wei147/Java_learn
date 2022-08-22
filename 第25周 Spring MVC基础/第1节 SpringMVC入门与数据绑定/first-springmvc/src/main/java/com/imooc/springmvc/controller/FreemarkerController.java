package com.imooc.springmvc.controller;

import com.imooc.springmvc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/fm")
public class FreemarkerController {
    @GetMapping("/test")
    public ModelAndView showView() {
        ModelAndView mav = new ModelAndView();
        //不再需要写 /test.ftl 直接写/test  会在WEB-INF/ftl中查找对应的文件
        mav.setViewName("/test");
        User user = new User();
        user.setUsername("陈小龙");
        mav.addObject("u",user);

        return mav;
    }
}

package com.imooc.reader.controller;

import com.imooc.reader.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyController {
    @GetMapping("/t")
    public String test(){
        return "Success ! 嗨嗨害";
    }

}

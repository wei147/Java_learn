package com.imooc.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//处理http的请求并且返回响应
@Controller
public class TestController {
    @GetMapping("/t")    //@GetMapping() 将当前这个方法绑定某个get方式请求的url   localhost/t
    @ResponseBody           //直接向响应输出字符串数据（提供数据），不跳转页面
    public String test(){
        return "Success ! 嗨嗨害";
    }

}

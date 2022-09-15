package com.imooc.springbootlearnnew;

import org.springframework.web.bind.annotation.*;

/**
 * 描述: 演示各种传参形式
 */
@RestController //RestController含义包含两层:1.是一个普通的Controller 2.有Restful的能力
@RequestMapping("/student")
public class ParaController {
    @GetMapping("/first")
    public String firstRequest() {
        return "第一个Spring Boot接口";
    }

    @GetMapping("/first_new")   //请求参数 http://localhost:8080/first_new/?num=9
    public String requestPara(@RequestParam Integer num) {
        return "param from request" + num;
    }

    @GetMapping("/path/{num}")   //路径变量 http://localhost:8080/path/9
    public String requestPath(@PathVariable Integer num) {
        return "param from request : " + num;
    }

    //一个接口希望是想有多个地址(多个地址想访问同一个接口)
    @GetMapping({"/url1", "/url2"})
    public String multiUrl(@RequestParam Integer num) {
        return "多url的写法  param from request : " + num;
    }

    //如果用户没有传参则使用默认值 (不是必须要传参)
    @GetMapping("/require")   // http://localhost:8080/require
    public String require(@RequestParam(required = false,defaultValue = "99") Integer num) {
        return "param from request" + num;
    }

}

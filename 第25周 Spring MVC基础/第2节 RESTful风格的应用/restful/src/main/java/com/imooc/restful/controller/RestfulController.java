package com.imooc.restful.controller;

import com.imooc.restful.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//Restful风格的程序

//@Controller
@RestController //默认当前返回的都是rest形式的数据，而不是页面的跳转。加了这个就不用加 @ResponseBody
@RequestMapping("/restful")
public class RestfulController {
    @GetMapping("/request")
//    @ResponseBody       //如果不写这个注解则代表这是进行页面的跳转。加了则代表直接向客户端输出结果
    public String doGetRequest(Person person) {
        System.out.println(person.getName()+person.getAge());
        return "{\"message\":\"返回查询结果\"}";
    }

    // POST /article/1  创建一个id值为1的文章  1是灵活的是变化的，可以是2,3
    // 这种放在uri的变量就称为 路径变量
    // POST /request/restful/100 在服务器端怎么接收到这个100? 它并不是请求的参数，它是uri中的一部分。好在SpringMVC为我们提供了路径变量的支持
    // 可以匹配到rid的数值会被自动注入到 requestId中
    @PostMapping("/request/{rid}")
//    @ResponseBody
    public String doPostRequest(@PathVariable("rid") Integer requestId,Person person){
        System.out.println("name : "+person.getName()+"age : "+person.getAge());
        return "{\"message\":\"数据新建成功\",\"id\":"+ requestId +"}";
    }

    @PutMapping("/request")
//    @ResponseBody
    public String doPutRequest(Person person){
        System.out.println(person.getName()+person.getAge());
        return "{\"message\":\"数据更新成功\"}";
    }

    @DeleteMapping("/request")
//    @ResponseBody
    public String doDeleteRequest(){
        return "{\"message\":\"数据删除成功\"}";
    }
}

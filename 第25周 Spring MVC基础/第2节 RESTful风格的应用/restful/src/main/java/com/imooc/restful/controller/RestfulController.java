package com.imooc.restful.controller;

import com.imooc.restful.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Restful风格的程序

//@Controller
@RestController //默认当前返回的都是rest形式的数据，而不是页面的跳转。加了这个就不用加 @ResponseBody
@RequestMapping("/restful")
//设置跨域的范围，也就是说明哪些其他域名下送过来的请求是容许被访问的？
//@CrossOrigin(origins = {"http://localhost:8089","http://www.imooc.com"})
//容许所有端口、所有域名以及http和https访问。maxAge是设置预检请求的处理结果缓存时间为3600秒。非简单请求都会先发送预检请求
//@CrossOrigin(origins = "*",maxAge = 3600)
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

    /**
     * 在SpringMVC要对对象进行json序列化是多么简单————只需要在返回之中把需要的对象就可以了
     * 那么多个对象如何返回？ 不是单个对象 答：见下面的findPersons方法
     * @param id
     * @return
     */
    @GetMapping("/person")
    public Person findByPersonId(Integer id){
        Person p = new Person();
        if (id == 1){
            p.setName("神里绫华");
            p.setAge(21);
        }else if (id == 2){
            p.setName("胡桃");
            p.setAge(22);
        }
        return p;   //访问http://localhost:8088/restful/person?id=1 显示 {"name":"神里绫华","age":21}
    }

    @GetMapping("/persons")
    public List<Person> findPersons(){
        List list = new ArrayList();
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();

        p1.setName("枫原万叶");
        p2.setName("夜兰");
        p3.setName("班尼特");

        p1.setAge(22);
        p2.setAge(25);
        p3.setAge(20);

//        json有一个坑: 在时间处理上，json做的不是那么理想
        p1.setBirthday(new Date()); //前端得到的是一个时间戳
        p2.setBirthday(new Date());
        p3.setBirthday(new Date());

        list.add(p1);
        list.add(p2);
        list.add(p3);

        return list;    //返回的是一个数组，然后数组里面是一个个json
    }
}

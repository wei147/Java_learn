package com.imooc.springmvc.controller;

import com.imooc.springmvc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/um")    //RequestMapping通常放在类上面 （这个注解在大多数情况下是用于进行url的全局设置的）/um/g 访问前缀
public class URLMappingController {

    //@GetMapping("/g")
    @RequestMapping(value = "/g", method = RequestMethod.GET)   //用于模拟get方法。（也可以是其他方法）
    //@RequestMapping("/g")   //作用在方法上，不再区分get/post请求
    @ResponseBody
    //@RequestParam专用于这种特殊的参数来进行描述,后面跟进行映射的原始参数。请求中的manager_name这个参数在运行时会被动态的注入到managerName参数中
    public String getMapping(@RequestParam("manager_name") String managerName) {
        System.out.println(managerName);
        return "This is get method";
    }

    @ResponseBody
    @PostMapping("/p")
    public String postMapping(String username, Long password) {
        // 报400错误可能是前端表单校验不够严谨造成的
        System.out.println("用户名: " + username + "   密码: " + password);
        return "This is post method， 登录成功";
    }

    // 在程序运行时，spring mvc就会自动的创建User这个对象，并且根据前面的请求结合实体类中的参数名来进行一一的自动赋值以及类型转换的工作 （注：实体类属性名和前台传入的参数名字保持一致）
    @ResponseBody
    @PostMapping("/p1")
    public String postMapping1(User user,String username){      //String username 一样会被赋值
        System.out.println(user);
        System.out.println(user.getUsername() + "  "+user.getPassword());
        return "ok ,it's good. This is post method";
    }
}

package com.imooc.springmvc.controller;

import com.imooc.springmvc.entity.Form;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

//自己搞的
@Controller
public class FormController {
    //数组进行接收
//    @PostMapping("/apply")
    @ResponseBody
    public String apply(@RequestParam(value = "n",defaultValue = "ANON") String name, String course, Integer[] purpose){
        //name属性有可能是匿名提交的，对于请求中不包括这个参数的话，则使用默认值进行替代 @RequestParam(value = "n",defaultValue = "ANON")
        System.out.println(name +"  "+course);
        for(Integer p:purpose){
            System.out.println(p);
        }
        return "success";
    }

    //建议使用这种，List         //List进行接收
//    @PostMapping("/apply")
    @ResponseBody
    public String apply(String name, String course, @RequestParam List<Integer> purpose){   //这里都是单独接收的，但理应存为一组数据
        //通过list来接收前端发来的其他复合数据,需要增加@RequestParam注解  （注：作为list它实际的载体是ArrayList。debug可以看到）
        System.out.println(name +"  "+course);
        for(Integer p:purpose){
            System.out.println(p);
        }
        return "success";
    }

    //对象的方式进行接收 (通过实体类接收复合数据)
//    @PostMapping("/apply")
    @ResponseBody
    public String apply(Form form){   //这里都是单独接收的，但理应存为一组数据
        return "success";
    }

    //用Map进行接收      （键值对的形式）复合数据不要用Map，否则数据会丢失
    @PostMapping("/apply")
    @ResponseBody
    public String apply(@RequestParam Map map){
        return "success";
    }
}

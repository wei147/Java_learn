package com.imooc.springmvc.controller;

import com.imooc.springmvc.entity.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/um")    //RequestMapping通常放在类上面 （这个注解在大多数情况下是用于进行url的全局设置的）/um/g 访问前缀
public class URLMappingController {

    @GetMapping("/g")
//    @RequestMapping(value = "/g", method = RequestMethod.GET)   //用于模拟get方法。（也可以是其他方法）
    //@RequestMapping("/g")   //作用在方法上，不再区分get/post请求
    @ResponseBody
    //@RequestParam专用于这种特殊的参数来进行描述,后面跟进行映射的原始参数。请求中的manager_name这个参数在运行时会被动态的注入到managerName参数中
    public String getMapping(@RequestParam("manager_name") String managerName, Date createTime) {
        System.out.println("managerName:  " + managerName);
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
    public String postMapping1(User user, String username, @DateTimeFormat(pattern = "yyyy-MM-dd") Date createTime) {      //String username 一样会被赋值
        //直接写Date createTime 会报错：无法将“java.lang.String”类型的值转换为所需类型“java.util.Date”
        // @DateTimeFormat 专用于按照指定的格式将前台输入的字符串转换为对应的Date对象（必须指定格式是什么）1999-09-09 和输入的格式一致
        System.out.println(user);
        System.out.println("createTime:   " + createTime);
        System.out.println(user.getUsername() + "  " + user.getPassword());
        return "<h2>ok ,it's good. This is post method 译文：这是一个post请求</h2>";
    }

    //有效的完成了数据之间的解耦  ModelAndView将页面和数据进行绑定
    @GetMapping("/view")
    public ModelAndView showView(Integer userId) { //返回值 必须是ModelAndView
        //作为ModelAndView 在默认进行跳转的时候，使用的是 请求转发
//        ModelAndView mav = new ModelAndView("redirect:/view.jsp"); //这里的 /view.jsp 这个文件要对应的在webapp根路径下
        ModelAndView mav = new ModelAndView();
        mav.setViewName("view.jsp");//设置要访问哪个页面  不加/的情况下，使用的是相对路径：相当于 /um/view.jsp 注：建议用加/的绝对路径
        User user = new User();
        if (userId == 1) {
            user.setUsername("王小虎");
        } else if (userId == 2) {
            user.setUsername("陈二虎");    //http://localhost:8081/um/view?userId=2
        }
        //如何将视图和数据绑定 addObject 在当前请求中增加一个对象，这个对象的别名可以自定义  http://localhost:8081/um/view?userId=2 动态的进行显示
        mav.addObject("u", user);   //为视图赋予数据 把user对象放入u中
        return mav;
    }

    //  用String 和 ModelMap这两个对象实现与相类似 ModelAndView的功能   modelMap对应了模型数据
    // 工作中十分常见的书写办法
    /**
     *Controller方法返回String的情况
     * 1.方法被@ResponseBody描述，SpringMVC直接响应String字符串本身
     * 2.方法不存在@ResponseBody，则SpringMVC处理String指代的视图（页面）
     */
    @GetMapping("/x")       //只加这个访问http://localhost:8081/um/x?userId=1 显示的是html页面
    //    @ResponseBody     加上这个，访问http://localhost:8081/um/x?userId=1 显示的是 纯文本 /view.jsp
    //  作为 ModelMap这个对象不是必须的，可以去掉，在未来工作中会看到没有ModelMap对象的，
    public String showView1(Integer userId, ModelMap modelMap){
        String view = "/view.jsp";
        User user = new User();
        if (userId == 1) {
            user.setUsername("王小虎");
        } else if (userId == 2) {
            user.setUsername("陈二虎");
        }
        //为视图赋予数据 addAttribute 和mav.addObject功能完全相同 就是为我们指定的view来提供数据的
        modelMap.addAttribute("u",user);
        return view;
    }
}

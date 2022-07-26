package com.imooc.spring.ioc;

import com.imooc.spring.ioc.controller.UserController;
import com.imooc.spring.ioc.dao.UserDao;
import com.imooc.spring.ioc.service.UserService;
import org.springframework.context.annotation.*;

/**
 * 使用Config类作为XML文件的替代者
 */
@Configuration  //当前类是一个配置类，用于代替application.xml
@ComponentScan(basePackages = "com.imooc")
public class Config {
    //问题 : 如何在ioc容器中放入bean？     注：下面的代码，不要把它看成是工程的一部分，当成是配置文件
    @Bean   //Java Config利用方法创建对象，将方法返回对象放入容器，beanId=方法名
    public UserDao udao(){
        UserDao userDao = new UserDao();
        System.out.println("已创建 "+userDao);
        return userDao;
    }

    @Bean   //Java Config利用方法创建对象，将方法返回对象放入容器，beanId=方法名
    @Primary
    public UserDao userDao1(){
        UserDao userDao = new UserDao();
        System.out.println("已创建 "+userDao);
        return userDao;
    }


    @Bean   //等同xml中<bean id="xxx" class="xxx">的 java表现形式
    //先按name尝试注入，name不存在则按类型注入
    public UserService userService(UserDao userDao){
        UserService userService = new UserService();
        System.out.println("已创建 "+userService);
        userService.setUserDao(userDao);
        System.out.println("调用setUserDao: "+ userDao);
        return userService;
    }

    @Bean
    @Scope("prototype")     //多例模式，只有在需要使用它的时候才会创建这个对象
    public UserController userController(UserService userService){
        UserController userController = new UserController();
        System.out.println("已创建 "+userController);
        userController.setUserService(userService);
        System.out.println("调用setUserService: "+ userService);
        return userController;
    }
}

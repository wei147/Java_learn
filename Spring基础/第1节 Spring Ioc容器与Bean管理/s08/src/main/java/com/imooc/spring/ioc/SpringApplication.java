package com.imooc.spring.ioc;

import com.imooc.spring.ioc.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        //基于Java Config配置Ioc容器的初始化
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);  //基于注解配置的应用程序上下文
        System.out.println("=================================");
        String[] ids = context.getBeanDefinitionNames();
        for (String id : ids) {
            System.out.println(id+" : "+context.getBean(id));
        }
//        UserDao userDao = context.getBean("userDao",UserDao.class);
//        System.out.println(userDao);

    }
}

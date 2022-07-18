package com.imooc.spring.ioc;

import com.imooc.spring.ioc.dao.UserDao;
import com.imooc.spring.ioc.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        /**
         * scope默认是单例模式。单例模式有一个典型特点：对于当前的bean来说是在ioc容器初始化过程中就会将这个对象进行创建
         * prototype 多例模式，prototype创建对象的时机并不在ioc容器初始化的时候，取而代之的是在获取bean的时候  context.getBean("userDao");
         *
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        UserDao userDao1 = context.getBean("userDao",UserDao.class);
//        UserDao userDao2 = context.getBean("userDao",UserDao.class);
//        UserDao userDao3 = context.getBean("userDao",UserDao.class);

        UserService userService = context.getBean("userService",UserService.class);     //实例化了两个对象（面试题）
    }
}

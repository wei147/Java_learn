package com.wei.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPRunner {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:aop.xml");
        UserService userService1 = (UserService)ctx.getBean("userService");
        userService1.createUser();
    }
}

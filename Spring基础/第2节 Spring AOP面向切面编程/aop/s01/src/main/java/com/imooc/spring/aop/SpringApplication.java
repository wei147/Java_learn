package com.imooc.spring.aop;

import com.imooc.spring.aop.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService userService = context.getBean("userService", UserService.class);

        userService.createUser();
        //测试通过getArgs() 方法，在MethodAspect.java切面类中获取传入的参数并打印 （getArgs 获取方法调用时传入的参数）
        userService.generateRandomPassword("hello",8);

    }
}

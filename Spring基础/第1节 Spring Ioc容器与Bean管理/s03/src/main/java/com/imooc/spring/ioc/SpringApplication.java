package com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import com.imooc.spring.ioc.entity.Child;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ApplicationContext.xml");

//        Apple apple = context.getBean("apple1", Apple.class);
//        System.out.println(apple.getTitle());

//        Child lily = context.getBean("lily",Child.class);
//        System.out.println(lily.getApple().getColor());
    }
}

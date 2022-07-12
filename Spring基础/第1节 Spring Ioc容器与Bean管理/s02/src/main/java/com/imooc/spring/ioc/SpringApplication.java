package com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        //初始化Ioc容器并实例化对象
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ApplicationContext.xml");

        //默认构造方法实现
//        Apple apple1 = context.getBean("apple1", Apple.class);
//        System.out.println(apple1.getTitle());

        //通过带参构造方法创建对象
//        Apple apple2 = context.getBean("apple2", Apple.class);
//        System.out.println(apple2.getTitle());
    }

}

package com.imooc.spring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println(context.getBean("userDao"));
        //getBeanDefinitionNames 方法获取容器内所有有效的beanId
        String[] ids = context.getBeanDefinitionNames();
        for (String id : ids){
            System.out.println(id+":"+context.getBean(id));
        }
    }
}

package com.wei.spring.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScopeRunner {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:scope.xml");
        //下面创建的两个对象是同一个对象吗
        /*
            答:对于ioc容器来说,默认情况下不做额外的设置,单例模式singleton,这个UserService对象是同一个对象。
            注释掉下面两行代码也一样。默认初始化的时候只会创建全局唯一的1个对象。
            如果是多例的情况下,prototype,只会在ctx.getBean的时候才会创建对象,初始化的时候并不会创建任何对象
         */
        UserService userService1 = (UserService) ctx.getBean("userService");
        UserService userService2 = (UserService) ctx.getBean("userService");
    }
}

package com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        String[] configLocations = new String[]{
                "classpath:ApplicationContext.xml","classpath:ApplicationContext-1.xml"
        };
        //初始化Ioc容器并实例化对象
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocations);

        //默认构造方法实现
//        Apple apple1 = context.getBean("apple2", Apple.class);
//        System.out.println(apple1.getTitle());
//
//        Apple apple4 = (Apple) context.getBean("apple4");   //强制类型转换
//        System.out.println(apple4.getTitle());

        //通过带参构造方法创建对象
//        Apple apple2 = context.getBean("apple2", Apple.class);
//        System.out.println(apple2.getTitle());

        //加载配置文件多个时，后来的会覆盖先来的（比如同名的id和name）
        Apple apple2 = context.getBean("apple7",Apple.class);
        System.out.println(apple2.getTitle());

        //获取没有标识的bean       （没有id和name的的bean默认使用类名全称作为bean标识）
        Apple appleZero = context.getBean("com.imooc.spring.ioc.entity.Apple",Apple.class);
        System.out.println(appleZero.getTitle());

    }

}

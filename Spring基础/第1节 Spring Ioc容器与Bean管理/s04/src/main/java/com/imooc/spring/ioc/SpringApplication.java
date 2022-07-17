package com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Company;
import com.imooc.spring.ioc.entity.Computer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ApplicationContext.xml");
        Company company = context.getBean("company",Company.class);
//        System.out.println(company);
        String address = company.getInfo().getProperty("address");
//        System.out.println(address);

        //获取容器内所有beanId数组
        String[] beanNames = context.getBeanDefinitionNames();
//        System.out.println(Arrays.toString(beanNames));//Arrays.toString 将数组转成String类型
        for (String beanName:beanNames){
            System.out.println("beanName: "+beanName);  //这里返回的是一个Object对象
            System.out.println("beanName.getClass: "+beanName.getClass());   //得到Object对象的类对象
            System.out.println("类型： "+context.getBean(beanName).getClass().getName()); //得到这个类对象的完整名称
//            System.out.println("内容： "+context.getBean(beanName).toString());   //重点：这里默认会调用ToString() 方法。我们重写了运行时原有
        }

        //要获取一个匿名bean，使用的是类的全称          问题：有两个匿名bean，这里获取的是哪一个？
        Computer computer = context.getBean("com.imooc.spring.ioc.entity.Computer", Computer.class);
//        System.out.println(computer.getBrand());    //这里获取的是第一个匿名的bean 惠普
        //ioc容器匿名提取的规则：不加#序号 默认获取第一个。 "com.imooc.spring.ioc.entity.Computer#1",获取指定的bean

        Computer computer1 = context.getBean("com.imooc.spring.ioc.entity.Computer#1", Computer.class);
//        System.out.println(computer1.getBrand());    //这里指定获取的是第二个匿名的bean 华硕

    }
}

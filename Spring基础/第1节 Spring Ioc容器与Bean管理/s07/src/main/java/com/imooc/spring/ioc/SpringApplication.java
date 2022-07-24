package com.imooc.spring.ioc;

import com.imooc.spring.ioc.service.DepartmentService;
import com.imooc.spring.ioc.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService userService = context.getBean("userService",UserService.class);
        //这里不会自动调用setUdao方法
//        System.out.println(userService.getUdao());
        //System.out.println(context.getBean("userDao"));
        //getBeanDefinitionNames 方法获取容器内所有有效的beanId
//        String[] ids = context.getBeanDefinitionNames();
//        for (String id : ids){
//            System.out.println(id+":"+context.getBean(id));}

        DepartmentService departmentService = context.getBean("departmentService",DepartmentService.class);
        departmentService.joinDepartment();


    }
}

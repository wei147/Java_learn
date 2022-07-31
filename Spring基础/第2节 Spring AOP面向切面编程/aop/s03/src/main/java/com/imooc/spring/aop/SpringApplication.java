package com.imooc.spring.aop;

import com.imooc.spring.aop.service.EmployeeService;
import com.imooc.spring.aop.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService userService = context.getBean("userService", UserService.class);
        EmployeeService employeeService = context.getBean("employeeService",EmployeeService.class);

        userService.createUser();
        employeeService.entry();
    }
}

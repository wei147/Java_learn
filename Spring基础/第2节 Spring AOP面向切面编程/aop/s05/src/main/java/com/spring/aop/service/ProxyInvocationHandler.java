package com.spring.aop.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
//这个类要实现一个至关重要的接口 InvocationHandler

/**
 * InvocationHandler是JDK提供的反射类，用于在JDK动态代理中对目标方法进行增强
 * InvocationHandler 实现类与切面类的环绕通知类似
 */
public class ProxyInvocationHandler implements InvocationHandler {
    //因为ProxyInvocationHandler这里是面向所有类生效的，所以这里不能写具体类型（UserService）
    private Object target;
    private UserService userService;

    //这里需要传入目标对象 Object target
    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 在invoke() 方法对目标方法进行增强
     *
     * @param proxy  代理类对象   （通常是JDK代理动态自动生成的）
     * @param method 目标方法对象
     * @param args   目标方法实参
     * @return 目标方法运行后返回值  （返回值是return，通常是返回目标方法运行后的返回值）
     * @throws Throwable 目标方法抛出的异常
     */
    @Override
    //在学习反射 Method方法，用它（invoke）来调用目标方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法执行前输出的内容
        System.out.println("=====" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()) + "=====");
        //method.invoke(目标对象方法,目标对象需要的实际参数)
        Object ret = method.invoke(target, args); //调用目标方法  返回值是Object。
        // 对比环绕通知: ProceedingJoinPoint.proceed() 和method.invoke()两者目的是一样的————“执行目标方法”。不同点: 一个经过了aop的封装，一个是使用java最底层的反射机制
        return ret;
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();//具体的目标对象 实现类
        ProxyInvocationHandler invocationHandler = new ProxyInvocationHandler(userService);
        //作为这个对象invocationHandler本身只有一个职责就是对目标方法进行扩展。但是这个扩展需要基于一个代理类，而代理类如何创建？
        //动态创建代理类   （核心语句）  newProxyInstance(将类的加载器传至其中，将该类要实现的接口对象传入其中,如何对目标方法进行扩展) 在运行时就会基于userService这个接口产生与之对应的代理类。 动态创建的代理也实现了userservice
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), invocationHandler);   //newProxyInstance()用于基于接口创建指定的代理类

        //基于代理进行调用
        userServiceProxy.createUser();

        //创建了新的接口和对应的实现类，invocationHandler如何体现动态代理
        //动态代理,必须实现接口才可以运行（反之会报错）
        //实际学习过程中有大量的类都没有实现接口，这时那么该怎么做？ Spring为我们通过了另外一种解决方案：依赖于第三方组件CGLib实现对类的增强
        EmployeeService employeeService = new EmployeeServiceImpl();
        //employeeServiceProxy指向了被代理对象 EmployeeServiceImpl targetObject.createEmployee()
        EmployeeService employeeServiceProxy = (EmployeeService) Proxy.newProxyInstance(employeeService.getClass().getClassLoader(),
                employeeService.getClass().getInterfaces(), new ProxyInvocationHandler(employeeService));
        employeeServiceProxy.createEmployee();
    }
}

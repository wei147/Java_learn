package com.imooc.spring.aop.service;

public class Application {
    public static void main(String[] args) {
        /**
         * 希望将这个方法的执行时间打印出来，该如何操作？（之前是使用spring aop 的环绕通知/切面类来做的。如果通过代理来做？）
         * 通过 UserServiceProxy 代理类来实现。
         * 还容许有二房东的存在，即这个租户自己又把房子租给别人。即嵌套使用
         * 静态代理是指必须手动创建代理类的代理模式使用方式
         */
//        UserServiceImpl userService = new UserServiceImpl();
        UserService userService = new UserServiceProxy(new UserServiceImpl());  //在没有修改最原始代码的情况下，对我们程序的行为进行了扩展
//        userService.createUser();

        UserService userService1 = new UserServiceProxy1(new UserServiceProxy(new UserServiceImpl()));
        userService1.createUser();
    }
}

package com.imooc.spring.ioc.entity.com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import com.imooc.spring.ioc.entity.Child;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * 所谓ioc容器就是通过配置的方式，让我们在不需要new关键字的情况下对对象进行创建
 * 对于Spring来说，创建对象是其最基础的工作。与此同时，它还有一个重要职能是维护对象的关联关系
 * 利用反射技术在程序运行时动态的进行设置。灵活的
 * 2022年7月11日00:25:47 利用spring ioc容器让我们对象和对象之间进行有效的解耦
 * 之前：对象关系通过代码来实现  现在：对象关系通过配置来实现
 * ioc何为控制反转：所谓控制反转是与我们程序主动创建相对的（通过new来创建）。现在是被动的从容器（所有对象、关系被ioc创建并管理）中提取。
 * 通过ioc这个第三者的介入，让程序的维护性和拓展性上升了一个层次
 */
public class SpringApplication {
    public static void main(String[] args) {
        //作为spring ioc容器要先启动才能对配置的bean进行实例化
        //含义是：去加载指定的xml文件来初始化Ioc容器
        //classpath 当前类路径下去查找文件
        //context指代了Spring ioc容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Apple sweetApple = context.getBean("sweetApple", Apple.class);
        System.out.println(sweetApple.getTitle());

        //从Ioc容器中提取beanId = Lily的对象
        Child lily = context.getBean("lily",Child.class);
//        System.out.println(lily.getApple().getTitle() + lily.getName());
        lily.eat();

        Child wei = context.getBean("wei",Child.class);
        wei.eat();

        Child YanFei = context.getBean("YanFei",Child.class);
        YanFei.eat();
    }
}

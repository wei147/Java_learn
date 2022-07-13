package com.imooc.spring.ioc.factory;

//苹果工厂实例

import com.imooc.spring.ioc.entity.Apple;

/**
 * 工厂实例方法创建对象是指Ioc容器对工厂类进行实例化并调用对应的实例方法创建对象的过程
 */
public class AppleFactoryInstance {
    public Apple createSweetApple(){
        Apple apple = new Apple();
        apple.setTitle("金苹果");
        apple.setOrigin("格林大陆");
        apple.setColor("金色");
        return apple;
    }
}

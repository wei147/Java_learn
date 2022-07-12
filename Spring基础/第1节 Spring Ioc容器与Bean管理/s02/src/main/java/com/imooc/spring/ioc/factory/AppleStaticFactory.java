package com.imooc.spring.ioc.factory;

import com.imooc.spring.ioc.entity.Apple;

/**
 * 静态工厂通过静态方法创建对象，隐藏创建对象的细节
 */
public class AppleStaticFactory {
    //创建一个甜苹果对象，返回值是apple对象
    //静态方法：用于创建对象的方法是静态的
    public static Apple createSweetApple(){
        //logger.info("")   用工厂的方法能实现一些额外的功能
        Apple apple = new Apple();
        apple.setTitle("金苹果");
        apple.setOrigin("格林大陆");
        apple.setColor("金色");
        return apple;
    }
}

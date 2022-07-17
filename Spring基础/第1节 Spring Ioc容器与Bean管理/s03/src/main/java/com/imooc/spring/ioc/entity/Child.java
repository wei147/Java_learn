package com.imooc.spring.ioc.entity;

public class Child {
    private String name;
    private Apple apple;

    //默认的构造方法
    public Child() {
        System.out.println("正在创建Child对象"+this);
    }

    //对于Child的实例化也需生成带参的构造方法
    public Child(String name, Apple apple) {
        System.out.println("构造方法参数apple: "+apple.getTitle());
        this.name = name;
        this.apple = apple;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        System.out.println("注入的Apple对象："+apple);
        this.apple = apple;
    }

    public void eat(){
        System.out.println(name+"吃到了"+apple.getOrigin()+"种植的"+apple.getTitle());
    }
}

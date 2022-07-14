package com.imooc.spring.ioc.entity;

public class Apple {
    private String title;
    private String color;
    private String origin;  //产地
    private Float price;


    // 为了满足java bean的要求，还需要生成默认的构造方法


    public Apple() {
    //  <!--作为bean，如果不写任何信息的话，默认是基于默认构造方法进行创建的。     对应xml配置ApplicationContext.xml
    // 如何通过带参构造方法创建对象呢？默认是无参的构造方法
        System.out.println("Apple对象已创建 "+this);
    }

    //为了方便对象实例化，将其构造方法自动生成。
    public Apple(String title, String color, String origin) {
        System.out.println("通过带参构造方法创建对象"+this);
        this.title = title;
        this.color = color;
        this.origin = origin;
    }

    public Apple(String title, String color, String origin,Float price) {
        System.out.println("通过带参构造方法创建对象"+this);
        this.title = title;
        this.color = color;
        this.origin = origin;
        this.price = price;
    }

    public String getTitle() {
        System.out.println("set title方法被调用");
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}

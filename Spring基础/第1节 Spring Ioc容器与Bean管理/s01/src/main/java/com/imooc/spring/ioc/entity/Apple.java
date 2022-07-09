package com.imooc.spring.ioc.entity;

public class Apple {
    private String title;
    private String color;
    private String origin;  //产地


    // 为了满足java bean的要求，还需要生成默认的构造方法


    public Apple() {
    }

    //为了方便对象实例化，将其构造方法自动生成。
    public Apple(String title, String color, String origin) {
        this.title = title;
        this.color = color;
        this.origin = origin;
    }

    public String getTitle() {
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

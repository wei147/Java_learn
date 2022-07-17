package com.imooc.spring.ioc.entity;

public class Computer {
    private String brand;
    private String type;
    private String sn;  //计算机序列号
    private Float price;

    //生成默认构造方法
    public Computer() {
    }

    //为了简化对象创建的过程，生成构造方法


    public Computer(String brand, String type, String sn, Float price) {
        this.brand = brand;
        this.type = type;
        this.sn = sn;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    //利用idea 重写toString。（方便程序的演示）

    //这里重写了toString()方法
    @Override
    public String toString() {
        return "Computer{" +
                "brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", sn='" + sn + '\'' +
                ", price=" + price +
                '}';
    }
}

package com.imooc.springmvc.entity;

import java.util.List;

public class Form {
    private String name;
    private String course;
    private List<Integer> purpose;
    //包含快递的信息  关联对象赋值  delivery对应delivery.name在form.html中书写
    private Delivery delivery =  new Delivery();

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<Integer> getPurpose() {
        return purpose;
    }

    public void setPurpose(List<Integer> purpose) {
        this.purpose = purpose;
    }
}

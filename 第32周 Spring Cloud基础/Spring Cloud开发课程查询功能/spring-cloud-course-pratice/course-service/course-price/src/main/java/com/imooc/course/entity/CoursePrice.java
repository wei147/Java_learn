package com.imooc.course.entity;

import java.io.Serializable;

/**
 * CoursePrice的实体类
 */

//为什么要实现Serializable接口?  序列化：就是把对象转化成字节。
public class CoursePrice implements Serializable {
    Integer id;
    Integer courseId;
    Float price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CoursePrice{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", Price=" + price +
                '}';
    }
}

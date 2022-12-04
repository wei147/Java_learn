package com.imooc.course.entity;

import java.io.Serializable;

/**
 * 课程与价格的融合类(整合了课程列表和价格的类)
 */

//为什么要实现Serializable接口?  序列化：就是把对象转化成字节。
public class CoursesAndPrice implements Serializable {
    Integer id;
    Integer courseId;
    Float price;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

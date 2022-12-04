package com.imooc.course.controller;

import com.imooc.course.client.CourseListClient;
import com.imooc.course.entity.Course;
import com.imooc.course.entity.CoursePrice;
import com.imooc.course.service.CoursePriceService;
import com.imooc.course.entity.CoursesAndPrice;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程价格控制器
 */
@RestController
public class CoursePriceController {

    @Resource
    CoursePriceService coursePriceService;

    @Resource
    CourseListClient courseListClient;

    //对外提供价格的能力
    @GetMapping("/price")
    public Float getCoursePrice(@RequestParam Integer courseId) {
        CoursePrice coursePrice = coursePriceService.getCoursePrice(courseId);
        return coursePrice.getPrice();
    }


    //在课程价格中调用课程列表的服务
    @GetMapping("/coursesInPrice")
    public List<Course> getCourseListInPrice() {
        return courseListClient.courseList();
    }

    //整合两个服务
    @GetMapping("/coursesAndPrice")
    public List<CoursesAndPrice> getCoursesAndPrice() {
        return coursePriceService.getCoursesAndPriceList();
    }
}


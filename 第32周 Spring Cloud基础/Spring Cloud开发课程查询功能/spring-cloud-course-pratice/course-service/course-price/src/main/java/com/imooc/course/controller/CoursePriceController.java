package com.imooc.course.controller;

import com.imooc.course.entity.CoursePrice;
import com.imooc.course.service.CoursePriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程价格控制器
 */
@RestController
public class CoursePriceController {

    @Resource
    CoursePriceService coursePriceService;

    //对外提供价格的能力
    @GetMapping("/price")
    public Float getCoursePrice(@RequestParam Integer courseId) {
        CoursePrice coursePrice = coursePriceService.getCoursePrice(courseId);
        return coursePrice.getPrice();
    }
}

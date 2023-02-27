package com.wei.consumer.controller;

import java.util.List;

import com.wei.consumer.entity.CourseAndPrice;
import com.wei.consumer.entity.CoursePrice;
import com.wei.consumer.service.CoursePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述：CoursePriceController
 */
@RestController
public class CoursePriceController {

    @Resource
    CoursePriceService coursePriceService;


    @GetMapping({"/price"})
    public Integer getCoursePrice(Integer courseId) {
        CoursePrice coursePrice = coursePriceService.getCoursePrice(courseId);
        if (coursePrice != null) {
            return coursePrice.getPrice();
        } else {
            return -1;
        }
    }


    @GetMapping({"/coursesAndPrice"})
    public List<CourseAndPrice> getcoursesAndPrice() {
        return coursePriceService.getCoursesAndPrice();
    }
}

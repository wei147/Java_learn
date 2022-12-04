package com.imooc.course.service;

import com.imooc.course.entity.CoursePrice;
import com.imooc.course.entity.CoursesAndPrice;

import java.util.List;

/**
 * 课程价格服务
 */
public interface CoursePriceService {

    CoursePrice getCoursePrice(Integer courseId);

    List<CoursesAndPrice> getCoursesAndPriceList();
}

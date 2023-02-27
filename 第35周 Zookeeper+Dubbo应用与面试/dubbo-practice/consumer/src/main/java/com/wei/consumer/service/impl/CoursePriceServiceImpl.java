package com.wei.consumer.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wei.consumer.dao.CoursePriceMapper;
import com.wei.consumer.entity.CourseAndPrice;
import com.wei.consumer.entity.CoursePrice;
import com.wei.consumer.service.CoursePriceService;
import com.wei.producer.entity.Course;
import com.wei.producer.service.CourseListService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述：     课程 价格服务
 */
@Service    //要对外暴露http请求所以用Spring的Service
public class CoursePriceServiceImpl implements CoursePriceService {

    @Resource
    CoursePriceMapper coursePriceMapper;

    // 调用dubbo的服务
    @Reference(version = "${demo.service.version}")
    CourseListService courseListService;

    @Override
    public CoursePrice getCoursePrice(Integer courseId) {
        return coursePriceMapper.findCoursePrices(courseId);
    }

    @Override
    public List<CourseAndPrice> getCoursesAndPrice() {
        List<CourseAndPrice> courseAndPriceList = new ArrayList<>();
        List<Course> courseList = courseListService.getCourseList();
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            if (course != null) {
                CoursePrice price = getCoursePrice(course.getCourseId());
                if (price != null && price.getPrice() > 0) {
                    CourseAndPrice courseAndPrice = new CourseAndPrice();
                    courseAndPrice.setId(course.getId());
                    courseAndPrice.setCourseId(course.getCourseId());
                    courseAndPrice.setName(course.getName());
                    courseAndPrice.setPrice(price.getPrice());
                    courseAndPriceList.add(courseAndPrice);
                }
            }
        }
        return courseAndPriceList;
    }

}

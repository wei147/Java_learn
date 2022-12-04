package com.imooc.course.service.Impl;

import com.imooc.course.client.CourseListClient;
import com.imooc.course.dao.CoursePriceMapper;
import com.imooc.course.entity.Course;
import com.imooc.course.entity.CoursePrice;
import com.imooc.course.entity.CoursesAndPrice;
import com.imooc.course.service.CoursePriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程价格服务具体实现类
 */

@Service("coursePriceService")
public class CoursePriceServiceImpl implements CoursePriceService {

    @Resource
    CoursePriceMapper coursePriceMapper;

    //课程列表从课程列表服务中拿
    @Resource
    CourseListClient courseListClient;


    @Override
    public CoursePrice getCoursePrice(Integer courseId) {
        return coursePriceMapper.getCoursePrice(courseId);
    }


    @Override
    public List<CoursesAndPrice> getCoursesAndPriceList() {
        List<CoursesAndPrice> coursesAndPriceList = new ArrayList<>();
        List<Course> courses = courseListClient.courseList();
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            if (course != null) {
                Integer courseId = course.getCourseId();
                CoursePrice coursePrice = coursePriceMapper.getCoursePrice(courseId);
                CoursesAndPrice coursesAndPrice = new CoursesAndPrice();
                coursesAndPrice.setCourseId(courseId);
                coursesAndPrice.setPrice(coursePrice.getPrice());
                coursesAndPrice.setName(course.getName());
//                coursesAndPrice.setId(i);

                coursesAndPriceList.add(coursesAndPrice);
            }
        }
        return coursesAndPriceList;
    }
}

package com.imooc.course.client;

import com.imooc.course.entity.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 断路器实现类
 */
@Component      //这个是必须加的吗
public class CourseListClientHystrix implements CourseListClient {
    @Override
    public List<Course> courseList() {
        List<Course> defaultCourses = new ArrayList<>();
        Course course = new Course();
        course.setId(108);
        course.setCourseId(108);
        course.setName("默认课程");
        course.setValid(1);
        defaultCourses.add(course);
        return defaultCourses;
    }
}

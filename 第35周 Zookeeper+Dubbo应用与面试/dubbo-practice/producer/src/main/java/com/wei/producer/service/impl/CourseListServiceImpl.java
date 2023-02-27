package com.wei.producer.service.impl;

import com.wei.producer.entity.Course;
import com.wei.producer.mapper.CourseMapper;
import com.wei.producer.service.CourseListService;
import org.apache.dubbo.config.annotation.Service;


import javax.annotation.Resource;
import java.util.List;

@Service(version = "${demo.service.version}")  // dubbo的service
public class CourseListServiceImpl implements CourseListService {

    @Resource
    CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList() {
        return courseMapper.findValidCourses();
    }
}

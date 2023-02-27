package com.wei.producer.service;

import com.wei.producer.entity.Course;

import java.util.List;

/**
 * 课程列表服务  (从数据库中拿到课程列表并把它展示出来)
 */
public interface CourseListService {
    List<Course> getCourseList();
}

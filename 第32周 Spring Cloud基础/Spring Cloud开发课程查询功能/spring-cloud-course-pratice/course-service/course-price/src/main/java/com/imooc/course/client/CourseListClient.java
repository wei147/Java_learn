package com.imooc.course.client;

import com.imooc.course.entity.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 课程列表的Feign客户端
 */
//fallback就是发生错误时所要调用的类。正常是调用本接口的方法,发生错误的话调用CourseListClientHystrix实现类
@FeignClient(value = "course-list",fallback = CourseListClientHystrix.class)
public interface CourseListClient {

    @GetMapping("/courses")
    List<Course> courseList();

}

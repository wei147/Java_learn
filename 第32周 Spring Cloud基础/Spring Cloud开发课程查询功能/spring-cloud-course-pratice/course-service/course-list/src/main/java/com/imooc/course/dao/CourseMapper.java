package com.imooc.course.dao;

import com.imooc.course.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程的Mapper类
 */
@Mapper
@Repository
public interface CourseMapper {

    //寻找所有上架的课程
    @Select("select * from course where valid=1")
    List<Course> findValidCourses();
}

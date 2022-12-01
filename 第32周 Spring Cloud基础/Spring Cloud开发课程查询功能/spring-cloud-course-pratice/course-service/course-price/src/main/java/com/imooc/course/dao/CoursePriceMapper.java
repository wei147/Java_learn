package com.imooc.course.dao;

import com.imooc.course.entity.CoursePrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CoursePriceMapper {

    @Select("select * from course_price where course_id=#{courseId}")
    CoursePrice getCoursePrice(@Param("courseId") Integer courseId);
}

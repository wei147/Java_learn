package com.imooc.course.service.Impl;

import com.imooc.course.dao.CoursePriceMapper;
import com.imooc.course.entity.CoursePrice;
import com.imooc.course.service.CoursePriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 课程价格服务具体实现类
 */

@Service("coursePriceService")
public class CoursePriceServiceImpl implements CoursePriceService {

    @Resource
    CoursePriceMapper coursePriceMapper;

    @Override
    public CoursePrice getCoursePrice(Integer courseId) {
        return coursePriceMapper.getCoursePrice(courseId);
    }
}

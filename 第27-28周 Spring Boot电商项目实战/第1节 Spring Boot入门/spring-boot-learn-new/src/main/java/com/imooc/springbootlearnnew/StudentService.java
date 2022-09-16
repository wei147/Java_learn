package com.imooc.springbootlearnnew;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 学生Service
 */
@Service
public class StudentService {
    @Resource
    StudentMapper studentMapper;

    public Student findStudent(Integer id){
        return studentMapper.findById(id);
    }
}

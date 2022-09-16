package com.imooc.springbootlearnnew;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 学生Controller
 */
@RestController
public class StudentController {
    @Resource
    StudentService studentService;

    @GetMapping("/student")
    public String student(@RequestParam Integer id){
        Student student = studentService.findStudent(id);
//        return "查询到对应学生名字是: "+student.getName();
        return student.toString();
    }
}

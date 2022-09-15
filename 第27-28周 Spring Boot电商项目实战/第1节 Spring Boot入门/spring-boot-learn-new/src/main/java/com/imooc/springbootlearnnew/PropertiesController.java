package com.imooc.springbootlearnnew;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertiesController {
    //通过@Value来读取配置文件中的值 (利用配置文件去配置属性)
    @Value("${school.grade}")
    Integer grade;
    @Value("${school.classNum}")
    Integer classNum;

    @GetMapping("/gradeClass")
    public String gradeClass() {
        return "年级:" + grade + "班级: " + classNum;
    }

}

package com.imooc.springbootlearnnew;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 读取配置类
 */
@RestController
public class ConfigController {
    @Resource
    SchoolConfig schoolConfig;

    @GetMapping("/gradeFromConfig")
    public String gradeClass(){
        return "年级:" + schoolConfig.grade + "班级: " + schoolConfig.classNum;
    }
}

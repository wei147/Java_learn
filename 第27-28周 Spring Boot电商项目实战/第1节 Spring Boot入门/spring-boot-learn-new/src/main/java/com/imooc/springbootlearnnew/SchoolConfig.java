package com.imooc.springbootlearnnew;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * School配置类
 */
@Component
@ConfigurationProperties(prefix = "school")            //配置相关的注解
public class SchoolConfig {
    Integer grade;
    Integer classNum;

    //这里要生成get和set方法,在ConfigController中才能读取到grade和classNum的值
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }
}

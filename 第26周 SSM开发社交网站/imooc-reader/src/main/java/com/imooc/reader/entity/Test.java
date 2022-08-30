package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test")  //说明实体类对应哪一张表
public class Test {
    @TableId(type = IdType.AUTO)     //说明表的主键,type设置为自动增长
    @TableField("id")   //说明属性对应哪个字段
    private Integer id;

    //如果字段名与属性名相同或者符合驼峰命名转换规则,则TableField可省略    textContent ——> test_content（数据库字段名）
    @TableField("content")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.imooc.reader.service.impl;

import com.imooc.reader.entity.Category;
import com.imooc.reader.service.CategoryService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class CategoryServiceImplTest extends TestCase {
    @Resource
    private CategoryService categoryService;

    @Test
    public void testSelectAll() {
        List<Category> list = categoryService.selectAll();
        System.out.println(list);
    }
}
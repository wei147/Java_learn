package com.imooc.reader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.mapper.TestMapper;
import com.imooc.reader.service.TestService;
//import org.junit.Test;
import com.imooc.reader.entity.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class MyBatisPlusTest {
    @Resource
    private TestMapper testMapper;

    @org.junit.Test
    public void testInsert() {
        Test test = new Test();
        test.setContent("MyBatisPlus 测试");
        testMapper.insert(test);
    }

    @org.junit.Test
    public void testUpdate() {
        Test test = testMapper.selectById(32);
        test.setContent("我被修改了,MyBatisPlus 测试1");
        testMapper.updateById(test);
    }

    @org.junit.Test
    public void testDelete() {
        testMapper.deleteById(27);
    }

    @org.junit.Test
    public void testSelect() {
        //查询多条查询数据。需要传入一个查询条件构造器，它的作用就是帮我们组织查询时的筛选条件
        QueryWrapper<Test> queryWrapper = new QueryWrapper<Test>();
        queryWrapper.eq("id",32); //查询等于32
        queryWrapper.gt("id",35);   //查询所有id大于35    Preparing: SELECT id,content FROM test WHERE (id > ?)
        //默认情况下,写了多个子句的话，会通过and进行连接,并将值按照前后顺序进行传入     Preparing: SELECT id,content FROM test WHERE (id = ? AND id > ?)
        List<Test> testList = testMapper.selectList(queryWrapper);
        System.out.println(testList);
//        System.out.println(testList.get(0));
    }


}

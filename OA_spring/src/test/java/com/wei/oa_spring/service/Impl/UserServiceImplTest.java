package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.model.dao.EmployeeMapper;

import com.wei.oa_spring.model.pojo.Employee;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest extends TestCase {

    @Resource
    EmployeeMapper employeeMapper;

    @Test
    public void test() {
        System.out.println(" i am come ===============");
        List<Employee> employeeList = employeeMapper.selectAll();
        System.out.println(employeeList.toString());
    }

    @Test
    public void hello() {
        System.out.println("qqqq++++++++++++++++++=====");
    }

    public void testSelectNodeByUserId() {
    }
}
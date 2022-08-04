package com.imooc.spring.jdbc.dao;

import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeDao {
    //持有jdbcTemplate
    private JdbcTemplate jdbcTemplate;

    public Employee findById(Integer eno){
        //queryForObject的含义是进行指定的查询，将唯一返回的数据转成对应的对象
        String sql = "select * from employee where eno=?";
        //如何将指定的sql转成相应的对象? BeanPropertyRowMapper的含义是 将bean的属性和每一行的列（？）来进行一一的对应(需要先刻意的将属性名和字段名按驼峰规则保持一致),
        // 由这个对象(RowMapper)来完成从数据库记录到实体对象的转化，类似mybatis中将每一条记录转化为实体对象的过程
        Employee employee = jdbcTemplate.queryForObject(sql,new Object[]{eno},new BeanPropertyRowMapper<Employee>(Employee.class));
        return employee;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

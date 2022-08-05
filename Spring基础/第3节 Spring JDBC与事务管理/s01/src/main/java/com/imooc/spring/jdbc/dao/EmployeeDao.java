package com.imooc.spring.jdbc.dao;

import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class EmployeeDao {
    //持有jdbcTemplate
    private JdbcTemplate jdbcTemplate;

    public Employee findById(Integer eno){
        //queryForObject的含义是进行指定的查询，将唯一返回的数据转成对应的对象  (查询单条数据)
        String sql = "select * from employee where eno=?";
        //如何将指定的sql转成相应的对象? BeanPropertyRowMapper的含义是 将bean的属性和每一行的列（？）来进行一一的对应(需要先刻意的将属性名和字段名按驼峰规则保持一致),
        // 由这个对象(RowMapper)来完成从数据库记录到实体对象的转化，类似mybatis中将每一条记录转化为实体对象的过程 （属性和字段间的彼此转换）
        Employee employee = jdbcTemplate.queryForObject(sql,new Object[]{eno},new BeanPropertyRowMapper<Employee>(Employee.class));
        return employee;
    }

    //查询多条/复合数据 （按照部门名称进行数据查询）
    public List<Employee> findByDname(String dname){
        String sql = "select * from employee where dname = ?";
        //jdbcTemplate.query()方法默认返回list集合  query(参数1 sql语句,参数2与sql语句对应的实际参数是什么, 参数3我们要将每一条记录转换为什么对象[期待转换为employee实体类])
        List<Employee> list = jdbcTemplate.query(sql, new Object[]{dname},new BeanPropertyRowMapper<Employee>(Employee.class));   //参数3 属性和字段间的彼此转换
        return list;
    }

    //测试: 当字段名和属性名不一样时会怎么样 （这里起了两个别名，和属性名就对不上了）模拟无法进行有效的实体属性映射
    public List<Map<String, Object>> findMapByDname(String dname){
        String sql = "select eno as empno, salary as s from employee where dname = ?";
        //将查询结果作为列表返回同时默认情况下将每一条数据按Map对象来进行包裹(将查询结果作为Map进行封装)  Map<String, Object>中的key是原始字段名，value则是字段名所对应的数值
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{dname});
        return maps;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

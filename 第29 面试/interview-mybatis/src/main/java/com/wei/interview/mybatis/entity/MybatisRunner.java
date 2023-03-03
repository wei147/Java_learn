package com.wei.interview.mybatis.entity;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MybatisRunner {
    public static void main(String[] args) {
        //mybatis的配置文件
        String resource = "mybatis.xml";
        InputStream is = MybatisRunner.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        String statement = "com.wei.interview.mybatis.mapper.EmpMapper.findById";
        SqlSession session = sessionFactory.openSession();

        Employee emp1 = session.selectOne(statement, 7566);
        System.out.println("编号为7566的,用户名是:  "+emp1.getEname());
        session.close();




    }
}

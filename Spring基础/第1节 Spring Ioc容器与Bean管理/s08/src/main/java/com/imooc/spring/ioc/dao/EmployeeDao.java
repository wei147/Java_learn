package com.imooc.spring.ioc.dao;

import org.springframework.stereotype.Repository;

/**
 * 模拟注解形式开发  （与Java Config的形式配合）
 * 在 Config.java 中加入 @ComponentScan(basePackages = "com.imooc")     扫描整个包
 *                          public class Config { ....
 * 那么在EmployeeDao.java 加上@Repository 就能被 扫描到并创建对象
 */
@Repository
public class EmployeeDao {

}

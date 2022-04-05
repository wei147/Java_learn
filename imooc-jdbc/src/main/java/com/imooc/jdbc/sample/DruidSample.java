package com.imooc.jdbc.sample;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.imooc.jdbc.common.DbUtils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Druid连接池配置与使用
 */
public class DruidSample {
    public static void main(String[] args) {
        //1.加载属性文件
        Properties properties = new Properties();
        String propertyFile = null;
        try {
//            propertyFile = DruidSample.class.getResource("/druid-config.properties").getPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //空格 -> %20 | c:java code\  c:java%20code\
        try {
            propertyFile = new URLDecoder().decode(propertyFile, "UTF-8");
            //加载路径文件
            properties.load(new FileInputStream(propertyFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //2.获取DataSource数据源对象       (数据源是数据库在JDBC中的别称)
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            //3.创建数据库连接
            conn = dataSource.getConnection();
            String sql = "SELECT  * from  employee limit 0,10";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int eno = rs.getInt(1);   //JDBC中字段索引从1开始
                String ename = rs.getString("ename");    //这里用的是字段名
                float salary = rs.getFloat("salary");
                String dname = rs.getString("dname");
                Date hiredate = rs.getDate("hiredate");
                System.out.println(dname + "-" + eno + "-" + ename + "-" + salary + "-" + hiredate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * 不使用连接池：conn.close() 关闭连接
             * 使用连接词：conn.close()  将连接回收至连接池
             */
            DbUtils.closeConnection(rs, pstmt, conn);
        }
    }
}














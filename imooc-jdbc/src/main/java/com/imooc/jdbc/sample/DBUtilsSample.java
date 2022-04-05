package com.imooc.jdbc.sample;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.imooc.jdbc.hrapp.entity.Employee;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Apache DBUtils + Druid联合使用演示
 */
public class DBUtilsSample {
    private static void query() {
        Properties properties = new Properties();
        String propertyFile = DBUtilsSample.class.getResource("/druid-config.properties").getPath();
//        如果含有空格的话，使用
        try {
            propertyFile = new URLDecoder().decode(propertyFile, "UTF-8");
//           加载配置文件
            properties.load(new FileInputStream(propertyFile));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            //利用Apache DbUtils大幅简化了数据的提取过程      (比如自动帮助关闭连接)
//            快速实现参数化查询
            QueryRunner qr = new QueryRunner(dataSource);
            //将自动的将每一条记录转成Employee对象
            List<Employee> list = qr.query("select * from employee limit ?,10",
                    new BeanListHandler<>(Employee.class),
                    new Object[]{10});
            for (Employee emp : list) {
                System.out.println(emp.getDname());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void update() {
        Properties properties = new Properties();
        String propertyFile = DBUtilsSample.class.getResource("/druid-config.properties").getPath();
        Connection conn = null;
//        如果含有空格的话，使用
        try {
            propertyFile = new URLDecoder().decode(propertyFile, "UTF-8");
//           加载配置文件
            properties.load(new FileInputStream(propertyFile));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);  //关闭自动提交
            String sql1 = "updata employee set salary+1000 where eno=?";
            String sql2 = "updata employee set salary-500 where eno=?";
            //QueryRunner是Apache DBUtils最核心的类，用于查询和写入
            QueryRunner qr = new QueryRunner();
            qr.update(conn, sql1, new Object[]{1000});    //1000对应的是员工编号
            qr.update(conn, sql2, new Object[]{1001});
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();    //如果报错就回滚
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();       //对数据库进行回收，而不是关闭
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static void query_test() {
        Properties properties = new Properties();
//        String propertyFile = DBUtilsSample.class.getResource("/druid-config.properties").getPath();
//        InputStream in = DBUtilsSample.class.getClassLoader().getResourceAsStream("druid-config.properties");
        InputStream in = null;
        try {
            //加载配置文件 Druid 连接池不能够主动加载配置文件 ,需要指定文件
            in = new BufferedInputStream(new FileInputStream("D:\\project\\Jave_learn\\imooc-jdbc\\src\\main\\java\\com\\imooc\\jdbc\\druid-config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        如果含有空格的话，使用
        try {
//            propertyFile = new URLDecoder().decode(propertyFile, "UTF-8");
//           加载配置文件
            properties.load(in);
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            //利用Apache DbUtils大幅简化了数据的提取过程      (比如自动帮助关闭连接)
//            快速实现参数化查询
            QueryRunner qr = new QueryRunner(dataSource);
            //将自动的将每一条记录转成Employee对象
            List<Employee> list = qr.query("select * from employee limit ?,10",
                    new BeanListHandler<>(Employee.class),
                    new Object[]{1});   //从第一行开始向后取十行
            for (Employee emp : list) {
                System.out.println(emp.getEname());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
//        query();
//        update();
        query_test();
    }
}

/**
 * //4.加载配置文件 Druid 连接池不能够主动加载配置文件 ,需要指定文件
 * InputStream inputStream = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
 * <p>
 * //5. 使用Properties对象的 load方法 从字节流中读取配置信息
 * p.load(inputStream);

 */
























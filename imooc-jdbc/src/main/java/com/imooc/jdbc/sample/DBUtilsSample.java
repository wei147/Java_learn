package com.imooc.jdbc.sample;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.imooc.jdbc.hrapp.entity.Employee;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
//          properties.load(new FileInputStream(propertyFile));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
//            快速实现参数化查询
            QueryRunner qr = new QueryRunner(dataSource);
            //将自动的将每一条记录转成Employee对象
            qr.query("select * from employee limit ?,10",
                    new BeanListHandler<>(Employee.class),
                    new Object[]{10});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


























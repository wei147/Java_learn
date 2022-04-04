package com.imooc.jdbc.sample;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * 标准JDBC操作五步骤
 * */
public class StandardJDBCSample {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            //1.加载并注册JDBC驱动
            Class.forName("com.mysql.jdbc.Driver"); //mysql5.7是这个
            //2.创建数据库连接
            String url = "jdbc:mysql://localhost:3306/imooc?useSSL=false&Unicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
            conn = DriverManager.getConnection(url, "root", "1234");
            //3.创建Statement对象
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from employee where dname='研发部'");
            //4.遍历查询结果
            while (rs.next()) {
                int eno = rs.getInt(1); //eno
                String ename = rs.getString("ename");
                float salary = rs.getFloat("salary");
                String dname = rs.getString("dname");
                System.out.println(dname + "-" + eno  + "-"  + ename + "-" + salary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && conn.isClosed() == false) {    //为了避免空指针异常，只有在conn不为空并且是连接活跃的情况下才close
                    //5.关闭连接，释放资源
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

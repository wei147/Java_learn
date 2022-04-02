package com.imooc.jdbc.hrapp.command;

import com.imooc.jdbc.common.DbUtils;

import java.sql.*;
import java.util.Scanner;

public class QueryCommand implements Command { // //实现刚才定义的接口
    @Override
    public void execute() {
        System.out.println("请输入部门名称 : ");
        Scanner in = new Scanner(System.in);
        String pdname = in.nextLine();  // 用户输入要查询的部门
        Connection conn = null; //  Connection放外边扩大生命周期
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //1.加载并注册JDBC驱动
            //2.创建数据库连接
            conn = DbUtils.getConnection();
            //3.创建Statement对象
//          //结果集
            stmt = conn.createStatement();
            System.out.println("select * from employee where dname = '" + pdname + "'");
            rs = stmt.executeQuery("select * from employee where dname = '" + pdname + "'");    //执行查询
            //4.遍历查询结果
            //rs.next() 返回布尔值，代表是否存在下一条记录
            //如果有，返回true，同时结果集提取下一条记录
            //如果没有，返回false，循环就会停止
            while (rs.next()) {
                int eno = rs.getInt(1);   //JDBC中字段索引从1开始
                String ename = rs.getString("ename");    //这里用的是字段名
                float salary = rs.getFloat("salary");
                String dname = rs.getString("dname");
                System.out.println(dname + "-" + eno + "-" + ename + "-" + salary);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //5.关闭连接，释放资源   (完全体)
            DbUtils.closeConnection(rs, stmt, conn);
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//            try {
//                if (conn != null && !conn.isClosed()) {
//                    conn.close();
//                }
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }

        }
    }

}


























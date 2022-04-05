package com.imooc.jdbc.common;

import java.sql.*;

public class DbUtils {
    /**
     * 创建新的数据库连接
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        //1.加载并注册JDBC驱动
        Class.forName("com.mysql.cj.jdbc.Driver"); //mysql5.7是这个
        //2.创建数据库连接
        String url = "jdbc:mysql://localhost:3306/imooc?useSSL=false&Unicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        Connection conn = DriverManager.getConnection(url, "root", "1234");
        return conn;
    }

    /**
     * 关闭连接释放资源
     *
     * @param rs    结果集对象
     * @param stmt  Statement对象
     * @param conn  Connection对象
     */
    public static void closeConnection(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}














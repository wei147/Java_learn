package com.wei.interview.jdbc;

import java.sql.*;

public class MysqlJDBC {
    public static void main(String[] args) {
        String driverName = "com.mysql.cj.jdbc.Driver";
        String URL = "jdbc:mysql://127.0.0.1:3306/scott";
        String sql = "SELECT  * from user";
        String username = "root";
        String password = "1234";
        Connection connection = null;
        try {
            //1.加载JDBC驱动           
            Class.forName(driverName);
            //2.建立连接
            connection = DriverManager.getConnection(URL, username, password);
            //3.创建命令(Statement)
            Statement statement = connection.createStatement();
            //4.处理结果 (结果集对象)
            ResultSet resultSet = statement.executeQuery(sql);
            //遍历结果集
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name")
                        + resultSet.getString("age"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    //5.关闭当前连接
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.imooc.jdbc.sample;

import com.imooc.jdbc.common.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JDBC中的事务控制
 */


public class TransactionSample {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtils.getConnection();
            // JDBC默认使用自动提交模式
            conn.setAutoCommit(false);  //关闭自动提交
            String sql = "insert into employee(eno,ename,salary,dname) values (?,?,?,?)";
            for (int i = 2000000; i < 2000009; i++) {
                if (i == 1005) {
//                    throw new RuntimeException("插入失败");   模拟异常，测试手动提交事务模式
                }
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, i);
                pstmt.setString(2, "员工" + i);
                pstmt.setFloat(3, 5000f);
                pstmt.setString(4, "市场部");
                pstmt.executeUpdate();
            }
            conn.commit();  //上面for循环代码完整执行之后，才提交数据
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();    //出现异常的话，直接将已提交到事务区的数据删除    回滚数据
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } catch (Exception e) {     //Exception 所有异常的父类
            e.printStackTrace();    //打印栈信息
        } finally {
            DbUtils.closeConnection(null, pstmt, conn);
        }
    }
}







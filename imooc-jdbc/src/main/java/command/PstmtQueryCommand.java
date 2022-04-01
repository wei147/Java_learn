package command;

import java.sql.*;
import java.util.Scanner;

public class PstmtQueryCommand implements Command { // //实现刚才定义的接口
    @Override
    public void execute() {
        System.out.println("请输入部门名称 : ");
        Scanner in = new Scanner(System.in);
        String pdname = in.nextLine();  // 用户输入要查询的部门
        Connection conn = null; //  Connection放外边扩大生命周期
//        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //1.加载并注册JDBC驱动
            Class.forName("com.mysql.jdbc.Driver"); //mysql5.7是这个
            //2.创建数据库连接
            String url = "jdbc:mysql://localhost:3306/imooc?useSSL=false&Unicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
            conn = DriverManager.getConnection(url, "root", "1234");
            //3.创建prepareStatement对象
//          //结果集
//            stmt = conn.createStatement();
            System.out.println("select * from employee where dname = '" + pdname + "'");
            String sql = "select * from employee where dname =? and eno > ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pdname);    //注意：参数索引从1开始    pdname就是上边的？
            pstmt.setInt(2, 3500);  //对应的是 eno的?值
            rs = pstmt.executeQuery();    //执行查询
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
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
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

}


























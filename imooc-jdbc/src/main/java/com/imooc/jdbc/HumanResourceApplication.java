package com.imooc.jdbc;

import com.imooc.jdbc.hrapp.command.*;

import java.util.Scanner;

public class HumanResourceApplication {
    public static void main(String[] args) {
        System.out.println("1-查询部门员工");
        System.out.println("2-办理新员工入职");
        System.out.println("3-调整薪资");
        System.out.println("4-删除员工数据");
        System.out.println("请选择功能 :");
        Scanner in = new Scanner(System.in); //接收命令行输入
        int cmd = in.nextInt(); //获取用户输入的一个整数
        Command command = null;
        switch (cmd) {
            case 1:
                command = new PstmtQueryCommand();
                command.execute();
                break;
            case 2:
                command = new InsertCommand();
                command.execute();
                break;
            case 3:
                command = new UpdateCommand();
                command.execute();
                break;
            case 4:
                command = new DeleteCommand();
                command.execute();
                break;


        }
    }
}

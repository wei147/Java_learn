package hrapp;

import command.Command;
import command.PstmtQueryCommand;
import command.QueryCommand;

import java.util.Scanner;

public class HumanResourceApplication {
    public static void main(String[] args) {
        System.out.println("1-查询部门员工");
        Scanner in = new Scanner(System.in); //接收命令行输入
        int cmd = in.nextInt(); //获取用户输入的一个整数
        switch (cmd){
            case 1:
                Command command = new PstmtQueryCommand();
                command.execute();
                break;

        }
    }
}

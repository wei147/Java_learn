package com.wei.interview.jdbc;

public class StatementSample {
    public void findByEname1(String ename){
        String driverName = "com.mysql.jdbc.Driver";
        String URL = "jdbc:mysql://127.0.0.1:3306/scott";

    }

    public static void main(String[] args) {
        StatementSample sample = new StatementSample();
        sample.findByEname1("wei");
    }
}

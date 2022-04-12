package com.imooc.reflect.entity;

/**
 * 员工实体类
 */
public class Employee {
    //作为一个类被加载完成之后，会有一个初始化的工作。作为这个类的初始化是通过静态块来完成的
    static {
        System.out.println("Employee类已被加载到jvm,并被初始化");
    }

    private Integer eno;
    public String ename;
    private Float salary;
    private String dname;

    /**
     * 构造方法要求方法名称和类名和保持一致，默认构造方法不带有如何参数
     */
    public Employee() {
        System.out.println("Employee默认构造方法已被执行");
    }

    public Employee(Integer eno, String ename, Float salary, String dname) {
        this.eno = eno;
        this.ename = ename;
        this.salary = salary;
        this.dname = dname;
        System.out.println("Employee带参构造方法已被执行");
    }

    public Integer getEno() {
        return eno;
    }

    public void setEno(Integer eno) {
        this.eno = eno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getDname() {
        return dname;
    }

    //ctrl + shift + a 唤出action面板，输入toString     重写
    @Override
    public String toString() {
        return "Employee{" +
                "eno=" + eno +
                ", ename='" + ename + '\'' +
                ", salary=" + salary +
                ", dname='" + dname + '\'' +
                '}';
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Employee updateSalary(Float val) {
        this.salary = this.salary + val;
        System.out.println(this.ename + "调薪至" + this.salary + "元");
        return this;    //将自己本身作为返回值
    }


}

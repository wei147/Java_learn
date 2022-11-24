package com.wei.interview.sorter;

import java.util.*;

public class ListSorter {
    public static void main(String[] args) {
        //如何按工资升序进行排列
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee("xiao m", 24, 3900f));
        employees.add(new Employee("yang", 25, 3800f));
        employees.add(new Employee("wei", 24, 5900f));

        Collections.sort(employees, new Comparator<Employee>() {
            @Override  //compare返回是一个正数的话,代表 o1大于o2
            public int compare(Employee o1, Employee o2) {
                return (int) (o2.getSalary() - o1.getSalary());
            }
        });
        //对已有的employees了一个调整,直接输出即可
        System.out.println(employees);
    }
}

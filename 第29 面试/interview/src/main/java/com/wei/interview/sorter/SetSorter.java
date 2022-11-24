package com.wei.interview.sorter;

import java.util.Comparator;
import java.util.TreeSet;

public class SetSorter {
    public static void main(String[] args) {
        TreeSet<Employee> employees = new TreeSet<Employee>();
//        TreeSet<Employee> employees = new TreeSet<Employee>(new Comparator<Employee>() {
//            @Override
//            public int compare(Employee o1, Employee o2) {
//                return (int) (o2.getSalary() - o1.getSalary());
//            }
//        });
        employees.add(new Employee("xiao m", 24, 3900f));
        employees.add(new Employee("yang", 25, 3800f));
        employees.add(new Employee("wei", 22, 5900f));
        System.out.println(employees);
    }
}

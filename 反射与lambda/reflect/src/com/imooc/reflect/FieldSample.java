package com.imooc.reflect;

import com.imooc.reflect.entity.Employee;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 利用Field对成员变量赋值/取值
 */
public class FieldSample {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("com.imooc.reflect.entity.Employee");
            Constructor constructor = employeeClass.getConstructor(new Class[]{
                    Integer.class, String.class, Float.class, String.class
            });
            Employee employee = (Employee) constructor.newInstance(new Object[]{
                    105, "李五", 6900f, "批发部"
            });
            //只能获取到public定义的成员变量
            Field enameField = employeeClass.getField("ename");
            //get 和 set 第一个参数都是要操作什么样的对象
            enameField.set(employee, "李武");
            String ename = (String) enameField.get(employee);
            System.out.println("ename:" + ename);
            //NoSuchMethodException 没有找到对应成员变量时抛出的异常
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

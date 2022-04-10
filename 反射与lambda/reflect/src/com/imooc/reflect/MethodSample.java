package com.imooc.reflect;

import com.imooc.reflect.entity.Employee;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 利用Method方法类调用
 */
public class MethodSample {
    public static void main(String[] args) {
            try {
                Class employeeClass = Class.forName("com.imooc.reflect.entity.Employee");
                Constructor constructor = employeeClass.getConstructor(new Class[]{
                        Integer.class, String.class, Float.class, String.class
                });
                Employee employee = (Employee) constructor.newInstance(new Object[]{
                        103, "小梅", 5800f, "设计部"
                });
                Method updateSalaryMethod = employeeClass.getMethod("updateSalary", new Class[]{
                        Float.class
                });
                //执行对应方法 updateSalaryMethod.invoke(); 有两个参数：1.执行哪个对象 2.对象数组(参数类型列表)  invoke()返回的是一个Obj
                Employee employee1 = (Employee) updateSalaryMethod.invoke(employee, new Object[]{1400f});
                //作为一个对象打印时会自动调用toString方法
                System.out.println(employee1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

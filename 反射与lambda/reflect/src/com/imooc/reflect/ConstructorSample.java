package com.imooc.reflect;

import com.imooc.reflect.entity.Employee;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 利用带参构造方法创建对象
 */
public class ConstructorSample {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("com.imooc.reflect.entity.Employee");
            try {
                Constructor constructor = employeeClass.getConstructor(new Class[]{     //得到指定格式的构造方法
                        Integer.class, String.class, Float.class, String.class
                });
                Employee employee =  (Employee) constructor.newInstance(new Object[]{
                        101, "夏明", 5900f, "批发部"
                });
                System.out.println(employee);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                //当被调用的方法的内部抛出了异常而没有被捕获时
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            //没有找到与之对应格式的方法
            e.printStackTrace();
        }
    }
}












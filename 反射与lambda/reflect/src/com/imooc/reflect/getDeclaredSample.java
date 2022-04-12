package com.imooc.reflect;

import com.imooc.reflect.entity.Employee;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 获取对象所有成员变量值
 */
public class getDeclaredSample {
    public static void main(String[] args) {
        try {
            Class employeeClass = Class.forName("com.imooc.reflect.entity.Employee");
            Constructor constructor = employeeClass.getConstructor(new Class[]{
                    Integer.class, String.class, Float.class, String.class
            });
            Employee employee = (Employee) constructor.newInstance(new Object[]{
                    103, "小梅", 5800f, "设计部"
            });
            // getDeclaredFields() 会返回一个Field数组，代表当前类所有的成员变量
            Field[] fields = employeeClass.getDeclaredFields();
            for (Field field : fields) {
//                System.out.println(field.getName());
                if (field.getModifiers() == 1) {      //public修饰
                    Object val = field.get(employee);
                    System.out.println(field.getName() + ":" + val);
                } else if (field.getModifiers() == 2) { //private修饰
                    String methodName = "get" + field.getName().substring(0,1).toUpperCase()
                            + field.getName().substring(1); // get + get后面第一个字母的大写+除大写后面的字符 即调用Employee中的get方法
                    Method getMethod = employeeClass.getMethod(methodName);
                    Object ret = getMethod.invoke(employee);    //这里不能强制类型转换。因为返回的类型不一样
                    System.out.println(field.getName() + ":" + ret);
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

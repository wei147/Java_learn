package com.imooc.reflect;

import com.imooc.reflect.entity.Employee;

/**
 * Class类的实例
 */
public class ClassSample {
    public static void main(String[] args) {
        try {
            //Class.forName() 方法将指定的类加载到jvm,并返回对应Class对象
            Class employeeClass = Class.forName("com.imooc.reflect.entity.Employee"); //得到Employee这个类的class对象
            System.out.println("Employee已被加载到jvm");
            //newInstance通过默认构造方法创建新的对象
            Employee emp = (Employee) employeeClass.newInstance();   //newInstance() 返回的是一个Object(Object是所有对象的父类)。实际使用时要进行类型的强制转换
            System.out.println(emp);
            /**
             * ClassNotFoundException   类名与类路径书写格式错误是抛出“类无法找到异常”    比如写错路径名
             * InstantiationException   非法访问异常，当做作用域外访问对象方法或成员变量时抛出     public的方法改成私有private
             * IllegalAccessException   对象无法被实例化，抛出“实例化异常”  比如改成抽象类abstract
             */
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

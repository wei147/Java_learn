package com.wei.spring.instance;

/**
 * Person静态工厂
 */
public class PersonStaticFactory {
    public static Person createPerson(String name, int age) {
        //对Person实例化并返回Person对象
        return new Person(name, age);
    }
}

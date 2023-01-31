package com.wei.spring.instance;

/**
 * Person实例工厂
 *
 * Person实例工厂和静态工厂最大的区别就是 在工厂方法上是否增加static关键字
 * (不增加static,必须对PersonFactory类进行实例化以后才能调用 createPerson())
 */
public class PersonFactory {
    public Person createPerson(String name, int age) {
        //对Person实例化并返回Person对象
        return new Person(name, age);
    }
}

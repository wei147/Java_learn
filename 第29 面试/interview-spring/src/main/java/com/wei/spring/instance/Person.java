package com.wei.spring.instance;

public class Person {
    private String name;
    private Integer age;

    //无参默认构造方法
    public Person() {
        System.out.println("Person默认构造函数");
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;

        System.out.println("Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}');
    }
}

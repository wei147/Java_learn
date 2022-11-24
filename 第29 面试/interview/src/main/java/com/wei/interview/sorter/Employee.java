package com.wei.interview.sorter;

//要实现自然排序就需要在employee中实现一个Comparable接口
//TreeSet的两种排序方式。原理是红黑树? 比较返回的是-1则放在红黑树的左边,即降序排列。反之放在右边,升序
public class Employee implements Comparable<Employee> {

    private String name;

    private Integer age;

    private Float salary;

    public Employee() {

    }

    //带三个参数的构造方法,能快速创建employee对象
    public Employee(String name, Integer age, Float salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }


    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int compareTo(Employee o) {
        //如果前面比后面大则返回1。 如果前面比后面小则返回-1。 两者相等则返回零,代表取前面的元素
        return o.getAge().compareTo(this.getAge());  //这样是降序。要用相同的属性进行比较
//        return this.getAge().compareTo(o.getAge());
    }
}

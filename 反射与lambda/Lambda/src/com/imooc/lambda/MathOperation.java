package com.imooc.lambda;

/**
 * 四则运算接口
 */
@FunctionalInterface    //通知编译器这是函数式接口，进行抽象方法检查
public interface MathOperation {
    //需要实现的方法operate();
    public Float operate(Integer a, Integer b);
//    public Float operate1(Integer a, Integer b);
}

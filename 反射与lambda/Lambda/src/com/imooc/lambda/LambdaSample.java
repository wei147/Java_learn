package com.imooc.lambda;

public class LambdaSample {

    public static void main(String[] args) {
        //1.标准使用方式
        //约束条件 : Lambda表达式只能实现有且只有一个抽象方法的接口,Java称为"函数式接口"
        MathOperation addition = (Integer a, Integer b) -> {
            System.out.println("加法运算");
            return a + b + 0f;  //+0f 隐式的转化为Float类型
        };
        System.out.println(addition.operate(6, 9));


        //2.Lambda允许忽略参数类型
        MathOperation subtraction = (a, b) -> {
            System.out.println("减法运算");
            return a - b + 0f;
        };
        System.out.println(subtraction.operate(9, 6));

        //3.单行实现代码可以省略大括号和return
        MathOperation multiplication = (a, b) -> a * b + 0f;
        System.out.println("乘法运算");
        System.out.println(multiplication.operate(6, 8));
    }
    //         传统代码
    /**
     *          class Addition implements MathOperation{
     *
     *          @Override public Float operate(Integer a, Integer b) {
     *          System.out.println("加法运算");
     *          return a+b+0f;
     *          }
     *          }
     *          Addition addition = new Addition();
     *          System.out.println(addition.operate(6,8));
     */

}

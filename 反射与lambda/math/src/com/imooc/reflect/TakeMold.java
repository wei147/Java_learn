package com.imooc.reflect;

/**
 * 取模运算
 */
public class TakeMold implements MathOperation {
    @Override
    public float operate(int a, int b) {
        System.out.println("执行取模运算 ：");
        return a % b;
    }
}

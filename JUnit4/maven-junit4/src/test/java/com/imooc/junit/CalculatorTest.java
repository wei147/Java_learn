package com.imooc.junit;

import org.junit.Test;

public class CalculatorTest {
    private Calculator cal = new Calculator();
    //1.与原方法保持一致
    //2.在原方法前增加test前缀

    @Test
    public void testAdd() {
        System.out.println(cal.add(1,2));
    }

    @Test
    public void testSubtract() {
        System.out.println(cal.subtract(1,2));
    }

    @Test
    public void testMultiply() {
        System.out.println(cal.multiply(2,2));
    }

    @Test
    public void testDivide() {
        System.out.println(cal.divide(1,0));
    }
}
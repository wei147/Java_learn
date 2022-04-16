package com.imooc.junit;

import junit.framework.TestCase;
import org.junit.Test;

public class CalculatorTest {
    Calculator cal = new Calculator();

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
        System.out.println(cal.divide(1,2));
    }
}
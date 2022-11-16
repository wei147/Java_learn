package com.wei.interview;

/**
 * String与字符串常量池
 *
 * 考察的是我们对于字符串底层存储的了解,我们都知道对于字符串它一旦创建以后就是final修饰的,也就是不可变。
 * 同时字符串默认会保存在我们方法区中特定开辟的区域叫做常量池。
 * 那么当字符串创建好了以后,常量池就会出现这个字符串的常量,不同的String对象引用相同的字符串的时候,其实就是指向同一个我们字符串的内存地址
 */

public class StringAndStringConstantPool {
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abc";
        String s3 = "abc"+"def";
        String s4 = "abcdef";
        //s2算是一个引用类型,对于引用类型,java、编译器在编译期间是无法确定它的数值的,
        // 也就意味着我们无法使用编译器对它进行优化,只有在运行时这个s2才能确定具体的值,因此即使是s2=abc它加上def之后,
        // 它会产生一个新的内存地址同时分配给s5,因为s4、s5它们的内存地址是不同的,所以返回是false,
        String s5 = s2+"def";
        //s1是存放在常量池中的常量,而s6 new String("abc")它创建的字符串对象是不会在常量池中的保存的,存储的地址不同,
        String s6 = new String("abc");

        //s1 == s2 就是在比较内存地址。
        // 那equals()方法比较的是字符串的内容是否相同
        //s1和s2都指向"abc" 这个地址
        System.out.println(s1 ==s2); //true
        System.out.println(s3 ==s4); //true
        System.out.println(s4 ==s5); //false
        System.out.println(s4.equals(s5)); //true？
        System.out.println(s2==s6); //false
        System.out.println(s2.equals(s6)); //true
    }


}

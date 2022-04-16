package com.imooc.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream流对象的五种创建方式
 */
public class StreamGenerator {
    //1.基于数组进行创建
    @Test
    public void generator1() {
        String[] arr = {"wei", "chen", "yang", "wu"};
        //只有创建了流对象才能进行一系列的流式处理来简化对数据的操作
        Stream<String> stream = Stream.of(arr);   //因为数组里每个都是String,所以范型也是String
        //s也是String类型
        stream.forEach(s-> System.out.println(s));
    }

    //2.基于集合进行创建
    @Test
    public void generator2() {
        List<String> list = new ArrayList<>();
        list.add("wei");
        list.add("chen");
        list.add("yang");
        list.add("wu");
        //这里为list自带的方法
        Stream<String> stream = list.stream();
        stream.forEach(s-> System.out.println(s));
    }

    //3.利用generate方法创建无限长度流
    @Test
    public void generator3() {
        Stream<Integer>stream =  Stream.generate(()->new Random().nextInt(100));   //new Random().nextInt(1000));返回的是一个整形
        stream.limit(10).forEach(i-> System.out.println(i));
    }

    //4.基于迭代器创建流
    @Test
    public void generator4(){
        Stream<Integer> stream = Stream.iterate(1,n->n+1);   //从1开始，每次增长为1
        stream.limit(10).forEach(i-> System.out.println(i));
    }

    //4.基于迭代器创建流
    @Test
    public void generator5(){
        String str = "abcdefg魏";
        IntStream stream = str.chars();
        //将int转为string类型
        stream.forEach(c -> System.out.println((char)c));
    }
}

package com.imooc.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMethod {
    //提取集合中所有偶数并求和
    @Test
    public void case1() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        int sum = list.stream() //获取stream对象
                .mapToInt(s -> Integer.parseInt(s))       //mapToInt将流中每一个数据转为整数
                .filter(n -> n % 2 == 0)  //filter对流数据进行过滤
                .sum();     //求和
        System.out.println(sum);
    }

    //所有名字首字母大写
    @Test
    public void case2() {
        List<String> list = Arrays.asList("wei", "chen", "yang", "hu");
        List<String> newList = list.stream()
                //map()  按规则对每一个流数据进行转换
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                //.forEach(s-> System.out.println(s));
                //collect对流数据进行收集，生成新的List/Set      注：Set会将重复数据自动清除
                .collect(Collectors.toList());
        System.out.println(newList);
    }

    //将所有奇数从大到小进行排列，且不许出现重复
    @Test
    public void case3() {
        List<Integer> list = Arrays.asList(2, 8, 4, 5, 7, 8, 9, 11, 3);
        List<Integer> newList = list.stream().distinct()    //去除重复的流数据
                .filter(n -> n % 2 == 1)
                .sorted((a,b)->b-a)     //a表示前一个数,b表示后一个数，b-a表示从大到小排序   流数据排序
                .collect(Collectors.toList());
        System.out.println(newList);
    }
}











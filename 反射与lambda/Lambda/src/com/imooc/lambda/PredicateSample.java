package com.imooc.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 理解函数式编程
 * Predicate函数式接口的使用方法
 */
public class PredicateSample {
    public static void main(String[] args) {
        Predicate<Integer> predicate = n -> n > 4;
        boolean result = predicate.test(9);
        System.out.println(result);
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
/*        for (int i = 0; i < 10; i++) {
            if (list.get(i) % 2 == 1) {
                System.out.println(list.get(i));
            }
        }*/
        filter(list, n -> n % 2 == 1);  //取所有的奇数
        filter(list, n -> n % 2 == 0);  //取所有的偶数
        filter(list, n -> n > 5 && n % 2 == 0);  //取所有大于6的偶数

//        怎么在打印目标数字的前面打印提醒，即在Lambda内部执行打印
//        filter(list, n -> {
//            n % 2 == 0;
//            System.out.println();
//            return false;
//        });  //取所有的偶数
    }

    public static void filter(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer num : list) {
            if (predicate.test(num)) {
                System.out.print(num + " ");
            }
        }
        System.out.println(" ");
    }
}

package com.imooc.lambda;

import java.util.Random;
import java.util.function.Function;

/**
 * 利用Function函数式接口生成定长随机字符串
 */
public class FunctionSample {
    public static void main(String[] args) {
        // Function<T, R>
        Function<Integer, String> randomStringFunction = r -> {
            String chars = "abcdefghigk1234567890";
            StringBuffer stringBuffer = new StringBuffer();
            Random random = new Random();
            for (int i = 0; i < r; i++) {
//                random.nextInt(chars.length()); 生成0到数组长度的值 比如 int x=new Random.nextInt(10);则x为一个0~9的任意整数
                int position = random.nextInt(chars.length());
                /**
                 *         String s = "www.runoob.com";
                 *         char result = s.charAt(6);
                 *         System.out.println(result);
                 *         打印结果: n
                 *         charAt() 方法用于返回指定索引处的字符。索引范围为从 0 到 length() - 1。
                 */
                stringBuffer.append(chars.charAt(position));
            }
            return stringBuffer.toString();
        };
        String randomstring = randomStringFunction.apply(6);
        System.out.println(randomstring);
    }
}

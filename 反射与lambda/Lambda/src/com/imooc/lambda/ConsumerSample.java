package com.imooc.lambda;

import java.util.function.Consumer;


/**
 * Consumer接口的使用
 */
public class ConsumerSample {
    public static void main(String[] args) {
        output(s -> System.out.println("来自诗经的句子: " + s));
        output(s -> {
            System.out.println("向XXX网站发送数据包: " + s);
        });
    }

    public static void output(Consumer<String> consumer) {
        String text = "关关雉鸠,在河之洲";
        consumer.accept(text);
    }
}

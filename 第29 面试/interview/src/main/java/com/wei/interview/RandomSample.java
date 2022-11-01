package com.wei.interview;

import java.util.Random;

public class RandomSample {
    public Integer randomInt1() {
        int min = 30;
        int max = 100;

        // nextInt(70)方法 含义是 生成随机数的范围是 0~70
        int result = new Random().nextInt(max - min) + min;
        return result;
    }

    public Integer randomInt2() {
        int min = 30;
        int max = 100;

        // Math.random()用于生成从0~1的随机小数
        int result = (int) (Math.random() * (max - min));
        //int result = (int) (Math.random() * (max - min))+min; 讲课视频的写法
        return result;
    }

    public static void main(String[] args) {
        System.out.println("randomInt1 :" + new RandomSample().randomInt1());
        System.out.println("randomInt2 :" + new RandomSample().randomInt2());
    }
}


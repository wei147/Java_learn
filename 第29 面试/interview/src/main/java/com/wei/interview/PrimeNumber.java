package com.wei.interview;

public class PrimeNumber {
    public void isPrimeNumber() {
        int max = 100;
        int count = 0;
        for (int i = 2; i < max; i++) {
            boolean flag = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.println(i);
                count++;
            }
        }
        System.out.println("总数是 "+count);
    }

    public static void main(String[] args) {
        new PrimeNumber().isPrimeNumber();
    }
}

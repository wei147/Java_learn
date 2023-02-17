package com.wei.spring.test;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
//        List<String> a = null;
//        test(a);
//        System.out.println(a.size());
        int compare = Compare('a', 'b');
        System.out.println(compare);
    }

    public static void test(List<String> a) {
        a = new ArrayList<String>();
        a.add("abc");
    }

    public static int Compare(char a, char b) {
        if (a > b) {
            return 1;
        } else if (a == b) {
            return 0;
        } else {
            return -1;
        }
    }
}

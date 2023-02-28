package com.wei.interview.createthreads;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 利用定时器新建线程。  Timer定时器其底层也是继承了 Thread类
 */
public class TimerDemo {
    public static void main(String[] args) {
        //打印主线程
        System.out.println(Thread.currentThread().getName());
        Timer timer = new Timer();
        //定时的执行内容
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        },1000,1000);
    }
}

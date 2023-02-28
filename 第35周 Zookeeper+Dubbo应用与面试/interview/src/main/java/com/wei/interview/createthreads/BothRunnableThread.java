package com.wei.interview.createthreads;

/**
 * 同时使用Runnable和Thread类两种方式来实现线程
 */
public class BothRunnableThread {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("实现Runnable接口的方式");
            }
        }){
            @Override
            public void run() {
                //当子类去覆盖父类方法的时候,那么它将会执行的是子类的内容,
                System.out.println("来自Thread");
            }
        };
        t1.start();
    }
}

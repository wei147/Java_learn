package com.wei.interview.createthreads;

import java.util.concurrent.Callable;

public class RunnableStyle extends Thread implements Runnable, Callable {
    public static void main(String[] args) {

        Thread thread = new Thread(new RunnableStyle());
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("用Runnable付费实现线程");
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {
        return null;
    }
}

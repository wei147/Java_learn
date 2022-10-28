package threadpool;

import java.util.concurrent.Executors;

/**
 * 每一个任务创建一个线程
 */
public class ForLoop {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Task());
            //启动线程
            thread.start();
        }
//        Executors.newFixedThreadPool();
    }

    static class Task implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            System.out.println("执行了方法");
        }
    }
}

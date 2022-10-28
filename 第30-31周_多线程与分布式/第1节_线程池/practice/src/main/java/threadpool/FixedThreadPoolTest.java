package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示 newFixedThreadPool
 */
public class FixedThreadPoolTest {
    public static void main(String[] args) {
        //固定的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(4); //线程池创建成功
        for (int i = 0; i < 40; i++) {
            executorService.execute(new Task());
        }
    }

    static class Task implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }
}

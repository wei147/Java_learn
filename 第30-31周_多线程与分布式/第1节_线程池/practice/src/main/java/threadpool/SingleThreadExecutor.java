package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 简单的线程池,这个线程池里只有一个固定的线程,
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 20; i++) {
            executorService.execute(new FixedThreadPoolTest.Task());
        }
    }
}

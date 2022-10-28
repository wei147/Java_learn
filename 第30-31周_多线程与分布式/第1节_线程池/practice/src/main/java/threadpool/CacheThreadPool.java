package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheThreadPool {
    public static void main(String[] args) {
        //线程几乎是可以无限创建的,如果线程数量特别多任务数量特别多,也有可能会导致oom,
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            executorService.execute(new FixedThreadPoolTest.Task());
        }
    }
}

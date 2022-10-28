package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示newFixedThreadPool出错的情况
 */
public class FixedThreadPoolOOM {
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new SubThread());
        }
    }
}

class SubThread implements Runnable{

    @Override
    public void run() {
        try {
            //这个任务的特点就是一直在睡觉。因为不想这个任务被执行完毕,因为被执行完毕之后,这个任务就结束了进入下一个任务,
            // 我们的目标是把这个队列塞满,随着时间增加,队列里的任务越来越多才可能触发OOM错误
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 演示关闭线程池
 */
public class ShuntDown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ShuntDownTask());
        }

        Thread.sleep(1500);
        //shutdownNow() 的威力是比较大的,列队内的还没来得及执行的任务则会被返回(runnableList)[一般会做进一步的处理,比如记录或者是重新执行]
        List<Runnable> runnableList = executorService.shutdownNow();
        System.out.println(runnableList.toString());
//        executorService.shutdown();
//        //这3秒钟会阻塞并返回一个布尔值。(三秒钟之内没有没有完全停止,还没有停止就打印false)
//        boolean b = executorService.awaitTermination(7, TimeUnit.SECONDS);
//        System.out.println(b);
//        System.out.println((executorService.isShutdown()));
//        executorService.shutdown(); //这里用了 shutdown,后面再加入任务就会报错 RejectedExecutionException
//        executorService.execute(new ShuntDownTask());
//        System.out.println((executorService.isShutdown()));
//        System.out.println("isTerminated: "+executorService.isTerminated());
//        Thread.sleep(9000);
//        System.out.println("isTerminated: "+executorService.isTerminated());
    }

    static class ShuntDownTask implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                //在睡眠期间假设有中断信号过来,会抛出这个异常
//                e.printStackTrace();
                System.out.println(Thread.currentThread().getName()+"被中断了");
            }
        }
    }
}

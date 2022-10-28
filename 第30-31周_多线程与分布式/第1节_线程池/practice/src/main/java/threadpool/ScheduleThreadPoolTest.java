package threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPoolTest {
    public static void main(String[] args) {
        //
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
        //这里的放任务和刚才的不太一样
        threadPool.schedule(new FixedThreadPoolTest.Task(), 3, TimeUnit.SECONDS); //延迟3秒才执行 TimeUnit.SECONDS是指定单位为秒

        //以一定频率重复运行,(开始时间是1秒后,然后是隔3秒运行一次,)
        threadPool.scheduleAtFixedRate(new FixedThreadPoolTest.Task(), 1, 3, TimeUnit.SECONDS);
    }
}

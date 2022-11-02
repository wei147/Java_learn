package threadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1000个打印日期的任务,用线程池来执行  (只新建一次 SimpleDateFormat。但是问题也出现了.会产生线程安全问题)
 */

public class ThreadLocalNormalUsage03 {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    //只新建一次 SimpleDateFormat (能解决重复创建对象、浪费资源的问题)
    //但问题也随之出现 打印了两个 1970-01-01 08:15:33 为什么会出现这种问题? [所有线程都共用一个simpleDateFormat对象。发生了线程安全问题]
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalNormalUsage03().date(finalI);
                    System.out.println("time:  " + date);
                }
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        //参数的单位是毫秒,从1970.1.1 00:00:00  GMT计时
        Date date = new Date(1000 * seconds);
        return dateFormat.format(date);
    }
}

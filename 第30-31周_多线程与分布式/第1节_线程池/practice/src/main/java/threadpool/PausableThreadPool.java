package threadpool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可暂停的线程池。演示每个任务执行前后放钩子函数  (钩子函数在设计模式中也是经常会被用到的)
 */

//继承于普通的线程池
public class PausableThreadPool extends ThreadPoolExecutor {

    //标记位。就是用来标记该线程是不是现在处于暂停状态的,,,,
    private boolean isPaused;
    //为了让这个布尔值的并发修改是安全的,我们给它上一把锁
    private final ReentrantLock lock = new ReentrantLock();
    private Condition unPaused = lock.newCondition();


    public PausableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public PausableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public PausableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public PausableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    //钩子函数登场,实际上是一个重写的方法。
    // 在每一次执行这个任务之前都会调用这个函数并且在这个函数中它会去检查 isPaused是不是想暂停了,如果是会让当前这个线程暂停,而不再继续执行 unPaused.await();
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        lock.lock();
        try {
            //在方法内部做一个检测:如果确实是true,说明我们确实想让它暂停
            while (isPaused) {
                unPaused.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    //暂停方法
    private void pause() {
        //在这里锁住之后,我们运用这样的锁锁住之后,一定要记得这样的锁一定要用try catch finally,否则可能会出现这个锁永远不释放的情况,
        lock.lock();
        try {
            isPaused = true;
        } finally {
            lock.unlock();
        }
    }

    //恢复函数
    public void resume() {
        lock.lock();
        //把之前暂停的去唤醒
        try {
            isPaused = false;
            unPaused.signalAll(); //唤醒全部的方法
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        PausableThreadPool pausableThreadPool = new PausableThreadPool(10, 20, 10l,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("被执行了");
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 1000; i++) {
            pausableThreadPool.execute(runnable);
        }
        try {
            Thread.sleep(1500);
            pausableThreadPool.pause();
            System.out.println("线程池被暂停了");
            Thread.sleep(1500);
            pausableThreadPool.resume();
            System.out.println("线程池被恢复了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

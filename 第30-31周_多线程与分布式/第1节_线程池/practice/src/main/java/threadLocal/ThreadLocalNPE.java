package threadLocal;

/**
 * 演示空指针异常  NPE是空指针异常的缩写
 */
public class ThreadLocalNPE {
    ThreadLocal<Long> longThreadLocal = new ThreadLocal<Long>();

    public void set() {
        longThreadLocal.set(Thread.currentThread().getId());
    }

    /**
     * ThreadLocal<Long> longThreadLocal =new ThreadLocal<Long>(); 这里的泛型指定的是Long,
     * 包装类型的一个长整形。这里get()方法拿到的是一个包装类型Long[按ctrl看],
     * 而把对象类型Long转为基本类型long,由于这个对象本身是空,所以在这里转化的时候会形成空指针异常,
     * 并不是ThreadLocal的问题,我们ThreadLocal照常是返回[longThreadLocal.get();]一个null空,
     * 但是呢它并没有返回空指针异常,也就是如果我们在获取get的时候,如果提前没有进行赋值无论是用initialValue还是set都没有进行赋值的话,
     * 默认ThreadLocal是不会抛出空指针异常的,只不过会返回一个null而已。(转换不当才返回异常,装箱拆箱导致的)
     */
    public Long get() { //这里小写的long会报空指针异常。大写的Long 可以输出 null
        return longThreadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalNPE threadLocalNPE = new ThreadLocalNPE();
        //没有任何人给它赋值,这里拿到的是一个空对象
        System.out.println("直接get: " + threadLocalNPE.get());
        //创建子线程
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocalNPE.set();
                System.out.println(threadLocalNPE.get());
            }
        });
        thread1.start();
    }
}

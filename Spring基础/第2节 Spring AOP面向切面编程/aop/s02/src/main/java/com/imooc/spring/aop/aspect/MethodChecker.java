package com.imooc.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MethodChecker {
    //ProceedingJoinPoint 是JoinPoint的升级版，在原有功能外，还可以控制目标方法是否执行
    public Object check(ProceedingJoinPoint pjp) throws Throwable {
        try {
            long startTime = new Date().getTime();  //得到起始时间    （这里可以视为方法执行前可以执行的操作）
            Object ret = pjp.proceed();//执行目标方法  （Object对象其本质是目标方法执行后的返回值）
            long endTime = new Date().getTime();    //方法执行后的结束时间    （这里以下可以视为方法执行后可以执行的操作）
            long duration = endTime - startTime;    //执行时间
            if (duration >= 1000) {
                String className = pjp.getTarget().getClass().getName();
                String methodName = pjp.getSignature().getName();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
                String now = sdf.format(new Date());
                System.out.println("=======" + now + ":" + className + "." + methodName + "(" + duration + "ms)=======");
            }
            return ret;
        } catch (Throwable throwable) {
            // 为什么这里要使用 throw throwable; 将异常抛出去呢？
            // 因为在系统中未来运行时可能并不只有一个通知，如果在当前的环绕通知中对这个异常进行了消化，
            // 那就意味着其他后续的处理都不会捕捉到这个异常，就可能会产生一些意料之外的问题。
            // 所以一般使用throw对捕获到的异常进行抛出
            System.out.println("Exception message: "+throwable.getMessage());
            throw throwable;
        }
    }
}

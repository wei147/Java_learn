package com.imooc.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Date;

public class MethodChecker {
    //ProceedingJoinPoint 是JoinPoint的升级版，在原有功能外，还可以控制目标方法是否执行
    public Object check(ProceedingJoinPoint pjp){
        try {
            long startTime = new Date().getTime();  //得到起始时间
            Object ret = pjp.proceed();//执行目标方法  （Object对象其本质是目标方法执行后的返回值）
            long endTime = new Date().getTime();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

package com.imooc.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *如何配置切面类？
 */

@Component //1.标记当前类为组件，这样Ioc容器会对其进行实例化和管理
@Aspect    //2.说明当前类是切面类，可以用于功能的拓展
public class MethodChecker {
    //3.环绕通知的注解,环绕通知，参数为PointCut切点表达式
    @Around("execution(* com.imooc..*Service.*(..))")   //一般采用环绕通知进行更细粒度的控制
//    @After()
//    @Before()
//    @AfterReturning()
//    @AfterThrowing
    //ProceedingJoinPoint是JoinPoint的升级版,在原有功能外,还可以控制目标方法是否执行
    public Object check(ProceedingJoinPoint pjp) throws Throwable {
        try {
            long startTime = new Date().getTime();
            Object ret = pjp.proceed();//执行目标方法
            long endTime = new Date().getTime();
            long duration = endTime - startTime; //执行时长
            if(duration >= 1000){
                String className = pjp.getTarget().getClass().getName();
                String methodName = pjp.getSignature().getName();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
                String now = sdf.format(new Date());
                System.out.println("=======" + now + ":" + className + "." + methodName + "(" + duration + "ms)======");
            }
            return ret;
        } catch (Throwable throwable) {
            System.out.println("Exception message:" + throwable.getMessage());
            throw throwable;
        }
    }
}

package com.imooc.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;

import java.text.SimpleDateFormat;
import java.util.Date;

// 切面类 (方法的切面)
public class MethodAspect {
    //切面方法，用于拓展额外功能
    //JoinPoint 连接点，通过连接点可以获取目标类/方法的信息
    public void printExecutionTime(JoinPoint joinPoint) {   //打印执行时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"); //SSS代表毫秒
        String now = sdf.format(new Date());
        String className = joinPoint.getTarget().getClass().getName(); //获取目标类的名称
        String methodName = joinPoint.getSignature().getName();//获取目标方法名称   （getSignature）目标执行的方法是哪个
        //getArgs 获取方法调用时传入的参数
        Object[] args = joinPoint.getArgs();
        System.out.println("---->参数个数: "+args.length);
        for (Object arg:args){
            System.out.println("---->参数: "+arg);
        }

        //在方法运行前，来将对应的类和方法名称在控制台进行打印
        System.out.println("---->" + now + ":" + className + "." + methodName);

    }

    // ret代表目标方法执行的返回值
    public void doAfterReturning(JoinPoint joinPoint,Object ret){
        System.out.println("<-------返回后通知: "+ret);
    }

    //Throwable 是所有异常的父类。为了捕获目标方法所抛出的异常
    public void daAfterThrowing(JoinPoint joinPoint,Throwable th){
        System.out.println("<-------异常通知: "+th.getMessage());

    }
    //after() 无法获取到目标方法运行时所产生的返回值或者是内部抛出的异常
    public void doAfter(JoinPoint joinPoint){
        System.out.println("<-----触发后置通知");
        String methodNameTest = joinPoint.getSignature().getName();
//        System.out.println(methodNameTest+"      after方法");
    }
}

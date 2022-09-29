package com.imooc.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 打印请求和响应信息
 */
@Aspect
@Component
public class WebLogAspect {

    //定义好log类来记录日志
    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);
    //编写将要拦截的内容和拦截点

    //该方法指定拦截点
    @Pointcut("execution(public * com.imooc.mall.controller.*.*(..))")  //拦截controller包下面所有的
    public void webLog() {

    }

    //在这个拦截点的前和后分别进行拦截
    //在此之前的是请求信息

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {  //joinPoint记录的是类的信息,比如说方法信息
        //收到请求,记录请求内容
        //除此之外还想得到请求信息,所以这里利用RequestContextHolder
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //用log类来记录日志
        log.info("URL : " + request.getRequestURI().toString());  //请求url
        log.info("HTTP_METHOD : " + request.getMethod()); //方法 get/post
        log.info("IP : " + request.getRemoteAddr());      //ip
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());  //获取到一些类的信息加上签名信息
        //参数可能不止1个,需要用到数组,然后用toString() 将其转为字符串
        log.info("ARGS(参数) : " + Arrays.toString(joinPoint.getArgs()));
    }


    //在此之后的是响应信息
    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //处理完请求,返回内容
        //ObjectMapper()是由jackson提供的把对象转为json的工具
        log.info("RESPONSE : " + new ObjectMapper().writeValueAsString(res));
    }
}

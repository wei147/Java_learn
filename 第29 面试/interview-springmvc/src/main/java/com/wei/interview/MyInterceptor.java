package com.wei.interview;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class MyInterceptor implements HandlerInterceptor {

    //会在运行controller方法前之前先进入这个方法,,,
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入 preHandle 方法..." + request.getRequestURL().toString());
        return true;
    }


    //上面preHandle为true的情况下会正常送达postHandle。false则请求中断,不再执行
    //在controller方法执行完但是在视图渲染之前执行,,,
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("进入 postHandle 方法..." + request.getRequestURL().toString());
    }


    //afterCompletion 无论前面返回True还是false都会执行
    //前两个方法执行之后才进入的后置方法
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("进入 afterCompletion 方法..." + request.getRequestURL().toString());
    }
}

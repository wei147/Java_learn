package com.imooc.restful.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//拦截器
//作为这个类要实现一个接口,拦截器必须要实现的接口。这个接口包含了三个接口要进行实现
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURL()+"准备执行");
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        //如果这里是return true,那我们的请求就会被送达给后面的拦截器或者控制器。
        //return false的话，当前的请求就会被阻止，直接产生响应，返回客户端了。
        //这个return的结果，决定了我们的请求是继续向后执行还是立即结束返回响应
        return true;
    }

    //目标资源被处理成功之后，但是没有产生响应文本之前，所要做的事情
    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(request.getRequestURL()+"目标处理成功");
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(request.getRequestURL()+"响应内容已产生");
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

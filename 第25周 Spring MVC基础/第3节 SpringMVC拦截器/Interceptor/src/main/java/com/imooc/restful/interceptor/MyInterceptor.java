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

        //可以对于某个url进行前置检查，通过就直接放行；不通过的在拦截器中进行处理，直接进行响应
//        response.getWriter().print("[不通过]");
//        response.getWriter().print("[]");     这个开启之后，直接就是访问服务器响应500了？无法正常拿到数据
        return true;
        //return false 更像是一个阻断器，原本应该执行的所有请求和后续处理都会被阻断，同时响应在当前方法中直接产生.
        //为false时，控制台只打印了  http://localhost:8080/restful/persons准备执行
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

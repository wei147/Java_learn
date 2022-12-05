package com.wei.course.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 请求处理后的过滤器
 */
@Component
public class PostRequestFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        //过滤器请求的一个位置 （这里拿的是别人定义好的响应的默认常量 1000-1代表在它之前执行,快一些?）
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext(); //上下文的概念。可以往里面存取东西
        Long startTime = (Long) currentContext.get("startTime");//key value的形式获取当前时间
        Long endTime = System.currentTimeMillis();
        Long result = endTime - startTime;
        String requestURI = currentContext.getRequest().getRequestURI();
        System.out.println("处理用时 : "+result);
        System.out.println("uri : "+requestURI);
        return null;
    }
}

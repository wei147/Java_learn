package com.wei.course.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 记录请求时间  （前置过滤器）
 */
@Component //加Component注解好让spring识别这是一个filter
public class PreRequestFilter extends ZuulFilter {
    @Override
    public String filterType() {
        //过滤器的类型
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //过滤器的顺序。在过滤器比较多的情况下,过滤器顺序比较关键
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否启动过滤器。(在这里可以进行比较复杂的逻辑判断)
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //我们要写的业务代码
        RequestContext currentContext = RequestContext.getCurrentContext(); //上下文的概念。可以往里面存取东西
        currentContext.set("startTime",System.currentTimeMillis()); //key value的形式存储当前时间
        System.out.println("过滤器已经记录时间");
        return null;
    }
}

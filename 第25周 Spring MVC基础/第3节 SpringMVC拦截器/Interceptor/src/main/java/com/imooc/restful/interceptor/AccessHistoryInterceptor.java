package com.imooc.restful.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//访问历史拦截器
//所有请求在被处理之前，要被这个拦截器所记录，所以这属于一个前置处理
public class AccessHistoryInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(AccessHistoryInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //当拿到了用户请求以后，应该把数据存放在哪？通常这些用户访问的额外信息，我们会通过日志文件来单独进行保存
        //对内容进行输出和打印 这个内容应该包含哪些信息？
        StringBuffer log = new StringBuffer();
        log.append(request.getRemoteAddr());    //远程用户的IP地址
        log.append("|");
        log.append(request.getRequestURL());    //用户访问的url网址
        log.append("|");
        log.append(request.getHeader("user-agent"));    //用户的环境
        logger.info(log.toString());
        return true;
    }
}

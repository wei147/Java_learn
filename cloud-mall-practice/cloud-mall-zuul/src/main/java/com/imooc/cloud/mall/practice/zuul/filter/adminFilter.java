package com.imooc.cloud.mall.practice.zuul.filter;

import com.imooc.cloud.mall.practice.zuul.feign.UserFeignClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.wei.cloud.mall.practice.user.model.pojo.User;
import com.wei.cloud.mall.practice.user.service.UserService;
import com.wei.mall.practice.common.common.Constant;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理员鉴权过滤器
 */
@Component
public class adminFilter extends ZuulFilter { //首先会继承Zuul过滤器实现相关的方法

    @Resource
    UserFeignClient userFeignClient;

    @Override
    public String filterType() {
        //1.首先选择类型 前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //2.顺序保持为默认的0
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //3.是否应当启用过滤器? 什么时候应当启用,什么时候不该
        //(1).先通过上下文获取到url
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //(2)获取到当前的网址
        String requestURI = request.getRequestURI();
        //(3)根据uri分情况决定通过还是不通过
        if (requestURI.contains("adminLogin")) {
            return false;
        }
        if (requestURI.contains("admin")) {
            return true;
        }
        return false;
    }

    @Override //run方法的含义是 一旦符合过滤器的规则(上面的 return true;),需要做哪些条件的鉴权
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            //这个方法的含义是 就不需要通过网关再去发送
            currentContext.setSendZuulResponse(false);
            //把将要返回的内容直接写下来。(直接返回给前端)
            currentContext.setResponseBody("{\n" +
                    "    \"status\": 10010,\n" +
                    "    \"msg\": \"NEED_LOGIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            //设定返回的状态码
            currentContext.setResponseStatusCode(200);
            return null;    //一但用户为空(还没有登录的情况下)就可以停止了,不进行后面的判断
        }
        Boolean adminRole = userFeignClient.checkAdminRole(currentUser);
        if (!adminRole ) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("{\n" +
                    "    \"status\": 10011,\n" +
                    "    \"msg\": \"NEED_ADMIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
        }
        return null;

    }
}

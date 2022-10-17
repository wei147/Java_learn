package com.wei.oa_spring.config;

import com.wei.oa_spring.filter.UserFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * User 过滤器的配置 (com/imooc/mall/filter/UserFilter.java)
 */
@Configuration
public class UserFilterConfig {
    //想要配置一个Filter有两步:  1.把这个Filter给定义出来  2.是我们的这个Filter放到我们整个的过滤器的链路中去
    @Bean   //要加一个@Bean才能识别到
    public UserFilter userFilter() {
        return new UserFilter();
    }

    @Bean(name = "userFilterConf") //bean的名字不能设置成和类名一样否则就冲突了
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(userFilter());
        //设置需要拦截的、需要用户登录权限的url
        filterRegistrationBean.addUrlPatterns("/leaveForm/*");
        filterRegistrationBean.addUrlPatterns("/notice/*");
        filterRegistrationBean.addUrlPatterns("/user/*");
        //给过滤器配置设置一个名字,以便于区分不同的配置
        filterRegistrationBean.setName("userFilterConfig");
        return filterRegistrationBean;
    }
}

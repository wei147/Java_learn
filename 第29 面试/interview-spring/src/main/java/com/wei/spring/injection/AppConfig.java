package com.wei.spring.injection;

import com.wei.spring.injection.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  //指定是java config配置文件
public class AppConfig {
    //对于当前的这个方法来说,ioc容器在初始化的时候,会执行下面的userDAO()方法,并且将这个方法产生的
    // 对象命名为"userDAO",放入ioc容器中
    @Bean(name = "userDAO")
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }

    @Bean(name = "userService")
    public UserService userService(){
        UserService userService = new UserService();
        userService.setUserDAO(this.userDAO());
        return userService;
    }

}

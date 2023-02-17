package com.wei.spring.scope;

import org.springframework.beans.factory.annotation.Autowired; //第三方的
import javax.annotation.Resource;   //java官方的

//@Service("userService")  //这里不写也行,默认就是userService
public class UserService {
//    @Resource  //优先按照beanId来加载对象
    @Autowired
    UserDAO userDAO;
    /**
     * 二.@Autowired和@Resource他们的机制是不同的:
     * 1.@Autowired只能按照类型在ioc中进行匹配
     * 2.而@Resource 要只能得多,@Resource可以设置name这个属性来指定beanId。匹配规则是如果设置了name属性
     * 则按照beanId进行精准匹配。如果没有设置name的话,优先将属性名字作为beanId在ioc容器中进行查找,
     * 如果没有的话再按照类型(UserDAO)在ioc容器中进行查找
     */

    public UserService() {
        System.out.println(this + "已创建");
    }

    //通过Setter方法注入
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
        System.out.println(this + "正在调用Setter方法注入,setUserDao" + userDAO + ")");
    }

    //业务方法
    public void createUser() {
        System.out.println(this + "正在调用UserService.createUser()方法");
        userDAO.insert();
    }
}

package com.imooc.spring.ioc.service;

import com.imooc.spring.ioc.dao.IUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentService {
    /**
     * 1. @Resource 设置name属性，则按name在Ioc容器中将bean注入
     * 2. @Resource未设置name属性
     * 2.1 以属性名作为bean  name在Ioc容器中匹配bean，如有匹配则注入
     * 2.2 按属性名未匹配，则按类型进行匹配，同@Autowired。需加入@Primary解决冲突
     * 使用建议：在使用@Resource对象时推荐设置name或保障属性名与bean名称一致
     */
//    @Resource(name = "userOracleDao")   //非常明确的在运行时将Ioc容器将 userOracleDao 注入到udao中
//    private IUserDao udao;

    @Resource
    private IUserDao udao;     //通过规范属性名的方式，和上边有一样的效果。  没有设置bean name时。它会按照属性名进行优先匹配

    public void joinDepartment(){
        //打印出udao的类型取决于 @Primary 是放在UserDao还是UserOracleDao。（没有准确匹配到时，@Primary优先级最高 ）
        System.out.println(udao);
    }

}

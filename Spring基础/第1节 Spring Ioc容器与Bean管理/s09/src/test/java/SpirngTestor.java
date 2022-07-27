import com.imooc.spring.ioc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 两个注解要配合使用
 */
//将Junit4的执行权交由spring Test，在测试用例执行前自动化初始化Ioc容器
@RunWith(SpringJUnit4ClassRunner.class) //用于接管junit4的控制权
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //用于说明要加载哪个配置文件
//@ContextConfiguration(locations = {"classpath:applicationContext_tag.xml"}) //突然不明白@Resource怎么用的，加这个配置文件测试一下。用在具体方法上？
public class SpirngTestor {
    @Resource
    private UserService userService;

    @Test
    public void testUserService(){
        userService.createUser();
    }
}

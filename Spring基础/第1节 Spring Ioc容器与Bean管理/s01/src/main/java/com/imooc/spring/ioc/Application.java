package com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import com.imooc.spring.ioc.entity.Child;

public class Application {
    public static void main(String[] args) {
        Apple apple1 = new Apple("红富士", "红色", "欧洲");
        Apple apple2 = new Apple("青苹果", "绿色", "中亚");
        Apple apple3 = new Apple("金帅", "黄色", "中国");

        //苹果对象被创建好之后需要和孩子产生关系
        Child lily = new Child("莉莉",apple1);
        Child wei = new Child("WEI",apple2);
        Child YanFei = new Child("烟绯",apple3);

        lily.eat();
        wei.eat();
        YanFei.eat();

        //弊端：
        //1.比如苹果的信息字段是写死的，只能通过修改源码进行修改（苹果信息可能随着季节变化），这样就必须要重新上线重新发布
        //2.对象的数量是固定写死的，写了三个只能创建三个对象
        //3.最重要一点，对象是硬关联：孩子和苹果的关系已经是确定了，这个确定关系是在程序编译时就完成的。例如：烟绯长大了想尝试红富士，那么就必须要修改源代码
        //而这一切背后的根源，就是在我们程序中使用了new关键字——在编译时就将对象和对象之间进行了强制的绑定。如何解决这个问题Spring Ioc容器就应运而生了。
        // 作为Spring它最根本的目的就是让我们通过配置的形式完成对对象的实例化以及对象和对象之间的依赖关系
        //下一讲，用IOC重构代码
    }
}

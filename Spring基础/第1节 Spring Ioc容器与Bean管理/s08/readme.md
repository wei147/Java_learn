基于Java Config配置Ioc容器

第三种spring ioc的配置方式 ：java Config	（Spring 3.0后推出）
主要原理是使用Java代码来替代传统的xml文件

<基于Java Config的优势>
完全摆脱XML的束缚，使用独立Java类管理对象与依赖
注解配置相对分散，利用Java Config可对配置集中管理
可以在编译时进行依赖检查，不容易出错	(像xml配置的时候，对象创建和属性1的注入都放在xml中，只有在运行起来之后才能看到配置是否正确)

即用Java类来替代原始的xml文件
package com.imooc.spring.ioc.context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//实现applicationContext 接口并且完成ioc容器的创建过程
public class ClassPathXmlApplicationContext implements ApplicationContext {
    //Map键值对的结构。键对应了beanId，而值就是容器创建过程中产生的对象
    private Map iocContainer = new HashMap();

    //增加对应的默认构造方法
    public ClassPathXmlApplicationContext() {
        //在初始化的时候读取XML文件    getResource()方法用于从classpath下获取指定的文件资源,之后通过getPath() 方法得到applicationContext.xml的路径
        try {
            String filePath = this.getClass().getResource("/applicationContext.xml").getPath();
            System.out.println("处理前 :" + filePath);   //配置文件的物理地址
            //如果路径中含有中文可能会发生路径找不到的情况，所以还需要进行URL的解码
            filePath = new URLDecoder().decode(filePath, "UTF-8");
            System.out.println("处理后 :" + filePath);
            //获取到xml路径，如何对其进行解析？
            //SAXReader去加载解析这个filePath所对应的xml
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(filePath));    //新建一个文件对象再提供给read进行读取解析，得到对应的XML文档对象
            List<Node> beans = document.getRootElement().selectNodes("bean");//拿到根节点下的所有bean标签,返回一个列表，每一项都是一个节点
            for (Node node : beans) {
                Element ele = (Element) node;       //作为每一个beans，实际类型为Element
                //读取当前节点对应的属性
                String id = ele.attributeValue("id");
                String className = ele.attributeValue("class");
                //拿到对应id和class，如何对Apple这个类进行实例化？    反射技术
                Class c = Class.forName(className);     //拿到对应的类对象
                Object obj = c.newInstance();    //通过默认构造方法创建Apple类实例
                //获取bean下面的property标签
                List<Node> properties = ele.selectNodes("property");
                for (Node p:properties){
                    Element property = (Element) p;
                    String propName = property.attributeValue("name");
                    String propValue = property.attributeValue("value");

                    //拼合成 setTitle   (通过setter方法注入)
                    String setMethodName = "set"+propName.substring(0,1).toUpperCase()+propName.substring(1);
                    System.out.println("准备执行 "+setMethodName+"方法注入数据");
                    /**
                     * Method Class.getMethod(String name, Class<?>... parameterTypes)的作用是获得对象所声明的公开方法
                     * 该方法的第一个参数name是要获得方法的名字，第二个参数parameterTypes是按声明顺序标识该方法形参类型。
                     * person.getClass().getMethod("Speak", null);
                     * //获得person对象的Speak方法，因为Speak方法没有形参，所以parameterTypes为null
                     */
                    Method setMethod = c.getMethod(setMethodName, String.class);
                    //通过setter方法注入数据
                    setMethod.invoke(obj,propValue); //调用这个方法。两个参数：1.要执行哪个对象的set方法？ 2.调用set方法需要传入字符串（value属性）
                }

                //将id和object放入其中    (放入Map中)
                iocContainer.put(id,obj);    //创建对象的职责已经完成   beanId对应一个Object对象
            }
            System.out.println(iocContainer);
            System.out.println("Ioc容器初始化完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object getBean(String beanId) {
        return iocContainer.get(beanId);   //对指定beanId进行提取
//        return null;
    }
}

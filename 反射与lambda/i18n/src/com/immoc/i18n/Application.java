package com.immoc.i18n;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

public class Application {
    public static void say(){
        Properties properties = new Properties();
        //获取配置文件的完整路径
        String configPath = Application.class.getResource("/config.properties").getPath();
//        System.out.println(configPath);   /D:/project/Jave_learn/%e5%8f%8d%e5%b0%84%e4%b8%8elambda/i18n/out/production/i18n/config.properties
        //获取到不包含任何特殊编码的配置文件路径
        try {
            configPath = new URLDecoder().decode(configPath,"UTF-8");
            //properties.load(); 需要传入一个InputStream  输入流，因为内容来自文件所以需要FileInputStream
            properties.load(new FileInputStream(configPath));
            //得到放在配置文件中的language    返回值是一个字符串，类似通过key得到value
            String language = properties.getProperty("language");
            //通过Class.forName加载language类    并通过默认构造方法实例化对象
            I18n i18n = (I18n)Class.forName(language).newInstance();
            System.out.println(i18n.say());
//            System.out.println(configPath);   /D:/project/Jave_learn/反射与lambda/i18n/out/production/i18n/config.properties
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.say();
    }
}

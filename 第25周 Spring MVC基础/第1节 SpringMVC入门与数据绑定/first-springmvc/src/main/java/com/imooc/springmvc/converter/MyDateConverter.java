package com.imooc.springmvc.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//日期转换器  Converter<String, Date> Date是要转换的目标类型  希望把前台输入的字符串转换为Date日期类型 String是原始字符串
public class MyDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) { //s代表原始的，从界面输入的字符串。返回值是经过转换的Date对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(s);
            return d;
        } catch (ParseException e) {
            return null;
        }
    }
}

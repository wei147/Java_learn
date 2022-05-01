package com.imooc.oa.utils;
import org.junit.Test;

public class MybatisUtilsTestor {
    @Test
    public void testcase1() {
        String result = (String) MybatisUtils.executeQuery(sqlSession -> {
            String out = sqlSession.selectOne("test.sample");
            //这里的return out; 返回的是MybatisUtils.java里的Object obj，
            // 所以如果需要String类型的话就需要强制类型转换
            return out;
        });
        System.out.println(result);
    }

    @Test
    public void testcase2() {
        String result = (String) MybatisUtils.executeQuery(sqlSession ->sqlSession.selectOne("test.sample"));
        System.out.println(result);
    }

}

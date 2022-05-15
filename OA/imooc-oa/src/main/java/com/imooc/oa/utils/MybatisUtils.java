package com.imooc.oa.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

/**
 * 在mybatis-config.xml写好的配置使用该工具类对其进行加载解析、初始化工作
 */

public class MybatisUtils {
    //利用static（静态）属于类不属于对象，且全局唯一
    private static SqlSessionFactory sqlSessionFactory = null;

    //利用静态块在初始化类时实例化sqlSessionFactory
    static {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            //初始化错误时，通过抛出异常ExceptionInInitializerError通知调用者 （初始化异常）
            throw new ExceptionInInitializerError(e);
        }
    }


    /**
     * 执行Select 查询语句的代码块
     *
     * @param func 要执行查询语句的代码块
     * @param func
     * @return 查询结果
     * 注意：这里的Function是package java.util.function; 包含的是函数式接口（Lambda是基于此进行开发）
     * Function<T,R>对应有一个输入参数且需要返回数据的功能代码
     * @return
     */
    public static Object executeQuery(Function<SqlSession, Object> func) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Object obj = func.apply(sqlSession);
            return obj;
        } finally {
            //用try...finally的设计保证了，一定要执行关闭连接 （回收连接池）
            sqlSession.close();
        }
    }

    /**
     * 执行insert/update/delete写操作SQL
     * 新增、修改、删除等写入操作就需要对事务进行控制，作为事务来说，如果我们代码处理成功则需要将事务进行提交(commit),如果失败则需要进行回滚
     * 在原有executeQuery/查询的基础上额外增加了对事务的控制
     *
     * @param func 要执行的写操作代码块
     * @return 写操作后返回的结果
     */
    public static Object executeUpdate(Function<SqlSession, Object> func) {
//        SqlSession sqlSession = sqlSessionFactory.openSession(false); //原来课程是这个
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        sqlSession.commit();
        try {
            Object obj = func.apply(sqlSession);
            return obj;
            //如果有RuntimeException 运行时异常,则进行回滚
        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw e;
        } finally {
            //用try...finally的设计保证了，一定要执行关闭连接 （回收连接池）
            sqlSession.close();
        }
    }
}

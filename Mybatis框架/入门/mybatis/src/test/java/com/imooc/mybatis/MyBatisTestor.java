package com.imooc.mybatis;

import com.imooc.mybatis.dto.GoodsDTO;
import com.imooc.mybatis.entity.Goods;
import com.imooc.mybatis.entity.GoodsDetail;
import com.imooc.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

//JUint单元测试类
public class MyBatisTestor {
    @Test
    public void testSqlSessionFactory() throws IOException {
        //利用Reader加载classpath下的mybatis-config.xml核心配置文件
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        //初始化SqlSessionFactory对象，同时解析mybatis-config.xml文件              （注）getResource表示拿到文本，AsReader按字符流的形式进行读取
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        System.out.println("SessionFactory加载成功");
        SqlSession sqlSession = null;
        try {
            //创建SqlSession对象，SqlSession是JDBC的拓展类，用于与数据库交互
            sqlSession = sqlSessionFactory.openSession();
            //创建数据库连接（测试用）
            Connection connection = sqlSession.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                //如果type="POOLED",代表使用连接池,close则是将连接回收到连接池
                //如果type="UNPOOLED",close则会调用Connection.close()方法关闭连接
                sqlSession.close();
            }
        }
    }

    @Test
    public void testMyBatisUtils() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //得到对应的数据库连接
            Connection connection = sqlSession.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectAll() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            //因为在good.xml中指定resultType="com.imooc.mybatis.entity.Goods" 是Goods，
            // 所以selectList返回的每一个对象都是Goods
            List<Goods> list = session.selectList("goods.selectAll");
            for (Goods g : list) {
                System.out.println(g.getTitle());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    @Test
    public void testSelectById() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 800);
            System.out.println(goods.getTitle());
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    @Test
    public void testSelectByPriceRange() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Map param = new HashMap();
            param.put("min", 100);
            param.put("max", 500);
            param.put("limt", 10);
            List<Goods> list = session.selectList("selectByPriceRange", param);
            for (Goods g : list) {
                System.out.println(g.getTitle() + ":" + g.getCurrentPrice());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    @Test
    public void testSelectGoodsMap() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            List<Map> list = session.selectList("goods.selectGoodsMap");
            for (Map map : list) {
                //这里打印出来的map都是字段的原始名称，即数据表定义的字段，而非实体类定义的字段
                System.out.println(map);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    @Test
    public void testSelectGoodDTO() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            List<GoodsDTO> list = session.selectList("goods.selectGoodsDTO");
            for (GoodsDTO g : list) {
                System.out.println(g.getGoods().getTitle() + "   " + g.getCategory().getCategoryName());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }

    }

    @Test
    public void testInsert() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = new Goods();
            goods.setTitle("测试商品");
            goods.setSubTitle("测试子标题");
            goods.setOriginalCost(200f);
            goods.setCurrentPrice(100f);
            goods.setDiscount(0.5f);
            goods.setIsFreeDelivery(1);
            goods.setCategoryId(49);
            //insert() 方法返回值代表本次成功插入的记录总数
            int num = session.insert("goods.insert", goods);
            session.commit();   //提交事务数据
            System.out.println(goods.getGoodsId() + "成功条数：" + num);
        } catch (Exception e) {
            if (session != null) {
                session.rollback(); //回滚事务
            }
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    @Test
    public void testSelectByTitle() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Map param = new HashMap();
            param.put("title", "'' or 1=1 or title ='兰多 升级版时尚妈咪包 桔色'");
            param.put("order", "order by title desc");

//            param.put("title", "'兰多 升级版时尚妈咪包 桔色'");
            /**
             * ${} 原文传值 会变成SQL语句的一部分
             *  select * from t_good where title = '' or 1=1 or title ='兰多 升级版时尚妈咪包 桔色'  ( '' or 1=1  永远为真)
             */
            /**
             * ${} 预编译
             *  select * from t_good where title = '' or 1=1 or title ='兰多 升级版时尚妈咪包 桔色'  select的是这整个字符串
             */
            List<Goods> list = session.selectList("goods.selectByTitle", param);
            for (Goods g : list) {
                //这里打印出来的map都是字段的原始名称，即数据表定义的字段，而非实体类定义的字段
                System.out.println(g.getTitle() + "  " + g.getCurrentPrice());
            }
        } catch (Exception e) {
            if (session != null) {
                session.rollback(); //回滚事务
            }
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    @Test
    public void testUpdate() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 739);
            System.out.println("更改前 ： " + goods.getTitle());
            goods.setTitle("更新测试商品名");
            int num = session.update("goods.update", goods);
            System.out.println(num + "更改后： " + goods.getTitle());
        } catch (Exception e) {
            if (session != null) {
                session.rollback(); //回滚事务
            }
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    @Test
    public void testDelete() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 740);
            System.out.println("要删除的是 ：" + goods.getTitle());
            int num = session.delete("goods.delete", 740);
            session.commit();   //提交事务数据
            System.out.println(num);
        } catch (Exception e) {
            if (session != null) {
                session.rollback(); //回滚事务
            }
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }


    /**
     * 多条件查询检索
     */

    @Test
    public void testDynamicSQL() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Map param = new HashMap();
            param.put("categoryId", 45);
            param.put("currentPrice", 500);
            //查询条件
            List<Goods> list = session.selectList("goods.dynamicSQL", param);
            for (Goods g : list) {
                System.out.println(g.getTitle() + ":" +
                        g.getCategoryId() + ":" + g.getCurrentPrice());
            }
        } catch (Exception e) {
            if (session != null) {
                session.rollback(); //回滚事务
            }
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /**
     * 印证一级缓存的存在以及SqlSession的不同会创建不同的一级缓存（一个会话中同样的查询语句内存地址是相同的，
     * 两个会话中内存地址是不同的）
     * 一级缓存的生存周期就在一个SqlSession对象中
     */
    @Test
    public void testLV1Cache() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 800);
            session.commit();   //commit提交时对该namespace缓存强制清空
            Goods goods1 = session.selectOne("goods.selectById", 800);
            System.out.println(goods.hashCode() + "   :    " + goods1.hashCode());  //这里打印的hashCode是一样的

        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }

        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 800);
            Goods goods1 = session.selectOne("goods.selectById", 800);
            System.out.println(goods.hashCode() + "   :    " + goods1.hashCode());  //这里打印的hashCode是一样的

        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /**
     * 测试二级缓存
     */
    @Test
    public void testLV2Cache() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 800);
            System.out.println(goods.hashCode());

        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }

        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 800);
            System.out.println(goods.hashCode());  //这里打印的hashCode是一样的

        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /**
     * OneToMany对象关联查询（一对多）
     */
    @Test
    public void testSelectOneToMany() {
        //测试java for循环简写    for(数据类型 表示符 : 表达式){....}
        List<Integer> num_list = Arrays.asList(18, 20, 4, 7);
        for (Integer num : num_list) {
            System.out.println(num);
        }
        Stream<Integer> stream = num_list.stream();
        stream.forEach(l -> System.out.println(l));

        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            List<Goods> list = session.selectList("goods.selectOneToMany");
            for (Goods goods : list) {
//                System.out.println(goods.getTitle() + " ---  " + goods.getGoodsDetails().size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /**
     * 测试ManyToOne对象关联查询（多对一）
     */
    @Test
    public void testSelectManyToOne() {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            List<GoodsDetail> list = session.selectList("goodsDetail.selectManyToOne");
            for (GoodsDetail gd : list) {
                System.out.println(gd.getGdPicUrl() + " ---  " + gd.getGoods().getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }
}

















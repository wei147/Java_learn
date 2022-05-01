package com.imooc.oa.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DruidDataSourceFactory extends UnpooledDataSourceFactory {
    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }

    @Override
    //重写父类方法
    //这里的DataSource是package javax.sql;提供的一个接口，
    // 这个接口要求所有的数据源都要实现这个接口。因为这里使用的是Druid所以返回的数据源接口
    //具体类型是DruidDataSource
    public DataSource getDataSource() {
        try {
            //上文中是this.dataSource = new DruidDataSource();对象，
            // 所以这里的也要强制类型转换
            ((DruidDataSource) this.dataSource).init();  //初始化Druid数据源
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.dataSource;
    }
}

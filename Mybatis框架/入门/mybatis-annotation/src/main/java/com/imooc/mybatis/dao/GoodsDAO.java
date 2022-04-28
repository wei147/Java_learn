package com.imooc.mybatis.dao;

import com.imooc.mybatis.dto.GoodsDTO;
import com.imooc.mybatis.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsDAO {
    //把原先写在xml文件中的SQL语句写到程序中
    @Select("select * from t_goods where current_price between #{min} and #{max} order by current_price limit 0,#{limit}")
    public List<Goods> selectByPriceRange(@Param("min") Float min, @Param("max") Float max, @Param("limit") Integer limit);

    @Insert("INSERT INTO t_goods(title, sub_title, original_cost, current_price, discount, is_free_delivery, category_id) VALUES (#{title} , #{subTitle} , #{originalCost}, #{currentPrice}, #{discount}, #{isFreeDelivery}, #{categoryId})")
    //<SelectKey 插入完成之后执行,设置goods_id为主键     resultType设置为整形，要传入包装对象的类>
    @SelectKey(statement = "select last_insert_id()", before = false, keyProperty = "goodsId", resultType = Integer.class)
    public int insert(Goods goods);

    @Select("select * from t_goods")
    //Results 相当于 <resultMap>
    @Results({
            //这个Result 相当于 <id>
            @Result(column = "goods_id", property = "goodsId", id = true),
            //这个Result 相当于 <result>
            @Result(column = "current_price", property = "currentPrice")
    })
    public List<GoodsDTO> selectAll();
}


//    @Select("select * from t_goods where current_price between  #{min} and #{max} order by current_price limit 0,#{limt}")
//    public List<Goods> selectByPriceRange(@Param("min") Float min ,@Param("max") Float max ,@Param("limt") Integer limt);
//
//    @Insert("INSERT INTO t_goods(title, sub_title, original_cost, current_price, discount, is_free_delivery, category_id) VALUES (#{title} , #{subTitle} , #{originalCost}, #{currentPrice}, #{discount}, #{isFreeDelivery}, #{categoryId})")
//    //<selectKey>
//    @SelectKey(statement = "select last_insert_id()" , before = false , keyProperty = "goodsId" , resultType = Integer.class)
//    public int insert(Goods goods);
//
//    @Select("select * from t_goods")
//    //<resultMap>
//    @Results({
//            //<id>
//            @Result(column = "goods_id" ,property = "goodsId" , id = true) ,
//            //<result>
//            @Result(column = "title" ,property = "title"),
//            @Result(column = "current_price" ,property = "currentPrice")
//    })
//    public List<GoodsDTO> selectAll();
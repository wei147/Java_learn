package com.imooc.jedis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class CacheSample {
    //数据初始化的工作
    public CacheSample() {
        //list里面所有的对象都是 商品信息 Integer goodsId, String goodsName, String description, Float price
        List<Goods> goodsList = new ArrayList<Goods>();

        Jedis jedis = new Jedis("192.168.10.102", 6379);
        try {
            //思路：将这些javabean 序列化为一个个json字符串，将其保存到redis里。用的时候再还原为java对象
            goodsList.add(new Goods(808, "小米手环", "一款运动手环", 99.9f));
            goodsList.add(new Goods(809, "跑步机配饰", "运动器材", 89.9f));
            goodsList.add(new Goods(8010, "进口奶粉", "来自新西兰", 79.9f));
            goodsList.add(new Goods(8011, "百乐P500", "一款考试用笔", 69.9f));
            jedis.auth("1234");
            jedis.select(8);
            for (Goods goods:goodsList){
                String json = JSON.toJSONString(goods);
                System.out.println(json);
                String key = "goods:"+goods.getGoodsId();
                jedis.set(key,json);
            }
            System.out.println("写入成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static void main(String[] args) {
        new CacheSample();
    }
}

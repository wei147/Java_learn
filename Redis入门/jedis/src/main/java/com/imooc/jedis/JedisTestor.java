package com.imooc.jedis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisTestor {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.10.102", 6379);
        try {
            jedis.auth("1234");
            jedis.select(2);

//            Set<String> str = jedis.keys("*");
//            System.out.println(str);
//            List<String> value = jedis.lrange("listkey", 0, -1);
//            System.out.println(value);
            System.out.println("redis 连接成功");
            //字符串
            jedis.set("sn", "10086");
            String sn = jedis.get("sn");
            System.out.println(sn);
            jedis.mset(new String[]{
                    "title", "迎击", "action", "铁马冰河入梦来", "time", "公元1200"
            });
            List<String> list = jedis.mget(new String[]{"sn", "action", "title", "time"});
            Long num = jedis.incr("sn");    //自增 +1
            System.out.println(num);
            System.out.println(list);

            //Hash
            jedis.hset("student:1433", "name", "小飞");
            String student = jedis.hget("student:1433", "name");  //要传入两个参数，key和属性才能拿到值
            System.out.println(student);
            Map<String, String> studentMap = new HashMap();
            studentMap.put("name", "火狐");
            studentMap.put("age", "19");
            studentMap.put("id", "2000");
            jedis.hmset("student:1999", studentMap);
            //这里的两个String是因为Map数据类型的键和值都需要转为String类型？
            Map<String, String> text = jedis.hgetAll("student:1999");
//            System.out.println(text);

            //List
            jedis.del("letter"); //清除letter
            jedis.rpush("letter",new String[]{"e","d","z"});    //注意：这里每执行一次都会添加
            jedis.lpush("letter", new String[]{"a","b","c"});
//            List<String> letter = jedis.lrange("letter",0,-1);
            List<String> letter1 = jedis.lrange("letter",0,-1);
            jedis.lpop("letter");   //将左侧第一个元素移出 c
            jedis.rpop("letter");   //将右侧第一个元素移出 z
            List<String> letter = jedis.lrange("letter",0,-1);
            System.out.println(letter1);
            System.out.println(letter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }
}

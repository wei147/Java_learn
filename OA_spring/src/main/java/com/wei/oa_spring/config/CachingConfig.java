package com.imooc.mall.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * 缓存的配置类
 */

@Configuration
@EnableCaching
public class CachingConfig {
    //当返回RedisCacheManager之后,spring就会知道你对它进行了这样的配置,它会找到对应的bean进行注入
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter
                .lockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(30)); //这里设置的超时时间是30秒

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter,
                cacheConfiguration);
        return redisCacheManager;
    }

}

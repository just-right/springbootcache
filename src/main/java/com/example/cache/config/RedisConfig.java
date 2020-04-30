package com.example.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @className: RedisConfig
 * @description
 * @author: luffy
 * @date: 2020/4/29 23:27
 * @version:V1.0
 */
@Configuration
public class RedisConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisPool instance(){
        return new JedisPool();
    }
}

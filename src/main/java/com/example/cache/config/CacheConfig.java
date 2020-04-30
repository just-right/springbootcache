package com.example.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: CacheConfig
 * @description
 * @author: luffy
 * @date: 2020/4/29 23:24
 * @version:V1.0
 */
@Configuration
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager redisCacheManager(RedisConnectionFactory factory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(factory),
                this.getRedisCacheConfigurationWithTtl(30), // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = this.buildJackson2JsonRedisSerializer();
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public KeyGenerator cacheKeyGenerator() {
        return new StudentCacheKeyGenerator();
    }


    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put(CacheConst.CACHE_STUDENT_NAME, this.getRedisCacheConfigurationWithTtl(2));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = this.buildJackson2JsonRedisSerializer();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }


    private Jackson2JsonRedisSerializer<Object>  buildJackson2JsonRedisSerializer(){
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        return jackson2JsonRedisSerializer;
    }
}

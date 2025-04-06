package com.merck.library_management_system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */

@Configuration
public class RedisConfig {

    /**
     * Will be used to get RedisTemplate instances
     *
     * @param factory - factory
     * @param <K>     - key type
     * @param <V>     - value type
     * @return -RedisTemplate
     */
    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}

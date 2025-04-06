package com.merck.library_management_system.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void set() {
        redisTemplate.opsForValue().set("salary", "100000");
    }

    public String get() {
        return redisTemplate.opsForValue().get("salary");
    }
}

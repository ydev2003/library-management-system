package com.merck.library_management_system.controller;

import com.merck.library_management_system.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */
@RestController
@RequestMapping("/login")
public class TestController {

    private final RedisService redisTest;

    @Autowired
    public TestController(RedisService redisTest) {
        this.redisTest = redisTest;
    }

    @RequestMapping("/set")
    public void set() {
        redisTest.set();
    }

    @RequestMapping("/get")
    public String get() {
        return redisTest.get();
    }
}

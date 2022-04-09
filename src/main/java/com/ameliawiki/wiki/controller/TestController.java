package com.ameliawiki.wiki.controller;

import com.ameliawiki.wiki.domain.Test;
import com.ameliawiki.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Value("${test.hello}") //读取自定义配置项的注解 @Value("${test.hello: default}") --> 冒号后可+default value
    private String testHello;

    @Resource
    private TestService testService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * GET, POST, PUT, DELETE
     * @return
     * 如果只是简单的用@RequestMapping注解，表示这个接口支持所有的请求方式
     * 如果要使这个接口只支持GET请求的话 --> @GetMapping(user + parameter)
     * @PostMapping/ @PutMapping/ DeleteMapping
     * Http请求有8种
     */
    //"/hello"是接口地址，拼装的地址是:http://127.0.0.1:8080
    @RequestMapping("/hello") //通用注解
    public String hello() {
        return "Hello~ It's Me~" + testHello;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "Hello World! Post, " + name;
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }

    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable Long key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
        LOG.info("key: {}, value: {}", key, value);
        return "success";
    }

    @RequestMapping("/redis/get/{key}")
    public Object get(@PathVariable Long key) {
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key: {}, value: {}", key, object);
        return object;
    }
}

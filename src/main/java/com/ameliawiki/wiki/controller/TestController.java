package com.ameliawiki.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${test.hello}") //读取自定义配置项的注解 @Value("${test.hello: default}") --> 冒号后可+default value
    private String testHello;

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
}

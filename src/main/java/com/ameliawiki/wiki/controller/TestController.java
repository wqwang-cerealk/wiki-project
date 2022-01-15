package com.ameliawiki.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

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
        return "Hello~ It's Me~";
    }
}

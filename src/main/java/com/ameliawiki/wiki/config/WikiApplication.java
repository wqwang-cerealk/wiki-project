package com.ameliawiki.wiki.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("com.ameliawiki") //使得能够扫描com.ameliawiki里面的东西
//@ComponentScan({"com.ameliawiki", "com.test"}) --> 扫描多个package里的东西
@SpringBootApplication
@MapperScan("com.ameliawiki.wiki.mapper")
@EnableScheduling
@EnableAsync
public class WikiApplication {
    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    public static void main(String[] args) {
//        SpringApplication.run(WikiApplication.class, args);
        SpringApplication app = new SpringApplication(WikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("launch success!!!!!!!!YEA!!!");
        LOG.info("address: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}

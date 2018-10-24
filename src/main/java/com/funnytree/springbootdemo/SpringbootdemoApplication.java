package com.funnytree.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.funnytree.springbootdemo.dao")
@EnableTransactionManagement
@EnableScheduling
public class SpringbootdemoApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringbootdemoApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        SpringApplication.run(SpringbootdemoApplication.class, args);
    }
}

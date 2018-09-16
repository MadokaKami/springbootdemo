package com.funnytree.springbootdemo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootdemoApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringbootdemoApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        SpringApplication.run(SpringbootdemoApplication.class, args);
    }
}

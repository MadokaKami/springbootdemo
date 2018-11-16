package com.funnytree.springbootdemo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description springMvc配置类
 * @ClassName WebConfig
 * @author 李英夫
 * @since 2018/9/8 14:56
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Configuration
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)}, useDefaultFilters = false)
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.port}")
    private String port;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/welcome");
    }

    /**
     * 定制url匹配规则
     * @param configurer 路径匹配配置
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //通过重写configurePathMatch方法不再忽略路径中.符号后面的内容
        configurer.setUseSuffixPatternMatch(true);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cors").allowedOrigins("http://localhost:" + port);
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setUploadTempDir(new FileSystemResource("/tmp/uploads"));
        multipartResolver.setMaxUploadSize(2097152);
        multipartResolver.setMaxInMemorySize(0);
        return multipartResolver;
    }

}
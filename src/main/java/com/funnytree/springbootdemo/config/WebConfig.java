package com.funnytree.springbootdemo.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.Ordered;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)}, useDefaultFilters = false)
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.port}")
    private String port;

    /**
     * jsp视图解析器
     * @return InternalResourceViewResolver
     */
    /*@Bean
    public InternalResourceViewResolver htmlViewResolver() {
        InternalResourceViewResolver htmlViewResolver = new InternalResourceViewResolver();
        htmlViewResolver.setPrefix("/WEB-INF/jsp/");
        htmlViewResolver.setSuffix(".jsp");
        htmlViewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        htmlViewResolver.setContentType("text/html;charset=UTF-8");
        htmlViewResolver.setViewClass(JstlView.class);
        return htmlViewResolver;
    }*/

    /**
     * html视图解析器
     * @return InternalResourceViewResolver
     */
//    @Bean
//    public InternalResourceViewResolver htmlViewResolver(){
//        InternalResourceViewResolver htmlViewResolver = new InternalResourceViewResolver();
//        htmlViewResolver.setPrefix("/templates/views/html/");
//        htmlViewResolver.setSuffix(".html");
//        htmlViewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        htmlViewResolver.setContentType("text/html;charset=UTF-8");
//        return htmlViewResolver;
//    }

    /**
     * freemarker视图解析器
     * @return FreeMarkerViewResolver
     */
//    @Bean
//    public FreeMarkerViewResolver freeMarkerViewResolver(){
//        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
//        freeMarkerViewResolver.setPrefix("/templates/views/ftl/");
//        freeMarkerViewResolver.setSuffix(".ftl");
//        freeMarkerViewResolver.setCache(true);
//        freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
//        freeMarkerViewResolver.setOrder(2);
//        return freeMarkerViewResolver;
//    }

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
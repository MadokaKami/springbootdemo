package com.funnytree.springbootdemo.config;

import java.util.Collections;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.utility.XmlEscape;

@Configuration
public class MyConfig {
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        //freeMarkerConfigurer.setTemplateLoaderPath("WEB-INF/ftl");
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        freeMarkerConfigurer.setFreemarkerVariables(Collections.singletonMap("xml_escape",xmlEscape()));
        //这里需要设为空，前缀分别在html和freemarker视图解析器中设置即可，不需要在这里设置
        freeMarkerConfigurer.setTemplateLoaderPath("");
        Properties properties = new Properties();
        properties.setProperty("locale","zh_CN");
        properties.setProperty("datetime_format","yyyy-MM-dd HH:mm:SS");
        properties.setProperty("date_format","yyyy-MM-dd");
        properties.setProperty("number_format","#.##");
        //设置模板刷新时间，为0时不从缓存读取
        properties.setProperty("template_update_delay","0");
        freeMarkerConfigurer.setFreemarkerSettings(properties);
        return freeMarkerConfigurer;
    }

    /**
     * 转义字符支持
     * @return 转义字符支持类
     */
    private XmlEscape xmlEscape(){
        return new XmlEscape();
    }
}

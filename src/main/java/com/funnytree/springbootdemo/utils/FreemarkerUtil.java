package com.funnytree.springbootdemo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class FreemarkerUtil {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private ServletContext servletContext;

    private String realPath = null;

    private Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * 生成静态页面
     * @param staticPageName 静态页面名称
     * @param data 数据
     * @param templateFileName 模板对象名称
     */
    public void createStaticPage(String staticPageName , Map<String, Object> data, String templateFileName) {
        logger.info(templateFileName);
        try {
            if(realPath == null){
                realPath=servletContext.getRealPath("/WEB-INF") + "/views/ftl/created";
                File file = new File(realPath);
                if(!file.exists()){
                    file.mkdirs();
                }
            }
            Configuration cfg = freeMarkerConfigurer.getConfiguration();
            cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);// 设置标签
            cfg.setServletContextForTemplateLoading(servletContext, "/WEB-INF/views/ftl/model");// 设置临时加载目录。
            logger.info("输出路径为:{}", realPath);
            Template temp = cfg.getTemplate(templateFileName);// 获取模板对象
            Writer out = new OutputStreamWriter(new FileOutputStream(realPath + File.separator + staticPageName+(new Date()).getTime()+".html"), "UTF-8");
            temp.process(data, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}

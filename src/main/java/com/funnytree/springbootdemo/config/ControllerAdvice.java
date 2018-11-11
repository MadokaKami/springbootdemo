package com.funnytree.springbootdemo.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description controller增强器
 * @ClassName ControllerAdvice
 * @author 李英夫
 * @since 2018/9/9 17:38
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    /**
     * 初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder){
        // The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 全局异常捕获
     * @param ex 异常类型
     * @return 异常捕获后返回值集合
     */
    @ExceptionHandler(Exception.class)
    public Map exceptionHandler(Exception ex){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 100);
        map.put("msg", ex.getMessage());
        log.error("异常：{}", map.get("msg"));
        ex.printStackTrace();
        return map;
    }
}

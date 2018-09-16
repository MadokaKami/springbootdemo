package com.funnytree.springbootdemo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description 测试类controller
 * @ClassName TestController
 * @author 李英夫
 * @since 2018/9/9 17:51
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@RequestMapping("/test")
@Controller
public class TestController {

    @GetMapping("/toPage")
    public ModelAndView toPage(ModelAndView mod, HttpSession httpSession){
        System.out.println(httpSession.getAttribute("888"));
        httpSession.setAttribute("888", "666");
        mod.setViewName("test/testPage");
        return mod;
    }
}

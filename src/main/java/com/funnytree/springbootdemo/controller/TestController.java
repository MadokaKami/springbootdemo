package com.funnytree.springbootdemo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.funnytree.springbootdemo.entity.TestEntity;

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

    @GetMapping("toBootStrapTest")
    public ModelAndView toBootStrapTest(ModelAndView mod){
        mod.setViewName("bootstrapTest");
        return mod;
    }

    @RequestMapping("toModalPopHtml")
    public ModelAndView toModalPopHtml(ModelAndView mod){
        mod.setViewName("modalPopHtml");
        List<TestEntity> testList = new ArrayList<>();
        TestEntity testEntity1 = TestEntity.builder().testInt(1).testString("abcd").testDate(new Date()).build();
        testList.add(testEntity1);
        TestEntity testEntity2 = TestEntity.builder().testInt(2).testString("efgh").testDate(new Date()).build();
        testList.add(testEntity2);
        mod.addObject("testList", testList);
        return mod;
    }
}

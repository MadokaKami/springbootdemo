package com.funnytree.springbootdemo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funnytree.springbootdemo.entity.MiningInfo;
import com.funnytree.springbootdemo.entity.TestEntity;
import com.funnytree.springbootdemo.service.ReptileMineralsService;
import com.funnytree.springbootdemo.service.TestEntityService;
import com.funnytree.springbootdemo.utils.pagination.PagingQuery;
import com.funnytree.springbootdemo.utils.pagination.PagingResult;
import com.github.pagehelper.Page;

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

    @Autowired
    private TestEntityService testEntityService;

    @Autowired
    private ReptileMineralsService reptileMineralsService;

    @GetMapping("/toPage")
    public ModelAndView toPage(ModelAndView mod, HttpSession httpSession){
        System.out.println(httpSession.getAttribute("888"));
        httpSession.setAttribute("888", "666");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("我手机哦", "66600");
        map.put("dasda", "333搜索44");
        map.put("456啊啊啊0", "789");
        String json = null;
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        mod.addObject("json", json);
        mod.setViewName("/test/testPage");
        return mod;
    }

    @GetMapping("toBootStrapTest")
    public ModelAndView toBootStrapTest(ModelAndView mod){
        try {
            testEntityService.selectAllTestEntity();
        }catch (Exception e){
            e.printStackTrace();
        }
        mod.setViewName("/bootstrapTest");
        return mod;
    }

    @RequestMapping("/toModalPopHtml")
    public ModelAndView toModalPopHtml(ModelAndView mod){
        List<TestEntity> testList = new ArrayList<>();
        TestEntity testEntity1 = TestEntity.builder().testInt(1).testString("abcd").testDate(new Date()).build();
        testList.add(testEntity1);
        TestEntity testEntity2 = TestEntity.builder().testInt(2).testString("efgh").testDate(new Date()).build();
        testList.add(testEntity2);
        mod.addObject("testList", testList);
        mod.setViewName("/modalPopHtml");
        return mod;
    }

    @GetMapping("/webSocketTest")
    public ModelAndView toWebSocketTest(ModelAndView mod){
        mod.setViewName("/webSocketTest");
        return mod;
    }

    @GetMapping("/jspTest")
    public ModelAndView jspTest(ModelAndView mod){
        mod.setViewName("jspTest");
        return mod;
    }

    @GetMapping("/jumpToAutoComplete")
    public ModelAndView jumpToAutoComplete(ModelAndView mod){
        mod.setViewName("/autoComplete");
        return mod;
    }

    @RequestMapping("/autoComplete")
    @CrossOrigin
    public ResponseEntity<List<String>> autoComplete(String q, Integer limit){
        List<String> list = new ArrayList<>();
        list.add(q);
        list.add("aaa");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 跳转至bootstrap table 样例页面
     * @param modelAndView modelAndView
     * @return bootstrap table 样例页面
     */
    @GetMapping("/jumpToBootstrapTableDemo")
    public ModelAndView jumpToBootstrapTableDemo(ModelAndView modelAndView){
        modelAndView.setViewName("/bootstrapTableDemo");
        return modelAndView;
    }

    /**
     * 分页查询矿权信息
     * @param query 查询条件
     * @return 查询结果
     */
    @PostMapping("/findMiningInfoList")
    @ResponseBody
    public PagingResult<MiningInfo> findMiningInfoList(@RequestBody PagingQuery<MiningInfo> query){
        Page<MiningInfo> page =  reptileMineralsService.findMiningInfoByQuery(query);
        return new PagingResult<>(page.getTotal(), page.getResult());
    }
}

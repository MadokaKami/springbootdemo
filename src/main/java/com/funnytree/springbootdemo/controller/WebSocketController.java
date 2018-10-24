package com.funnytree.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.funnytree.springbootdemo.entity.TestEntity;
import com.funnytree.springbootdemo.entity.WebSocketMsg;

/**
 * @Description WebSocket控制层
 * @ClassName WebSocketController
 * @author 李英夫
 * @since 2018/10/8 22:59
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Controller
public class WebSocketController {

    //用于转发数据(sendTo)
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/testEntityOperate")
    @SendTo("/topic/testEntityOperateSendTo")
    public WebSocketMsg testEntityOperate(TestEntity testEntity){
        new TestEntity();
        return new WebSocketMsg("哈哈哈");
    }

    /*@Scheduled(cron = "0/5 * * * * ?")
    public void send(){
        template.convertAndSend("/topic/testEntityOperateSendTo", new WebSocketMsg("哈哈哈"));
    }
*/
}

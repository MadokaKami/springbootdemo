package com.funnytree.springbootdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description WebSocket返回消息类
 * @ClassName WebSocketMsg
 * @author 李英夫
 * @since 2018/10/8 23:15
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@AllArgsConstructor
@Getter
@Setter
public class WebSocketMsg {

    /**
     * 返回的信息
     */
    String msg;

}

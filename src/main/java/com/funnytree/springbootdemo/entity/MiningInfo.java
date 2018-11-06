package com.funnytree.springbootdemo.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @Description 开采信息
 * @ClassName MiningInfo
 * @author 李英夫
 * @since 2018/10/11 23:51
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Data
public class MiningInfo {

    /**业务主键*/
    private String pkId;

    /**矿权类型*/
    private String miningRightsType;

    /**所在政区*/
    private String adminDivisions;

    /**类型*/
    private String type;

    /**许可证号*/
    private String licenseKey;

    /**矿权名称*/
    private String miningRightsName;

    /**页面url*/
    private String pageURL;

    /**矿权人*/
    private String miningHoldingPerson;

    /**公示日期*/
    @DateTimeFormat(style = "yyyy-MM-dd")
    private Date publicityDate;


}

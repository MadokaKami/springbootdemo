package com.funnytree.springbootdemo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funnytree.springbootdemo.entity.MiningInfo;

/**
 * @Description 矿权信息数据模型层
 * @ClassName MiningInfoMapper
 * @author 李英夫
 * @since 2018/11/16 22:54
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Repository
public interface MiningInfoMapper {

    int insert(MiningInfo record);

    int insertSelective(MiningInfo record);

    /**
     * 获取全部开采信息
     * @param record 查询条件
     * @return 开采信息集合
     */
    List<MiningInfo> findMiningInfoByQuery(MiningInfo record);
}
package com.funnytree.springbootdemo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funnytree.springbootdemo.entity.MiningInfo;

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
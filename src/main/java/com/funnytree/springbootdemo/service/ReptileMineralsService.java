package com.funnytree.springbootdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funnytree.springbootdemo.dao.MiningInfoMapper;
import com.funnytree.springbootdemo.entity.MiningInfo;
import com.funnytree.springbootdemo.utils.pagination.PagingQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @Description 矿产资源爬取服务层
 * @ClassName ReptileMineralsService
 * @author 李英夫
 * @since 2018/10/13 1:05
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Service
public class ReptileMineralsService {

    @Autowired
    private MiningInfoMapper miningInfoMapper;

    public boolean saveMiningInfo(MiningInfo miningInfo){
        miningInfoMapper.insertSelective(miningInfo);
        return true;
    }

    /**
     * 获取全部开采信息
     * @param query 查询条件
     * @return 开采信息集合
     */
    public Page<MiningInfo> findMiningInfoByQuery(PagingQuery<MiningInfo> query){
        Page<MiningInfo> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        miningInfoMapper.findMiningInfoByQuery(query.getEntity());
        return page;
    }

}

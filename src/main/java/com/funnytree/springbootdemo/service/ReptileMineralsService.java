package com.funnytree.springbootdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.funnytree.springbootdemo.dao.MiningInfoMapper;
import com.funnytree.springbootdemo.entity.MiningInfo;
import com.funnytree.springbootdemo.utils.pagination.PageContainer;
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

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public boolean saveMiningInfo(MiningInfo miningInfo){
        miningInfoMapper.insertSelective(miningInfo);
        return true;
    }

    /**
     * 获取全部开采信息
     * @param query 查询条件
     * @return 开采信息集合
     */
    @Cacheable(cacheNames="findMiningInfoByQuery", sync = true)
    public PageContainer<MiningInfo> findMiningInfoByQuery(PagingQuery<MiningInfo> query){
        return PageContainer.offsetInstance(query, q -> miningInfoMapper.findMiningInfoByQuery(q.getEntity()));
    }

}

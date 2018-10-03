package com.funnytree.springbootdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funnytree.springbootdemo.dao.TestEntityMapper;
import com.funnytree.springbootdemo.entity.TestEntity;

@Service
public class TestEntityService {

    @Autowired
    private TestEntityMapper testEntityMapper;

    public List<TestEntity> selectAllTestEntity(){
        return testEntityMapper.selectAllTestEntity();
    }
}

package com.funnytree.springbootdemo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.funnytree.springbootdemo.entity.TestEntity;

@Repository
@Mapper
public interface TestEntityMapper {
    List<TestEntity> selectAllTestEntity();
}
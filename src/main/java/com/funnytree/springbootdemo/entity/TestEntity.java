package com.funnytree.springbootdemo.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class TestEntity {
    int testInt;

    String testString;

    Date testDate;
}

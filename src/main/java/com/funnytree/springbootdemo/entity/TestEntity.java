package com.funnytree.springbootdemo.entity;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TestEntity {

    int testInt;

    String testString;

    Date testDate;

    public TestEntity() {
    }

    public TestEntity(int testInt, String testString, Date testDate) {
        this.testInt = testInt;
        this.testString = testString;
        this.testDate = testDate;
    }
}

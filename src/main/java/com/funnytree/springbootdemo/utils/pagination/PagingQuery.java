package com.funnytree.springbootdemo.utils.pagination;

import lombok.Data;

/**
 * @Description bootstrap-table 分页查询类
 * @ClassName PagingQuery
 * @author 李英夫
 * @since 2018/11/7 0:37
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Data
public class PagingQuery<T> {

    /**页面大小*/
    private int limit;

    /**偏移量*/
    private int offset;

    /**查询条件对象*/
    private T entity;
}

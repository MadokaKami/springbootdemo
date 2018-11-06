package com.funnytree.springbootdemo.utils.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description bootstrap-table 分页返回值
 * @ClassName PagingResult
 * @author 李英夫
 * @since 2018/11/7 0:42
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Data
@AllArgsConstructor
public class PagingResult<T> {

    /**合计行数*/
    private long total;

    /**返回业务数据集合*/
    private List<T> rows;
}

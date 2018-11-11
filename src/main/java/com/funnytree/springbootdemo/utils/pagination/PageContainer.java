package com.funnytree.springbootdemo.utils.pagination;

import java.util.ArrayList;

import com.github.pagehelper.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description pagehelper Page类包装，便于redis序列化
 * @ClassName PageContainer
 * @author 李英夫
 * @since 2018/11/12 2:29
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 */
@Getter
@Setter
@NoArgsConstructor
public class PageContainer<T> {

    /**Page对象*/
    ArrayList<T> pageResult;

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 起始行
     */
    private int startRow;
    /**
     * 末行
     */
    private int endRow;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;

    public PageContainer(Page<T> page){
        this.pageResult = page;
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.startRow = page.getStartRow();
        this.endRow = page.getEndRow();
        this.total = page.getTotal();
        this.pages = page.getPages();
    }

}

package com.funnytree.springbootdemo.utils.pagination;

import java.util.List;
import java.util.function.Function;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description PageHelper Page类包装，便于redis序列化
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
    List<T> pageResult;

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

    /**
     * pageContainer初始化
     * @param query 查询条件
     * @param callback 回调函数，用于调用{@literal dao}层中的数据查询方法
     * @param <T> 业务对象的数据类型泛型
     * @return 业务对象的分页查询结果
     */
    public static <T> PageContainer<T> offsetInstance(PagingQuery<T> query, Function<PagingQuery<T>, List<T>> callback){
        // 调用PageHelper分页
        Page<T> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
        // 执行回调函数
        callback.apply(query);
        return new PageContainer<>(page);
    }

    /**
     * 通过PageHelper返回的{@literal page}对象建立page包装类
     * @param page PageHelper返回值
     */
    private PageContainer(Page<T> page){
        this.pageResult = page.getResult();
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.startRow = page.getStartRow();
        this.endRow = page.getEndRow();
        this.total = page.getTotal();
        this.pages = page.getPages();
    }

}

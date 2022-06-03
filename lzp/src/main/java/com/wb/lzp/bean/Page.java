package com.wb.lzp.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Page<T> {

    // 数据
    private List<T> data;
    // 当前页
    private Integer currentPage;

    // 本页起始位置
    private Integer st;
    // 本页结束位置
    private Integer et;
    // 每页大小
    private Integer pageSize;
    // 总条数
    private Integer totalRow;
    // 总页数
    private Integer totalPages;

    public Page(Integer currentPage,Integer pageSize,Integer totalRow,List<T> data) {
        this.data = data;
        this.currentPage = currentPage;

        this.st = (currentPage-1) * pageSize;

        this.et = currentPage * pageSize;

        if (this.et > totalRow) this.et = totalRow;

        this.pageSize = pageSize;

        this.totalRow = totalRow;

        this.totalPages = totalRow/pageSize;
    }



}

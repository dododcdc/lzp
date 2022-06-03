package com.wb.lzp.service;

import com.wb.lzp.bean.Page;
import com.wb.lzp.bean.vo.SeriesData;
import com.wb.lzp.bean.vo.TogetherVo;

import java.lang.reflect.Array;
import java.util.List;

public interface AnalysisService {

    /**
     * 地区人数统计
     * @return
     */
    List<SeriesData> regNums() ;

    /**
     * 地区评论数统计
     * @return
     */
    List<SeriesData> regComments();

    /**
     * 铁粉非铁粉人数统计
     * @return
     */
    List<SeriesData> tfNums();


    /**
     * 某个地区只有两个人，且是一男一女
     * @return
     */
    List<SeriesData> together();

    /**
     * 男女人数比例分析
     * @return
     */
    List<SeriesData> fmProportion();

    /**
     * 获取统计周期
     * @return
     */
    List<String> getPeriod() throws Exception;

    /**
     * 评论数前十统计
     * @return
     */
    List<SeriesData> cmTop() ;

    /**
     * 一条评论恰好有两个人发，且为异性
     * @return
     */
    Page<TogetherVo> togCm(Integer currentPage);




}

package com.wb.lzp.service;

import com.wb.lzp.bean.vo.SeriesData;

import java.util.List;

public interface AnalysisService {

    /**
     * 地区人数统计
     * @return
     */
    List<SeriesData> regNums() ;
}

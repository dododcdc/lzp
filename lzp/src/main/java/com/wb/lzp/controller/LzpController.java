package com.wb.lzp.controller;

import com.wb.lzp.bean.vo.SeriesData;
import com.wb.lzp.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("lzp")
public class LzpController {

    @Autowired
    public AnalysisService analysisService;

    /**
     * 同一地区只有两人且为一男一女
     * @return
     */
    @GetMapping("together")
    public List<SeriesData> together() {

        List<SeriesData> data = analysisService.together();

        return data;

    }

    /**
     * 地区人数统计
     * @return
     */
    @GetMapping("reg-nums")
    public List<SeriesData> regNums() {
        List<SeriesData> data = analysisService.regNums();
        return data;
    }




}

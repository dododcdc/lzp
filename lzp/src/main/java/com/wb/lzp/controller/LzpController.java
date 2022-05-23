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

    @GetMapping("together")
    public List<SeriesData> together() {

        List<SeriesData> data = analysisService.together();

        List<SeriesData> collect = data.stream()
                .map(x -> {
                    SeriesData seriesData = new SeriesData();
                    seriesData.setName(x.getName());
                    seriesData.setValue("11");
                    return seriesData;
                }).collect(Collectors.toList());


        return collect;

    }




}

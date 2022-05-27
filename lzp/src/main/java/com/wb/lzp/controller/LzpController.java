package com.wb.lzp.controller;

import com.wb.lzp.bean.vo.SeriesData;
import com.wb.lzp.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 铁粉非铁粉评论数量分析
     * @return
     */
    @GetMapping("tf-nums")
    public List<SeriesData> tfNums() {

        List<SeriesData> data = analysisService.tfNums();
        return data;
    }

    /**
     * 男女比例统计
     * @return
     */
    @GetMapping("fm-proportion")
    public List<SeriesData> fmProportion() {

        List<SeriesData> data = analysisService.fmProportion();
        return data;

    }

    /**
     * 获取贡献评论最多的前十人
     * @return
     */
    @GetMapping("cm-top")
    public List<SeriesData> cmTop() {
        List<SeriesData> data = analysisService.cmTop();
        return data;
    }


    /**
     * 获取统计周期
     * @return
     */
    @GetMapping("getPeriod")
    public List<String> getPeriod() {
        try {
            List<String> period = analysisService.getPeriod();
            return period;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }




}

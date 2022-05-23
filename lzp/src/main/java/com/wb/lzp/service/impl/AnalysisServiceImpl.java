package com.wb.lzp.service.impl;

import com.wb.lzp.bean.vo.SeriesData;
import com.wb.lzp.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<SeriesData> regNums() {
//        String sql1 = "SELECT * FROM `u_user_img` WHERE ID = ?";
//        BeanPropertyRowMapper<UUserImgEntity> rowMapper = new BeanPropertyRowMapper<>(UUserImgEntity.class);
//        List<UUserImgEntity> uiList = f2fJdbcTemplate.query(sql1, rowMapper, id);

        String sql = "select t1.source as name ,count(*) as value  from " +
                "( \n" +
                "select source,cmu_id from lzp_data GROUP BY source,cmu_id " +
                ") t1 group by source ";
        BeanPropertyRowMapper<SeriesData> rowMapper = new BeanPropertyRowMapper<>(SeriesData.class);

        List<SeriesData> data = jdbcTemplate.query(sql, rowMapper);

        return data;
    }
}

package com.wb.lzp.service.impl;

import com.wb.lzp.bean.vo.SeriesData;
import com.wb.lzp.service.AnalysisService;
import com.wb.lzp.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<SeriesData> regNums() {

        String sql = "select t1.source as name ,count(*) as value  from " +
                "( \n" +
                "select source,cmu_id from lzp_data GROUP BY source,cmu_id " +
                ") t1 group by source ";
        BeanPropertyRowMapper<SeriesData> rowMapper = new BeanPropertyRowMapper<>(SeriesData.class);

        List<SeriesData> data = jdbcTemplate.query(sql, rowMapper);

        return data;
    }

    @Override
    public List<SeriesData> regComments() {

        String sql = "SELECT SOURCE AS NAME,COUNT(*) AS VALUE FROM LZP_DATA GROUP BY SOURCE";
        BeanPropertyRowMapper<SeriesData> rowMapper = new BeanPropertyRowMapper<>(SeriesData.class);
        List<SeriesData> data = jdbcTemplate.query(sql, rowMapper);

        return data;
    }

    @Override
    public List<SeriesData> tfNums() {
        String sql = "SELECT CASE WHEN IS_TF=1 THEN '铁粉' ELSE '非铁粉' END AS NAME,COUNT(*) AS VALUE FROM LZP_DATA GROUP BY IS_TF";
        List<SeriesData> data = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SeriesData.class));
        return data;
    }

    @Override
    public List<SeriesData> together() {
        String sql = "SELECT  SOURCE AS NAME,GROUP_CONCAT(DISTINCT concat(SCREEN_NAME,'332067',profile_url) SEPARATOR '||') AS VALUE FROM LZP_DATA A WHERE \n" +
                "SOURCE IN (\n" +
                "SELECT SOURCE FROM ( \n" +
                "SELECT SOURCE,CMU_ID FROM LZP_DATA GROUP BY SOURCE,CMU_ID ) A GROUP BY SOURCE HAVING COUNT(*) = 2 \n" +
                ") GROUP BY SOURCE HAVING COUNT(DISTINCT GENDER) = 2 ";
        List<SeriesData> data = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SeriesData.class));

        return data;
    }

    @Override
    public List<SeriesData> fmProportion() {
        String sql = "select '男' as name,convert(sum(case when gender='m' then 1 else 0 end )/count(*),char) as value\n" +
                "      from (\n" +
                "select distinct cmu_id,gender from lzp_data ) a\n" +
                "union\n" +
                "select '女',convert(sum(case when gender='f' then 1 else 0 end )/count(*),char) as mp\n" +
                "from (\n" +
                "         select distinct cmu_id,gender from lzp_data ) a";

        List<SeriesData> data = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SeriesData.class));
        return data;
    }

    @Override
    public List<String> getPeriod() throws Exception {

        String sql = "select cm_time from lzp_data";
        List<String> period = jdbcTemplate.queryForList(sql,String.class);
        List<String> collect = period.stream().map(TimeUtil::formate).collect(Collectors.toList());
        String max = Collections.max(collect);
        String min = Collections.min(collect);
        return Arrays.asList(min,max);
    }

    @Override
    public List<SeriesData> cmTop() {

        String sql = "select screen_name as name ,count(1) as value from lzp_data group by screen_name order by count(1) desc limit 10";

        List<SeriesData> data = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SeriesData.class));

        return data;
    }

}

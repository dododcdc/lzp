package com.wb.lzp.service;

import com.wb.lzp.bean.LzpData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LzpDataRepository extends JpaRepository<LzpData,Long> {

    //根据名称进行查询用户列表
    List<LzpData> findByCmId(String cmId);

}

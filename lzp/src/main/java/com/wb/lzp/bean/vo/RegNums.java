package com.wb.lzp.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论人员地区分布
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegNums {

    private String region;

    private int nums;
}

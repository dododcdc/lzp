package com.wb.lzp.bean.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TogetherVo {
    /**
     * 地区，评论，。。。。
     */

    private String name;

    /**
     * 评论者1id
     */
    private String cmuId1;

    /**
     * 评论者1名称
     */
    private String cmName1;

    /**
     * 评论者1头像
     */
    private String avatar1;

    /**
     * 评论者1微博地址
     */
    private String url1;


    /**
     * 评论者2id
     */
    private String cmuId2;

    /**
     * 评论者1名称
     */
    private String cmName2;

    /**
     * 评论者2头像
     */
    private String avatar2;

    /**
     * 评论者2微博地址
     */
    private String url2;

}

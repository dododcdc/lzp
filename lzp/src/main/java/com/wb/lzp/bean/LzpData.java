package com.wb.lzp.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lzp_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LzpData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    该条评论的id
    private String cmId;
//    该条评论的rootid(如果该条评论的id和rootid相同则是该条微博下的一级评论)
    private String cmRootId;
//    评论内容
    private String text;
//    评论者id
    private String cmuId;
//    评论者名称
    private String screenName;
//    评论者性别
    private String gender;
//    评论者简介
    private String description;
//    评论者粉丝数量
    private String followersCount;
//    评论者主页地址profile_url
    private String profileUrl;
//    评论者是否铁粉
    private boolean isTf;
//    评论者归属地
    private String source;
//    评论的maxId
    private String maxId;
//    评论的maxIdType
    private String maxIdType;
//    评论来源接口地址
    private String cmUrl;
//    此条微博id
    private String wId;
//    此条微博mid
    private String wMid;
//    微博内容
    private String wText;
//    此条微博的地址
    private String wUrl;

//    评论时间
    private String cmTime;
//    此条评论所属微博的发博时间
    private String wTime;


}


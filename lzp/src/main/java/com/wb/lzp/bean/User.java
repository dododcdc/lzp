package com.wb.lzp.bean;

import lombok.Data;

@Data
public class User {
    // 用户id
    private String id;
    // 用户名称
    private String screen_name;
    // 用户主页地址
    private String profile_url;
    // 用户简介
    private String description;
    // 用户头像
    private String avatar_hd;
}

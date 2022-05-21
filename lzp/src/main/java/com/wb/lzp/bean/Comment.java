package com.wb.lzp.bean;

import lombok.Data;

import java.util.List;
@Data
public class Comment {
    //评论时间
    private String created_at;
    // 评论内容
    private String text;
    // 归属地
    private String source;
    // 该条评论下的评论
    private List<Comment> comments;
}

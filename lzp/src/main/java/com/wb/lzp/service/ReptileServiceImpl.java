package com.wb.lzp.service;

import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import com.ejlchina.data.TypeRef;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.OkHttps;
import com.ejlchina.okhttps.gson.GsonMsgConvertor;
import com.wb.lzp.bean.Comment;
import com.wb.lzp.bean.Result;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;


// 后续配置调度 每开始一次新调度时要初始化 urlRes
@Service
public class ReptileServiceImpl implements ReptileService {
    private final String baseUrl = "https://m.weibo.cn/";

    private final String urlFirst = "api/container/getIndex?uid=6027016891&t=0&luicode=10000011&lfid=100103type=1&q=李壮平&type=uid&value=6027016891&containerid=1076036027016891";
    private String urlRes = "";
    private final HTTP http = getHttp(baseUrl);

    // start方法 最大递归次数 防止 内存泄漏
    private  int maxSt = 0;

    private boolean isFirst = true;

    @Override
    public void start(String sinceId) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.isFirst) {
            this.urlRes = this.urlFirst ;
            this.isFirst = false;
        }
        else {
            this.urlRes = this.urlFirst + "&since_id=" + sinceId;
        }

        Mapper m1 = this.http.sync(urlRes)
                .get()
                .getBody()
                .toMapper();
        // 爬到所有数据后返回
        if("0".equals(m1.getString("ok"))) {
            return ;
        }
        Mapper m2 = m1.getMapper("data");
        Array cards = m2.getArray("cards");
        sinceId = m2.getMapper("cardlistInfo").getString("since_id");

        for (int i = 0; i < cards.size(); i++) {
            Mapper m3 = cards.getMapper(i);
            Mapper m4 = m3.getMapper("mblog");
            String scheme = m3.getString("scheme");
            String id = m4.getString("id");
            String mid = m4.getString("mid");
            String created_at = m4.getString("created_at");

            // todo 通过id去访问评论 拿到评论的内容写入数据库
            String format = String.format("comments/hotflow?id=%s&mid=%s&max_id_type=0", id, mid);
//            Mapper comments = http.sync(format)
//                    .get()
//                    .getBody().toMapper();
//            Mapper data = comments.getMapper("data");
//            List<Comment> cts = data.getArray("data").toList(Comment.class);

//            Result<List<Comment>> result = data.toBean(new TypeRef<Result<List<Comment>>>() {
//                @Override
//                public Type getType() {
//                    return super.getType();
//                }
//            });
            Result<List<Comment>> listResult = http.sync(format)
                    .get()
                    .getBody()
                    .toBean(new TypeRef<Result<List<Comment>>>() {});

//            Result<List<Comment>> result = http.sync(format)
//                    .get()
//                    .getBody()
//                    .toBean(new TypeRef<Result<List<Comment>>>() {
//                        @Override
//                        public Type getType() {
//                            return super.getType();
//                        }
//                    });




            // todo 写入数据库前先根据该条评论的id判断这条评论是否已经爬取过

            // todo 获取到所有评论后记得结束

//            list.add(id + "," + mid );
            System.out.println(id + "," + mid);
        }

        if(maxSt<10000) {
            start(sinceId);
            maxSt++;
        }
        else return;
        System.out.println("good");

    }



    private HTTP getHttp(String baseUrl) {
        HTTP http = HTTP.builder()
                .baseUrl(baseUrl)
                .addMsgConvertor(new GsonMsgConvertor())
                .build();

        return http;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUrlRes() {
        return urlRes;
    }

    public void setUrlRes(String urlRes) {
        this.urlRes = urlRes;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

}

package com.wb.lzp.service;

import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import com.ejlchina.data.TypeRef;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.OkHttps;
import com.ejlchina.okhttps.gson.GsonMsgConvertor;
import com.wb.lzp.bean.Comment;
import com.wb.lzp.bean.LzpData;
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
            // 该条微博地址
            String scheme = m3.getString("scheme");
            String id = m4.getString("id");
            String mid = m4.getString("mid");
            String createdAt = m4.getString("created_at");

//            String cm = String.format("comments/hotflow?id=%s&mid=%s&max_id_type=0", id, mid);
//            Result<List<Comment>> result = OkHttps.sync(baseUrl+format).get().getBody()
//                    .toBean(new TypeRef<Result<List<Comment>>>(){});
//            https://m.weibo.cn/comments/hotflow?id=4771565386858781&mid=4771565386858781&max_id_type=0&max_id=138859827146766
//            {
//                "ok": 0
//            }
//            Mapper comments = http.sync(cm)
//                    .get()
//                    .getBody().toMapper();
//            String ok = comments.getString("ok");
//            String maxId = comments.getMapper("data").getString("max_id");
//            int max = comments.getMapper("data").getInt("max");
//            String maxIdType = comments.getMapper("data").getString("max_id_type");

            // 获取该条微博下的第一部分一级评论，后续根据 maxId max maxIdType
//            Array array = comments.getMapper("data").getArray("data");
//            sink(array,id,mid,scheme,createdAt);
            //递归拿评论
            ss(scheme,id,mid,createdAt,0,"",1,"0");

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

    private void ss(String scheme,String id,String mid,String createdAt,int count,String maxId,int max,String maxIdType) {
        if (count<max) {
            String url = "";
            if (count == 0) {
                url = String.format("comments/hotflow?id=%s&mid=%s&max_id_type=0", id, mid);
            }

            if (count != 0) {
                url = String.format("comments/hotflow?id=%s&mid=%s&max_id_type=%s&max_id=%s", id, mid, maxIdType, maxId);
            }

            Mapper comments = http.sync(url)
                    .get()
                    .getBody().toMapper();

            int ok = comments.getInt("ok");
            // 访问ok不等于1说明没评论了直接返回
            if (ok!=1) {
                return;
            }
            // 对这一次拿到的评论做处理
            Array array = comments.getMapper("data").getArray("data");
            sink(array,id,mid,scheme,createdAt);

            // 获取下一部分的评论
            maxId = comments.getMapper("data").getString("max_id");
            max = comments.getMapper("data").getInt("max");
            maxIdType = comments.getMapper("data").getString("max_id_type");
            // 继续调用该函数
            ss(scheme,id,mid,createdAt,++count,maxId,max,maxIdType);

        }

    }


    private void sink(Array array,String wId,String wMid,String wUrl,String wTime) {

        for (int i = 0; i < array.size(); i++) {
            // 拿到一个评论
            Mapper mapper = array.getMapper(i);

            String loyal = mapper.getArray("comment_badge").getMapper(0).getString("name");
            boolean isTf = false;
            if ("loyal_fans".equals(loyal)) isTf = true;
            String source = mapper.getString("source");
            source = source.replaceAll("来自","");


            // 获取评论中的内容写入数据库，或者写入一个队列，通过消费者达到一定数量再写入数据库
            LzpData lzpData = LzpData.builder()
                    .cmId(mapper.getString("id"))
                    .cmRootId(mapper.getString("rootid"))
                    .cmuId(mapper.getMapper("user").getString("id"))
                    .screenName(mapper.getMapper("user").getString("screen_name"))
                    .gender(mapper.getMapper("user").getString("gender"))
                    .description(mapper.getMapper("user").getString("description"))
                    .followersCount(mapper.getMapper("user").getString("followers_count"))
                    .profileUrl(mapper.getMapper("user").getString("profile_url"))
                    .isTf(isTf)
                    .source(source)
                    .wId(wId)
                    .wMid(wMid)
                    .wUrl(wUrl)
                    .cmTime(mapper.getString("created_at"))
                    .wTime(wTime)
                    .build();

            // todo 写入数据库前先根据该条评论的id判断这条评论是否已经爬取过


            // 如果这个评论下面还有回复则继续调用本函数
            if (!"false".equals(mapper.getString("comments")) && mapper.getArray("comments").size()>0) {
                sink(mapper.getArray("comments"),wId,wMid,wUrl,wTime);
            }

        }

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

package com.wb.lzp.service;

import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.gson.GsonMsgConvertor;
import com.wb.lzp.bean.LzpData;
import com.wb.lzp.config.HttpConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


// 后续配置调度 每开始一次新调度时要初始化 urlRes
@Slf4j
@Service
public class ReptileServiceImpl implements ReptileService {

    @Autowired
    LzpDataRepository lzpDataRepository;

    private final String baseUrl = "https://m.weibo.cn/";
    private final String urlFirst = "api/container/getIndex?uid=6027016891&t=0&luicode=10000011&lfid=100103type=1&q=李壮平&type=uid&value=6027016891&containerid=1076036027016891";
    private String urlRes = "";
//    private HTTP http = getHttp(baseUrl);
    @Autowired
    private HTTP http ;

    private boolean isFirst = true;

    private int cmNow = 0;

    private int wNow = 0;

    private int totalCm = 0;

    @Override
    public void start(String sinceId) {

        sleep(5000);

        this.urlRes = this.urlFirst + "&since_id=" + sinceId;

        Mapper m1 = this.http.sync(urlRes)
                .get()
                .getBody()
                .toMapper();
        // 爬到所有数据后返回
        int ok = m1.getInt("ok");
        if (ok != 1) {
            return;
        }
        Mapper m2 = m1.getMapper("data");
        Array cards = m2.getArray("cards");

        for (int i = 1; i < cards.size(); i++) { // i等于1 第一条微博不爬取 (是置顶广告)
            Mapper m3 = cards.getMapper(i);
            Mapper m4 = m3.getMapper("mblog");
            // 该条微博地址
            String scheme = m3.getString("scheme");
            String id = m4.getString("id");
            String mid = m4.getString("mid");
            String createdAt = m4.getString("created_at");
            String wText = m4.getString("text");

            log.info("第" + wNow + "条微博开始" );
            log.info("本次获取微博的接口地址为：" + this.baseUrl+this.urlRes);
            log.info("微博内容是：" + wText );

            //递归拿评论
            getCm(scheme, id, mid,wText, createdAt, 0, "", 1, "0");
            wNow++;
        }

        sinceId = m2.getMapper("cardlistInfo").getString("since_id");
        if (wNow < 10000) {

            start(sinceId);
        } else return;

        System.out.println("good");

    }

    // todo bean 注入  配置超时重试机制
    private HTTP getHttp(String baseUrl) {
        HTTP http = HTTP.builder()
//                .config(b -> {
//                    IP ip = HttpConfig.ips.get(new Random(3));
//                    b.proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("ip",8080)));
//                })
                .baseUrl(baseUrl)
                .addMsgConvertor(new GsonMsgConvertor())
                .build();

        return http;
    }

    private void getCm(String scheme, String id, String mid,String wText, String createdAt, int count, String maxId, int max, String maxIdType) {
        if (count < max) {
            String url = "";
            if (count == 0) {
                url = String.format("comments/hotflow?id=%s&mid=%s&max_id_type=0", id, mid);
            }

            if (count != 0) {
                url = String.format("comments/hotflow?id=%s&mid=%s&max_id_type=%s&max_id=%s", id, mid, maxIdType, maxId);
            }
            sleep(9000);
            log.info("本次微博接口的url为：{}",this.baseUrl+this.urlRes);
            log.info("本次获取评论的url为：{}",this.baseUrl+url);
            Mapper comments = http.sync(url)
                    .addHeader(HttpConfig.headers.get(new Random().nextInt(3)))
                    .get()
                    .getBody().toMapper();

            int ok = comments.getInt("ok");
            // 访问ok不等于1说明没评论了直接返回
            if (ok != 1) {
                return;
            }
            // 对这一次拿到的评论做处理
            Array array = comments.getMapper("data").getArray("data");
            sink(array, id, mid,wText, scheme, createdAt,maxId,maxIdType,baseUrl+url);

            // 获取下一部分的评论
            maxId = comments.getMapper("data").getString("max_id");
            max = comments.getMapper("data").getInt("max");
            maxIdType = comments.getMapper("data").getString("max_id_type");
            if ("0".equals(maxId)){
                return;
            }

            if (this.cmNow < 1000000) {
                cmNow++;
                getCm(scheme, id, mid,wText, createdAt, ++count, maxId, max, maxIdType);
            }
        }
    }

    private void sink(Array array, String wId,String wMid,String wText, String wUrl, String wTime,String maxId,String maxIdType,String cmUrl) {

        for (int i = 0; i < array.size(); i++) {
            // 拿到一个评论
            Mapper mapper = array.getMapper(i);
            String loyal = "";

            if (mapper.getArray("comment_badge") != null) {
                loyal = mapper.getArray("comment_badge").getMapper(0).getString("name");
            }
            boolean isTf = false;
            if ("loyal_fans".equals(loyal)) isTf = true;
            String source = mapper.getString("source");
            source = source.replaceAll("来自", "");


            // 获取评论中的内容写入数据库，或者写入一个队列，通过消费者达到一定数量再写入数据库
            LzpData lzpData = LzpData.builder()
                    .cmId(mapper.getString("id"))
                    .cmRootId(mapper.getString("rootid"))
                    .text(mapper.getString("text"))
                    .cmuId(mapper.getMapper("user").getString("id"))
                    .screenName(mapper.getMapper("user").getString("screen_name"))
                    .gender(mapper.getMapper("user").getString("gender"))
                    .description(mapper.getMapper("user").getString("description"))
                    .followersCount(mapper.getMapper("user").getString("followers_count"))
                    .profileUrl(mapper.getMapper("user").getString("profile_url"))
                    .isTf(isTf)
                    .source(source)
                    .maxId(maxId)
                    .maxIdType(maxIdType)
                    .cmUrl(cmUrl)
                    .wId(wId)
                    .wMid(wMid)
                    .wText(wText)
                    .wUrl(wUrl)
                    .cmTime(mapper.getString("created_at"))
                    .wTime(wTime)
                    .build();

            // todo 写入数据库前先根据该条评论的id判断这条评论是否已经爬取过,因为在爬取时可能有人正在评论
            List<LzpData> lp = lzpDataRepository.findByCmId(lzpData.getCmId());
            if (lp.size()<1) {
                lzpDataRepository.save(lzpData);
            }
            log.info(lzpData.toString());
            this.totalCm++;

            log.info("本次微博接口的url为：{}",this.baseUrl+this.urlRes);
            log.info("当前在爬取第" + wNow + "条微博" + "第" + totalCm + "条评论");
            // 如果这个评论下面还有回复则继续调用本函数
            String str = mapper.getString("comments");
            Array arr = mapper.getArray("comments");

            if (!"false".equals(str) && arr != null && arr.size()>0) {
                sink(arr, wId, wMid,wText, wUrl, wTime,maxId,maxIdType,cmUrl);
            }
        }

    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

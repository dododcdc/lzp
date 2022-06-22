package com.wb.lzp.config;


import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.gson.GsonMsgConvertor;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.Response;
import static java.util.Objects.requireNonNull;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;
import okio.GzipSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static okio.Okio.buffer;

@Configuration
public class Config {

    @Value("${lzp.baseUrl}")
    private String baseUrl;

    @Bean
    public HTTP http() {
        // todo 配置超时重试机制
        HTTP.Builder builder = HTTP.builder();
        builder.config(b -> {

            b.addInterceptor(chain -> {
                int times = 0;
                while(true) {
                    try {
                        return chain.proceed(chain.request());
                    } catch (UnknownHostException e) {
                        if (times > 3) {
                            throw e;
                        }
                        try {
                            Thread.sleep(3600000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });


            // 配置超时重试
            b.addInterceptor(chain -> {

                int times = 0;
                while (true) {

                    try {
                        return chain.proceed(chain.request());
                    } catch (SocketTimeoutException e) {
                        // 重试次数超过三次就不再试了
                        if (times > 3) {
                            throw e;
                        }
                        // 请求超时就先休息一小时
                        try {
                            Thread.sleep(3600000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println("超时重试第" + times + "次");
                        times++;
                    }
                }

            });
            // 配置返回不是json数据重试
            b.addInterceptor(chain -> {
                int times = 0;
                while (true) {
                    Response rsp = chain.proceed(chain.request());


                    String str = rsp.body().string();


                    if (str.startsWith("{") && str.endsWith("}")) {
                        // 因为这个response前面已经被消费了，所以必须重新构建一个
                        ResponseBody responseBody = RealResponseBody.create(MediaType.parse("application/json; charset=utf-8"), str);
                        Response rsp2 = rsp.newBuilder().body(responseBody).build();

                        return rsp2;
                    } else {
                        if (times > 3) return rsp;
                        System.out.println("接收到非json重试" + times + "次");
                        rsp.close();
                        times++;
                        try {
                            Thread.sleep(3600000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

        });
        builder.baseUrl(this.baseUrl);
        builder.addMsgConvertor(new GsonMsgConvertor());
        return builder
                .build();
    }
}

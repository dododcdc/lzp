package com.wb.lzp.config;


import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.gson.GsonMsgConvertor;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.SocketTimeoutException;

@Configuration
public class Config {

    @Value("${lzp.baseUrl}")
    private String baseUrl;

    @Bean
    public HTTP http() {
        // todo 配置超时重试机制
        return HTTP.builder()
                .config(b -> {
                    // 配置超时重试
                    b.addInterceptor(chain -> {

                        int times = 0;
                        while(true) {

                            try{
                                return chain.proceed(chain.request());
                            } catch (SocketTimeoutException e) {
                                // 重试次数超过三次就不再试了
                                if (times> 3) {
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
                        while(true) {
                            Response rsp = chain.proceed(chain.request());
                            String str = rsp.body().string();
//                            判断str是json还是html 如果是html 且只重试不超过三次 就休息 一小时 再去爬
                            if (true) {
                                rsp.close();
                                times++;
                                continue;
                            }
                            return rsp;
                        }
                    });

                })
                .baseUrl(this.baseUrl)
                .addMsgConvertor(new GsonMsgConvertor())
                .build();
    }
}

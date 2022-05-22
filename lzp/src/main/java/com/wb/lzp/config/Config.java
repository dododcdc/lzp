package com.wb.lzp.config;


import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.gson.GsonMsgConvertor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${lzp.baseUrl}")
    private String baseUrl;

    @Bean
    public HTTP http() {
        return HTTP.builder().baseUrl(this.baseUrl)
                .addMsgConvertor(new GsonMsgConvertor())
                .build();
    }
}

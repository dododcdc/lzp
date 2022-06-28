package com.wb.lzp.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class LzpHttpConfig {

    @Value("#{${lzp.header}}")
    private Map<String,String> header;


    public String test(){
        return this.header.get("Cookie");
    }


    public Map getHeader() {
        return this.header;
    }


}

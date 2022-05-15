package com.wb.lzp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("get")
    public String get() {

        return "i am back";
    }


}

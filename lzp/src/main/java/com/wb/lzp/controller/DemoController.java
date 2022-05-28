package com.wb.lzp.controller;

import com.wb.lzp.service.ReptileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class DemoController {

    private final ReptileService reptileService;

    public DemoController(ReptileService reptileService) {
        this.reptileService = reptileService;
    }

    @GetMapping("get")
    public String get() {

        return "i am back";
    }

    @GetMapping("demo1")
    public String demo1() {
        String sinceId = "4774130194318809";
//        String sinceId = "0";
        reptileService.start(sinceId);
        return "dddd";
    }


}

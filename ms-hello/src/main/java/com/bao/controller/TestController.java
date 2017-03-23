package com.bao.controller;

import com.bao.external.WorldClient;
import com.bao.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by baochunyu on 2017/3/22.
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/timeout")
    public String auth() {
        StopWatch stopWatch = new StopWatch("timeout");
        stopWatch.start();
        try {
            testService.timeout();
        }catch (Exception e){

        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        return "auth";
    }

    @GetMapping("/netty")
    public String netty() {
        StopWatch stopWatch = new StopWatch("netty");
        stopWatch.start();
        try {
            testService.netty();
        }catch (Exception e){

        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        return "auth";
    }
}

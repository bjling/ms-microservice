package com.bao.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by baochunyu on 2017/3/10.
 */
@Slf4j
@RestController
public class TestController {
    @GetMapping("/world")
    @HystrixCommand
    public String world(){
        log.info("world");
        return "world";
    }
}

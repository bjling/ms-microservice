package com.bao.controller;

import com.alibaba.fastjson.JSON;
import com.bao.config.UserStatus;
import com.bao.model.UserDto;
import com.bao.service.TestService;
import com.bao.util.Md5Util;
import com.bao.util.PayModel;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by baochunyu on 2017/3/10.
 */
@Slf4j
@RestController
@Api(value = "test", tags = "test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/world")
//    @HystrixCommand
    @ApiOperation(
            httpMethod = "GET",
            value = "Finds Pets by status",
            notes = "Multiple status values can be provided with comma seperated strings",
            response = String.class,
            responseContainer = "Object")
    public String world() {
        log.info("===================world");
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            log.info("===================hehe");
            e.printStackTrace();
        }
        return "world";
    }

    @GetMapping("/test")
//    @HystrixCommand
    public String test() {
        log.info("===================world");
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            log.info("===================hehe");
            e.printStackTrace();
        }
        return "world";
    }

    @PostMapping("/enum")
    public String enumTest(@RequestBody UserDto userDto) {
        System.out.println(userDto.getUserType().name());
        return userDto.getName();
    }

    @GetMapping("/asyn")
    public String asyn(){
        log.info("sdfd");
        testService.asyn();
        return ";";
    }

    @Autowired
    HttpServletRequest servletRequest;

    @PostMapping("/pay")
    public String pay(@RequestBody PayModel payModel){
        log.info(JSON.toJSONString(payModel));
        String sign = servletRequest.getHeader("X-Sign".toLowerCase());
        System.out.println(sign);
        String a = JSON.toJSONString(payModel);
        String b = a.concat("bx2018");
        String c = Md5Util.md5(b).toUpperCase();
        System.out.println(c.equals(sign));
        return "pay";
    }
}

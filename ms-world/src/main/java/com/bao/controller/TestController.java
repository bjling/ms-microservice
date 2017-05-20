package com.bao.controller;

import com.bao.config.UserStatus;
import com.bao.model.UserDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by baochunyu on 2017/3/10.
 */
@Slf4j
@RestController
@Api(value = "test", tags = "test")
public class TestController {
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
}

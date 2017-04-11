package com.bao.controller;

import org.ff4j.FF4j;
import org.ff4j.aop.Flip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by baochunyu on 2017/4/10.
 */

@RestController
public class TestController {

    @Autowired
    FF4j ff4j;

    @GetMapping(value = "/test1")
    public String test1(){
        System.out.println(ff4j.check("test1"));
        System.out.println(ff4j.check("test2"));
        return "test1";
    }

    @GetMapping(value = "/test2")
    public String test2(){
        return "test2";
    }
}

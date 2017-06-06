package com.bao.msdatasources.controller;

import com.bao.msdatasources.mapper.TOrderItemMapper;
import com.bao.msdatasources.mapper.TOrderMapper;
import com.bao.msdatasources.model.TOrder;
import com.bao.msdatasources.model.TOrderItem;
import com.bao.msdatasources.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nannan on 2017/6/6.
 */
@RestController
public class TestController {

    @Autowired
    TestService testService;


    @GetMapping(value = "/test")
    public void test() {
        testService.test();
    }
}

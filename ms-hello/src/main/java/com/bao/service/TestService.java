package com.bao.service;

import com.bao.external.NettyClient;
import com.bao.external.WorldClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by baochunyu on 2017/3/10.
 */
@Service
public class TestService {

    @HystrixCommand(
            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
            },
//            threadPoolProperties = {
//                    @HystrixProperty(name = "coreSize", value = "30"),
//                    @HystrixProperty(name = "maxQueueSize", value = "101"),
//                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
//                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
//
//            },
            fallbackMethod = "testFallback")
    public String test() {
        return "test";
    }

    public String testFallback() {
        return "success";
    }


    @Autowired
    WorldClient worldClient;

    @Autowired
    NettyClient nettyClient;


//    @HystrixCommand(
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
//            },fallbackMethod = "timeoutFallback")
    public String timeout() {
        System.out.println("test timeout");
        return worldClient.world();
    }

    public String timeoutFallback() {
        System.out.println("timeoutFallback");
        return "success";
    }


    public String netty() {
        System.out.println("test netty");
        return nettyClient.netty();
    }

}

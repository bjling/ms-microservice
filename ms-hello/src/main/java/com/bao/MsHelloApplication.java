package com.bao;

import com.bao.exception.BaseException;
import com.bao.exception.constant.ExceptionLevelEnum;
import com.bao.service.TestService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.Request;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryPolicyFactory;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

@RestController
@Slf4j
@SpringBootApplication
//@EnableScheduling
@EnableHystrix
@EnableHystrixDashboard
@EnableDiscoveryClient
@EnableFeignClients
public class MsHelloApplication {

    @Autowired
    TestService testService;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand
    @GetMapping("/hello")
    public String hello() {
        String world = restTemplate.getForObject("http://localhost:10002/world", String.class);
        log.info("hello {}", world);
        return "hello ";
    }

    @GetMapping("/exception")
    public String exception() throws Exception {
        log.info("exception");
        throw new Exception();
    }

    @GetMapping("/callable")
    public Callable<String> callable() throws InterruptedException {
        log.info("callable");
        return () -> {
            //支付接到第三方通知后可以更新DB，并且放入redis，程序通过查询redis获取支付结果
            //与前端约定一个时间，用来读取支付结果
            Thread.sleep(10000);
            return "callable";
        };
    }

    @GetMapping("/base")
    public String base() {
        log.info("exception");
        throw new BaseException("ms-hello", "1001", "base exception", ExceptionLevelEnum.Middle);
    }

    @GetMapping("/auth")
    public String auth() {
        log.info("auth");
        return "auth";
    }

    @GetMapping("/test")
    public String test() {
        return testService.test();
    }


    public static void main(String[] args) {
        SpringApplication.run(MsHelloApplication.class, args);
    }

    //    @Bean
//    @Primary
//    @ConditionalOnClass(name = "org.springframework.retry.support.RetryTemplate")
//    public LoadBalancedRetryPolicyFactory loadBalancedRetryPolicyFactory() {
//        return new LoadBalancedRetryPolicyFactory.NeverRetryFactory();
//    }
//
    @Bean
    @Primary
    Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    Request.Options options() {
        return new Request.Options(3000, 30 * 1000);
    }

}

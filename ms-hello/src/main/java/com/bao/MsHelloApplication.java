package com.bao;

import com.bao.exception.BaseException;
import com.bao.exception.constant.ExceptionLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

@RestController
@Slf4j
@SpringBootApplication
@EnableScheduling
public class MsHelloApplication {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

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
        throw new BaseException("ms-hello","1001","base exception",ExceptionLevelEnum.Middle);
    }

    @GetMapping("/auth")
    public String auth() {
        log.info("auth");
        return "auth";
    }

    public static void main(String[] args) {
        SpringApplication.run(MsHelloApplication.class, args);
    }
}

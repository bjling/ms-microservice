package com.bao;

import feign.Retryer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
//@EnableHystrixDashboard
//@EnableHystrix
@EnableEurekaClient
public class MsWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsWorldApplication.class, args);
    }

    @Bean
    Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }
}

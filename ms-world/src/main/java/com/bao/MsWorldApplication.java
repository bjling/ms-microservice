package com.bao;

import feign.Retryer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableHystrixDashboard
@EnableHystrix
@EnableDiscoveryClient
public class MsWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsWorldApplication.class, args);
    }

    @Bean
    Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }
}

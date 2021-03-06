package com.bao;

import com.bao.retry.TestService;
import de.codecentric.boot.admin.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableAdminServer
public class MsNettyApplication {

	@GetMapping("/netty")
//    @HystrixCommand
	public String world() {
		log.info("===================world");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			log.info("===================hehe");
			e.printStackTrace();
		}
		return "world";
	}

	@Autowired
	TestService testService;

	@GetMapping("/retry")
//    @HystrixCommand
	public String retry() {

		ConcurrentHashMap map = new ConcurrentHashMap();
		map.put("test","test");

		testService.service();
		return "world";
	}

	@GetMapping("/retry1")
//    @HystrixCommand
	public String retry1() {
		testService.service1();
		return "world";
	}

	@GetMapping("/test")
//    @HystrixCommand
	public String test() {
		testService.service1();
		return "test";
	}

	public static void main(String[] args) {
		SpringApplication.run(MsNettyApplication.class, args);
	}
}

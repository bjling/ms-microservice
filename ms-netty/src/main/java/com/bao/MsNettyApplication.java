package com.bao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class MsNettyApplication {

	@GetMapping("/netty")
//    @HystrixCommand
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

	public static void main(String[] args) {
		SpringApplication.run(MsNettyApplication.class, args);
	}
}

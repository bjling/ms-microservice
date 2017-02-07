package com.bao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@SpringBootApplication
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



	public static void main(String[] args) {
		SpringApplication.run(MsHelloApplication.class, args);
	}
}

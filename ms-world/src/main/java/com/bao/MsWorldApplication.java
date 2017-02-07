package com.bao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class MsWorldApplication {

	@GetMapping("/world")
	public String world(){
		log.info("world");
		return "world";
	}

	public static void main(String[] args) {
		SpringApplication.run(MsWorldApplication.class, args);
	}
}

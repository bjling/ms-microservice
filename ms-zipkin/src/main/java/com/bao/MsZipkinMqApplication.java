package com.bao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@EnableZipkinStreamServer
@SpringBootApplication
public class MsZipkinMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsZipkinMqApplication.class, args);
	}
}

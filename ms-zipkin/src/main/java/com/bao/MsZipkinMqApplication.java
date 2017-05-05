package com.bao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

//@EnableZipkinStreamServer
@EnableZipkinServer
@SpringBootApplication
public class MsZipkinMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsZipkinMqApplication.class, args);
	}
}

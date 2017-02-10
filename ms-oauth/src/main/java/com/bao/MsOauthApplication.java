package com.bao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

//@EnableDiscoveryClient
@SpringBootApplication
public class MsOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOauthApplication.class, args);
	}
}

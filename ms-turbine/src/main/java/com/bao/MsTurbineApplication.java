package com.bao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;


@EnableTurbine
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class MsTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsTurbineApplication.class, args);
	}
}

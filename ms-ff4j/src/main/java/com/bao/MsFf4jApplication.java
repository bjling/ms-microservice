package com.bao;

import org.ff4j.FF4j;
import org.ff4j.web.embedded.ConsoleServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsFf4jApplication  extends SpringBootServletInitializer {

	@Autowired
	private ConsoleServlet consoleServlet;

	/** {@inheritDoc} */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MsFf4jApplication.class);
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		return new ServletRegistrationBean(consoleServlet, "/ff4j-console/*");
	}

	public static void main(String[] args) {
		SpringApplication.run(MsFf4jApplication.class, args);
	}
}

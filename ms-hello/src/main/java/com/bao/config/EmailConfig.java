package com.bao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by baochunyu on 2017/3/7.
 */
@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender mailSender(){

        Properties properties = new Properties();
        properties.setProperty("mail.debug", "true");// 是否显示调试信息(可选)
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.auth", "true");

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.163.com");
        sender.setUsername("baochunyu1987@163.com");
        sender.setPassword("a10051544ABCD");
        sender.setPort(25);
//        sender.setProtocol();
        sender.setJavaMailProperties(properties);
        sender.setDefaultEncoding("UTF-8");

        return sender;
    }

}

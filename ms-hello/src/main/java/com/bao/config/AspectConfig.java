package com.bao.config;

import com.bao.aspect.RetryAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by baochunyu on 2017/2/18.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class AspectConfig {
    @Bean
    public RetryAspect retryAspect() {
        return new RetryAspect();
    }
}

package com.bao.feign;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Created by baochunyu on 2017/3/22.
 */
public class FeignConfig {
    //默认不去重试z
    @Bean
    @Primary
    Retryer retryer() {

        return Retryer.NEVER_RETRY;
    }

//    @Bean
//    Request.Options options() {
//        return new Request.Options(1000, 30*1000);
//    }

//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }


}

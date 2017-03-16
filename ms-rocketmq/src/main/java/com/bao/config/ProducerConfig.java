package com.bao.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by baochunyu on 2017/3/15.
 */
@Configuration
@ConfigurationProperties("rocketmq")
@Data
@Slf4j
public class ProducerConfig {

    private String producerGroup;
    private String namesrvAddr;
    private String instanceName;


    @Bean(initMethod = "start",destroyMethod = "shutdown")
    public MQProducer producer(){
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName(instanceName);
        //关闭VIP通道，避免出现connect to <:10909> failed导致消息发送失败
        producer.setVipChannelEnabled(false);
//        try {
//            producer.start();
//        } catch (MQClientException e) {
//            log.error(e.getErrorMessage());
//        }

        return producer;
    }
}

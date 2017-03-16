package com.bao.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
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
public class ConsumerConfig {

    private String consumerGroup;
    private String namesrvAddr;
    private String instanceName;
    private String topic;
    private String tag;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public MQConsumer consumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setInstanceName(instanceName);

        //设置订阅tag下的subExpression
        try {
            consumer.subscribe(topic, tag);
        } catch (MQClientException e) {
            log.error(e.getErrorMessage());
        }

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //设置为集群消费(区别于广播消费)
        consumer.setMessageModel(MessageModel.CLUSTERING);

        //注册监听
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        //关闭VIP通道，避免接收不了消息
        consumer.setVipChannelEnabled(false);
        return consumer;

    }

}



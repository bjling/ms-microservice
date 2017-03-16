package com.bao.config;

import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by baochunyu on 2017/3/15.
 */
@Component
public class ProduceClient implements CommandLineRunner{

    @Autowired
    MQProducer producer;

    @Override
    public void run(String... strings) throws Exception {
        for (int i = 0; i < 10; i++)
            try {
                {
                    Message msg = new Message("MyTopic",
                            "MyTag",
                            "OrderID188"+i,
                            "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = producer.send(msg);
                    System.out.printf("%s%n", sendResult);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}

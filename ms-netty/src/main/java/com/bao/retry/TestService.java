package com.bao.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * Created by baochunyu on 2017/3/23.
 */
@Slf4j
@Service
public class TestService {

    @Autowired
    RetryService retryService;

    @Retryable(RemoteAccessException.class)
    public void service() {
        // ... do something
        log.info("doing");
        throw new RemoteAccessException("timeout");
    }

    @Recover
    public void recover(RemoteAccessException e) {
        log.info("recover");
    }

    public void service1(){
        RetryTemplate template = new RetryTemplate();

        //If that call fails then it is retried until a timeout is reached
        TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
        policy.setTimeout(10000L);

        template.setRetryPolicy(policy);

        StopWatch stopWatch = new StopWatch("retry");
        stopWatch.start();
        String result = template.execute((RetryCallback<String,RuntimeException>) context -> {
            // Do stuff that might fail, e.g. webservice operation
            return "";
        });
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

}

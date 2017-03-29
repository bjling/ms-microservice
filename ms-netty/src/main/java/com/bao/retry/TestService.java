package com.bao.retry;

import com.bao.external.WorldClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryState;
import org.springframework.retry.annotation.Backoff;
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

    @Autowired
    WorldClient worldClient;

    @Retryable(value = RemoteAccessException.class, backoff = @Backoff(delay = 100, maxDelay = 500))
    public void service() {
        // ... do something
        log.info("doing");
        throw new RemoteAccessException("timeout");
    }

    @Recover
    public void recover(RemoteAccessException e) {
        log.info("recover");
    }

    public void service1() {
        RetryTemplate template = new RetryTemplate();

        //If that call fails then it is retried until a timeout is reached
        TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
        policy.setTimeout(20000L);

        template.setRetryPolicy(policy);

        StopWatch stopWatch = new StopWatch("retry");
        stopWatch.start();
        try {
            String result = template.execute(
                    new RetryCallback<String, Throwable>() {
                        @Override
                        public String doWithRetry(RetryContext context) throws Throwable {
                            System.out.println(context.getRetryCount());

                            String a = worldClient.world();
                            return a;
                        }
                    },
                    new RecoveryCallback<String>() {
                        @Override
                        public String recover(RetryContext context) throws Exception {
                            System.out.println(context.getRetryCount());
                            return "cover";
                        }
                    });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }


    public void service2() {
        RetryTemplate template = new RetryTemplate();

        //If that call fails then it is retried until a timeout is reached
        TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
        policy.setTimeout(20000L);

        template.setRetryPolicy(policy);

        StopWatch stopWatch = new StopWatch("retry");
        stopWatch.start();
        try {
            String result = template.execute(
                    new RetryCallback<String, Throwable>() {
                        @Override
                        public String doWithRetry(RetryContext context) throws Throwable {
                            System.out.println(context.getRetryCount());

                            String a = worldClient.world();
                            return a;
                        }
                    },
                    new RetryState() {
                        @Override
                        public Object getKey() {
                            return null;
                        }

                        @Override
                        public boolean isForceRefresh() {
                            return false;
                        }

                        @Override
                        public boolean rollbackFor(Throwable exception) {
                            return false;
                        }
                    });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

}

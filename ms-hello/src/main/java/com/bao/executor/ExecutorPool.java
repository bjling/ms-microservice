package com.bao.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * Created by baochunyu on 2017/2/18.
 */
@Component
public class ExecutorPool {

    public PriorityBlockingQueue queue = new PriorityBlockingQueue(100);
    public ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30, TimeUnit.MINUTES, queue);

    public void execute() {

    }

}

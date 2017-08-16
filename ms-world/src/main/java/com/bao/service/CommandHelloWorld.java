package com.bao.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;

import java.util.concurrent.Future;

/**
 * Created by nannan on 2017/8/3.
 */
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        // a real example would do work like a network call here
        return "Hello " + name + "!";
    }

    public static void main(String args[]){
        String s = new CommandHelloWorld("Bob").execute();
        Future<String> s1 = new CommandHelloWorld("Bob").queue();
        Observable<String> s2 = new CommandHelloWorld("Bob").observe();
    }
}

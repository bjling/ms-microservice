package com.bao.service;

/**
 * Created by baochunyu on 2018/1/1.
 */
public class CglibService {
    public ThreadLocal threadLocal = new ThreadLocal();
    public void add() {
        threadLocal.set("111");
        Thread current = Thread.currentThread();
        current.getId();
        System.out.println("add");
    }

    public void delete(int i) {
        System.out.println("delete");
    }
}

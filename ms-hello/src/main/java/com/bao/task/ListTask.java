package com.bao.task;

/**
 * Created by baochunyu on 2017/2/18.
 */
public class ListTask implements Runnable ,Comparable {
    @Override
    public void run() {
        System.out.println("list");
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
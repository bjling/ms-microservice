package com.bao.executor;

/**
 * Created by baochunyu on 2017/2/19.
 */
public class Event implements Comparable<Event>{

    private int thread;
    private int priority;
    public Event(int thread, int priority){
        this.thread=thread;
        this.priority=priority;
    }

    public int getPriority() {
        return priority;
    }

    public int getThread() {
        return thread;
    }


    @Override
    public int compareTo(Event e) {
        if (this.priority>e.getPriority()) {
            return -1;
        } else if (this.priority<e.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }

}

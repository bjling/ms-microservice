package com.bao.util;

import com.google.common.base.Joiner;

/**
 * Created by nannan on 2017/6/15.
 */
public class TestUtil {
    public static void main(String []args){
        System.out.println( Joiner.on(":").join("room","seat","111","222"));

        Integer i  = new Integer('\uea60');
        System.out.println(i);
    }
}

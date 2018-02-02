package com.bao.service;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by baochunyu on 2018/1/1.
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before : " + method.getName());
        Object j = methodProxy.invokeSuper(o,objects);
        System.out.println("after : " + method.getName());
        return j;
    }
}

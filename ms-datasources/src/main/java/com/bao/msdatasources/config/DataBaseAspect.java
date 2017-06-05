package com.bao.msdatasources.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



/**
 * Created by baochunyu on 2017/2/18.
 */
@Component
@Aspect
public class DataBaseAspect {

    /*
    注解	通知
    @After	通知方法会在目标方法返回或抛出异常后调用
    @AfterRetruening	通常方法会在目标方法返回后调用
    @AfterThrowing	通知方法会在目标方法抛出异常后调用
    @Around	通知方法将目标方法封装起来
    @Before	通知方法会在目标方法执行之前执行
    */

    /**
     * 定义一个公共的切点
     */
    @Pointcut("@annotation(com.bao.msdatasources.config.DataBaseSelector)")
    public void select() {
    }

    /**
     * 目标方法执行之前调用
     * ProceedingJoinPoint is only supported for around advice
     */
    @Before("select()")
    public void setThreadLocal(JoinPoint jp) {
        DataBaseSelector selector = (DataBaseSelector) jp.getSignature().getDeclaringType().getAnnotation(DataBaseSelector.class);
        DatabaseContextHolder.setDatabaseType(selector.name());
    }

}

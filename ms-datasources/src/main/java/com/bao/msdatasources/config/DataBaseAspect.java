package com.bao.msdatasources.config;

import com.bao.msdatasources.service.TestService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
//    @Pointcut("@annotation(com.bao.msdatasources.config.DataBaseSelector)")
//    public void select() {
//    }
//
//    /**
//     * 目标方法执行之前调用
//     * ProceedingJoinPoint is only supported for around advice
//     */
//    @Before("select()")
//    public void setThreadLocal(JoinPoint jp) {
//        DataBaseSelector selector = (DataBaseSelector) jp.getSignature().getDeclaringType().getAnnotation(DataBaseSelector.class);
//        System.out.println(selector);
//        DatabaseContextHolder.setDatabaseType(selector.name());
//    }

    /**
     * 使用空方法定义切点表达式
     */
    @Pointcut("execution(* com.bao.msdatasources.mapper.*.*(..))")
    public void declareJointPointExpression() {
    }


    @Pointcut("execution(* com.bao.msdatasources.mapper.*.select*(..))")
    public void slaveExpression() {
    }


    @Before("declareJointPointExpression()")
    public void setDataSourceKey(JoinPoint point){
        //根据连接点所属的类实例，动态切换数据源

        DataBaseSelector selector = (DataBaseSelector) point.getSignature().getDeclaringType().getAnnotation(DataBaseSelector.class);
        System.out.println(selector);
        DatabaseContextHolder.setDatabaseType(selector.name());
    }

    @Before("slaveExpression()")
    public void setSlaveDataSourceKey(){
        //根据连接点所属的类实例，动态切换数据源
        DatabaseContextHolder.setDatabaseType(DataType.test1.name());
    }

}

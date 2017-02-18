package com.bao.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by baochunyu on 2017/2/18.
 */
@Component
@Aspect
public class RetryAspect {

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
//    @Pointcut("execution(** com.spring.aop.service.Perfomance.perform(..))")
    @Pointcut("@annotation(com.bao.annotation.RetryTrans)")
    public void performance() {
    }

    /**
     * 目标方法执行之前调用
     * ProceedingJoinPoint is only supported for around advice
     */
    @Before("performance()")
    public void silenceCellPhone() {
        System.out.println("before 1 ");
    }

    /**
     * 目标方法执行之前调用
     */
    @Before("performance()")
    public void takeSeats() {
        System.out.println("before 2 ");
    }

    /**
     * 目标方法执行完后调用
     */
    @AfterReturning("performance()")
    public void applause() {
        System.out.println("AfterReturning");
    }

    /**
     * 目标方法发生异常时调用
     */
    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println("AfterThrowing");
    }

    /**
     * 环绕通知
     * ProceedingJoinPoint is only supported for around advice
     * @param jp 通过它调用目标方法
     */
    @Around("performance()")
    public Object watchPerformance(ProceedingJoinPoint jp) {
        Object obj = null;
        try {
            System.out.println("Around before");
            obj = jp.proceed();
            System.out.println("Around after");
        } catch (Throwable e) {
            System.out.println("Demanding a refund");
        }
        return obj;
    }
}

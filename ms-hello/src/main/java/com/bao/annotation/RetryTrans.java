package com.bao.annotation;

import java.lang.annotation.*;

/**
 * Created by baochunyu on 2017/2/18.
 */
// annotation : http://www.cnblogs.com/peida/archive/2013/04/24/3036689.html
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
public @interface RetryTrans {
    int[] interval() default {1000};
    String value() default "retry";
}

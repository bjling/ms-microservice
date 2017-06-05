package com.bao.msdatasources.config;

import java.lang.annotation.*;

/**
 * Created by nannan on 2017/6/5.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataBaseSelector {
    String name() default "";
}

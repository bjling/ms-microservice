package com.bao.annotation;

import java.lang.annotation.*;

/**
 * Created by baochunyu on 2017/2/18.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fruit {

    String value();

    Color color() default Color.GREEN;

    public enum Color {RED, GREEN, YELLOW}

}

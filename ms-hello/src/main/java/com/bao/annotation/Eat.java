package com.bao.annotation;

import org.springframework.stereotype.Component;

/**
 * Created by baochunyu on 2017/2/18.
 */
@Component
public class Eat {

    @Fruit(value = "apple", color = Fruit.Color.RED)
    public String fruit;

    @RetryTrans(interval = {200, 500})
    public String eating() {
        System.out.println("eating ...");
        return "a";
    }
}

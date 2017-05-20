package com.bao.model;

import lombok.Data;

/**
 * Created by baochunyu on 2017/5/4.
 */
@Data
public class Foo<T> {
    private String name;
    private T data;
}

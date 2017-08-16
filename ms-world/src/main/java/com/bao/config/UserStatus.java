package com.bao.config;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * Created by baochunyu on 2017/5/2.
 */
public enum UserStatus {
    normal("1", "normal"),
    stop("2", "stop");

    private UserStatus(String code, String name) {

        Field fieldName = null;
        try {
            fieldName = getClass().getSuperclass().getDeclaredField("name");
            fieldName.setAccessible(true);
            fieldName.set(this, code);
            fieldName.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private String code;
    private String name;

}

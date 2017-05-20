package com.bao.config;

import lombok.Data;

/**
 * Created by baochunyu on 2017/5/2.
 */
public enum UserStatus {
    normal(1, "normal"),
    stop(2, "stop");

    private UserStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

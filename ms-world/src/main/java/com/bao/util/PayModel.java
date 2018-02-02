package com.bao.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by baochunyu on 2018/1/8.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayModel {
    private String name;
    private String id;
    private String icon;
    private String number;
    private Integer age;
    private List<String> toTokens;
    private SubPayModel subPayModel;

}

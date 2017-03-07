package com.bao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by baochunyu on 2017/3/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    private int number;
    private String name;
}

package com.bao.msdatasources.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TOrder {
    private Integer orderId;
    private Integer userId;
    private String orderName;
}
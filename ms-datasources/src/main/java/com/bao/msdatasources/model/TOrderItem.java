package com.bao.msdatasources.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TOrderItem {

    private Integer itemId;

    private Integer orderId;

    private Integer userId;

    private String itemName;

}
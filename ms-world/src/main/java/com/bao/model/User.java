package com.bao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by baochunyu on 2017/4/16.
 */
@Data
public class User {
    @ApiModelProperty("用户id")
    private Integer userCode;
    @ApiModelProperty("用户类型")
    private String userType;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("用户手机号")
    private String mobileNumber;
}

package com.bao.model;

import com.bao.config.UserStatus;
import com.bao.config.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by baochunyu on 2017/5/2.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private String name;
    private UserType userType;
}

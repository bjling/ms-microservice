package com.bao.util;

import com.alibaba.fastjson.JSON;
import com.bao.config.UserStatus;
import com.bao.config.UserType;
import com.bao.model.TOrder;
import com.bao.model.TOrderItem;
import com.bao.model.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nannan on 2017/7/4.
 */
public class TestUtil {
    public static void main(String args[]) throws NoSuchFieldException {
        TOrderItem item = TOrderItem.builder().itemId(1).build();
        TOrder order = TOrder.builder().orderId(2).userId(2).build();

        BeanUtils.copyProperties(order,item);

        System.out.println(JSON.toJSONString(item));

        Double d = new Double("1.11");
        System.out.println(d.intValue());

        String a = "上海市";
        System.out.println(a.length());
        System.out.println(a.substring(0,a.length()-1));

        boolean flag = true;
        System.out.println(Boolean.TRUE.equals(flag));


        UserDto dto = UserDto.builder().name("gaga").userType(UserType.normal).userStatus(UserStatus.stop).build();
        System.out.println(JSON.toJSONString(dto));

        UserDto userDto = JSON.parseObject(JSON.toJSONString(dto),UserDto.class);
        System.out.println(JSON.toJSONString(userDto));

        System.out.println(UserStatus.class.getSuperclass());
//        Field field = UserStatus.class.getSuperclass().getField("name");
        System.out.println();

        List<String> list = new ArrayList<>();

        String hobbies = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (String str : list) {
                hobbies = hobbies + " " + str;
            }
        }
        System.out.println(hobbies.replaceFirst(" ",""));

        String ee = "\u0014\u0014\u0014\u0014\u0014\u0014爱笑";

        System.out.println(Emoji.reverse(ee));

    }
}

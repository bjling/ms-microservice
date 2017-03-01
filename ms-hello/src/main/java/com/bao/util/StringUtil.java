package com.bao.util;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by baochunyu on 2017/2/22.
 */
public class StringUtil {
    public static void main(String []args){
        System.out.println(Optional.ofNullable(null).orElse("test"));
        LocalDate localDate = LocalDate.parse("2017-03-01");
        localDate.minusDays(7);
        System.out.println(localDate.toString());
    }
}

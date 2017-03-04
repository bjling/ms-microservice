package com.bao.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by baochunyu on 2017/2/22.
 */
public class StringUtil {
    public static void main(String[] args) {
        System.out.println(Optional.ofNullable(null).orElse("test"));
        LocalDate localDate = LocalDate.parse("2017-03-01");
        localDate.minusDays(7);
        System.out.println(localDate.toString());

        System.out.println("2017-03-01".substring(8));

        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("12");
        list.add("13");
        list = list.stream().distinct().collect(Collectors.toList());
        System.out.println(list.size());

    }
}

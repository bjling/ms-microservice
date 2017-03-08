package com.bao.util;

import com.bao.model.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
        System.out.println(localDate.lengthOfMonth());
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

        List<Model> integers = new ArrayList<>();

        integers.add(Model.builder().number(9).name("9").build());
        integers.add(Model.builder().number(8).name("8").build());
        integers.add(Model.builder().number(10).name("10").build());

        integers = integers.stream().sorted(Comparator.comparingInt(Model::getNumber).reversed()).collect(Collectors.toList());

        integers.forEach(model -> System.out.println(model.getNumber()));

        List<String> strings = new ArrayList<>();
        strings.add("11");
        System.out.println(strings.removeAll(null));


    }
}

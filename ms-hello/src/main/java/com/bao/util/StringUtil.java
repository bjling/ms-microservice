package com.bao.util;

import com.bao.model.Model;
import com.google.common.math.IntMath;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by baochunyu on 2017/2/22.
 */
public class StringUtil {
    public static void main(String[] args) {
//        System.out.println(Optional.ofNullable(null).orElse("test"));
//        LocalDate localDate = LocalDate.parse("2017-03-01");
//        System.out.println(localDate.lengthOfMonth());
//        localDate.minusDays(7);
//        System.out.println(localDate.toString());
//
//        System.out.println("2017-03-01".substring(8));
//
//        List<String> list = new ArrayList<>();
//        list.add("11");
//        list.add("11");
//        list.add("11");
//        list.add("12");
//        list.add("13");
//        list = list.stream().distinct().collect(Collectors.toList());
//        System.out.println(list.size());

//
//        List<Model> integers = new ArrayList<>();
//
//        integers.add(Model.builder().number(9).name("9").build());
//        integers.add(Model.builder().number(8).name("8").build());
//        integers.add(Model.builder().number(10).name("10").build());
//
//        integers = integers.stream().sorted(Comparator.comparingInt(Model::getNumber).reversed()).collect(Collectors.toList());
//
//        integers.forEach(model -> System.out.println(model.getNumber()));
//
//        List<String> strings = new ArrayList<>();
//        strings.add("11");
////        System.out.println(strings.removeAll(null));
//
//        for (int i = 0; i < 100; i++) {
//            System.out.println(RandomUtils.nextInt(1, 3));
//        }

//        String a = "adf*111";
//        String[] array = a.split("\\*");
//        for (String str:
//             array) {
//            System.out.println(str);
//        }
//
//        LocalDate localDate = LocalDate.now();
//        System.out.println(localDate.getYear()+" "+localDate.getMonthValue()+"  "+localDate.getDayOfMonth());
//        SimpleDateFormat format = new SimpleDateFormat();
//        System.out.println(localDate.format(DateTimeFormatter.ISO_DATE) );
//        Date endTime =  new Date(localDate.getYear()-1900, localDate.getMonthValue()-1, localDate.getDayOfMonth());
//        System.out.println(endTime.getYear()+" "+endTime.getMonth()+" "+endTime.getDate());

//        String pass = DigestUtils.md5Hex("78917f0ca14b7649328ff70394e5490c".getBytes());
//        System.out.println(pass.toString());
//
//        LocalDate localDate = LocalDate.now();
//        LocalDate localDate1 = LocalDate.now();
//        System.out.println(localDate.isAfter(localDate1));
//
//        Map map = new ConcurrentHashMap();

        System.out.println(timeToDouble(LocalDateTime.now()));

        System.out.println(Optional.ofNullable("").get());

    }

    public static double timeToDouble(LocalDateTime time){
        System.out.println(time.getMinute());
        System.out.println(time.getSecond());
        return Double.parseDouble(new StringBuffer().append(time.getMinute()%10).append(time.getSecond()).toString());
    }
}

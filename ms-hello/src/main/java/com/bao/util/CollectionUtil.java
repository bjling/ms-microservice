package com.bao.util;

import com.google.common.collect.Lists;
import org.springframework.util.PatternMatchUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by baochunyu on 2017/3/1.
 */
public class CollectionUtil {
    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            list.add(String.valueOf(i));
//        }
//
//        Lists.partition(list,10).forEach(slice->{
//            System.out.println(slice.size());
//        });

        String name = "test测试";
        boolean flag = PatternMatchUtils.simpleMatch("^[\u4e00-\u9fa5a-zA-Z0-9_]*",name);
        System.out.println(flag);

        Pattern p = Pattern.compile("^[\u4e00-\u9fa5a-zA-Z0-9_]*");

        Matcher m = p.matcher(name);


        System.out.println(m.matches());

        String a = "B001:haha:test";
        String[] b = a.split(":");
        System.out.println(b[b.length-1]);
    }
}

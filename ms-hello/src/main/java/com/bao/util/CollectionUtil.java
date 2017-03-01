package com.bao.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baochunyu on 2017/3/1.
 */
public class CollectionUtil {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }

        Lists.partition(list,10).forEach(slice->{
            System.out.println(slice.size());
        });
    }
}

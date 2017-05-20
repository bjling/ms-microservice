package com.bao.util;

import com.bao.model.Bar;
import com.bao.model.Foo;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by baochunyu on 2017/5/1.
 */
public class UnsafeUtil {
    public static void main(String[] args) throws InstantiationException {
//        Unsafe unsafe = Unsafe.getUnsafe();
//
//        Class a = TreeMapBao.class;
//
//        TreeMapBao t = (TreeMapBao)unsafe.allocateInstance(a);

        Type fooType = new TypeToken<Foo<Bar>>() {

        }.getType();
        System.out.println(fooType.toString());
        System.out.println(fooType.getTypeName());


    }
}

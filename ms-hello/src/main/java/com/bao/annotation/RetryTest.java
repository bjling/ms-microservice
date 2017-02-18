package com.bao.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by baochunyu on 2017/2/18.
 */
public class RetryTest {
    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {

        Eat e = new Eat();
        String a = e.eating();
        System.out.println(a);

//        Field field = Eat.class.getField("fruit");
//
//        Method method = Eat.class.getMethod("eating", null);
//
//        Annotation[] annotations = field.getAnnotations();
//        for(Annotation annotation : annotations){
//            if(Fruit.class.getClass().equals(annotation.annotationType().getClass())){
//                Fruit fruit = (Fruit) annotation;
//                System.out.println(fruit.color());
//                System.out.println(fruit.value());
//            }
//        }
//
//        RetryTrans retryTrans = method.getDeclaredAnnotation(RetryTrans.class);
//        System.out.println(retryTrans.value());
//        System.out.println(retryTrans.interval());

    }
}

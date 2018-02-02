package com.bao.util;

import com.alibaba.fastjson.JSON;
import com.bao.config.UserStatus;
import com.bao.config.UserType;
import com.bao.model.TOrder;
import com.bao.model.TOrderItem;
import com.bao.model.UserDto;
import com.bao.service.CglibService;
import com.bao.service.MyMethodInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by nannan on 2017/7/4.
 */
public class TestUtil {
    public static void main(String args[]){

//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(CglibService.class);
//        enhancer.setCallback(new MyMethodInterceptor());
//        CglibService cglibService = (CglibService)enhancer.create();
//        cglibService.add();
//        System.out.println(cglibService.threadLocal.get());



//        TOrderItem item = TOrderItem.builder().itemId(1).build();
//        TOrder order = TOrder.builder().orderId(2).userId(2).build();
//
//        BeanUtils.copyProperties(order,item);
//
//        System.out.println(JSON.toJSONString(item));
//
//        Double d = new Double("1.11");
//        System.out.println(d.intValue());
//
//        String a = "上海市";
//        System.out.println(a.length());
//        System.out.println(a.substring(0,a.length()-1));
//
//        boolean flag = true;
//        System.out.println(Boolean.TRUE.equals(flag));
//
//
//        UserDto dto = UserDto.builder().name("gaga").userType(UserType.normal).userStatus(UserStatus.stop).build();
//        System.out.println(JSON.toJSONString(dto));
//
//        UserDto userDto = JSON.parseObject(JSON.toJSONString(dto),UserDto.class);
//        System.out.println(JSON.toJSONString(userDto));
//
//        System.out.println(UserStatus.class.getSuperclass());
////        Field field = UserStatus.class.getSuperclass().getField("name");
//        System.out.println();
//
//        List<String> list = new ArrayList<>();
//
//        String hobbies = "";
//        if (!CollectionUtils.isEmpty(list)) {
//            for (String str : list) {
//                hobbies = hobbies + " " + str;
//            }
//        }
//        System.out.println(hobbies.replaceFirst(" ",""));
//
//        String ee = "\u0014\u0014\u0014\u0014\u0014\u0014爱笑";
//
//        System.out.println(Emoji.reverse(ee));
//        int i = 666666;
//        int b = 99999;
//        System.out.println(i*b);
//
//        BigDecimal bigDecimal = new BigDecimal(i);
//        BigDecimal bigDecimal2 = new BigDecimal(b);
//        System.out.println(bigDecimal.multiply(bigDecimal2).toString());


        PayModel payModel = new PayModel();
        payModel.setAge(20);
        payModel.setName("ss");
        payModel.setNumber("s2");
        payModel.setIcon("http://ldd.com");
        List<String> toTokens = new ArrayList<>();
        toTokens.add("sdf");
        toTokens.add("adf");
        toTokens.add("asf");
        payModel.setToTokens(toTokens);

        SubPayModel subPayModel = new SubPayModel();
        subPayModel.setIcon("http://baidu.com");
        subPayModel.setId("123");
        subPayModel.setName("heh");

        payModel.setSubPayModel(subPayModel);

        Field[] fields=payModel.getClass().getDeclaredFields();
        Map<String,String> map = new TreeMap<>();

        for(Field f : fields){
            String name = f.getName();
            System.out.println(f.getGenericType().getTypeName());
            f.setAccessible(true);
            try {
                String value = f.get(payModel).toString();
                if(!StringUtils.isEmpty(value)){
                    map.put(name,value);
                }

            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println(f.getName()+ " value is null ");
            }
        }

        System.out.println(JSON.toJSONString(payModel));
        System.out.println(payModel.getName());

        for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            String value = map.get(key);
            System.out.println(key + " " + value);
        }


    }
}

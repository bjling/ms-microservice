package com.bao.util;

/**
 * Created by baochunyu on 2017/2/22.
 */
public class StringUtil {
    public static void main(String []args){
        String a = "messageID=7B7E1B234476BE85-2-15A5F2E435A-20000001E&receiver=13262868235&state=2&biz_id=106017272375^1108163808557&err_code=M2:0043";
        if(a.contains("err_code")){
            System.out.println(a.substring(a.indexOf("err_code")).replace("err_code=",""));
        }
    }
}

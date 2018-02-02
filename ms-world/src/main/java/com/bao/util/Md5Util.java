package com.bao.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by nannan on 2017/5/19.
 */
public class Md5Util {

    public static String md5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] md5Bytes = md.digest(str.getBytes());
        return Hex.encodeHexString(md5Bytes);

    }

    public static void main(String []args){
        String []profiles = {"test","prod"};
    }


}

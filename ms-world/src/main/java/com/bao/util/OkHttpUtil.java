package com.bao.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by nannan on 2017/5/3.
 */
public class OkHttpUtil {
    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();
        while(true) {
            try {
                Request request = new Request.Builder()
//                        .addHeader("Content-Type","application/json; charset=UTF-8")
//                        .addHeader("X-Requested-With","XMLHttpRequest")
//                        .addHeader("X-Vnum","3.3.3")
//                        .addHeader("X-Channel","wywk")
//                        .addHeader("X-Device","android")
//                        .addHeader("X-Udid","86916102208886878b2821e7fa139d8")
//                        .addHeader("X-Client-Time","1493782943152")
//                        .addHeader("X-Pub-Key","PHP")
//                        .addHeader("X-Signature","PHP")
                        .addHeader("Authorization","Basic dXNlcjp1c2Vy")
                        .url("http://localhost:9005/dump")
                        .build();

                Response response = client.newCall(request).execute();

                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

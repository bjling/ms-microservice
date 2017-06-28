package com.bao.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nannan on 2017/5/3.
 */
public class OkHttpUtil {
    public static void main(String args[]) {
//        OkHttpClient client = new OkHttpClient();
//        while(true) {
//            try {
//                Request request = new Request.Builder()
////                        .addHeader("Content-Type","application/json; charset=UTF-8")
////                        .addHeader("X-Requested-With","XMLHttpRequest")
////                        .addHeader("X-Vnum","3.3.3")
////                        .addHeader("X-Channel","wywk")
////                        .addHeader("X-Device","android")
////                        .addHeader("X-Udid","86916102208886878b2821e7fa139d8")
////                        .addHeader("X-Client-Time","1493782943152")
////                        .addHeader("X-Pub-Key","PHP")
////                        .addHeader("X-Signature","PHP")
//                        .addHeader("Authorization","Basic dXNlcjp1c2Vy")
//                        .url("http://localhost:9005/dump")
//                        .build();
//
//                Response response = client.newCall(request).execute();
//
//                System.out.println(response.body().string());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        String a = "\"a:52:{s:6:\"god_id\";s:32:\"18b7201a867353a8882af36a50838722\";s:5:\"token\";s:32:\"62f546f1f9a7d51c09ecf68a0e88cec2\";s:8:\"nickname\";s:4:\"3001\";s:8:\"birthday\";s:10:\"1998-01-01\";s:6:\"gender\";s:1:\"1\";s:7:\"is_view\";s:1:\"1\";s:6:\"avatar\";s:76:\"https://yppphoto.yupaopao.cn/upload/F6B129E4-38C9-43E6-AD79-F1ABBB94F4AF.jpg\";s:10:\"love_count\";s:1:\"0\";s:12:\"comment_rate\";s:3:\"5.0\";s:5:\"score\";s:3:\"106\";s:8:\"store_id\";s:0:\"\";s:9:\"city_name\";s:6:\"上海\";s:10:\"store_name\";N;s:13:\"store_address\";N;s:3:\"lat\";s:17:\"31.16541178385417\";s:3:\"lng\";s:17:\"121.3994623480903\";s:7:\"geohash\";N;s:6:\"isbind\";s:1:\"0\";s:5:\"price\";s:5:\"50.00\";s:11:\"create_time\";s:19:\"2017-06-15 17:31:25\";s:10:\"is_working\";s:1:\"1\";s:7:\"contact\";s:0:\"\";s:11:\"is_dispatch\";s:1:\"1\";s:19:\"is_dispatch_silence\";s:1:\"0\";s:15:\"is_view_contact\";s:1:\"0\";s:11:\"order_count\";s:1:\"0\";s:15:\"last_login_time\";s:19:\"2017-06-26 17:25:51\";s:11:\"is_official\";s:1:\"0\";s:4:\"tags\";s:0:\"\";s:12:\"common_score\";s:1:\"6\";s:6:\"is_vip\";s:1:\"0\";s:14:\"user_view_type\";s:1:\"1\";s:16:\"vip_default_time\";s:19:\"0000-00-00 00:00:00\";s:15:\"vip_default_poi\";s:0:\"\";s:10:\"is_silence\";s:1:\"0\";s:4:\"sign\";s:0:\"\";s:7:\"is_chat\";s:1:\"1\";s:9:\"is_sheild\";s:1:\"0\";s:16:\"total_unit_count\";s:1:\"0\";s:12:\"is_redonline\";s:1:\"0\";s:8:\"isallday\";s:1:\"1\";s:11:\"start_hours\";s:5:\"00:00\";s:9:\"end_hours\";s:5:\"24:00\";s:10:\"order_days\";s:13:\"0,1,2,3,4,5,6\";s:19:\"is_unlimited_online\";s:1:\"0\";s:20:\"is_unlimited_offline\";s:1:\"0\";s:9:\"playgames\";s:0:\"\";s:9:\"god_icons\";a:1:{i:0;s:51:\"https://yppphoto.yupaopao.cn/upload/assets/wake.png\";}s:15:\"parent_cat_list\";a:1:{i:0;s:32:\"47017d0856f63f4d13010014bec452fe\";}s:8:\"cat_list\";a:1:{i:0;O:8:\"stdClass\":54:{s:2:\"id\";s:32:\"52587e8bf0c3038384cd91e4687e6438\";s:6:\"cat_id\";s:32:\"548a76f6cec7f5b716a6f0e2581d3846\";s:5:\"price\";s:4:\"5.00\";s:12:\"origin_price\";s:4:\"5.00\";s:14:\"discount_ratio\";s:4:\"1.00\";s:5:\"score\";i:6;s:8:\"cat_icon\";s:51:\"https://yppphoto.yupaopao.cn/upload/assets/wake.png\";s:11:\"order_count\";s:1:\"0\";s:10:\"unit_count\";s:1:\"0\";s:8:\"img_urls\";a:1:{i:0;s:76:\"https://yppphoto.yupaopao.cn/upload/F6B129E4-38C9-43E6-AD79-F1ABBB94F4AF.jpg\";}s:4:\"memo\";s:0:\"\";s:14:\"property_value\";s:0:\"\";s:6:\"status\";s:1:\"1\";s:10:\"rate_count\";s:1:\"0\";s:10:\"rate_score\";s:4:\"0.00\";s:17:\"total_jishu_score\";s:1:\"0\";s:21:\"total_xingxiang_score\";s:1:\"0\";s:16:\"total_sudu_score\";s:1:\"0\";s:8:\"cat_name\";s:6:\"叫醒\";s:17:\"is_items_dispatch\";s:1:\"1\";s:8:\"cat_logo\";s:51:\"https://yppphoto.yupaopao.cn/upload/assets/wake.png\";s:4:\"unit\";s:3:\"次\";s:10:\"play_level\";s:0:\"\";s:17:\"cat_property_name\";s:0:\"\";s:7:\"game_id\";s:0:\"\";s:17:\"game_partition_id\";s:0:\"\";s:12:\"game_duanwei\";s:0:\"\";s:15:\"rate_count_show\";s:1:\"0\";s:12:\"approve_time\";s:19:\"2017-06-15 17:31:25\";s:13:\"new_user_time\";s:19:\"2017-06-15 17:31:25\";s:10:\"game_weizi\";s:0:\"\";s:8:\"cat_type\";s:1:\"0\";s:9:\"is_online\";s:1:\"1\";s:8:\"store_id\";s:0:\"\";s:11:\"poi_keyword\";s:0:\"\";s:9:\"parent_id\";s:32:\"47017d0856f63f4d13010014bec452fe\";s:7:\"is_free\";s:1:\"0\";s:13:\"poi_type_code\";s:0:\"\";s:13:\"open_qiangdan\";s:1:\"1\";s:13:\"refuse_reason\";s:0:\"\";s:12:\"is_main_item\";s:1:\"1\";s:9:\"red_price\";s:6:\"999.00\";s:5:\"video\";s:0:\"\";s:5:\"audio\";s:0:\"\";s:10:\"audio_time\";s:0:\"\";s:7:\"is_auth\";s:1:\"0\";s:5:\"level\";s:1:\"1\";s:8:\"tag_name\";s:0:\"\";s:7:\"tag_ids\";s:0:\"\";s:7:\"hot_new\";s:1:\"0\";s:12:\"punctual_tag\";s:1:\"0\";s:9:\"video_img\";s:0:\"\";s:10:\"store_name\";s:0:\"\";s:9:\"game_name\";s:0:\"\";}}s:9:\"is_hiding\";s:1:\"0\";s:15:\"local_city_name\";s:6:\"上海\";}\"";

        Pattern p = Pattern.compile("[a-z]:[0-9]+:");
        Matcher m = p.matcher(a);
        System.out.println(m.find());
        System.out.println(m.replaceAll(""));
    }
}

package com.bao.util;

import com.bao.model.Model;
import org.apache.commons.lang3.RandomUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baochunyu on 2017/2/22.
 */
public class StringUtil {
    public static void main(String[] args) throws InterruptedException, ParseException {

//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        Thread.sleep(1000);
//        stopWatch.stop();
//        System.out.println(stopWatch.getTotalTimeSeconds());
//
//        System.out.println("=======");
//        stopWatch.start();
//        Thread.sleep(2000);
//        stopWatch.stop();
//        System.out.println(stopWatch.getLastTaskTimeMillis());
//
//        System.out.println("=======");
//        stopWatch.start();
//        Thread.sleep(1000);
//        stopWatch.stop();
//        System.out.println(stopWatch.getLastTaskTimeMillis());

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

//        System.out.println(timeToDouble(LocalDateTime.now()));
//
//        System.out.println(Optional.ofNullable("").get());
//
//
//        List<String> a = new ArrayList<>();
//        a.add("111");
//        a.add("222");
//
//        Optional<String> optional = Optional.empty();
//        String b = "";
//        if(!CollectionUtils.isEmpty(a)){
//            b = a.stream().filter(node->node.equals("333")).findAny().orElse(null);
//        }
//
//        System.out.println(b);
//
//
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] md5Bytes = md.digest("78917f0ca14b7649328ff70394e5490c".getBytes());
//            System.out.println("JDK MD5:" + Hex.encodeHexString(md5Bytes));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Date date = new Date();
//        Long time = date.getTime();
//        System.out.println(time);
//        Thread.sleep(512);
//        date = new Date();
//        Long time2 = date.getTime();
//        System.out.println(time2-time);
//
//        System.out.println(Math.floor(512/60));
//        System.out.println(DoubleMath.roundToLong(512/60, RoundingMode.FLOOR));

        Date date1 = new Date(1496246400000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        System.out.println(format.format(date1));

        LocalDate localDate = LocalDate.now();
        Date date = covertLocalDate(localDate);
//        date = format.parse("20170602 00:00:00");

        System.out.println(date.getTime());



//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = format.format(new Date());
//        Date dateTime = format.parse(date);
//
//        System.out.println(format.format(dateTime));
//        System.out.println(dateTime.getTime());
//
//        System.out.println(Boolean.TRUE.toString());
//
//        float f = 2.3f;
//
//        StringUtil stringUtil = new StringUtil();
//        stringUtil.test1();
//        Map map = new HashMap();
//
//        map.put("111","111");
//
//        Map map1 = new HashMap();
//        map1.put("111","333");
//        map1.put("222","222");
//
//        map.putAll(map1);
//
//        System.out.println(map.get("111"));

//        StringUtil stringUtil = new StringUtil();
//        System.out.println(stringUtil.test3());


//        System.out.println(RandomUtils.nextInt(100000,999999));


    }

    public static Date covertLocalDate(LocalDate localDate) {
        return new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
    }

    public static double timeToDouble(LocalDateTime time) {
        System.out.println(time.getMinute());
        System.out.println(time.getSecond());
        return Double.parseDouble(new StringBuffer().append(time.getMinute() % 10).append(time.getSecond()).toString());


    }

    public void test1() {
        String str = "dd";
        int i = 0;

        Model model = Model.builder().name("test").number(1).build();


        test2(str, i,model);

        System.out.println(str + " " + i);
        System.out.println(model.getName());

    }

    public void test2(String str, int i,Model model) {
        str = "tt";
        i = 1;
        model.setName("ss");
    }

    public boolean test3(){
        try{
            int a = Integer.parseInt("2w");
            return true;
        }catch (Exception e){
            System.out.println("hh");
            return false;
        }

    }
}

package com.bao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by nannan on 2017/4/20.
 */
public class DateUtils {
    public static Date covertLocalDate(LocalDate localDate) {
        return new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
    }

    public static LocalDate covertDate(Date date) {
        return LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDay());
    }

    public static double timeToDouble(LocalDateTime time) {
        return Double.parseDouble(new StringBuffer().append(time.getMinute() % 10).append(time.getSecond()).toString());
    }

    public static Long timeToLong() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String d = format.format(new Date());
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static void main(String args[]){
        System.out.println(timeToLong());
        Long d = 1494729795000L;
        Date date = new Date(d);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String d1 = format.format(date);
        System.out.println(d1);
    }
}

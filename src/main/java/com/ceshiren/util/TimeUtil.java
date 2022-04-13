package com.ceshiren.util;

/**
 * @author wyl
 * @description 获取时间戳的工具类
 * @create 2022-04-10 16:15
 */

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
public class TimeUtil {

    //获取当前年月日   20220402
    public static String getDate(){
        LocalDate date = LocalDate.now(); // get the current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return date.format(formatter);

    }
    //获取当前时分秒  14:29:16
    public static String getTime(){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);

    }

    //获取当前年月日时分秒,中间用T隔开
    public static String getFormat(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}

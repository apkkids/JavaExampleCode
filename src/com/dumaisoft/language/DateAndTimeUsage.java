package com.dumaisoft.language;

import com.dumaisoft.reflection.ReflectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/1
 * Create Time: 21:55
 * Description: introduce how to use Calendar,DateTime and SimpleDateFormat to get some function about date and time.
 *              remember that Calendar is better than DateTime.
 */
public class DateAndTimeUsage {
    public static void main(String[] args) {
        getCurrentTime();
        formatTime();
        setTime();
        moveTime();
        compareTime();
    }


    private static void setTime() {
        ReflectionUtils.printMethodName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

        //first,set year,month and day
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,3,2); //2016-4-2
        System.out.println(simpleDateFormat.format(calendar.getTime()));

        //second, set year,month,day,hour,minute and second
        calendar.set(2016,3,2,24,0,0);       //notice , if you set hour to 24, then you will get a tomorrow time.
        System.out.println(simpleDateFormat.format(calendar.getTime()));

        //third,set every field
        calendar.set(Calendar.YEAR,2016);
        calendar.set(Calendar.MONTH,3);
        calendar.set(Calendar.DAY_OF_MONTH,2);
        calendar.set(Calendar.HOUR,23);
        calendar.set(Calendar.MINUTE,10);
        calendar.set(Calendar.SECOND,18);
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }

    private static void formatTime() {
        ReflectionUtils.printMethodName();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String formatTimeStr = simpleDateFormat.format(date);
        System.out.println(formatTimeStr);
    }

    private static void getCurrentTime() {
        ReflectionUtils.printMethodName();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;  //notice
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String strDate = String.format("%d/%d/%d:%d:%d:%d", year, month, day, hour, minute, second);
        System.out.println(strDate);
    }

    private static void moveTime() {
        ReflectionUtils.printMethodName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 3, 2, 22, 21, 50);
        System.out.println(simpleDateFormat.format(calendar.getTime()));

        //move to next day
        calendar.add(Calendar.DAY_OF_MONTH,1);
        System.out.println(simpleDateFormat.format(calendar.getTime()));

        //move to last week
        calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH,-1);
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }


    private static void compareTime() {
        ReflectionUtils.printMethodName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(2016, 3, 2, 22, 21, 50);
        calendar2.set(2016, 3, 2, 22, 20, 23);

        String str1 = simpleDateFormat.format(calendar1.getTime());
        String str2 = simpleDateFormat.format(calendar2.getTime());
        if(calendar1.before(calendar2)){
            System.out.println(str1 +" before "+str2);
        }else{
            System.out.println(str1 +" after "+str2);
        }
    }
}

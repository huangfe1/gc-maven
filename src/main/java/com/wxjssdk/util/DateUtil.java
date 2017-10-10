package com.wxjssdk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by huangfei on 26/06/2017.
 */
public class DateUtil {

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String formatDate(Date date, String str) {
        SimpleDateFormat format = new SimpleDateFormat(str);
        return format.format(date);
    }

    public static Date formatStr(String date, String str) {
        SimpleDateFormat format = new SimpleDateFormat(str);
        try {
            return format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 某天开始时间
     *
     * @param date
     * @return
     */
    public static Date formatStartTime(String date) {
        String str = "yyyy-MM-dd HH:mm:ss";
        date += " 00:00:00";
        return formatStr(date, str);
    }

    /**
     * 某个月的开始时间
     *
     * @param date
     * @return
     */
    public static Date formatStartDayOfMonth(Date date) {
        String str = "yyyy-MM-01";
        String strDate = formatDate(date, str);
        return formatStr(strDate, str);
    }


    /**
     * 某天结束时间
     *
     * @param date
     * @param str
     * @return
     */
    public static Date formatEndTime(String date) {
        String str = "yyyy-MM-dd HH:mm:ss";
        date += " 23:59:59";
        return formatStr(date, str);
    }


    /**
     * 获取过去或者未来 任意天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> getPastDates(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
//            fetureDaysList.add(getFetureDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }


    public static int countMonths(String date1, String date2, String pattern) {
       try {
           SimpleDateFormat sdf = new SimpleDateFormat(pattern);

           Calendar c1 = Calendar.getInstance();
           Calendar c2 = Calendar.getInstance();

           c1.setTime(sdf.parse(date1));
           c2.setTime(sdf.parse(date2));

           int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

           //开始日期若小月结束日期
           if (year < 0) {
               year = -year;
               return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
           }
           return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
       }catch (Exception e){
           e.printStackTrace();
           return 0;
       }
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(DateUtil.countMonths("2017-06-22 15:44:14",DateUtil.formatDate(new Date()),"yyyy-MM-dd"));
//        Date date = new Date();
//        System.out.println(date.toString());
//        System.out.println(DateUtil.formatDate(date));
//        System.out.println(DateUtil.formatStr(DateUtil.formatDate(date), "yyyy-MM-dd"));
//        System.out.println(DateUtil.formatStartTime("2012-01-29"));
    }


}

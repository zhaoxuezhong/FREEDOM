package com.zxz.freedomlogging.utils;

import java.util.Calendar;

public class DateUtil {

    public String getCurrDate(String pattern) {
        Calendar calendar = Calendar.getInstance();
        String str = pattern;
        if(str.contains("YYYY") || str.contains("yyyy")){
            String year = calendar.get(Calendar.YEAR) + "";
            str = str.replace("YYYY", year);
            str = str.replace("yyyy", year);
        }
        if(str.contains("MM")){
            String month = one2Two(calendar.get(Calendar.MONTH) + 1);
            str = str.replace("MM", month);
        }
        if(str.contains("DD") || str.contains("dd")){
            String date = one2Two(calendar.get(Calendar.DATE));
            str = str.replace("DD", date);
            str = str.replace("dd", date);
        }
        if(str.contains("HH")){
            String hour = one2Two(calendar.get(Calendar.HOUR_OF_DAY));
            str = str.replace("HH", hour);
        }
        if(str.contains("hh")){
            String hour = one2Two(calendar.get(Calendar.HOUR));
            str = str.replace("hh", hour);
        }
        if(str.contains("mm")){
            String minute = one2Two(calendar.get(Calendar.MINUTE));
            str = str.replace("mm", minute);
        }
        if(str.contains("SS") || str.contains("ss")){
            String second = one2Two(calendar.get(Calendar.SECOND));
            str = str.replace("SS", second);
            str = str.replace("ss", second);
        }
        return str;
    }

    private String one2Two(int val){
        if(val < 10){
            return "0" + val;
        }
        return val + "";
    }

    public static void main(String[] args) {
         DateUtil dateUtil = new DateUtil();
         System.out.println(dateUtil.getCurrDate("dd"));
    }

}

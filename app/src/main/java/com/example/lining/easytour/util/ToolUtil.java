package com.example.lining.easytour.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ToolUtil {
    /**
     *字符串的日期格式的计算
     */
    public static String daysBetween(String smdate,String bdate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(smdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time1 = cal.getTimeInMillis();
        try {
            cal.setTime(sdf.parse(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return String.valueOf(between_days);
    }

}

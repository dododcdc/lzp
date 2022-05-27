package com.wb.lzp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    public static String formate(String str) {

        // Tue May 24 02:19:20 +0800 2022
        String s = str.replaceAll("\\+0800", "GMT+08:00");
        // Tue May 24 02:19:20 GMT+08:00 2022
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf1.format(date);

    }
}

package com.example.dvtweather.util;

import android.util.Log;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseClass {
    public static String generateAlphaNumericString(int n) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public static String getDayForInt(int num) {
        String day = "wrong";
        Log.e("Num", String.valueOf(num));
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] days = dfs.getWeekdays();
        if (num >= 0 && num <= 7) {
            day = days[num];
        }
        return day;
    }

    public static String getTempDegree(double temp) {
        double newTemp = Math.round(temp - 273.15);
        return String.valueOf((int) newTemp) + (char) 0x00B0;
    }

    public static String getHour(Date date) {
        return new SimpleDateFormat("HH", Locale.getDefault()).format(date);
    }


}

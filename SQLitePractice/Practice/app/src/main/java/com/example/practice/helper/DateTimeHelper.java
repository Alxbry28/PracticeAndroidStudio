package com.example.practice.helper;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeHelper {
    private static final String TAG = "DateTimeHelper";

    public static String formatDate(String dateFormatFrom, String dateFormatInto, String dateToFormat) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat(dateFormatFrom);
            Date date = parser.parse(dateToFormat);
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormatInto);
            String formattedDate = formatter.format(date);
            return formattedDate;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return null;
        }
    }

    public static String getFormattedDateToday(String format){
        String formattedDate = new SimpleDateFormat(format, Locale.getDefault()).format(new Date());
        return formattedDate;
    }
}

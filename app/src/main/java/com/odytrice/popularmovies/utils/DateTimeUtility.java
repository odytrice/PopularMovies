package com.odytrice.popularmovies.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtility {
    public static Date parseDate(String dateString) {
        Date date = new Date();
        if(dateString == null) return null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(dateString);
        } catch (ParseException pex) {

        }
        return date;
    }

    public static String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy");
        return formatter.format(date);
    }
}

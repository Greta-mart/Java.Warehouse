package com.warehouse.utils;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimestampUtils {
    public static Timestamp addDays(Timestamp timestamp, int days){
        return add(timestamp, Calendar.DAY_OF_WEEK, days);
    }

    public static Timestamp addMonth(Timestamp timestamp, int month){
        return add(timestamp, Calendar.MONTH, month);
    }

    private static Timestamp add(Timestamp timestamp, int scope, int value){
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        cal.add(scope, value);

        return new Timestamp(cal.getTime().getTime());
    }
}

package com.gsquad.lunih.utils;

import java.util.Date;

public class TimeUtils {

    public static boolean checkTime(Date fromDate, Date toDate) {
        return fromDate.after(toDate);
    }

}

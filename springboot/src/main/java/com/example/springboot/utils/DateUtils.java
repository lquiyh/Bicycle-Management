package com.example.springboot.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static Integer dateDifferenceInHours(Date first, Date second) {
        long diffInMillies = Math.abs(second.getTime() - first.getTime());
        return (int) TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

}

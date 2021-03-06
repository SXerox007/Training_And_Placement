package com.skeleton.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * +++++++++++++++++++++++++++++++
 * +++++++++Amit labs +++++++++++
 * +++++++++++++++++++++++++++++++
 */
public final class DateTimeUtil {

    private static final int ONE_MIN_IN_MILLISECONDS = 60000;
    private static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String LOCAL_FORMAT = "yyyy, dd MMM - hh:mm a";
    private static final String API_FORMAT = "yyyy-MM-dd";

    /**
     * Empty Constructor
     * not called
     */
    private DateTimeUtil() {
    }

    /**
     * Method to get current timezone offset value
     *
     * @return offset value in minutes
     */
    public static String getCurrentZoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        return String.valueOf(tz.getOffset(Calendar.ZONE_OFFSET) / ONE_MIN_IN_MILLISECONDS);
    }

    /**
     * Method to convert UTC to LOCAL time format
     *
     * @param date UTC date
     * @return local timezone converted date
     */
    public static String getLocalDateFromUTC(final String date) {
        DateFormat f = new SimpleDateFormat(UTC_FORMAT);
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date d = f.parse(date);
            DateFormat formatedDate = new SimpleDateFormat(LOCAL_FORMAT);
            return formatedDate.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Method to convert UTC to LOCAL time format
     *
     * @param date UTC date
     * @return local timezone converted date
     */
    public static String getDateToDisplay(final Date date) {
        SimpleDateFormat formattedDate = new SimpleDateFormat(API_FORMAT);
        return formattedDate.format(date);
    }

    /**
     * Method to convert UTC to LOCAL time format
     *
     * @param date UTC date
     * @return local timezone converted date
     */
    public static String getAppDate(final String date) {
        DateFormat f = new SimpleDateFormat(UTC_FORMAT);
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date d = f.parse(date);
            DateFormat formatedDate = new SimpleDateFormat("EEE ,dd MMM");
            return formatedDate.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}

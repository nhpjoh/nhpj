package se.nhpj.soap.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class CurrentDateTime specilly made for eHM.<br> This class has several methods get date/time in diffrent string formats depending ehm:s formats. 
 * @version 0.1
 * @author nhpj
 */
public class CurrentDateTime {
    private static final DateFormat datum = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat tid = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat kortTid = new SimpleDateFormat("HH:mm");
    private static final DateFormat longTid = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * Returns todays date as YYYY-MM-DD
     * @return date as YYYY-MM-DD
     */
    public static String getTodaysDate() {
        Date date = new Date();
        return datum.format(date);
    }
    
    /**
     * Returns time now as HH:mm:ss
     * @return time as HH:mm:ss
     */
    public static String getTimeNow() {
        Date date = new Date();
        return tid.format(date);
    }

    /**
     * Returns time now as HH:mm
     * @return time as HH:mm
     */
    public static String getShortTimeNow() {
        Date date = new Date();
        return kortTid.format(date);
    }

    /**
     * Returns time now as HH:mm:ss.SSS
     * @return time as HH:mm:ss.SSS
     */
    public static String getLongTimeNow() {
        Date date = new Date();
        return longTid.format(date);
    }

    /**
     * Returns dateTime as YYYY-MM-DDTHH:mm:ss
     * @return time as YYYY-MM-DDTHH:mm:ss
     */
    public static String getTLong() {
        return getTodaysDate() + "T" + getTimeNow();
    }

    /**
     * Returns dateTime as YYYY-MM-DDTHH:mm:ss.SSS
     * @return time as YYYY-MM-DDTHH:mm:ss.SSS
     */
    public static String getTExtraLong() {
        return getTodaysDate() + "T" + getLongTimeNow();
    }

    /**
     * Returns dateTime as YYYY-MM-DDTHH:mm:ss.SSS
     * @param offset - in days
     * @return time as YYYY-MM-DDTHH:mm:ss.SSS
     */
    public static String getTExtraLong( Integer offset ) {
        return getDate(offset) + "T" + getLongTimeNow();
    }

    /**
     * Returns dateTime as YYYY-MM-DDTHH:mm
     * @return time as YYYY-MM-DDTHH:mm
     */
    public static String getTShort() {
        return getTodaysDate() + "T" + getShortTimeNow();
    }
    
    /**
     * Returns todays date as YYYY-MM-DD
     * @param offset - in days
     * @return date as YYYY-MM-DD
     */
    public static String getDate(Integer offset ) {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, offset);
        return datum.format(cal.getTime());
    }
}


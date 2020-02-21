package se.nhpj.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author kundtest1
 */
public class DateValidate {
    /**
     * Denna metod verifierar om datumet 'String inDate' är ett datum av typen  yyyy-MM-dd HH:mm:ss:ms
     * @param inDate String 'ett datum'
     * @return boolean
     */
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;        
    }

    /**
     * Denna metod verifierar om datumet 'String inDate' är ett datum av typen SimpleDateFormat .... (ex. yyyy-MM-dd)
     * @param inDate String 'ett datum'
     * @param dateFormat SimpleDateFormat   ex. .. new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms")
     * @return boolean
     */
    public static boolean isValidDate(String inDate, SimpleDateFormat dateFormat) {
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;        
    }

    /**
     * Denna metod verifierar om datumet 'String inDate' är ett datum av typen String .. (ex. "2018-12-12 10:00:33")
     * @param inDate String 'ett datum'
     * @param format String 'ett format'
     * @return boolean
     */
    public static boolean isValidDate(String inDate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;        
    }

    /**
     * Denna metod verifierar om datumet 'String inDate' är ett datum av typen String .. (ex. "2018-12-12")
     * @param inDate String 'ett datum'
     * @return boolean true om det är ett datum
     */
    public static boolean isValidEHMDate(String inDate) {
        
        if(isValidDate(inDate,"yyyy-MM-dd"))                {return true;}
        if(isValidDate(inDate,"yyyy-MM-dd HH:mm:ss"))       {return true;}
        if(isValidDate(inDate,"yyyy-MM-dd HH:mm:ss:ms"))    {return true;}
        if(isValidDate(inDate,"yyyy-MM-dd'T'HH:mm:ss:ms"))  {return true;}
        
        return false;
    }


}

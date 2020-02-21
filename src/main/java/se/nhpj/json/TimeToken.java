package se.nhpj.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Denna klass representerar en tisdsskillnad basserad på en json sträng<BR>
 * t.ex. <B>{"year":"+1","month":"-2","day":"-3","hour":"+4","minute":"-5","second":"+6","millisecond":"+7"}</B><BR>
 * Alla värden kan vara + eller -.<BR>
 * Exempelkod: <BR>
 * <PRE><CODE>
        import com.fasterxml.jackson.databind.ObjectMapper;

        String s = "{\"year\":\"+1\",\"month\":\"-2\",\"day\":\"-3\",\"hour\":\"+4\",\"minute\":\"-5\",\"second\":\"+6\",\"millisecond\":\"+7\"}";
        
        ObjectMapper mapper = new ObjectMapper();
        TimeToken token = mapper.readValue(s, TimeToken.class);

        System.out.println(token.toString()); // Skriver ut innehållet i instansen av objektet "formaterat"
        System.out.println(token.toLog());    // Skriver ut innehållet i instansen av objektet i logformaterat (enRad)
 * </CODE></PRE>
 * @author nhpj
 */
public class TimeToken {
    /** 
     * Privata variabler som representerar de olika beståndsdelar dom finns i ett Date/Callendar objekt.
     * <br> Denna kan vara satt men + eller - framför för att beskriva tidsskillnad mot deta datum/tis som är satt i instansen av objektet. 
     */
    @JsonProperty("year")
    private String year;
    @JsonProperty("month")
    private String month;
    @JsonProperty("day")
    private String day;
    @JsonProperty("hour")
    private String hour;
    @JsonProperty("minute")
    private String minute;
    @JsonProperty("second")
    private String second;
    @JsonProperty("millisecond")
    private String millisecond;

    /**
     * Denna metod returnerar vad månaden är satt till. <br>Tidsskillnad mot deta datum/tis som är satt i instansen av objektet.
     * @return month som en sträng
     */
    public String getMonth() { 
            String retVal = "0";
        try {
            if(month.length() > 0) { retVal = month;}
        } catch(NullPointerException e) {  }
        return retVal;
    }
    /**
     * Denna metod sätter vilken tidsskillnad du vill ha gällande månader basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @param month (ex. 12) - "yyyy-<B>MM</B>-dd HH:mm:ss.SSS"
     */
    public void setMonth(String month) { this.month = month; }
    /**
     * Denna metod returnerar vad dagen är satt till. <br>Tidsskillnad mot deta datum/tis som är satt i instansen av objektet.
     * @return dagen som en sträng
     */
    public String getDay() { 
        String retVal = "0";
        try {
            if(day.length() > 0) { retVal = day;}
        } catch(NullPointerException e) {  }
        return retVal;
    }
    /**
     * Denna metod sätter vilken tidsskillnad du vill ha gällande dagar basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @param day (ex. 28) - "yyyy-MM-<B>dd</B> HH:mm:ss.SSS"
     */
    public void setDay(String day) { this.day = day; }
    /**
     * Denna metod returnerar vad årtalet är satt till. <br>Tidsskillnad mot deta datum/tis som är satt i instansen av objektet.
     * @return årtalet som en sträng
     */
    public String getYear()  {
            String retVal = "0";
        try {
            if(year.length() > 0) { retVal = year;}
        } catch(NullPointerException e) {  }
        return retVal;
    }
    /**
     * Denna metod sätter vilken tidsskillnad du vill ha gällande timmar basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @param year (ex. 22) - "<B>yyyy</B>-MM-dd HH:mm:ss.SSS"
     */
    public void setYear(String year) { this.year = year; }
    /**
     * Denna metod returnerar vad timmar är satt till. <br>Tidsskillnad mot deta datum/tis som är satt i instansen av objektet.
     * @return timmar som en sträng
     */
    public String getHour() {             
        String retVal = "0";
        try {
            if(hour.length() > 0) { retVal = hour;}
        } catch(NullPointerException e) {  }
        return retVal;
    }
    /**
     * Denna metod sätter vilken tidsskillnad du vill ha gällande timmar basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @param hour (ex. 22) - "yyyy-MM-dd <B>HH</B>:mm:ss.SSS"
     */
    public void setHour(String hour) { this.hour = hour; }
    /**
     * Denna metod returnerar vad minute är satt till. <br>Tidsskillnad mot deta datum/tis som är satt i instansen av objektet.
     * @return minuter som en sträng
     */
    public String getMinute() {             
        String retVal = "0";
        try {
            if(minute.length() > 0) { retVal = minute;}
        } catch(NullPointerException e) {  }
        return retVal;
    }
    /**
     * Denna metod sätter vilken tidsskillnad du vill ha gällande minuter basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @param minute (ex. 59) - "yyyy-MM-dd HH:<B>mm</B>:ss.SSS"
     */
    public void setMinute(String minute) { this.minute = minute; }
    /**
     * Denna metod returnerar vad sekund är satt till. <br>Tidsskillnad mot deta datum/tis som är satt i instansen av objektet.
     * @return sekunder som en sträng
     */
    public String getSecond() {             
        String retVal = "0";
        try {
            if(second.length() > 0) { retVal = second;}
        } catch(NullPointerException e) {  }
        return retVal;
    }
    /**
     * Denna metod sätter vilken tidsskillnad du vill ha gällande sekund basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @param second (ex. 45) - "yyyy-MM-dd HH:mm:<B>ss</B>.SSS"
     */
    public void setSecond(String second) { this.second = second; }
    /**
     * Denna metod returnerar vad millisekund är satt till. <br>Tidsskillnad mot deta datum/tis som är satt i instansen av objektet.
     * @return millisekunder som en sträng
     */
    public String getMillisecond() {             
        String retVal = "0";
        try {
            if(millisecond.length() > 0) { retVal = millisecond;}
        } catch(NullPointerException e) {  }
        return retVal;
    }
    /**
     * Denna metod sätter vilken tidsskillnad du vill ha gällande millisekund basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @param millisecond (ex. 234) - "yyyy-MM-dd HH:mm:ss.<B>SSS</B>"
     */
    public void setMillisecond(String millisecond) { this.millisecond = millisecond; }
    
    /**
     * Denna metod returnerar ett datum basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @return String (Datumet) - Med detta format - "yyyy-MM-dd HH:mm:ss.SSS"
     */
    public String getDateTimeEHM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }
    /**
     * Denna metod returnerar ett datum basserat på ett "startDate" datum (yyyy-MM-ddTHH:mm:ss) och den tidsskillnad som är satt i instansen av objektet.
     * @param startDate ex. "2000-01-02T10:10:10"
     * @return String (Datumet) 
     */
    public String getDateTimeEHM(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.setTime(sdf.parse(startDate));
        } catch (ParseException ex) {
            Logger.getLogger(TimeToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }
    /**
     * Denna metod returnerar ett datum basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @return String (Datumet) - Med detta format - "yyyy-MM-dd HH:mm:ss"
     */
    public String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }
    /**
     * Denna metod returnerar ett datum basserat på ett "startDate" datum (yyyy-MM-dd HH:mm:ss) och den tidsskillnad som är satt i instansen av objektet.
     * @param startDate ex. "2000-01-02 10:10:10"
     * @return String (Datumet) 
     */
    public String getDateTime(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.setTime(sdf.parse(startDate));
        } catch (ParseException ex) {
            Logger.getLogger(TimeToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }
    /**
     * Denna metod returnerar ett datum basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @return String (Datumet) - Med detta format - "yyyy-MM-dd"
     */
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }
    
    /**
     * Denna metod returnerar ett datum basserat på ett "startDate" datum (YYYY-MM-DD) och den tidsskillnad som är satt i instansen av objektet.
     * @param startDate ex. "2000-01-02"
     * @return String (Datumet) 
     */
    public String getDate(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.setTime(sdf.parse(startDate));
        } catch (ParseException ex) {
            Logger.getLogger(TimeToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }

    /**
     * Denna metod returnerar ett datum basserat på dagens datum med den tidsskillnad som är satt i instansen av objektet.
     * @return String (Datumet) - Med detta format - "yyyy-MM"
     */
    public String getYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }

    /**
     * Denna metod returnerar ett datum basserat på ett "startDate" datum (YYYY-MM-DD) och den tidsskillnad som är satt i instansen av objektet.
     * @param startDate ex. "2000-01-02"
     * @return String (Datumet) 
     */
    public String getYearMonth(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.setTime(sdf.parse(startDate));
        } catch (ParseException ex) {
            Logger.getLogger(TimeToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }

    /**
     * Denna metod returnerar datum/tid basserat på ett "startDate" ('2007-01-01 10:00:00')i det format du vill genom att du skickar <br>
     * med ett SimpleDateFormat med beskrivning om hur du vill ha informationen formaterad
     * ex. get( new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss.SSS Z") )
     * @param sdf Ett SimpleDateFormat() objekt
     * @return Formaterad sträng
     */
    public String get( SimpleDateFormat sdf ) {
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }
    /**
     * Denna metod returnerar datum/tid e det format du vill genom att du skickar med ett SimpleDateFormat med beskrivning om hur du vill ha informationen formaterad
     * ex. get( new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss.SSS Z") )
     * @param sdf Ett SimpleDateFormat() objekt
     * @param startDate startDate ex. "2000-01-02"
     * @return Formaterad sträng
     */
    public String get( SimpleDateFormat sdf, String startDate ) {
        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.setTime(sdf.parse(startDate));
        } catch (ParseException ex) {
            Logger.getLogger(TimeToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        cal.add(Calendar.YEAR, Integer.parseInt(this.getYear()));
        cal.add(Calendar.MONTH, Integer.parseInt(this.getMonth()));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getDay()));
        cal.add(Calendar.HOUR, Integer.parseInt(this.getHour()));
        cal.add(Calendar.MINUTE, Integer.parseInt(this.getMinute()));
        cal.add(Calendar.SECOND, Integer.parseInt(this.getSecond()));
        cal.add(Calendar.MILLISECOND, Integer.parseInt(this.getMillisecond()));
        
        return sdf.format(cal.getTime()); 
    }
    
    /**
     * Denna metod returnerar en formaterad sträng med innehållet på alla tidsskillnads delar i instansen av detta objekt.
     * @return String
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***********************************************************************");
        sb.append("\n* year:          ").append(year);
        sb.append("\n* month:         ").append(month);
        sb.append("\n* day:           ").append(day);
        sb.append("\n* hour:          ").append(hour);
        sb.append("\n* minute:        ").append(minute);
        sb.append("\n* second:        ").append(second);
        sb.append("\n* millisecond:   ").append(millisecond);
        sb.append("\n*********************************************************************");
        return sb.toString();
    }
    /**
     * Denna metod returnerar en rad med innehållet på alla tidsskillnads delar i instansen av detta objekt.
     * @return String
     */
    public String toLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("year: ").append(year);
        sb.append(", month: ").append(month);
        sb.append(", day: ").append(day);
        sb.append(", hour: ").append(hour);
        sb.append(", minute: ").append(minute);
        sb.append(", second: ").append(second);
        sb.append(", millisecond: ").append(millisecond);
        return sb.toString();
    }
}

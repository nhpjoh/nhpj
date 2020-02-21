package se.nhpj.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kundtest1
 */
public class TimeTokenTest {
    
    TimeToken token;
    
    public TimeTokenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        String s = "{\"year\":\"-1\",\"month\":\"-2\",\"day\":\"-3\",\"hour\":\"-4\",\"minute\":\"-5\",\"second\":\"-6\",\"millisecond\":\"-7\"}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            token = mapper.readValue(s, TimeToken.class);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMonth method, of class TimeToken.
     */
    @Test
    public void testGetMonth() {
        System.out.println("getMonth");
        TimeToken instance = token;
        String expResult = "-2";
        String result = instance.getMonth();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMonth method, of class TimeToken.
     */
    @Test
    public void testSetMonth() {
        System.out.println("setMonth");
        String month = "5";
        TimeToken instance = token;
        String oldVal = instance.getMonth();
        
        instance.setMonth(month);
        assertEquals(month, instance.getMonth());
        instance.setMonth(oldVal);
    }

    /**
     * Test of getDay method, of class TimeToken.
     */
    @Test
    public void testGetDay() {
        System.out.println("getDay");
        TimeToken instance = token;
        String expResult = "-3";
        String result = instance.getDay();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDay method, of class TimeToken.
     */
    @Test
    public void testSetDay() {
        System.out.println("setDay");
        String day = "5";
        TimeToken instance = token;
        String oldVal = instance.getDay();
        instance.setDay(day);
        assertEquals(day, instance.getDay());
        instance.setDay(oldVal);
    }

    /**
     * Test of getYear method, of class TimeToken.
     */
    @Test
    public void testGetYear() {
        System.out.println("getYear");
        TimeToken instance = token;
        String expResult = "-1";
        String result = instance.getYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of setYear method, of class TimeToken.
     */
    @Test
    public void testSetYear() {
        System.out.println("setYear");
        String year = "-6";
        TimeToken instance = token;
        String oldVal = instance.getYear();
        instance.setYear(year);
        String result = instance.getYear();
        assertEquals(year, result);
        instance.setYear(oldVal);
    }

    /**
     * Test of getHour method, of class TimeToken.
     */
    @Test
    public void testGetHour() {
        System.out.println("getHour");
        TimeToken instance = token;
        String expResult = "-4";
        String result = instance.getHour();
        assertEquals(expResult, result);
    }

    /**
     * Test of setHour method, of class TimeToken.
     */
    @Test
    public void testSetHour() {
        System.out.println("setHour");
        String hour = "-9";
        TimeToken instance = token;
        String oldVal = instance.getHour();
        instance.setHour(hour);
        String result = instance.getHour();
        assertEquals(hour, result);
        instance.setHour(oldVal);
    }

    /**
     * Test of getMinute method, of class TimeToken.
     */
    @Test
    public void testGetMinute() {
        System.out.println("getMinute");
        TimeToken instance = token;
        String expResult = "-5";
        String result = instance.getMinute();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMinute method, of class TimeToken.
     */
    @Test
    public void testSetMinute() {
        System.out.println("setMinute");
        String minute = "0";
        TimeToken instance = token;
        String oldVal = instance.getMinute();
        instance.setMinute(minute);
        String result = instance.getMinute();
        assertEquals(minute, result);
        instance.setMinute(oldVal);
    }

    /**
     * Test of getSecond method, of class TimeToken.
     */
    @Test
    public void testGetSecond() {
        System.out.println("getSecond");
        TimeToken instance = token;
        String expResult = "-6";
        String result = instance.getSecond();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSecond method, of class TimeToken.
     */
    @Test
    public void testSetSecond() {
        System.out.println("setSecond");
        String second = "+1";
        TimeToken instance = token;
        String oldVal = instance.getSecond();
        instance.setSecond(second);
        String result = instance.getSecond();
        assertEquals(second, result);
        instance.setSecond(oldVal);
    }

    /**
     * Test of getMillisecond method, of class TimeToken.
     */
    @Test
    public void testGetMillisecond() {
        System.out.println("getMillisecond");
        TimeToken instance = token;
        String expResult = "-7";
        String result = instance.getMillisecond();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMillisecond method, of class TimeToken.
     */
    @Test
    public void testSetMillisecond() {
        System.out.println("setMillisecond");
        String millisecond = "+10";
        TimeToken instance = token;
        String oldVal = instance.getMillisecond();
        instance.setMillisecond(millisecond);
        String result = instance.getMillisecond();
        assertEquals(millisecond, result);
        instance.setMillisecond(oldVal);
    }

    /**
     * Test of getDateTimeEHM method, of class TimeToken.
     */
    @Test
    public void testGetDateTimeEHM_0args() {
        System.out.println("getDateTimeEHM");
        TimeToken instance = token;
        Integer expResult = "2017-06-24T08:55:09.388".length();
        Integer result = instance.getDateTimeEHM().length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDateTimeEHM method, of class TimeToken.
     */
    @Test
    public void testGetDateTimeEHM_String() {
        System.out.println("getDateTimeEHM");
        String startDate = "2000-01-01T00:00:00.000";
        TimeToken instance = token;
        String expResult = "1998-10-28T19:54:53.993";
        String result = instance.getDateTimeEHM(startDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDateTime method, of class TimeToken.
     */
    @Test
    public void testGetDateTime_0args() {
        System.out.println("getDateTime");
        TimeToken instance = token;
        Integer expResult = "2000-01-01T00:00:00".length();
        Integer result = instance.getDateTime().length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDateTime method, of class TimeToken.
     */
    @Test
    public void testGetDateTime_String() {
        System.out.println("getDateTime");
        String startDate = "2000-01-01 00:00:00.000";
        TimeToken instance = token;
        String expResult = "1998-10-28 19:54:53";
        String result = instance.getDateTime(startDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDate method, of class TimeToken.
     */
    @Test
    public void testGetDate_0args() {
        System.out.println("getDate");
        TimeToken instance = token;
        Integer expResult = "2000-01-01".length();
        Integer result = instance.getDate().length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDate method, of class TimeToken.
     */
    @Test
    public void testGetDate_String() {
        System.out.println("getDate");
        String startDate = "2000-01-01";
        TimeToken instance = token;
        String expResult = "1998-10-28";
        String result = instance.getDate(startDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class TimeToken.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        TimeToken instance = token;
        Integer expResult = 281;
        Integer result = instance.toString().length();
        assertEquals(expResult, result);
    }

    /**
     * Test of toLog method, of class TimeToken.
     */
    @Test
    public void testToLog() {
        System.out.println("toLog");
        TimeToken instance = token;
        String expResult = "year: -1, month: -2, day: -3, hour: -4, minute: -5, second: -6, millisecond: -7";
        String result = instance.toLog();
        assertEquals(expResult, result);
    }
    
}

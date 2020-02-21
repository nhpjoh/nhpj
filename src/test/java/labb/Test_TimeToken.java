package labb;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nhpj.json.TimeToken;

/**
 *
 * @author kundtest1
 */
public class Test_TimeToken {
    
    public Test_TimeToken() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void Test1() {
        String basVal3 = "[{\"month\":\"-2\",\"day\":\"-7\",\"year\":\"-2\"}," +
                          "{\"year\":\"+2\",\"month\":\"-3\",\"day\":\"-2\",\"hour\":\"+23\",\"minute\":\"+5\",\"second\":\"+4\",\"millisecond\":\"+3\"}]";

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<TimeToken> listToken = mapper.readValue(basVal3, new TypeReference<List<TimeToken>>(){});
            System.out.println("Antal Tokens: " + listToken.size());

            for (TimeToken t : listToken) {
                System.out.println(t.toString());
                System.out.println(t.getDateTimeEHM());
            }            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    @Test
    public void Test2() {
        String excelH2 = "[{\"month\":\"+2\"}]";
        String d = "2000-06-10T00:00:00.000";

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<TimeToken> listToken = mapper.readValue(excelH2, new TypeReference<List<TimeToken>>(){});
            System.out.println("Antal Tokens: " + listToken.size());

            for (TimeToken t : listToken) {
                System.out.println(t.toString());
                System.out.println("\nUtgångs datum:\t"+d);
                System.out.println("Nytt datum:\t\t" + t.getDateTimeEHM(d));
            }            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void Test_toLog() throws IOException {
        String s = "{\"year\":\"+2\",\"month\":\"-3\",\"day\":\"-2\",\"hour\":\"+23\",\"minute\":\"+5\",\"second\":\"+4\",\"millisecond\":\"+3\"}";

        ObjectMapper mapper = new ObjectMapper();
        TimeToken token = mapper.readValue(s, TimeToken.class);
        
        System.out.println(token.toLog());
    }
    
    @Test
    public void Test_toString() throws IOException {
        String s = "{\"year\":\"+2\",\"month\":\"-3\",\"day\":\"-2\",\"hour\":\"+23\",\"minute\":\"+5\",\"second\":\"+4\",\"millisecond\":\"+3\"}";

        ObjectMapper mapper = new ObjectMapper();
        TimeToken token = mapper.readValue(s, TimeToken.class);
        
        System.out.println(token.toString());
    }
    
    @Test
    public void Test_getDate() throws IOException {
        String s = "{\"year\":\"+2\",\"month\":\"-3\",\"day\":\"-2\",\"hour\":\"+23\",\"minute\":\"+5\",\"second\":\"+4\",\"millisecond\":\"+3\"}";

        ObjectMapper mapper = new ObjectMapper();
        TimeToken token = mapper.readValue(s, TimeToken.class);
        
        System.out.println(token.toLog());
        System.out.println("startDate: toDay : " + token.getDate());
        System.out.println("startDate: 2000-06-15 : " + token.getDate("2000-06-15"));
    }
    
    @Test
    public void Test_getYearMonth() throws IOException {
        String s = "{\"year\":\"+2\",\"month\":\"-3\",\"day\":\"-2\",\"hour\":\"+23\",\"minute\":\"+5\",\"second\":\"+4\",\"millisecond\":\"+3\"}";

        ObjectMapper mapper = new ObjectMapper();
        TimeToken token = mapper.readValue(s, TimeToken.class);
        
        System.out.println(token.toLog());
        System.out.println("startDate: toDay : " + token.getYearMonth());
        System.out.println("startDate: 2000-06-15 : " + token.getYearMonth("2000-06-15"));
    }
    
    @Test
    public void Test_get() throws IOException {
        String s = "{\"year\":\"+2\",\"month\":\"-3\",\"day\":\"-2\",\"hour\":\"+23\",\"minute\":\"+5\",\"second\":\"+4\",\"millisecond\":\"+3\"}";

        ObjectMapper mapper = new ObjectMapper();
        TimeToken token = mapper.readValue(s, TimeToken.class);
        
        System.out.println("Invärden: " + token.toLog());
        System.out.println("startDate: toDay : " + token.get( new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z")));
        System.out.println("startDate: 2007-01-01T10:00:00.000 +0100 : " + token.get( new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z"),"2007-01-01T10:00:00.000 +0100"));
        
    }
    
}

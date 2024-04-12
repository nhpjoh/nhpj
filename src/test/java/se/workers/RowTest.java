package se.workers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Test;
import se.workers.transaktionlogger.Row;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.util.regex.Pattern.matches;

public class RowTest {

    @Test
    public void getTransactionName() {
        Row row = new Row();
        row.setTransactionName("worker");
        Assert.assertEquals("(getTransactionName) Olika","worker",row.getTransactionName());
    }

    @Test
    public void setTransactionName() {
        Row row = new Row();
        row.setTransactionName("worker");
        Assert.assertEquals("(setTransactionName) Olika","worker",row.getTransactionName());
    }

    @Test
    public void getTransactionTime() {
        Row row = new Row();
        row.setTransactionTime("0.345");
        Assert.assertEquals("(getTransactionTime) Olika","0.345",row.getTransactionTime());
    }

    @Test
    public void setTransactionTime() {
        Row row = new Row();
        row.setTransactionTime("0.346");
        Assert.assertEquals("(setTransactionTime) Olika","0.346",row.getTransactionTime());
    }

    @Test
    public void getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String t = sdf.format(cal.getTime());

        Row row = new Row();
        row.setTime(t);
        Assert.assertEquals("(getTime) Olika",t,row.getTime());
    }

    @Test
    public void setTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String t = sdf.format(cal.getTime());

        Row row = new Row();
        row.setTime(t);
        Assert.assertEquals("(setTime) Olika",t,row.getTime());
    }

    @Test
    public void RowTest() {
        Row row = new Row("WorkerTest", "3.456");
        Assert.assertEquals("(Row time) olika : " + row.getTime() + "regex: 20[0-9][0-9]-[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9].[0-9][0-9][0-9]",
                true,
                matches("20[0-9][0-9]-[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9].[0-9][0-9][0-9]",row.getTime()));
    }

    @Test
    public void slask() {
        Row row = new Row("WorkerTest", "3.456");
        ObjectMapper mapper = new ObjectMapper();

        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(row));
        } catch (IOException ex) {
                ex.printStackTrace();
        }
    }
}
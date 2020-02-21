package se.nhpj.testdata;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {

    Transaction transaction;

    @Before
    public void setUp() {
        transaction = new Transaction();
        transaction.start("Trans");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        transaction.stop("Trans");
    }


    @Test
    public void getElapsed() {
        Float millis = transaction.getElapsedTime("Trans",Transaction.MILLISECONDS);
        assertTrue("Något blev fel med tidtagningen(MILLISECONDS): " + millis + " ska vara > 2000",millis > 2000);
        millis = transaction.getElapsedTime("Trans",Transaction.SECONDS);
        assertTrue("Något blev fel med tidtagningen(SECONDS): " + millis + " ska vara > 2",millis > 2);
        millis = transaction.getElapsedTime("Trans",Transaction.MINUTES);
        assertTrue("Något blev fel med tidtagningen (MINUTES): " + millis + " ska vara < 1",millis < 1);
        String string = transaction.getElapsedTime("Trans");
        assertTrue("Något blev fel med tidtagningen: " + string + " ska vara > 2000",(Float.valueOf(string)*1000) > 2000);
    }

//    @Test
//    public void test() throws InterruptedException {
//        Transaction t = new Transaction();
//        Integer millis = RndData.getNumber(50,500);
//        t.start("Apa");
//        Thread.sleep(millis);
//        t.stop("Apa");
//
//        System.out.println("Apa: " + t.getElapsedTime("Apa"));
//
//        t.logElapsedTime("Apa",Transaction.SECONDS,"c:/log.log");
//
//    }
}

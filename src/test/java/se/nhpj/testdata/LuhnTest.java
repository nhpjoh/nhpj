package se.nhpj.testdata;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LuhnTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void check() {
        System.out.println(Luhn.Check("8880999"));
        assertTrue("Checksiffran blev fel String: ", Luhn.Check("8880999"));
    }

    @Test
    public void calcNext() {
        System.out.println(Luhn.CalcNext("888099"));
        assertTrue("Checksiffran blev fel String: ", Luhn.CalcNext("888099").matches("9"));
    }

    @Test
    public void test_setCheckNum() {
        System.out.println(Luhn.setCheckNum("888099"));
        assertTrue("Checksiffran blev fel String: " , Luhn.Check(Luhn.setCheckNum("888099")));

        System.out.println(Luhn.setCheckNum(888099));
        assertTrue("Checksiffran blev fel Integer: " , Luhn.Check(Integer.toString(Luhn.setCheckNum(888099))));
    }
}
package se.nhpj.testdata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonnummerTest {

    Personnummer pnr;

    @Before
    public void setUp() throws Exception {
        pnr = new Personnummer();
        pnr.setPersonnummer("20001212-1117");
    }

    @After
    public void tearDown() throws Exception {
        pnr = null;
    }

    @Test
    public void Test_toString() {
        assertTrue("Inget data i Personnummer objektet : " + pnr.toString(),pnr.toString().length() == 13);
    }

    @Test
    public void getPersonnummer() {
        assertTrue("Inget data i Personnummer objektet : " + pnr.getPersonnummer(),pnr.getPersonnummer().length() == 13);
    }

    @Test
    public void ärKvinna() {
        assertFalse("ärKvinna ska ge false",pnr.isKvinna());
    }

    @Test
    public void ärMan() {
        assertTrue("ärMan ska ge false",pnr.isMan());
    }

    @Test
    public void generatePnr() {
        pnr = new Personnummer();
        String p = pnr.generatePnr(1940,2018);
        assertFalse("genererat personnummer skapat: " + p,p.isEmpty());
    }

    @Test
    public void generateOrgNr() {
        System.out.println(Personnummer.generateOrgNr());
    }
}
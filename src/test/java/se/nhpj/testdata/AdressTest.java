package se.nhpj.testdata;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdressTest {

    Adress adress;
    @Before
    public void setUp() throws Exception {
        adress = new Adress();
        adress = adress.getSwedishAdress();
    }

    @Test
    public void getGata() {
        assertTrue("Its no Gata in Adress instance.", !adress.getGata().isEmpty());
    }

    @Test
    public void setGata() {
        adress.setGata("Testvägen");
        assertTrue("Its no Gata in Adress instance.", adress.getGata().contains("Testvägen"));
    }

    @Test
    public void getGatunummer() {
    }

    @Test
    public void setGatunummer() {
    }

    @Test
    public void getPostnummer() {
    }

    @Test
    public void setPostnummer() {
    }

    @Test
    public void getOrt() {
    }

    @Test
    public void setOrt() {
    }

    @Test
    public void getLKF_kod() {
    }

    @Test
    public void setLKF_kod() {
    }

    @Test
    public void getLän() {
    }

    @Test
    public void setLän() {
    }

    @Test
    public void getKommun() {
    }

    @Test
    public void setKommun() {
    }

    @Test
    public void getFrånNummer() {
    }

    @Test
    public void setFrånNummer() {
    }

    @Test
    public void getTillNummer() {
    }

    @Test
    public void setTillNummer() {
    }

    @Test
    public void getGatuAdress() {
    }

    @Test
    public void setGatuAdress() {
    }

    @Test
    public void getLand() {
    }

    @Test
    public void setLand() {
    }

    @Test
    public void getSwedishAdress() {
        adress = adress.getSwedishAdress();
        Boolean isempty = adress.toString().isEmpty();
        assertTrue("Fick inget tillbaka från metoden getSwedishAdress",!isempty);
    }

    @Test
    public void testToString() {
        String s = adress.toString();
        assertTrue("toString funkar inte...", s.indexOf(';') > 0);
    }
}
package se.ehm.testdata.FORS;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ForskrivareTest {

    @Test
    public void getKodCheck() {
        Forskrivare forskrivare = new Forskrivare();
        System.out.println( forskrivare.getKodCheck("888100") );
    }

    @Test
    public void checkIfKodExists() {
        Forskrivare forskrivare = new Forskrivare();
        Assert.assertTrue("Personen / förskrivarkoden finns inte () 190004109815,888099 ", forskrivare.checkIfKodExists("190004109815","888099","TEST28") );
    }

}
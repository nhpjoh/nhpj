package se.ehm.testdata;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArbetsplatsHandlerTest {

    @Test
    public void getRndArbetsplats() {
        System.out.println(ArbetsplatsHandler.getRndArbetsplats("TEST28"));
    }

    @Test
    public void getPreSetArbetsplats() {
        System.out.println(se.ehm.testdata.ArbetsplatsHandler.getPreSetArbetsplats());
    }
}
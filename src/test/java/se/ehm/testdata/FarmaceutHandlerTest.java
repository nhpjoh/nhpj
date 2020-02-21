package se.ehm.testdata;

import org.junit.Test;

import static org.junit.Assert.*;

public class FarmaceutHandlerTest {

    @Test
    public void getAllFarmaceut() {
    }

    @Test
    public void getRndFarmaceut() {
        Farmaceut farmaceut = FarmaceutHandler.getRndFarmaceut("TEST28");
        System.out.println(farmaceut);
    }
}
package se.ehm.testdata;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApotekHandlerTest {

    @Test
    public void getRndApotek() {
        System.out.println(ApotekHandler.getRndApotek("TEST28"));
    }
}
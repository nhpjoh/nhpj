package se.ehm.testdata.FORS;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class GenerateCSVTest {

    @Test
    public void farmaceut() {
        GenerateCSV g = new GenerateCSV();

//        g.Farmaceut(890000,6000 ,"AP");
        g.Farmaceut(834000,15000,"RC");
    }
}
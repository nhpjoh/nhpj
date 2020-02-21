package se.ehm.testdata;

import org.junit.Test;

import static org.junit.Assert.*;

public class LakemedelHandlerTest {

    @Test
    public void getRndLakemedel() {
        Lakemedel lakemedel; // = new Lakemedel();
        lakemedel = se.ehm.testdata.LakemedelHandler.getRndLakemedel("TEST28");
        lakemedel.setAntalUttag(String.valueOf(se.nhpj.testdata.RndData.getNumber(5,20)));
        lakemedel.setNumberOfPackages(String.valueOf(se.nhpj.testdata.RndData.getNumber(5,20)));
        System.out.println(lakemedel);
    }

    @Test
    public void _getRndLakemedel() {
        LakemedelHandler lakemedelHandler = new LakemedelHandler();
        Lakemedel lakemedel = lakemedelHandler.getRndLakemedel();
        lakemedel.setAntalUttag(String.valueOf(se.nhpj.testdata.RndData.getNumber(5,20)));
        lakemedel.setNumberOfPackages(String.valueOf(se.nhpj.testdata.RndData.getNumber(5,20)));

        System.out.println(lakemedel);

    }
}
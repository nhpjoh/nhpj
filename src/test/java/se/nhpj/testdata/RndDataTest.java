package se.nhpj.testdata;

import org.junit.Before;
import org.junit.Test;

import javax.management.relation.RelationNotFoundException;

import java.util.List;

import static org.junit.Assert.*;

public class RndDataTest {


    @Test
    public void getChars() {
        String s = RndData.getChars(10);
        assertTrue("Antal tecken (10) stämmer inte: " + s, s.length() == 10);
    }

    @Test
    public void getNumbers() {
        String s = RndData.getNumbers(10);
        assertTrue("Antal nummertecken (10) stämmer inte: " + s, s.length() == 10);
    }

    @Test
    public void getANumber() {
        String s = RndData.getANumber(1000,9999);
        assertTrue("Siffran som kon tillbaka stämmer inte: " + s,s.length() == 4);
    }

    @Test
    public void getNumber() {
        int i = RndData.getNumber(1000,9999);
        assertTrue("Siffran som kon tillbaka stämmer inte: " + i,i >= 1000 && i <= 9999);
    }

    @Test
    public void getRandomASCIIString() {
        String s = RndData.getRandomASCIIString(10);
        assertTrue("Antal tecken (10) stämmer inte: " + s, s.length() == 10);
    }

    @Test
    public void fromFile() {
    }

    @Test
    public void rndFrom() {
        String[] s = {"a","b","c","d"};

        System.out.println(RndData.rndFrom( s ));
    }
}
package se.nhpj.testdata;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void getId() {
    }

    @Test
    public void setId() {
    }

    @Test
    public void getPersonnummer() {
    }

    @Test
    public void setPersonnummer() {
    }

    @Test
    public void getFornamn() {
    }

    @Test
    public void setFornamn() {
    }

    @Test
    public void getMellannamn() {
    }

    @Test
    public void setMellannamn() {
    }

    @Test
    public void getEfternamn() {
    }

    @Test
    public void setEfternamn() {
    }

    @Test
    public void isKvinna() {
    }

    @Test
    public void isMan() {
    }

    @Test
    public void isKvinna1() {
    }

    @Test
    public void isMan1() {
    }

    @Test
    public void setAdress() {
    }

    @Test
    public void getAdress() {
    }

    @Test
    public void getFodelseDatum() {
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getSlumpadPerson();
        System.out.println(person.getPersonnummer());
        System.out.println(person.getFodelseDatum());
    }

    @Test
    public void TestToString() {
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getSlumpadPerson();
        System.out.println(person);
    }

    @Test
    public void TestNotInList() {
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getPerson("193808269801");
        try {
            String test = person.getPersonnummer();
        } catch (NullPointerException ex) { System.out.println("NullPointer");}
        System.out.println(person);
    }

}
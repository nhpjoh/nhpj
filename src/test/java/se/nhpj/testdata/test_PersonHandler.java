package se.nhpj.testdata;

import org.junit.Test;

import static org.junit.Assert.*;

public class test_PersonHandler {

    @Test
    public void test_getPersonLogicId() {
        Person person = new Person();
        person.setPersonnummer("191010189809");

        System.out.println(PersonHandler.getPersonLogicId(person, "TEST28"));
    }

}
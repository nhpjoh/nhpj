package se.nhpj.testdata;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SkatteverketTest {

    private Person p;
    private Skatteverket s;

    @Before
    public void setUp() throws Exception {
        s = new Skatteverket();
    }

    @Test
    public void getSlumpadPerson() {
        p = s.getSlumpadPerson();
        System.out.println(p.toString());
    }

    @Test
    public void getSlumpadPerson1() {
        p = s.getSlumpadPerson(2000, 2018);
        System.out.println(p.toString());
    }

    @Test
    public void getPersons() {
        List alla = s.getPersons();
        System.out.println(alla.size());
    }

    @Test
    public void test_getPerson() {
        Person p = s.getPerson("191010189809");
        System.out.println(p);
    }
}
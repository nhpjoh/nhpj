package se.nhpj.testdata;

import org.junit.*;

import static org.junit.Assert.*;

public class NamnTest {

    Namn namn;
    @Before
    public void setUp() throws Exception {
        System.out.print("Testar: classen Namn : ");
        namn = new Namn();
    }

    @After
    public void tearDown() {
        System.out.println(" : Klar");
        namn = null;
    }

    @Test
    public void getFirstNameMale() {
        System.out.print("Testar: metoden getFirstNameMale");
        String n = namn.getFirstNameMale();
        assertTrue("Fick inget namn: getFirstNameMale : " + n, !n.isEmpty());
    }

    @Test
    public void getFirstNameFemale() {
        System.out.print("Testar: metoden getFirstNameFemale");
        String n = namn.getFirstNameFemale();
        assertTrue("Fick inget namn: getFirstNameFemale : " + n, !n.isEmpty());
    }

    @Test
    public void getLastNameTest() {
        System.out.print("Testar: metoden getLastNameTest");
        String n = namn.getLastNameTest();
        assertTrue("Fick inget namn: getLastNameTest : " + n, !n.isEmpty());
    }

    @Test
    public void getLastNameSwedish() {
        System.out.print("Testar: metoden getLastNameSwedish");
        String n = namn.getLastNameSwedish();
        assertTrue("Fick inget namn: getLastNameSwedish : " + n, !n.isEmpty());
    }
}
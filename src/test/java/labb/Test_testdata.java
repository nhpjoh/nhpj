package labb;

import java.util.*;

import org.junit.Test;
import se.nhpj.testdata.*;

import static se.nhpj.testdata.RndData.*;

/**
 * @author nhpj
 */
public class
Test_testdata {



    @Test
    public void testRndFrom_String() {
        String[] s = {"U","J","V"};

        System.out.println( rndFrom(s) );

        System.out.println( rndFrom( new String[]{"U","J","V"} ));
    }


    @Test
    public void test_RndData_fromFile() {
        System.out.println( RndData.fromFile("K:\\Testcenter\\Patrik_OR\\SkapaVardtagareV1.csv") );
    }

    @Test
    public void IngemarTest() {
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getSlumpadPerson();

        System.out.println(person);

        System.out.println(person.getPersonnummer() + "\t" + person.getFornamn() + ", " + person.getEfternamn());

        person.setMellannamn("Mellanders");
        System.out.println(person.getMellannamn());
        System.out.println(person.getAdress().toString());
    }

    @Test
    public void test() {
        System.out.println("\nTestar RndData klassen: ");
        System.out.println("Ett nummer mellan 20 & 256: " + getNumber(20,256));
        System.out.println("Tio slumade bokstäver: " + getChars(10));
        System.out.println("10 slumpade ascii tecken: " + getRandomASCIIString(10));
        
        System.out.println("\nTestar Adress, Namn, Telefonnummer och Personnummer klasserna: ");
        Adress adr = new Adress().getSwedishAdress();
        Namn namn = new Namn();
        Telefonnummer tfn = new Telefonnummer();
        Personnummer pnr = new Personnummer();
        pnr.setPersonnummer(pnr.generatePnr(1900,2018));
       
        String fnamn = "";
        String enamn = namn.getLastNameSwedish();
        
        if (pnr.isKvinna() == true ){ fnamn = namn.getFirstNameFemale(); }
        if (pnr.isMan()    == true ){ fnamn = namn.getFirstNameMale();   }
         
        Person patient = new Person();
        
        patient.setPersonnummer(pnr.toString());
        patient.setFornamn(fnamn);
        patient.setEfternamn(enamn);
        patient.setAdress(adr);
        
        System.out.println("Slumpar fram personData: " + patient);
        System.out.println("Slumpar fram tel.nr basserat på kommun: " + tfn.getTelefonnummer(adr.getKommun()));       // Telefon
        System.out.println("Slumpar fram ett mobilnummer: " + tfn.getMobilNummer());                        // Mobiltelefon

        System.out.println("\nTestar Personnummer klassen: ");
        pnr = new Personnummer();
        ArrayList al = new ArrayList();
        String nyttPnr;
        for (int i=0; i < 25 ; i++) {
            nyttPnr = pnr.generatePnr(1890,2018);
            if (al.indexOf(nyttPnr) == -1 )
                al.add(nyttPnr);
        }

        System.out.println("Genererar slumpmässigt " + al.size() + " olika personnummer till en lista");
        Collections.sort(al);
        System.out.println(al);

        System.out.println("\nTestar Skatteverket klassen: ");
        Skatteverket sk = new Skatteverket();
        System.out.println("Slumpad       : " + sk.getSlumpadPerson(1990,1999));
        List<Person> personer = sk.getPersons();
        System.out.println("Första i filen: " + personer.get(0).toString());
        
        System.out.println(patient.toString());
        System.out.println("Riktnummer: " + tfn.getRiktnummer("Testorten"));
    }

    @Test
    public void testSlumpaRader() {
        int antal = 10;
        for (int i = 0 ; i < antal ; i++ ) {
            System.out.println( RndData.fromFile("K:\\Testcenter\\Patrik_OR\\SkapaVardtagareV1.csv") );
        }
    }

}

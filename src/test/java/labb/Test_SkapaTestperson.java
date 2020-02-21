package labb;

import static org.junit.Assert.*;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import se.nhpj.testdata.*;

public class Test_SkapaTestperson {

    @Test
    public void skapaTestPerson() {
        TestPerson person = new TestPerson();
        Adress adress = new Adress();
        Namn namn = new Namn();
        Telefonnummer telefonnummer = new Telefonnummer();

        person.setId("1");
        String personnummer = new Personnummer().generatePnr(1900,2019);
        person.setPersonnummer(personnummer);

        if (person.isKvinna()) { person.setFornamn(namn.getFirstNameFemale()); }
        else { person.setFornamn(namn.getFirstNameMale()); }

        person.setMellannamn("TEST");

        person.setEfternamn(namn.getLastNameSwedish());

        adress = adress.getSwedishAdress();
        person.setAdress(adress);

        person.setTel(telefonnummer.getTelefonnummer(adress.getKommun()));
        person.setMobil(Telefonnummer.getMobilNummer());

        person.setePost(person.getFornamn().toLowerCase() + "." + person.getEfternamn().toLowerCase() + "@" + "testadressen" + ".se");

        System.out.println(person);

        System.out.println();
        System.out.println(person.toJson());

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(person.toJson());

        String pnr = JsonPath.read(document, "$.testperson.personnummer");
        String postnr = JsonPath.read(document, "$.testperson.adress.postnummer");

        System.out.println( "\nPersonnummer: " + pnr );
        System.out.println( "Postnummer: " + postnr );

        System.out.println((String) JsonPath.read(document, "$.testperson.personnummer") );



    }
}

package se.nhpj.ws;

import se.nhpj.testdata.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.UnsupportedEncodingException;

@Path("testperson")
public class TestPerson {
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getTestPerson() {
        // Startar en timer
        se.nhpj.testdata.Transaction transaction = new Transaction();
        transaction.start("testperson");

        se.nhpj.testdata.TestPerson person = new se.nhpj.testdata.TestPerson();
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

        transaction.stop("testperson");
        Integer transTime = transaction.getElapsedTime("testperson",Transaction.MILLISECONDS).intValue();
        System.out.println("RESTfull - testperson: " + person.toJson() + "  ResponseTime: " + transTime + "ms");

        return person.toJson();
    }

}

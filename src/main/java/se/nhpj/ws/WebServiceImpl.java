package se.nhpj.ws;

import se.nhpj.database.DB_AccessHSQLdb;
import se.nhpj.testdata.*;
import se.nhpj.testdata.Bolag;
import javax.jws.WebService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import se.nhpj.testdata.TestPerson;

@WebService(endpointInterface = "se.nhpj.ws.WebServiceInterface")
public class WebServiceImpl implements WebServiceInterface {

    @Override
    public String printMessage(String name) {
        return "Hello "+name+" from Puttes Java Code Server";
    }

    @Override
    public Person getSoap() {
        Person person = new Person();
        person.setId("998877");
        person.setPersonnummer("19660102171");
        person.setFornamn("Patrik");
        person.setMellannamn("TEST");
        person.setEfternamn("Johansson");
        return person;
    }

    @Override
    public List getSoapBig() {
        List<Person> lista = new ArrayList<>();
        Adress adress = new Adress();

        adress = adress.getSwedishAdress();
        Person person = new Person();
        person.setId("100001");
        person.setPersonnummer("196601020000");
        person.setFornamn("Patrik");
        person.setMellannamn("TEST");
        person.setEfternamn("Johansson");
        person.setAdress(adress);
        lista.add(person);

        adress = adress.getSwedishAdress();
        person = new Person();
        person.setId("100002");
        person.setPersonnummer("196701160000");
        person.setFornamn("Ulrika");
        person.setEfternamn("Johansson");
        person.setMellannamn("TEST");
        person.setAdress(adress);
        lista.add(person);

        return lista;
    }

    @Override
    public List<Bolag> getCompanies(String namn) {
        List<Bolag> lista = new ArrayList<>();
        Connection con;

        // Skapar kontakt med databasen
        con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");

        String SQL = "SELECT * FROM person WHERE bolag = true and fornamn like '%"+ namn + "%'";

        ResultSet result = DB_AccessHSQLdb.getResultSet(con,SQL);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                Bolag bolag = new Bolag();
                bolag.setId(result.getInt("ID"));
                bolag.setOrgNr(result.getString("PERSONNUMMER"));
                bolag.setNamn(result.getString("FORNAMN"));
                lista.add( bolag );
            }
            // Stänger databaskopplingen
            DB_AccessHSQLdb.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(lista);
        return lista;
    }

    @Override
    public List<Person> getPersons( String pnr, String fnamn, String enamn) {
        List<Person> lista = new ArrayList<>();
        Connection con;

        // Skapar kontakt med databasen
        con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");

        String SQL = "SELECT * FROM person WHERE bolag = false and PERSONNUMMER like '%"+ pnr + "%' and FORNAMN like '%"+ fnamn + "%' and EFTERNAMN like '%"+ enamn + "%'";

        ResultSet result = DB_AccessHSQLdb.getResultSet(con,SQL);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                Person person = new Person();
                person.setId(result.getString("ID"));
                person.setPersonnummer(result.getString("PERSONNUMMER"));
                person.setFornamn(result.getString("FORNAMN"));
                person.setEfternamn(result.getString("EFTERNAMN"));
                lista.add( person );
            }
            // Stänger databaskopplingen
            DB_AccessHSQLdb.closeConnection(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(lista);
        return lista;
    }

    @Override
    public TestPerson getTestPerson() {
        // Startar en timer
        se.nhpj.testdata.Transaction transaction = new Transaction();
        transaction.start("testperson");

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

        person.setePost(person.getFornamn().toLowerCase() +
                "." + person.getEfternamn().toLowerCase() +
                "@" + "testadressen" + ".se");

        transaction.stop("testperson");
        Integer transTime = transaction.getElapsedTime("testperson",Transaction.MILLISECONDS).intValue();
        System.out.println("WebService - testperson: " + person.toJson() + "  ResponseTime: " + transTime + "ms");

        return person;
    }
}

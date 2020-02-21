package se.nhpj.ws;

import org.junit.Test;

import se.nhpj.database.DB_Access;
import se.nhpj.database.DB_AccessHSQLdb;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.utils.XmlFormatter;
import se.nhpj.testdata.Bolag;
import se.nhpj.testdata.Person;

import javax.xml.soap.SOAPMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class WebServiceImplTest {


    @Test
    public void getSoap() {
    }

    @Test
    public void getSoapBig() {
    }

    @Test
    public void Test_getCompanies_request() throws Exception {

        // Skapar anropets XML
        SoapPerson sp = new SoapPerson();

        // Uppdatera/sätter method
        System.out.println("Sätter method och värden\n");

        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:getCompanies><namn></namn></ws:getCompanies>");

        // Anropets URL
        sp.setSoapEndpointUrl("http://localhost:8080/nhpj/person");
        String soapEndpointUrl = sp.getSoapEndpointUrl();

        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(sp.getXML());

        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);

        // Convert the response to XML response objekt
        SoapResponseXML responseXML = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );

        // kontrolerar svaret
        // sp.checkResponse(response);

        // Skriver ut svaret
        //System.out.println(new XmlFormatter().format(responseXML.getXML()));
        System.out.println(responseXML.getXML());

        // Leta i XML svaret
        // System.out.println( "Svaret: " + responseXML.getTagValue("*//return[1]") );

        // Logga svaret
        //response.logXML();

        // Konvertera svaret till HTML

        Integer antal = responseXML.getTagCount("*//return");

        for (int i = 0 ; i < antal ; i++ ) {

            System.out.print(responseXML.getTagValue("*//return["+(i+1)+"]/id"));
            System.out.print(" : "+responseXML.getTagValue("*//return["+(i+1)+"]/orgNr"));
            System.out.print(" : "+responseXML.getTagValue("*//return["+(i+1)+"]/namn"));
            System.out.println();
        }

    }

    @Test
    public void getCompanies_SQL() {
        List lista = new ArrayList();
        Connection con;

        // Skapar kontakt med databasen
        con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");

        String SQL = "SELECT * FROM person WHERE bolag = true";

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
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }

        // Skriver ut resultat listan
        Iterator itr = lista.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println(lista);


    }

    @Test
    public void Test_getPersons_request() throws Exception {

        // Skapar anropets XML
        SoapPerson sp = new SoapPerson();

        // Uppdatera/sätter method
        System.out.println("Sätter method och värden\n");
        String pnr = "19960102";

//        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:getSoapBig/>");
//        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:getSoap/>");
//        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:getPerson><id>100001</id></ws:getPerson>");
//        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:printMessage><arg0>Patrik</arg0></ws:printMessage>");
//        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:getCompanies><namn></namn></ws:getCompanies>");
        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:getPersons><pnr>"+pnr+"</pnr><fnamn></fnamn><enamn></enamn></ws:getPersons>");
//        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:getTestPerson/>");

        // Anropets URL
        sp.setSoapEndpointUrl("http://localhost:8080/nhpj/person");
        String soapEndpointUrl = sp.getSoapEndpointUrl();

        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(sp.getXML());

        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);

        // Convert the response to XML response objekt
        SoapResponseXML responseXML = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );

        // Skriver ut svaret
        System.out.println(new XmlFormatter().format(responseXML.getXML()));

        // Leta i XML svaret
        System.out.println( "Svaret: " + responseXML.getTagValue("*//return[1]") );

        // Konvertera svaret till HTML

        Integer antalBolag = responseXML.getTagCount("*//return");

        for (int i = 0 ; i < antalBolag ; i++ ) {
            System.out.println(responseXML.getTagValue("*//return["+(i+1)+"]/id"));
            System.out.println(responseXML.getTagValue("*//return["+(i+1)+"]/personnummer"));
            System.out.println(responseXML.getTagValue("*//return["+(i+1)+"]/fornamn"));
            System.out.println(responseXML.getTagValue("*//return["+(i+1)+"]/efternamn"));
        }

    }

    @Test
    public void getPersons_SQL() {
        List<Person> lista = new ArrayList<>();
        Connection con;

        String pnr = "1996010";
        String fnamn = "";
        String enamn = "";

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

        // Skriver ut resultat listan
        Iterator itr = lista.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println("\n\n" + lista);
    }

    @Test
    public void Test_getTestPersons_request() throws Exception {

        // Skapar anropets XML
        SoapPerson sp = new SoapPerson();

        // Väljer metod
        sp.insertXmlAfterTag("<soapenv:Body>", "<ws:getTestPerson/>");

        // Anropets URL
        sp.setSoapEndpointUrl("http://localhost:8080/nhpj/person");
        String soapEndpointUrl = sp.getSoapEndpointUrl();

        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(sp.getXML());

        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);

        // Convert the response to XML response objekt
        SoapResponseXML responseXML = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );

        // Skriver ut svaret
        System.out.println(new XmlFormatter().format(responseXML.getXML()));

        // Hanterar svaret
            System.out.println(responseXML.getTagValue("*//return/id"));
            System.out.println(responseXML.getTagValue("*//return/personnummer"));
            System.out.println(responseXML.getTagValue("*//return/adress"));

    }

}
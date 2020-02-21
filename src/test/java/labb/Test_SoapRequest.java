package labb;

import javax.xml.soap.SOAPMessage;
import org.junit.Test;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.HamtaGallandeUnderlagsversion;
import se.nhpj.soap.services.HamtaAktuellaOrdinationer;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.utils.XmlFormatter;
import se.nhpj.testdata.Transaction;

/**
 * @author nhpj
 */
public class Test_SoapRequest {

    String SERVICEENDPOINT_STP      = "https://prestanda/apisp";

    SoapResponseXML response;

    @Test
    public void test_HamtaGallandeUnderlagsversion() throws Exception {

        // Skapar anropets XML
        HamtaGallandeUnderlagsversion hgu = new HamtaGallandeUnderlagsversion();

        // Sätter miljö
        hgu.setSoapEndpointUrl(SERVICEENDPOINT_STP + "/HamtaGallandeUnderlagsversionResponder/V5");
        System.out.println("Endpoint: " + hgu.getSoapEndpointUrl());

        // Skapa en ticket
        Tickets ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
	    ticket.setHealthcareProfessionalLicense("AP");

        // Lägger till en ticket till anropet
        hgu.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        
        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken fil?: "+hgu.getStandardDefaultFileName());
        hgu.setStandardDefaultValues();
        System.out.println("Värdet från properties nu satt till: " + hgu.getTagValue("*//personnummer"));
        
        // Uppdatera/sätter indata
        System.out.println("Värde sätts nu till: 190001019801");
        hgu.setTagValue("*//personnummer", "190001019801"); // orgVal 189910109819
        
        // Anropets URL
        String soapEndpointUrl = hgu.getSoapEndpointUrl();

        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(hgu.getXML());
        
        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);
        
        // Convert the response to XML response objekt
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );
        
        // kontrolerar svaret
        hgu.checkResponse(response);
        
        // Skriver ut svaret
        System.out.println(response.getXML());
        
        // Leta i XML svaret
        System.out.println( "Underlagsversion: " + response.getTagValue("*//underlagsversion") );

        // Logga svaret
        response.logXML();

    }

}

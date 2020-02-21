package se.ehm.testdata.SOL.services;

import org.junit.Test;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.SoapResponseXML;

import javax.xml.soap.SOAPMessage;

public class SokDosmottagareTest {

    @Test
    public void checkResponse() {
        // Skapar anropets XML
        SokDosmottagare sd = new SokDosmottagare();

        // Skapa en ticket
        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode("9000027");
        ticket.setHealthcareProfessionalLicense("LK");

        // Lägger till en ticket till anropet
        sd.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // Uppdatera/sätter indata
//        System.out.println("Värde sätts nu till: 190001019801");
//        hgu.setTagValue("*//personnummer", "190001019801"); // orgVal 189910109819

        // Anropets URL
        String soapEndpointUrl = sd.getSoapEndpointUrl();

        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(sd.getXML());

        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);

        // Convert the response to XML response objekt
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );

        // kontrolerar svaret
        sd.checkResponse(response);

        // Skriver ut svaret
        System.out.println(response.getXML());
    }
}
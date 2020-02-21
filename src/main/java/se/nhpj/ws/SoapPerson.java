package se.nhpj.ws;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class SoapPerson extends BaseXML {

    public SoapPerson(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/SoapPerson.properties");
    }

    public SoapPerson() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/SoapPerson.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en SoapPerson returneras
        Integer retVal=0;
	retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SoapPerson hämtat OK ");
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//faultstring") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "http://192.168.37.129:8080/nhpj/person";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "               <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.nhpj.se/\">\n" +
            "               <soapenv:Header/>\n" +
            "                   <soapenv:Body>\n" +
//            "                       <ws:getPerson><id></id></ws:getPerson>\n" +
//            "                       <ws:getSoap/>\n" +
//            "                       <ws:getSoapBig/>\n" +
//            "                       <ws:printMessage><arg0></arg0></ws:printMessage>\n" +
            "                   </soapenv:Body>\n" +
            "               </soapenv:Envelope>";


}

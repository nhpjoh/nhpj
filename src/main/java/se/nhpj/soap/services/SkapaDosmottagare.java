package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SkapaDosmottagare extends BaseXML {

    public SkapaDosmottagare(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/SkapaDosmottagare.properties");
    }

    public SkapaDosmottagare() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/SkapaDosmottagare.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
        retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SkapaDosmottagare: OK ");
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

    private String soapEndpointUrl = "https://prestanda/apisp/SkapaDosmottagareResponder/V4";

    private String XML =    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:SkapaDosmottagare xmlns:urn=\"urn:riv:se.apotekensservice:expo:SkapaDosmottagareResponder:4\">\n" +
                            "         <urn:adress></urn:adress>\n" +
                            "         <urn:apoteksIdDosmottagare></urn:apoteksIdDosmottagare>\n" +
                            "         <urn:apoteksIdDosproducent></urn:apoteksIdDosproducent>\n" +
                            "         <urn:arbetsplatskod></urn:arbetsplatskod>\n" +
                            "         <urn:avdelning></urn:avdelning>\n" +
                            "         <urn:dosmottagarId></urn:dosmottagarId>\n" +
                            "         <urn:kommunkod></urn:kommunkod>\n" +
                            "         <urn:lanskod></urn:lanskod>\n" +
                            "         <urn:mottagarnamn></urn:mottagarnamn>\n" +
                            "         <urn:postnummer></urn:postnummer>\n" +
                            "         <urn:postort></urn:postort>\n" +
                            "         <urn:typ></urn:typ>\n" +
                            "      </urn:SkapaDosmottagare>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";


}

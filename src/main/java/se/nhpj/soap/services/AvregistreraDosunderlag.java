package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class AvregistreraDosunderlag extends BaseXML {

    public AvregistreraDosunderlag(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/AvregistreraDosunderlag.properties");
    }

    public AvregistreraDosunderlag() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/AvregistreraDosunderlag.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att rätt svar returneras
        Integer retVal=0;
	retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("AvregistreraDosunderlag OK ");
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

    private String soapEndpointUrl = "http://test28:10080/apisp/AvregistreraDosunderlagResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:AvregistreraDosunderlag xmlns:urn=\"urn:riv:se.apotekensservice:or:AvregistreraDosunderlagResponder:4\">\n" +
                            "         <urn:personnummer>189001109819</urn:personnummer>\n" +
                            //"         <urn:gruppforskrivarkod></urn:gruppforskrivarkod>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:AvregistreraDosunderlag>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setPersonnummer( String personnummer ) { this.setTagValue("*//personnummer", personnummer); }

}

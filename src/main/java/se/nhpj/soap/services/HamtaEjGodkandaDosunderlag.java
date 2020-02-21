package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class HamtaEjGodkandaDosunderlag extends BaseXML {
    
    public HamtaEjGodkandaDosunderlag(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaEjGodkandaDosunderlag.properties");
    }
   
    public HamtaEjGodkandaDosunderlag() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaEjGodkandaDosunderlag.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en HamtaEjGodkandaDosunderlagslista returneras
        Integer retVal=0;
	retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaEjGodkandaDosunderlagslistan hämtad OK ");
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

    private String soapEndpointUrl = "http://test28.systest.receptpartner.se:10080/apisp/HamtaEjGodkandaDosunderlagResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                            "   <soapenv:Header>" +
                            "   </soapenv:Header>" +
                            "   <soapenv:Body>" +
                            "      <urn:HamtaEjGodkandaDosunderlag xmlns:urn=\"urn:riv:se.apotekensservice:or:HamtaEjGodkandaDosunderlagResponder:4\">" +
                            "         <urn:apoteksid>7350045511997</urn:apoteksid>" +
                            "         <urn:extension/>" +
                            "      </urn:HamtaEjGodkandaDosunderlag>" +
                            "   </soapenv:Body>" +
                            "</soapenv:Envelope>";

    public void setApoteksid(String apoteksid) {
        this.setTagValue("*//apoteksid", apoteksid);
    }

}

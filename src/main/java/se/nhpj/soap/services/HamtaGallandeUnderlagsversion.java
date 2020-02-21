package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author Paj
 */
public class HamtaGallandeUnderlagsversion extends BaseXML {

    public HamtaGallandeUnderlagsversion(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaGallandeUnderlagsversion.properties");
    }

    public HamtaGallandeUnderlagsversion() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaGallandeUnderlagsversion.properties");
    }
    
    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
        Boolean rc = false;
        
	    retVal = retVal + response.getTagCount("*//faultstring");
		
        try {
            if (retVal == 0 ) {
                System.out.println("Underlagsversion hämtad OK: " + response.getTagValue("*//underlagsversion") + " : personnummer: " + this.getTagValue("*//personnummer"));
                System.out.println("\n");
                rc = true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//faultstring") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return rc;
    }

    
//    private String soapEndpointUrl = "https://10.251.123.13/apisp/HamtaGallandeUnderlagsversionResponder/V5";
    private String soapEndpointUrl = "https://prestanda/apisp/HamtaGallandeUnderlagsversionResponder/V5";
    
    private String XML =    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:HamtaGallandeUnderlagsversion xmlns:urn=\"urn:riv:se.apotekensservice:or:HamtaGallandeUnderlagsversionResponder:5\">\n" +
                            "         <urn:personnummer>189910109819</urn:personnummer>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:HamtaGallandeUnderlagsversion>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    
}

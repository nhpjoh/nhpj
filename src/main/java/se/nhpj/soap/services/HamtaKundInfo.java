package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class HamtaKundInfo extends BaseXML {
    
    public HamtaKundInfo(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaKundInfo.properties");
    }
   
    public HamtaKundInfo() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaKundInfo.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
        
	    retVal = retVal + response.getTagCount("*//faultstring");
		
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaKundInfo hämtat OK");
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaKundInfoResponder/V7";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:HamtaKundInfo xmlns:urn=\"urn:riv:se.apotekensservice:axs:HamtaKundInfoResponder:7\">\n" +
            "         <urn:klientinformation>\n" +
            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\">PRESTANDA</urn1:anvandare>\n" +
            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\">1</urn1:session>\n" +
            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\">1234567890123</urn1:system>\n" +
            "         </urn:klientinformation>\n" +
            "         <urn:personnummer>189912319812</urn:personnummer>\n" +
            "         <urn:extension/>\n" +
            "      </urn:HamtaKundInfo>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    public void setPersonnummer(String personnummer) {
        this.setTagValue("*//personnummer", personnummer);
    }
}

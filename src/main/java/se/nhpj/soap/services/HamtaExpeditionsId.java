package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;


/**
 * @author nhpj
 */
public class HamtaExpeditionsId extends BaseXML {
    
    public HamtaExpeditionsId(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaExpeditionsId.properties");
    }
   
    public HamtaExpeditionsId() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaExpeditionsId.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
        
	    retVal = retVal + response.getTagCount("*//expeditionsId");
		
        try {
            if (retVal > 0 ) {
                System.out.println("ExpeditionsId hämtat OK: " + response.getTagValue("*//expeditionsId"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaExpeditionsIdResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:HamtaExpeditionsId xmlns:urn=\"urn:riv:se.apotekensservice:axs:HamtaExpeditionsIdResponder:4\">\n" +
            "         <urn:djurRecept></urn:djurRecept>\n" +
            "         <urn:antalExpeditionsId></urn:antalExpeditionsId>\n" +
            "      </urn:HamtaExpeditionsId>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";


}

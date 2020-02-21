package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class HamtaFolkInfo extends BaseXML {
    
    public HamtaFolkInfo(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaFolkInfo.properties");
    }
   
    public HamtaFolkInfo() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaFolkInfo.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en HamtaFolkInfo returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaFolkInfo hämtat OK : Statuskod: " + response.getTagValue("*//statusKod"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaFolkInfoResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:HamtaFolkInfo xmlns:urn=\"urn:riv:se.apotekensservice:axs:HamtaFolkInfoResponder:5\">\n" +
                            "         <urn:klientinformation>\n" +
                            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:anvandare>\n" +
                            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:session>\n" +
                            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:system>\n" +
                            "         </urn:klientinformation>\n" +
                            "         <urn:personnummer></urn:personnummer>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:HamtaFolkInfo>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setPersonnummer(String personnummer){
        this.setTagValue("*//personnummer", personnummer);
    }
}

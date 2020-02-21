package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;

/**
 * @author nhpj
 */
public class HamtaHkdbKonto extends BaseXML {
    
    public HamtaHkdbKonto(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaHkdbKonto.properties");
    }
   
    public HamtaHkdbKonto() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaHkdbKonto.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
        
	    retVal = retVal + response.getTagCount("*//faultstring");
		
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaHkdbKonto hämtat OK - Kund: " + response.getTagValue("*//ansluten"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaHkdbKontoResponder/V1";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "   <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:HamtaHkdbKonto xmlns:urn=\"urn:riv:se.apotekensservice:pris:HamtaHkdbKontoResponder:1\">\n" +
                            "         <urn:klientinformation>\n" +
                            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:pris:1\"></urn1:anvandare>\n" +
                            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:pris:1\"></urn1:session>\n" +
                            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:pris:1\"></urn1:system>\n" +
                            "         </urn:klientinformation>\n" +
                            "         <urn:persNr></urn:persNr>\n" +
                            "      </urn:HamtaHkdbKonto>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setPersonnummer(String personnummer) {
        this.setTagValue("*//persNr", personnummer);
    }


}

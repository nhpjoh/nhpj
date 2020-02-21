package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class HamtaIckeAktuellaOrdinationer extends BaseXML {
    
    public HamtaIckeAktuellaOrdinationer(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaIckeAktuellaOrdinationer.properties");
    }
   
    public HamtaIckeAktuellaOrdinationer() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaIckeAktuellaOrdinationer.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en HamtaIckeAktuellaOrdinationer returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaIckeAktuellaOrdinationer hämtat OK ");
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaIckeAktuellaOrdinationerResponder/V6";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:HamtaIckeAktuellaOrdinationer xmlns:urn=\"urn:riv:se.apotekensservice:or:HamtaIckeAktuellaOrdinationerResponder:6\">\n" +
                            "         <urn:personnummer>201310202393</urn:personnummer>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:HamtaIckeAktuellaOrdinationer>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setPersonnummer( String personnummer ){
        this.setTagValue("*//personnummer", personnummer);
    }

}

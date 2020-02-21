package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;


/**
 * @author nhpj
 */
public class HamtaAktuellaOrdinationer extends BaseXML {
    
    public HamtaAktuellaOrdinationer(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaAktuellaOrdinationer.properties");
    }
   
    public HamtaAktuellaOrdinationer() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaAktuellaOrdinationer.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaAktuellaOrdinationer OK : artikelLista: " + response.getTagCount("*//artikelLista") + " : helforpackningsordinationLista : " + response.getTagCount("*//helforpackningsordinationLista"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaAktuellaOrdinationerResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:HamtaAktuellaOrdinationer xmlns:urn=\"urn:riv:se.apotekensservice:or:HamtaAktuellaOrdinationerResponder:5\">\n" +
                            "         <urn:personnummer></urn:personnummer>\n" +
                            "         <urn:ordinationsId></urn:ordinationsId>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:HamtaAktuellaOrdinationer>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setPersonnummer(String personnummer) {
        this.setTagValue("*//personnummer", personnummer);
    }
    public void setOrdinationsId(String ordinationsId) {
        this.setTagValue("*//ordinationsId", ordinationsId);
    }

}

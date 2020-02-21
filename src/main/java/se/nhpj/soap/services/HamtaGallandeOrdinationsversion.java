package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class HamtaGallandeOrdinationsversion extends BaseXML {

    public HamtaGallandeOrdinationsversion(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaGallandeOrdinationsversion.properties");
    }

    public HamtaGallandeOrdinationsversion() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaGallandeOrdinationsversion.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
	retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaGallandeOrdinationsversion hämtat OK ");
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

    private String soapEndpointUrl = "<serviceEndpoint>";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "               <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "                   <soapenv:Header>\n" +
            "                   </soapenv:Header>\n" +
            "                   <soapenv:Body>\n" +
            "                       <urn:HamtaGallandeOrdinationsversion xmlns:urn=\"urn:riv:se.apotekensservice:or:HamtaGallandeOrdinationsversionResponder:4\">\n" +
            "                       <urn:ordinationsid>4845654326</urn:ordinationsid>\n" +
            "                       <urn:extension/>\n" +
            "                       </urn:HamtaGallandeOrdinationsversion>\n" +
            "                   </soapenv:Body>\n" +
            "               </soapenv:Envelope>";

    public void setOrdinationsId(String ordinationsid) {
        this.setTagValue("*//ordinationsid", ordinationsid);
    }

}

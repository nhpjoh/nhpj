package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class GodkannDosunderlag extends BaseXML {

    public GodkannDosunderlag(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/GodkannDosunderlag.properties");
    }

    public GodkannDosunderlag() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/GodkannDosunderlag.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
	retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("GodkannDosunderlag uppdaterat OK ");
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

    private String soapEndpointUrl = "http://test28:10080/apisp/GodkannDosunderlagResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                            "   <soapenv:Header>" +
                            "   </soapenv:Header>" +
                            "   <soapenv:Body>" +
                            "      <urn:GodkannDosunderlag xmlns:urn=\"urn:riv:se.apotekensservice:or:GodkannDosunderlagResponder:5\">" +
                            "         <urn:personnummer></urn:personnummer>" +
                            "         <urn:underlagsVersion></urn:underlagsVersion>\n" +
                            "         <urn:anvandare></urn:anvandare>" +
                            "         <urn:yrkeskod></urn:yrkeskod>" +
                            "         <urn:efternamn></urn:efternamn>" +
                            "         <urn:fornamn></urn:fornamn>" +
                            "         <urn:befattningskod></urn:befattningskod>" +
                            "         <urn:extension/>" +
                            "      </urn:GodkannDosunderlag>" +
                            "   </soapenv:Body>" +
                            "</soapenv:Envelope>";

    public void setPersonnummer( String personnummer ) { this.setTagValue("*//personnummer", personnummer); }
    public void setUnderlagsVersion( String underlagsVersion ) { this.setTagValue("*//underlagsVersion", underlagsVersion); }

}

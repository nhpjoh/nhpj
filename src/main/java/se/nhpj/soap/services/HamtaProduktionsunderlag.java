package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class HamtaProduktionsunderlag extends BaseXML {

    public HamtaProduktionsunderlag(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaProduktionsunderlag.properties");
    }

    public HamtaProduktionsunderlag() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaProduktionsunderlag.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en HamtaProduktionsunderlag returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaProduktionsunderlag hämtat OK : DosunderlagsStatus: " + response.getTagValue("*//status") + ", Tidpunkt: " + response.getTagValue("*//tidpunkt"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaProduktionsunderlagResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:riv:se.apotekensservice:or:HamtaProduktionsunderlagResponder:4\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:HamtaProduktionsunderlag>\n" +
                            "         <urn:personnummer></urn:personnummer>\n" +
                            "         <urn:extension>\n" +
                            "         </urn:extension>\n" +
                            "      </urn:HamtaProduktionsunderlag>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>\n";


}

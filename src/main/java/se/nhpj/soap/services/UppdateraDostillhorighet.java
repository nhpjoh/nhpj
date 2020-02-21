package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class UppdateraDostillhorighet extends BaseXML {

    public UppdateraDostillhorighet(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/UppdateraDostillhorighet.properties");
    }

    public UppdateraDostillhorighet() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/UppdateraDostillhorighet.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
	retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("UppdateraDostillhorighet skapat OK ");
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

    private String soapEndpointUrl = "http://test28:10080/apisp/UppdateraDostillhorighetResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "<soapenv:Header>" +
                            "</soapenv:Header>" +
                            "   <soapenv:Body>" +
                            "      <urn:UppdateraDostillhorighet xmlns:urn=\"urn:riv:se.apotekensservice:or:UppdateraDostillhorighetResponder:4\">" +
                            "         <urn:franApoteksId>7350045511997</urn:franApoteksId>" +
                            "         <urn:personnummer>200401072392</urn:personnummer>" +
                            "         <urn:tillApoteksId>7350045511997</urn:tillApoteksId>" +
                            "      </urn:UppdateraDostillhorighet>" +
                            "   </soapenv:Body>" +
                            "</soapenv:Envelope>";

    public void setFranApoteksId( String franApoteksId) {
        this.setTagValue("*//franApoteksId", franApoteksId);
    }
    public void setPersonnummer(  String personnummer) {  this.setTagValue("*//personnummer", personnummer); }
    public void setTillApoteksId( String tillApoteksId) {
        this.setTagValue("*//tillApoteksId", tillApoteksId);
    }

}

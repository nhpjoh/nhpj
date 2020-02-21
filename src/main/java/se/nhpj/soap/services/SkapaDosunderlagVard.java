package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class SkapaDosunderlagVard extends BaseXML {

    public SkapaDosunderlagVard(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/SkapaDosunderlagVard.properties");
    }

    public SkapaDosunderlagVard() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/SkapaDosunderlagVard.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att rätt svar returneras
        Integer retVal=0;
	retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SkapaDosunderlagVard skapat OK ");
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

    private String soapEndpointUrl = "http://test28:10080/apisp/SkapaDosunderlagVardResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "   <soapenv:Header>\n" +
                        "   </soapenv:Header>\n" +
                        "   <soapenv:Body>\n" +
                        "      <urn:SkapaDosunderlagVard xmlns:urn=\"urn:riv:se.apotekensservice:or:SkapaDosunderlagVardResponder:4\">\n" +
                        "         <urn:personnummer>190111179818</urn:personnummer>\n" +
                        "         <urn:samtyckeDos>true</urn:samtyckeDos>\n" +
                        "         <urn:arbetsplatskod>4000000000000</urn:arbetsplatskod>\n" +
                        "         <urn:yrkeskod>LK</urn:yrkeskod>\n" +
                        "         <urn:efternamn>Lakare</urn:efternamn>\n" +
                        "         <urn:fornamn>Lars</urn:fornamn>\n" +
                        "         <urn:befattningskod>1234</urn:befattningskod>\n" +
                        "      </urn:SkapaDosunderlagVard>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";

    public void setPersonnummer( String personnummer)     { this.setTagValue("*//personnummer", personnummer); }
    public void setSamtyckeDos( String samtyckeDos)       { this.setTagValue("*//samtyckeDos", samtyckeDos); }
    public void setArbetsplatskod( String arbetsplatskod) {
        this.setTagValue("*//arbetsplatskod", arbetsplatskod);
    }
    public void setYrkeskod( String yrkeskod)             { this.setTagValue("*//yrkeskod", yrkeskod); }
    public void setEfternamn( String efternamn)           { this.setTagValue("*//efternamn", efternamn); }
    public void setFornamn( String fornamn)               { this.setTagValue("*//fornamn", fornamn); }
    public void setBefattningskod( String befattningskod) {
        this.setTagValue("*//befattningskod", befattningskod);
    }

}

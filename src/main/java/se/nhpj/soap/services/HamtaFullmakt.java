package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;


/**
 * @author nhpj
 */
public class HamtaFullmakt extends BaseXML {
    
    public HamtaFullmakt(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaFullmakt.properties");
    }
   
    public HamtaFullmakt() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaFullmakt.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaFullmakt hämtat OK - persNr: " + response.getTagValue("*//persNr"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaFullmaktResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header></soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:HamtaFullmakt xmlns:urn=\"urn:riv:se.apotekensservice:folk:HamtaFullmaktResponder:4\">\n" +
                            "         <urn:enhetId>TaBort</urn:enhetId>\n" +
                            "         <urn:fullmaktstatus>1</urn:fullmaktstatus>\n" +
                            "         <urn:fullmaktval>A</urn:fullmaktval>\n" +
                            "         <urn:hsaId>TaBort</urn:hsaId>\n" +
                            "         <urn:klientinformation>\n" +
                            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:folk:4\">Prestanda</urn1:anvandare>\n" +
                            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:folk:4\">1</urn1:session>\n" +
                            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:folk:4\">7350045511997</urn1:system>\n" +
                            "         </urn:klientinformation>\n" +
                            "         <urn:persNr>189001099804</urn:persNr>\n" +
                            "         <urn:vhavPersNr>TaBort</urn:vhavPersNr>\n" +
                            "      </urn:HamtaFullmakt>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";
    
    public void setPersonnummer(String personnummer) {
        this.setTagValue("*//HamtaFullmakt/persNr", personnummer);
    }

    @Override
    public void setStandardDefaultValues() {
        super.setStandardDefaultValues();
        this.removeTag("*//HamtaFullmakt/enhetId");
        this.removeTag("*//HamtaFullmakt/hsaId");
        this.removeTag("*//HamtaFullmakt/vhavPersNr");
    }
}

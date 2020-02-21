package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class KontrolleraForman extends BaseXML {
    
    public KontrolleraForman(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/KontrolleraForman.properties");
    }
   
    public KontrolleraForman() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/KontrolleraForman.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en KontrolleraForman returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("KontrolleraForman hämtat OK : formanskod = " + response.getTagValue("*//formanskod"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/KontrolleraFormanResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:KontrolleraForman xmlns:urn=\"urn:riv:se.apotekensservice:pris:KontrolleraFormanResponder:4\">\n" +
                            "         <urn:artikelIdLista>\n" +
                            "            <urn1:gtin xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"/>\n" +
                            "            <urn1:nplPackageId xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:nplPackageId>\n" +
                            "            <urn1:varunr xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:varunr>\n" +
                            "         </urn:artikelIdLista>\n" +
                            "         <urn:klientinformation>\n" +
                            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:anvandare>\n" +
                            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:session>\n" +
                            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:system>\n" +
                            "         </urn:klientinformation>\n" +
                            "         <urn:fodelsedatum></urn:fodelsedatum>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:KontrolleraForman>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setFodelsedatum( String fodelsedatum) {
        this.setTagValue("*//fodelsedatum", fodelsedatum);
    }
    public void setPersonnummer( String personnummer) {
        this.setFodelsedatum(personnummer.substring(0, personnummer.length()-4));
    }
    
}

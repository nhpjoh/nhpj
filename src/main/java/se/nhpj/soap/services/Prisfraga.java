package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class Prisfraga extends BaseXML {
    
    public Prisfraga(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/Prisfraga.properties");
    }
   
    public Prisfraga() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/Prisfraga.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("Prisfraga hämtat OK på person " + response.getTagValue("*//personnummer"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/PrisfragaResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:Prisfraga xmlns:urn=\"urn:riv:se.apotekensservice:pris:PrisfragaResponder:5\">\n" +
            "         <urn:klientinformation>\n" +
            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:anvandare>\n" +
            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:session>\n" +
            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:system>\n" +
            "         </urn:klientinformation>\n" +
            "         <urn:personnummer></urn:personnummer>\n" +
            "         <urn:rader>\n" +
            "            <urn2:antal xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:antal>\n" +
            "            <urn2:formansVal xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:formansVal>\n" +
            "            <urn2:pris xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:pris>\n" +
            "            <urn2:prisUtbyte xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:prisUtbyte>\n" +
            "            <urn2:radNr xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:radNr>\n" +
            "            <urn2:artikelIdentiteter xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"/>\n" +
            "            <urn2:extension xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"/>\n" +
            "         </urn:rader>\n" +
            "      </urn:Prisfraga>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    public void setPersonnummer( String personnummer) {
        this.setTagValue("*//personnummer", personnummer);
    }
}

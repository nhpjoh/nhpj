package se.nhpj.soap.services;

import se.nhpj.soap.*;
import se.nhpj.soap.services.SoapResponseXML;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class KorrigeraOrdinationApotek extends BaseXML {
    
    public KorrigeraOrdinationApotek(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/KorrigeraOrdinationApotek.properties");
    }
   
    public KorrigeraOrdinationApotek() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/KorrigeraOrdinationApotek.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println( "Ordination: " + response.getTagValue("*//ordinationsid") + "  korrigerat OK ");
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

    private String soapEndpointUrl = "https://prestanda//apisp/KorrigeraOrdinationApotekResponder/V7";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:KorrigeraOrdinationApotek xmlns:urn=\"urn:riv:se.apotekensservice:or:KorrigeraOrdinationApotekResponder:7\">\n" +
            "         <urn:akut></urn:akut>\n" +
            "         <urn:andamalKlartext></urn:andamalKlartext>\n" +
            "         <urn:antalForpackningar></urn:antalForpackningar>\n" +
            "         <urn:apoteketsKommentar></urn:apoteketsKommentar>\n" +
            "         <urn:doseringstext1></urn:doseringstext1>\n" +
            "         <urn:doseringstext2></urn:doseringstext2>\n" +
            "         <urn:ejTillatenSubstitution></urn:ejTillatenSubstitution>\n" +
            "         <urn:forandrandeOrdinator>\n" +
            "            <urn1:arbetsplatskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:arbetsplatskod>\n" +
            "            <urn1:befattningskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "            <urn1:efternamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:efternamn>\n" +
            "            <urn1:fornamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:fornamn>\n" +
            "            <urn1:forskrivarkod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:forskrivarkod>\n" +
            "            <urn1:legitimationskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:legitimationskod>\n" +
            "            <urn1:yrkeskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:yrkeskod>\n" +
            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "         </urn:forandrandeOrdinator>\n" +
            "         <urn:forandring>\n" +
            "            <urn1:kommentar xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:kommentar>\n" +
            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "         </urn:forandring>\n" +
            "         <urn:forman></urn:forman>\n" +
            "         <urn:forstaUttagFore></urn:forstaUttagFore>\n" +
            "         <urn:ingenDygnsdos></urn:ingenDygnsdos>\n" +
            "         <urn:nplId></urn:nplId>\n" +
            "         <urn:nplPackId></urn:nplPackId>\n" +
            "         <urn:ordinationsid></urn:ordinationsid>\n" +
            "         <urn:resterandeAntalUttag></urn:resterandeAntalUttag>\n" +
            "         <urn:sistaGiltighetsdag></urn:sistaGiltighetsdag>\n" +
            "         <urn:slutexpedierad></urn:slutexpedierad>\n" +
            "         <urn:startforpackning></urn:startforpackning>\n" +
            "         <urn:totalmangdKvar></urn:totalmangdKvar>\n" +
            "         <urn:underlagsversion></urn:underlagsversion>\n" +
            "         <urn:anvandare></urn:anvandare>\n" +
            "         <urn:yrkeskod></urn:yrkeskod>\n" +
            "         <urn:efternamn></urn:efternamn>\n" +
            "         <urn:fornamn></urn:fornamn>\n" +
            "         <urn:befattningskod></urn:befattningskod>\n" +
            "      </urn:KorrigeraOrdinationApotek>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";
    
    public void setUnderlagsversion(String underlagsversion) {
        this.setTagValue("*//KorrigeraOrdinationApotek/underlagsversion", underlagsversion);
    }
    public void setOrdinationsId(String ordinationsid) {
        this.setTagValue("*//KorrigeraOrdinationApotek/ordinationsid", ordinationsid);
    }
    public void setAntalForpackningar(String antal) {
        this.setTagValue("*//KorrigeraOrdinationApotek/antalForpackningar", antal);
    }
    public void setTotalmangdKvar(String antal) {
        this.setTagValue("*//KorrigeraOrdinationApotek/totalmangdKvar", antal);
    }
    public void setResterandeAntalUttag(String antal) {
        this.setTagValue("*//KorrigeraOrdinationApotek/resterandeAntalUttag", antal);
    }
    public void setNplId(String nplId) {
        this.setTagValue("*//KorrigeraOrdinationApotek/nplId", nplId);
    }
    public void setNplPackId(String nplPackId) {
        this.setTagValue("*//KorrigeraOrdinationApotek/nplPackId", nplPackId);
    }
    public void setForstaUttagFore(String datum) {
        this.setTagValue("*//KorrigeraOrdinationApotek/forstaUttagFore", datum);
    }
    public void setSistaGiltighetsdag(String datum) {
        this.setTagValue("*//KorrigeraOrdinationApotek/sistaGiltighetsdag", datum);
    }



        
}

package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class AnonymPrisfraga extends BaseXML {
    
    public AnonymPrisfraga(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/AnonymPrisfraga.properties");
    }
   
    public AnonymPrisfraga() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/AnonymPrisfraga.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en AnonymPrisfraga returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("AnonymPrisfraga hämtat OK : bruttoBeloppHogkostnad = " + response.getTagValue("*//bruttoBeloppHogkostnad"));
                return true;
            } else {
                    System.err.println(response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//faultstring") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/apisp/AnonymPrisfragaResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:AnonymPrisfraga xmlns:urn=\"urn:riv:se.apotekensservice:pris:AnonymPrisfragaResponder:5\">\n" +
                            "         <urn:ackumuleratBrutto></urn:ackumuleratBrutto>\n" +
                            "         <urn:ackumuleratNetto></urn:ackumuleratNetto>\n" +
                            "         <urn:harFrikort>true</urn:harFrikort>\n" +
                            "         <urn:klientinformation>\n" +
                            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:anvandare>\n" +
                            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:session>\n" +
                            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:pris:4\"></urn1:system>\n" +
                            "         </urn:klientinformation>\n" +
                            "         <urn:periodstart>${#TestCase#periodStart}</urn:periodstart>\n" +
                            "         <urn:rader>\n" +
                            "            <urn2:antal xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:antal>\n" +
                            "            <urn2:formansVal xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:formansVal>\n" +
                            "            <urn2:pris xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:pris>\n" +
                            "            <urn2:radNr xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:radNr>\n" +
//                            "            <urn2:artikelIdentiteter xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"/>\n" +
                            "         </urn:rader>\n" +
                            "         <urn:rader>\n" +
                            "            <urn2:antal xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:antal>\n" +
                            "            <urn2:formansVal xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:formansVal>\n" +
                            "            <urn2:pris xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:pris>\n" +
                            "            <urn2:radNr xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"></urn2:radNr>\n" +
//                            "            <urn2:artikelIdentiteter xmlns:urn2=\"urn:riv:se.apotekensservice:pris:5\"/>\n" +
                            "         </urn:rader>\n" +
                            "         <urn:fodelsedatum></urn:fodelsedatum>\n" +
                            "      </urn:AnonymPrisfraga>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setFodelsedatum( String fodelsedatum) {
        this.setTagValue("*//fodelsedatum", fodelsedatum);
    }
    public void setPersonnummer( String personnummer) {
        this.setFodelsedatum(personnummer.substring(0, personnummer.length()-4));
    }

}

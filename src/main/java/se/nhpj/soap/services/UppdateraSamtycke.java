package se.nhpj.soap.services;

import se.nhpj.soap.services.SoapResponseXML;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class UppdateraSamtycke extends BaseXML {
    
    public UppdateraSamtycke(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/UppdateraSamtycke.properties");
    }
   
    public UppdateraSamtycke() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/UppdateraSamtycke.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att ett tomt svar returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("Uppdaterat OK");
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

    private String soapEndpointUrl = "https://prestanda/apisp/UppdateraSamtyckeResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "<soapenv:Header>\n" +
            "</soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:UppdateraSamtycke xmlns:urn=\"urn:riv:se.apotekensservice:axs:UppdateraSamtyckeResponder:4\">\n" +
            "         <urn:klientinformation>\n" +
            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:anvandare>\n" +
            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:session>\n" +
            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:system>\n" +
            "         </urn:klientinformation>\n" +
            "         <urn:personnummer></urn:personnummer>\n" +
            "         <urn:samtycke>\n" +
            "            <urn1:ees xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:ees>\n" +
//            "            <urn1:hkdb xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:hkdb>\n" +
            "            <urn1:rr xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:rr>\n" +
            "            <urn1:rrd xmlns:urn1=\"urn:riv:se.apotekensservice:axs:4\"></urn1:rrd>\n" +
            "         </urn:samtycke>\n" +
            "      </urn:UppdateraSamtycke>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    public void setPersonnummer( String personnummer) {
        this.setTagValue("*//personnummer", personnummer);
    }
    public void setSamtyckeEES(Boolean value) {
        this.setTagValue("*//samtycke/ees", value.toString());
    }
    public void setSamtyckeRR(Boolean value) {
        this.setTagValue("*//samtycke/rr", value.toString());
    }
    public void setSamtyckeRRD(Boolean value) {
        this.setTagValue("*//samtycke/rrd", value.toString());
    }

}

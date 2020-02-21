package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class UppdateraForsaljningOppenvardForskrivning extends BaseXML {
    
    public UppdateraForsaljningOppenvardForskrivning(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/UppdateraForsaljningOppenvardForskrivning.properties");
    }
   
    public UppdateraForsaljningOppenvardForskrivning() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/UppdateraForsaljningOppenvardForskrivning.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//fel");
        try {
            if (retVal == 0 ) {
                System.out.println("UppdateraForsaljningOppenvardForskrivning utförd OK ");
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//fel[1]/text[1]") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/apisp/UppdateraForsaljningOppenvardForskrivningResponder/V6";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:UppdateraForsaljningOppenvardForskrivning xmlns:urn=\"urn:riv:se.apotekensservice:fota:UppdateraForsaljningOppenvardForskrivningResponder:6\">\n" +
                            "         <urn:aktorExpeditionsId></urn:aktorExpeditionsId>\n" +
                            "         <urn:expeditionsId></urn:expeditionsId>\n" +
                            "         <urn:expoButiksId></urn:expoButiksId>\n" +
                            "         <urn:expoOrgNr></urn:expoOrgNr>\n" +
                            "         <urn:ingaendeEgenavgift></urn:ingaendeEgenavgift>\n" +
                            "         <urn:klientinformation>\n" +
                            "            <urn1:anvandare xmlns:urn1=\"urn:riv:se.apotekensservice:fota:4\"></urn1:anvandare>\n" +
                            "            <urn1:session xmlns:urn1=\"urn:riv:se.apotekensservice:fota:4\"></urn1:session>\n" +
                            "            <urn1:system xmlns:urn1=\"urn:riv:se.apotekensservice:fota:4\"></urn1:system>\n" +
                            "         </urn:klientinformation>\n" +
                            "         <urn:kommun></urn:kommun>\n" +
                            "         <urn:lan></urn:lan>\n" +
                            "         <urn:omradesKod></urn:omradesKod>\n" +
                            "         <urn:periodStartDatum></urn:periodStartDatum>\n" +
                            "         <urn:personNummer></urn:personNummer>\n" +
                            "         <urn:rader>\n" +
                            "            <urn2:aktorReceptId xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:aktorReceptId>\n" +
                            "            <urn2:aktorTransId xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:aktorTransId>\n" +
                            "            <urn2:antalEnheter xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:antalEnheter>\n" +
                            "            <urn2:arbetsplatsKod xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:arbetsplatsKod>\n" +
                            "            <urn2:aupExMomsAktor xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:aupExMomsAktor>\n" +
                            "            <urn2:avhamtadDatum xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:avhamtadDatum>\n" +
                            "            <urn1:byteTillatet xmlns:urn1=\"urn:riv:se.apotekensservice:fota:5\"></urn1:byteTillatet>" +
                            "            <urn2:dosText xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:dosText>\n" +
                            "            <urn2:egenavgiftExMoms xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:egenavgiftExMoms>\n" +
                            "            <urn2:formanExMoms xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:formanExMoms>\n" +
                            "            <urn2:formansTyp xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:formansTyp>\n" +
                            "            <urn2:forsaljningsSatt xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:forsaljningsSatt>\n" +
                            "            <urn2:forskrivarKod xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:forskrivarKod>\n" +
                            "            <urn2:forskrivarNamn xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:forskrivarNamn>\n" +
                            "            <urn1:merkostnadExMoms xmlns:urn1=\"urn:riv:se.apotekensservice:fota:5\"></urn1:merkostnadExMoms>" +
                            "            <urn1:momsSats xmlns:urn1=\"urn:riv:se.apotekensservice:fota:5\"></urn1:momsSats>" +
                            "            <urn1:nplPackid xmlns:urn1=\"urn:riv:se.apotekensservice:fota:5\"></urn1:nplPackid>" +
                            "            <urn2:receptId xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:receptId>\n" +
                            "            <urn2:startForpackning xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:startForpackning>\n" +
                            "            <urn2:ursprung xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:ursprung>\n" +
                            "            <urn2:utfardarDatum xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:utfardarDatum>\n" +
                            "            <urn2:utfardarKategori xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:utfardarKategori>\n" +
                            "            <urn1:varuNr xmlns:urn1=\"urn:riv:se.apotekensservice:fota:5\"></urn1:varuNr>" +
                            "            <urn2:varuTyp xmlns:urn2=\"urn:riv:se.apotekensservice:fota:5\"></urn2:varuTyp>\n" +
                            "         </urn:rader>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:UppdateraForsaljningOppenvardForskrivning>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setPersonnummer( String personnummer ) {
        this.setTagValue("*//personNummer", personnummer);
    }

}

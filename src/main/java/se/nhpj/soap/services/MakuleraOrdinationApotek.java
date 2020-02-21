package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class MakuleraOrdinationApotek extends BaseXML {
    
    public MakuleraOrdinationApotek(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/MakuleraOrdinationApotek.properties");
    }
   
    public MakuleraOrdinationApotek() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/MakuleraOrdinationApotek.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("MakuleraOrdinationApotek hämtat OK");
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

    private String soapEndpointUrl = "https://prestanda/apisp/MakuleraOrdinationApotekResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>\n" +
            "   </soapenv:Header>\n" +
            "      <soapenv:Body>\n" +
            "      <urn:MakuleraOrdinationApotek xmlns:urn=\"urn:riv:se.apotekensservice:or:MakuleraOrdinationApotekResponder:5\">\n" +
            "         <urn:forandrandeOrdinator>\n" +
            "            <urn1:arbetsplatskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:arbetsplatskod>\n" +
            "            <urn1:befattningskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:befattningskod>\n" + 
            "            <urn1:efternamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:efternamn>\n" +
            "            <urn1:fornamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:fornamn>\n" +
            "            <urn1:forskrivarkod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:forskrivarkod>\n" +
            "            <urn1:legitimationskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:legitimationskod>\n" +
            "            <urn1:yrkeskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:yrkeskod>\n" +
            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "         </urn:forandrandeOrdinator>\n" +
            "         <urn:makulering>\n" +
            "            <urn1:kommentar xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:kommentar>\n" +
            "            <urn1:orsakskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:orsakskod>\n" +
            "            <urn1:paUppdragAv xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:paUppdragAv>\n" +
            "            <urn1:samtyckeMakulering xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:samtyckeMakulering>\n" +
            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "         </urn:makulering>\n" +
            "         <urn:ordinationsId></urn:ordinationsId>\n" +
            "         <urn:underlagsversion></urn:underlagsversion>\n" +
            "         <urn:anvandare></urn:anvandare>\n" +
            "         <urn:yrkeskod></urn:yrkeskod>\n" +
            "         <urn:efternamn></urn:efternamn>\n" +
            "         <urn:fornamn></urn:fornamn>\n" +
            "         <urn:befattningskod></urn:befattningskod>\n" +
            "         <urn:extension/>\n" +
            "      </urn:MakuleraOrdinationApotek>\n" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";

    public void setUnderlagsversion(String underlagsversion) {
        this.setTagValue("*//MakuleraOrdinationApotek/underlagsversion", underlagsversion);
    }

    public void setOrdinationsId(String ordinationsId) {
        this.setTagValue("*//MakuleraOrdinationApotek/ordinationsId", ordinationsId);
    }

    
//    public static void main(String args[]) {
//
//        String ordId = "123456789";
//        String underlagsversion = "2";
//        MakuleraOrdinationApotek moa = new MakuleraOrdinationApotek();
//        moa.setStandardDefaultValues();
//        moa.setOrdinationsId(ordId);
//        moa.setUnderlagsversion(underlagsversion);
//
//
//        String arbetsplats_kod      = "0100000000001";
//        String LK_fname             = "Fatima";
//        String LK_ename             = "Gerhardsson";
//        String LK_kod               = "8880023";
//        String LK_yrkeskod          = "LK";
//        String AP_fname             = "Lina";
//    	String AP_ename             = "Svensson";
//    	String AP_kod               = "920001";
//    	String AP_yrkeskod          = "AP";
//    	String AP_befattningskod    = "1234";
//
//
//        moa.setTagValue("*//forandrandeOrdinator/arbetsplatskod", arbetsplats_kod);
//        moa.setTagValue("*//forandrandeOrdinator/fornamn", LK_fname);
//        moa.setTagValue("*//forandrandeOrdinator/efternamn", LK_ename);
//        moa.setTagValue("*//forandrandeOrdinator/forskrivarkod", LK_kod);
//        moa.setTagValue("*//forandrandeOrdinator/legitimationskod", LK_kod);
//        moa.setTagValue("*//forandrandeOrdinator/yrkeskod", LK_yrkeskod);
//
//        moa.setTagValue("*//MakuleraOrdinationApotek/fornamn", AP_fname);
//        moa.setTagValue("*//MakuleraOrdinationApotek/efternamn", AP_ename);
//        moa.setTagValue("*//MakuleraOrdinationApotek/anvandare", AP_kod);
//        moa.setTagValue("*//MakuleraOrdinationApotek/yrkeskod", AP_yrkeskod);
//        moa.setTagValue("*//MakuleraOrdinationApotek/befattningskod", AP_befattningskod);
//
//
//        System.out.print(moa.getTagValue("*//anvandare") + "\n\n");
//        System.out.print(moa.getXML());
//
//    }

}

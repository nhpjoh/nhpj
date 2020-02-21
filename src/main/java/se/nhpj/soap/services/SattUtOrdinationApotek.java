package se.nhpj.soap.services;

import se.nhpj.soap.utils.CurrentDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;


/**
 * @author nhpj
 */
public class SattUtOrdinationApotek extends BaseXML {
    
    public SattUtOrdinationApotek(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/SattUtOrdinationApotek.properties");
    }
   
    public SattUtOrdinationApotek() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/SattUtOrdinationApotek.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att ett rescept skapas
        Integer retVal=0;
        String text = "";
        
	    retVal = retVal + response.getTagCount("*//felmeddelande");
		
        try {
            if (retVal == 0 ) {
                System.out.println("Ordinationen utsatt " + response.getTagValue("*//status"));
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    for (int i=1 ; i <= retVal ; i++ ) {
                        System.err.println("\n");
                        text = text + response.getTagValue("*//affFelLista["+i+"]/felmeddelande") + "\n";
                    }
                    throw new Exception("\n<<<<<\n" + text + ">>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/apisp/SattUtOrdinationApotekResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:SattUtOrdinationApotek xmlns:urn=\"urn:riv:se.apotekensservice:or:SattUtOrdinationApotekResponder:5\">\n" +
            "         <urn:forandrandeOrdinator>\n" +
            "            <urn1:arbetsplatskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:arbetsplatskod>\n" +
            "            <urn1:efternamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:efternamn>\n" +
            "            <urn1:fornamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:fornamn>\n" +
            "            <urn1:forskrivarkod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:forskrivarkod>\n" +
            "            <urn1:legitimationskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:legitimationskod>\n" +
            "            <urn1:yrkeskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:yrkeskod>\n" +
            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "         </urn:forandrandeOrdinator>\n" +
            "         <urn:momentanUtsattning></urn:momentanUtsattning>\n" +
            "         <urn:ordinationsId></urn:ordinationsId>\n" +
            "         <urn:underlagsversion></urn:underlagsversion>\n" +
            "         <urn:utsattning>\n" +
            "            <urn1:kommentar xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:kommentar>\n" +
            "            <urn1:utsattningstidpunkt xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:utsattningstidpunkt>\n" +
            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "         </urn:utsattning>\n" +
            "         <urn:akut></urn:akut>\n" +
            "         <urn:anvandare></urn:anvandare>\n" +
            "         <urn:yrkeskod></urn:yrkeskod>\n" +
            "         <urn:efternamn></urn:efternamn>\n" +
            "         <urn:fornamn></urn:fornamn>\n" +
            "         <urn:befattningskod></urn:befattningskod>\n" +
            "         <urn:extension/>\n" +
            "      </urn:SattUtOrdinationApotek>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";
    
    public void setUtsattningstidpunkt() {
        this.setTagValue("*//utsattningstidpunkt", CurrentDateTime.getTodaysDate() + "T00:00:00.000+02:00");
    }    
    public void setOrdinationsId( String id) {
        this.setTagValue("*//ordinationsId", id);
    }    
    public void setUnderlagsversion( String underlagsversion) {
        this.setTagValue("*//underlagsversion", underlagsversion);
    }    
    public void setMomentanUtsattning( Boolean value) {
        this.setTagValue("*//momentanUtsattning", value.toString());
    }    

//    public static void main(String args[]) {
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
//        String ordId = "1234567890";
//        String underlagsversion = "777";
//
//        // 2017-12-20T00:00:00.000+02:00
//        SattUtOrdinationApotek suoa = new SattUtOrdinationApotek();
//        suoa.setStandardDefaultValues();
//        suoa.setUtsattningstidpunkt();
//        suoa.setMomentanUtsattning(Boolean.TRUE);
//        suoa.setOrdinationsId(ordId);
//        suoa.setUnderlagsversion(underlagsversion);
//
//
//
//        suoa.setTagValue("*//forandrandeOrdinator/arbetsplatskod", arbetsplats_kod);
//        suoa.setTagValue("*//forandrandeOrdinator/fornamn", LK_fname);
//        suoa.setTagValue("*//forandrandeOrdinator/efternamn", LK_ename);
//        suoa.setTagValue("*//forandrandeOrdinator/forskrivarkod", LK_kod);
//        suoa.setTagValue("*//forandrandeOrdinator/legitimationskod", LK_kod);
//        suoa.setTagValue("*//forandrandeOrdinator/yrkeskod", LK_yrkeskod);
//        suoa.setTagValue("*//SattUtOrdinationApotek/fornamn", AP_fname);
//        suoa.setTagValue("*//SattUtOrdinationApotek/efternamn", AP_ename);
//        suoa.setTagValue("*//SattUtOrdinationApotek/anvandare", AP_kod);
//        suoa.setTagValue("*//SattUtOrdinationApotek/yrkeskod", AP_yrkeskod);
//        suoa.setTagValue("*//SattUtOrdinationApotek/befattningskod", AP_befattningskod);
//
//        System.out.println(suoa.getTagValue("*//utsattningstidpunkt"));
//        System.out.println(suoa.getXML());
//
//    }

}

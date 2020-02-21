package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class SkrivUtERecept extends BaseXML {
    
    public SkrivUtERecept(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/SkrivUtERecept.properties");
    }
   
    public SkrivUtERecept() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/SkrivUtERecept.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SkrivUtERecept OK : Antal-Receptoriginal: " + response.getTagCount("*//receptoriginal"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/SkrivUtEReceptResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:SkrivUtERecept xmlns:urn=\"urn:riv:se.apotekensservice:or:SkrivUtEReceptResponder:5\">\n" +
                            "         <urn:kommentar>Utsrivet</urn:kommentar>\n" +
                            "         <urn:ordinationsId>2770504752</urn:ordinationsId>\n" +
                            "         <urn:anvandare>7347939675449</urn:anvandare>\n" +
                            "         <urn:yrkeskod>AP</urn:yrkeskod>\n" +
                            "         <urn:efternamn>Whinst</urn:efternamn>\n" +
                            "         <urn:fornamn>Milla</urn:fornamn>\n" +
                            "         <urn:befattningskod>1234</urn:befattningskod>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:SkrivUtERecept>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setOrdinationsId( String ordinationsid) {
        this.setTagValue("*//ordinationsId", ordinationsid);
    }
    
//    public static void main(String args[]) {
//
//        String ordId = "123456789";
//        String underlagsversion = "2";
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
//        SkrivUtERecept suer = new SkrivUtERecept();
//        suer.setStandardDefaultValues();
//        suer.setOrdinationsId(ordId);
//        suer.setTagValue("*//fornamn", AP_fname);
//        suer.setTagValue("*//efternamn", AP_ename);
//        suer.setTagValue("*//yrkeskod", AP_yrkeskod);
//        suer.setTagValue("*//befattningskod", AP_befattningskod);
//        suer.setTagValue("*//anvandare", "7347939675449");  // GLN
//        suer.setTagValue("*//kommentar", "Utskrivet");
//
//        System.out.print(suer.getXML());
//
//    }

}

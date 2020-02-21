package se.nhpj.soap.services;

import se.nhpj.soap.utils.CurrentDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;


/**
 * @author nhpj
 */
public class HamtaSubstitution extends BaseXML {
    
    public HamtaSubstitution(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/HamtaSubstitution.properties");
    }
   
    public HamtaSubstitution() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/HamtaSubstitution.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("HamtaSubstitution hämtat OK - Akuellt pris för " + 
                        response.getTagValue("*//utbytesgruppArtiklar[1]/nplPackId[1]") + " är " + 
                        response.getTagValue("*//utbytesgruppArtiklar[1]/aktuellPeriodsForsaljningspris[1]/aktuelltForsaljningsPris[1]"));
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

    private String soapEndpointUrl = "https://prestanda/apisp/HamtaSubstitutionResponder/V4";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:HamtaSubstitution xmlns:urn=\"urn:riv:se.apotekensservice:vara:HamtaSubstitutionResponder:4\">\n" +
            "         <urn:artikelIdentitet>\n" +
            "            <urn1:nplPackageId xmlns:urn1=\"urn:riv:se.apotekensservice:vara:4\"></urn1:nplPackageId>\n" +
            "         </urn:artikelIdentitet>\n" +
            "         <urn:forhinderUtbyte></urn:forhinderUtbyte>\n" +
            "         <urn:forskrivningsDatum></urn:forskrivningsDatum>\n" +
            "      </urn:HamtaSubstitution>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";
    
    
    public void setForskrivningsDatum() {
        this.setTagValue("*//forskrivningsDatum", CurrentDateTime.getTExtraLong());
    }
    
    public void setNplPackageId( String nplPackageId ) {
        this.setTagValue("*//nplPackageId", nplPackageId);
    }
    

//    public static void main(String args[]) {
//        HamtaSubstitution hs = new HamtaSubstitution();
//        hs.setStandardDefaultValues();
//        hs.setForskrivningsDatum();
//
//        System.out.println(hs.getTagValue("*//forskrivningsDatum"));
//        System.out.println(hs.getXML());
//
//    }

}

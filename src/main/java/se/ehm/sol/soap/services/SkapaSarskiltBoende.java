package se.ehm.sol.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class SkapaSarskiltBoende extends BaseXML {

    public SkapaSarskiltBoende(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/sol/soap/services/SkapaSarskiltBoende.properties");
    }

    public SkapaSarskiltBoende() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/sol/soap/services/SkapaSarskiltBoende.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
        Boolean b = null;
        try {
            b = response.getTagValue("*//resultatkod").contains("INFO");
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "SkapaOrdinationsAnsvarigEnhetDosaktor - checkResponse", ex);
        }
        if (!b) {retVal++;}
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SkapaSarskiltBoende: " + response.getTagValue("*//meddelandetext"));
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//meddelandetext") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/sol-service-provider/SkapaSarskiltBoendeService/V1";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:skap=\"http://skapasarskiltboende.service.sol.apotekensservice.se:1\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <skap:skapaSarskiltBoende>\n" +
                            "         <arg0>\n" +
                            "            <GLNkod></GLNkod>\n" +
                            "            <behorighetsInformationdosaktor>\n" +
                            "               <fornamn></fornamn>\n" +
                            "               <efternamn></efternamn>\n" +
                            "               <legitimationskod></legitimationskod>\n" +
                            "               <yrkesroll></yrkesroll>\n" +
                            "               <dosapotekid></dosapotekid>\n" +
                            "            </behorighetsInformationdosaktor>\n" +
                            "            <sarskiltboende>\n" +
                            "               <boendeenhetnamn></boendeenhetnamn>\n" +
                            "               <boendeenhetid></boendeenhetid>\n" +
                            "               <boendeenhetadress></boendeenhetadress>\n" +
                            "               <boendeenhetpostnummer></boendeenhetpostnummer>\n" +
                            "               <boendeenhetort></boendeenhetort>\n" +
                            "               <boendeenhetavdelning>1</boendeenhetavdelning>\n" +
                            "               <arbetsplatskod></arbetsplatskod>\n" +
                            "               <dosmottagareid></dosmottagareid>\n" +
                            "               <dosmottagarenamn></dosmottagarenamn>\n" +
                            "            </sarskiltboende>\n" +
                            "         </arg0>\n" +
                            "      </skap:skapaSarskiltBoende>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";


}

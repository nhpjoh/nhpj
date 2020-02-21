package se.ehm.sol.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class SkapaOrdinationsAnsvarigEnhetDosaktor extends BaseXML {

    public SkapaOrdinationsAnsvarigEnhetDosaktor(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/sol/soap/services/SkapaOrdinationsAnsvarigEnhetDosaktor.properties");
    }

    public SkapaOrdinationsAnsvarigEnhetDosaktor() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/sol/soap/services/SkapaOrdinationsAnsvarigEnhetDosaktor.properties");
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

        if (!b) {retVal=retVal+1;}
        retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SkapaOrdinationsAnsvarigEnhetDosaktor: " + response.getTagValue("*//meddelandetext"));
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//faultstring") + response.getTagValue("*//resultatkod") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/apisp-sol/SkapaOrdinationsAnsvarigEnhetDosaktorService/V1";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:skap=\"http://skapaordinationsansvarigenhet.service.sol.apotekensservice.se:1\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <skap:skapaOrdinationsAnsvarigEnhetDosaktor>\n" +
                            "         <!--Optional:-->\n" +
                            "         <arg0>\n" +
                            "            <ordinationsansvarigenhet>\n" +
                            "               <vardandeenhetid></vardandeenhetid>\n" +
                            "               <vardandeenhetnamn></vardandeenhetnamn>\n" +
                            "               <vardandeenhetpostort></vardandeenhetpostort>\n" +
                            "               <Glnkod></Glnkod>\n" +
                            "               <dosapoteknamn></dosapoteknamn>\n" +
                            "            </ordinationsansvarigenhet>\n" +
                            "            <behorigshetsInformationDosaktor>\n" +
                            "               <fornamn></fornamn>\n" +
                            "               <efternamn></efternamn>\n" +
                            "               <legitimationskod></legitimationskod>\n" +
                            "               <yrkesroll></yrkesroll>\n" +
                            "               <dosapotekid></dosapotekid>\n" +
                            "            </behorigshetsInformationDosaktor>\n" +
                            "         </arg0>\n" +
                            "      </skap:skapaOrdinationsAnsvarigEnhetDosaktor>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";


}

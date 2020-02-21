package se.ehm.sol.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class HamtaVardtagareinformation extends BaseXML {

    public HamtaVardtagareinformation(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/sol/soap/services/HamtaVardtagareinformation.properties");
    }

    public HamtaVardtagareinformation() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/sol/soap/services/HamtaVardtagareinformation.properties");
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
                System.out.println("HamtaVardtagareinformation: " + response.getTagValue("*//meddelandetext"));
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

    private String soapEndpointUrl = "https://prestanda/sol-service-provider/HamtaVardtagareinformationResponderService/V1";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:add=\"http://www.w3.org/2005/08/addressing\" xmlns:urn=\"urn:riv:druglogistics:dosedispensing:HamtaVardtagareinformationResponder:1\" xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:1\">\n" +
            "   <soapenv:Header>\n" +
            "      <add:To/>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:HamtaVardtagareinformation>\n" +
            "         <urn:glnkod></urn:glnkod>\n" +
            "         <urn:Behorighetsinformation>\n" +
            "            <urn1:fornamn></urn1:fornamn>\n" +
            "            <urn1:efternamn></urn1:efternamn>\n" +
            "            <urn1:forskrivarkod></urn1:forskrivarkod>\n" +
            "            <urn1:yrkeskod></urn1:yrkeskod>\n" +
            "            <urn1:arbetsplatskod></urn1:arbetsplatskod>\n" +
            "            <urn1:hsaid></urn1:hsaid>\n" +
            "            <urn1:personnummer></urn1:personnummer>\n" +
            "            <urn1:organisationsnummer></urn1:organisationsnummer>\n" +
            "         </urn:Behorighetsinformation>\n" +
            "         <urn:identitetstyp></urn:identitetstyp>\n" +
            "         <urn:personid></urn:personid>\n" +
            "      </urn:HamtaVardtagareinformation>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";


}

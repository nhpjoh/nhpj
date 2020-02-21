package se.ehm.sol.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class SkickaMeddelanden extends BaseXML {

    public SkickaMeddelanden(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/sol/soap/services/SkickaMeddelanden.properties");
    }

    public SkickaMeddelanden() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/sol/soap/services/SkickaMeddelanden.properties");
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
                System.out.println("SkickaMeddelanden: skickat OK ");
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

    private String soapEndpointUrl = "https://prestanda/sol-service-provider/SkickaMeddelandenResponderService/V1";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:add=\"http://www.w3.org/2005/08/addressing\" xmlns:urn=\"urn:riv:druglogistics:dosedispensing:SkickaMeddelandenResponder:1\" xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:1\">\n" +
                            "   <soapenv:Header>\n" +
                            "      <add:To/>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:SkickaMeddelanden>\n" +
                            "         <urn:glnkod></urn:glnkod>\n" +
                            "         <urn:Behorighetsinformation>\n" +
                            "            <urn1:fornamn></urn1:fornamn>\n" +
                            "            <urn1:efternamn></urn1:efternamn>\n" +
                            "            <urn1:forskrivarkod></urn1:forskrivarkod>\n" +
                            "            <urn1:yrkeskod></urn1:yrkeskod>\n" +
                            "            <urn1:arbetsplatskod></urn1:arbetsplatskod>\n" +
                            "            <urn1:hsaid></urn1:hsaid>\n" +
                            "         </urn:Behorighetsinformation>\n" +
                            "         <urn:Meddelandeninfo>\n" +
                            "            <urn1:Meddelandetyp></urn1:Meddelandetyp>\n" +
                            "            <urn1:Patientinformation>\n" +
                            "               <urn1:fornamn></urn1:fornamn>\n" +
                            "               <urn1:efternamn></urn1:efternamn>\n" +
                            "               <urn1:identitetstyp></urn1:identitetstyp>\n" +
                            "               <urn1:personid></urn1:personid>\n" +
                            "            </urn1:Patientinformation>\n" +
                            "            <urn1:glnkod></urn1:glnkod>\n" +
                            "            <urn1:sandningstidpunkt></urn1:sandningstidpunkt>\n" +
                            "            <urn1:rubrik></urn1:rubrik>\n" +
                            "            <urn1:prioritet></urn1:prioritet>\n" +
                            "            <urn1:meddelande></urn1:meddelande>\n" +
                            "         </urn:Meddelandeninfo>\n" +
                            "      </urn:SkickaMeddelanden>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";


}

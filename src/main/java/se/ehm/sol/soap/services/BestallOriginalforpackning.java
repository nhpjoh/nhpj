package se.ehm.sol.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.utils.XmlFormatter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class BestallOriginalforpackning extends BaseXML {

    public BestallOriginalforpackning(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/sol/soap/services/BestallOriginalforpackning.properties");
    }

    public BestallOriginalforpackning() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/sol/soap/services/BestallOriginalforpackning.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
        XmlFormatter formatter = new XmlFormatter();

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
                System.out.println("BestallOriginalforpackning: " + response.getTagValue("*//meddelandetext"));
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + formatter.format(response.getXML()));
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//meddelandetext") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/sol-service-provider/BestallOriginalforpackningResponderService/V1";

    private String XML =    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:add=\"http://www.w3.org/2005/08/addressing\" xmlns:urn=\"urn:riv:druglogistics:dosedispensing:BestallOrginalforpackningResponder:1\" xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:1\" xmlns:urn2=\"urn:riv:druglogistics:dosedispensing:BestallOriginalforpackningResponder:1\">\n" +
                            "   <soapenv:Header>\n" +
                            "      <add:To/>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn2:BestallOriginalforpackning>\n" +
                            "         <urn2:glnkod></urn2:glnkod>\n" +
                            "         <urn2:Behorighetsinformation>\n" +
                            "            <urn1:fornamn></urn1:fornamn>\n" +
                            "            <urn1:efternamn></urn1:efternamn>\n" +
                            "            <urn1:forskrivarkod></urn1:forskrivarkod>\n" +
                            "            <urn1:yrkeskod></urn1:yrkeskod>\n" +
                            "            <urn1:arbetsplatskod></urn1:arbetsplatskod>\n" +
                            "            <urn1:hsaid>hsaid</urn1:hsaid>\n" +
                            "            <urn1:personnummer></urn1:personnummer>\n" +
                            "            <urn1:organisationsnummer></urn1:organisationsnummer>\n" +
                            "         </urn2:Behorighetsinformation>\n" +
                            "         <urn2:Bestallningsinfo>\n" +
                            "            <urn1:Patientinformation>\n" +
                            "               <urn1:fornamn></urn1:fornamn>\n" +
                            "               <urn1:mellannamn></urn1:mellannamn>\n" +
                            "               <urn1:efternamn></urn1:efternamn>\n" +
                            "               <urn1:identitetstyp></urn1:identitetstyp>\n" +
                            "               <urn1:personid></urn1:personid>\n" +
                            "               <urn1:lanskod></urn1:lanskod>\n" +
                            "               <urn1:kommunkod></urn1:kommunkod>\n" +
                            "            </urn1:Patientinformation>\n" +
                            "            <urn1:bestallningsid></urn1:bestallningsid>\n" +
                            "            <urn1:radid></urn1:radid>\n" +
                            "            <urn1:NPLpackid></urn1:NPLpackid>\n" +
                            "            <urn1:varunummer></urn1:varunummer>\n" +
                            "            <urn1:receptid></urn1:receptid>\n" +
                            "            <urn1:ordinationsid></urn1:ordinationsid>\n" +
                            "            <urn1:dosunderlagsversion></urn1:dosunderlagsversion>\n" +
                            "            <urn1:antalforpackningar></urn1:antalforpackningar>\n" +
                            "            <urn1:akutbestallning></urn1:akutbestallning>\n" +
                            "            <urn1:maxveckodos></urn1:maxveckodos>\n" +
                            "            <urn1:maxdygnsdos></urn1:maxdygnsdos>\n" +
                            "            <urn1:dosmottagareid></urn1:dosmottagareid>\n" +
                            "            <urn1:dosmottagarenamn></urn1:dosmottagarenamn>\n" +
                            "            <urn1:meddelandetillapotek></urn1:meddelandetillapotek>\n" +
                            "            <urn1:onskadleveransdatum></urn1:onskadleveransdatum>\n" +
                            "         </urn2:Bestallningsinfo>\n" +
                            "      </urn2:BestallOriginalforpackning>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";


}

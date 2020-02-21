package se.ehm.testdata.SOL.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class SokDosmottagare extends BaseXML {

    public SokDosmottagare(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/SokDosmottagare.properties");
    }

    public SokDosmottagare() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/SokDosmottagare.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en SokDosmottagare returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SokDosmottagare hämtat OK ");
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

    private String soapEndpointUrl = "https://test10:19443/apisp-sol/SokDosmottagareResponderService/V2";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:SokDosmottagareRequest xmlns:urn=\"urn:riv:druglogistics:dosedispensing:SokDosmottagareResponder:2\">\n" +
            "         <urn:Behorighetsinformation>\n" +
            "            <urn1:fornamn xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:3\">Lars</urn1:fornamn>\n" +
            "            <urn1:efternamn xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:3\">Lakare</urn1:efternamn>\n" +
            "            <urn1:forskrivarkod xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:3\">9000027</urn1:forskrivarkod>\n" +
            "            <urn1:yrkeskod xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:3\">LK</urn1:yrkeskod>\n" +
            "            <urn1:arbetsplatskod xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:3\">4000000000000</urn1:arbetsplatskod>\n" +
            "         </urn:Behorighetsinformation>\n" +
            "         <urn:dosapotekid>7310000000019</urn:dosapotekid>\n" +
            "         <urn:dosmottagareid>9995842471894</urn:dosmottagareid>\n" +
            "         <urn:ort>Prestandastaden723968723431619</urn:ort>\n" +
            "      </urn:SokDosmottagareRequest>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";


}

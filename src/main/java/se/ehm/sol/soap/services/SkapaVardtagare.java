package se.ehm.sol.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * För stt skaps en vårdtagare så måste GLN koden tillhöra samma för Vårdande enheten (vardandeenhetid) annars funkar det inte samt att
 * dosmottagareid måste oxå tillhöra samma GLN så flödet blir ...
 * SkapaDosmottagare()
 * SkapaProduktionsinformationDosaktor()
 * SkapaOrdinationsAnsvarigEnhetDosaktor()
 * SkapaVardtagare()
 *
 * @author nhpj
 */
public class SkapaVardtagare extends BaseXML {

    public SkapaVardtagare(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/sol/soap/services/SkapaVardtagare.properties");
    }

    public SkapaVardtagare() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/sol/soap/services/SkapaVardtagare.properties");
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
                System.out.println("<XXXXXXXXX> hämtat OK ");
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

    private String soapEndpointUrl = "https://prestanda/apisp-sol/SkapaVardtagareResponderService/V1";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:add=\"http://www.w3.org/2005/08/addressing\" xmlns:urn=\"urn:riv:druglogistics:dosedispensing:SkapaVardtagareResponder:1\" xmlns:urn1=\"urn:riv:druglogistics:dosedispensing:1\">\n" +
                            "   <soapenv:Header>\n" +
                            "      <add:To/>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:SkapaVardtagare>\n" +
                            "         <urn:glnkod>7310000000015</urn:glnkod>\n" +
                            "         <urn:Behorighetsinformation>\n" +
                            "            <urn1:fornamn>Sofia</urn1:fornamn>\n" +
                            "            <urn1:efternamn>Pedersen</urn1:efternamn>\n" +
                            "            <urn1:forskrivarkod>7095797</urn1:forskrivarkod><urn1:yrkeskod>LK</urn1:yrkeskod>\n" +
                            "            <urn1:arbetsplatskod>4000000000000</urn1:arbetsplatskod>\n" +
                            "         </urn:Behorighetsinformation>\n" +
                            "         <urn:forskrivaresamtyckefornamn>Sofia</urn:forskrivaresamtyckefornamn>\n" +
                            "         <urn:forskrivaresamtyckeefternamn>Pedersen</urn:forskrivaresamtyckeefternamn>\n" +
                            "         <urn:forskrivaresamtyckeforskrivarkod>7095797</urn:forskrivaresamtyckeforskrivarkod>\n" +
                            "         <urn:forskrivaresamtyckearbetsplatskod>4000000000000</urn:forskrivaresamtyckearbetsplatskod>\n" +
                            "         <urn:forskrivaresamtyckeyrkeskod>LK</urn:forskrivaresamtyckeyrkeskod>\n" +
                            "         <urn:Vardtagarinformation>\n" +
                            "            <urn1:hemmaboende>true</urn1:hemmaboende>\n" +
                            "            <urn1:dosapoteksid>7310000000015</urn1:dosapoteksid>\n" +
                            "            <urn1:dosapoteknamn>Apotek 16</urn1:dosapoteknamn>\n" +
                            "            <urn1:forstadosdag>2017-07-17T00:00:00+02:00</urn1:forstadosdag>\n" +
                            "            <urn1:avvikandedosschema>false</urn1:avvikandedosschema>\n" +
                            "            <urn1:Patientinformation>\n" +
                            "               <urn1:fornamn>AA</urn1:fornamn>\n" +
                            "               <urn1:mellannamn>BB</urn1:mellannamn><urn1:efternamn>CC</urn1:efternamn>\n" +
                            "               <urn1:identitetstyp>P</urn1:identitetstyp>\n" +
                            "               <urn1:personid>333333333335</urn1:personid>\n" +
                            "            </urn1:Patientinformation>\n" +
                            "            <urn1:Hemmaboendeinformation>\n" +
                            "               <urn1:adress>Aderssen 3</urn1:adress>\n" +
                            "               <urn1:postnummer>33333</urn1:postnummer>\n" +
                            "               <urn1:ort>Orten 3</urn1:ort>\n" +
                            "               <urn1:telefon>010-333333</urn1:telefon>\n" +
                            "               <urn1:dosmottagareid>9995668000099</urn1:dosmottagareid>" +
                            "               <urn1:dosmottagarenamn>PrestandaCentralen 55711749281299322786</urn1:dosmottagarenamn>\n" +
                            "            </urn1:Hemmaboendeinformation>\n" +
                            "            <urn1:Kontaktinformation>\n" +
                            "               <urn1:PALforskrivarkod>9674989</urn1:PALforskrivarkod>\n" +
                            "               <urn1:PALfornamn>QQ</urn1:PALfornamn>\n" +
                            "               <urn1:PALefternamn>EE</urn1:PALefternamn>\n" +
                            "               <urn1:anhorigkontaktnamn>RR</urn1:anhorigkontaktnamn>\n" +
                            "               <urn1:anhorigkontaktemail>TT@prestandatest.se</urn1:anhorigkontaktemail>\n" +
                            "               <urn1:ansvarigkontaktnamn>YY</urn1:ansvarigkontaktnamn><urn1:ansvarigkontaktemail>UU@prestandatest.se</urn1:ansvarigkontaktemail>\n" +
                            "               <urn1:ansvarigkontaktadress>II</urn1:ansvarigkontaktadress>\n" +
                            "               <urn1:ansvarigkontaktpostnummer>12345</urn1:ansvarigkontaktpostnummer>\n" +
                            "               <urn1:ansvarigkontaktpostort>OO</urn1:ansvarigkontaktpostort>\n" +
                            "               <urn1:ansvarigkontakttelefon1>0300-123456</urn1:ansvarigkontakttelefon1>\n" +
                            "               <urn1:ansvarigkontakttelefon2>0300-234567</urn1:ansvarigkontakttelefon2>\n" +
                            "               <urn1:vardandeenhetid>BIDKJ9995668000099</urn1:vardandeenhetid>\n" +
                            "               <urn1:vardandeenhetnamn>PP</urn1:vardandeenhetnamn>\n" +
                            "               <urn1:vardandeenhetpostort>SS</urn1:vardandeenhetpostort><urn1:vardandeenhetpostnummer>22222</urn1:vardandeenhetpostnummer>\n" +
                            "            </urn1:Kontaktinformation>\n" +
                            "            <urn1:Vardtagarstatus>\n" +
                            "               <urn1:statuskod>1</urn1:statuskod>\n" +
                            "            </urn1:Vardtagarstatus>\n" +
                            "         </urn:Vardtagarinformation>\n" +
                            "         <urn:akut>false</urn:akut>\n" +
                            "         <urn:meddelandetillapotek>Prestandatest_333333333</urn:meddelandetillapotek>\n" +
                            "      </urn:SkapaVardtagare>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";


}

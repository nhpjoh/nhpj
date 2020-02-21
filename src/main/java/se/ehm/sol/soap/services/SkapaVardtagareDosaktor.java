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
 * SkapaVardtagareDosaktor()
 * @author nhpj
 */
public class SkapaVardtagareDosaktor extends BaseXML {

    public SkapaVardtagareDosaktor(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/sol/soap/services/SkapaVardtagareDosaktor.properties");
    }

    public SkapaVardtagareDosaktor() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/sol/soap/services/SkapaVardtagareDosaktor.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
        Boolean b = null;
        try {
            b = response.getTagValue("*//resultatkod").contains("INFO");
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "checkResponse", ex);
        }
        if (!b) {retVal++;}
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SkapaVardtagareDosaktor: " + response.getTagValue("*//meddelandetext") + " person: " + response.getTagValue("*//personid"));
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//meddelandetext") +" >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = " ";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:skap=\"http://skapavardtagaredosaktor.service.sol.apotekensservice.se:1\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <skap:skapaVardtagareDosaktor>\n" +
            "         <arg0>\n" +
            "            <Glnkod>1000000000017</Glnkod>\n" +
            "            <samtyckeforskrivare>\n" +
            "               <fornamn>Sofia</fornamn>\n" +
            "               <efternamn>Pedersen</efternamn>\n" +
            "               <forskrivarKod>7095797</forskrivarKod>\n" +
            "               <arbetsplatsKod>4000000000000</arbetsplatsKod>\n" +
            "               <yrkesKod>LK</yrkesKod>\n" +
            "            </samtyckeforskrivare>\n" +
            "            <vardtagarinformation>\n" +
            "               <hemmaboende>JA</hemmaboende>\n" +
            "               <dosapoteksid>1000000000017</dosapoteksid>\n" +
            "               <dosapoteknamn>Apotek 17</dosapoteknamn>\n" +
            "               <forstadosdag>2017-07-17T00:00:00+02:00</forstadosdag>\n" +
            "               <avvikandedosschema>NEJ</avvikandedosschema>\n" +
            "               <patientinformation>\n" +
            "                  <fornamn>Förnamn</fornamn>\n" +
            "                  <mellannamn>MellanNamn</mellannamn>\n" +
            "                  <efternamn>Efternamn</efternamn>\n" +
            "                  <identitetstyp>P</identitetstyp>\n" +
            "                  <personid>111111111122</personid>\n" +
            "                  <lanskod>01</lanskod>\n" +
            "                  <kommunkod>08</kommunkod>\n" +
            "               </patientinformation>\n" +
            "               <ordinartboendeinformation>\n" +
            "                  <adress>Gatan</adress>\n" +
            "                  <dosmottagareid>4887152666838</dosmottagareid>\n" +
            "                  <dosmottagarenamn>C</dosmottagarenamn>\n" +
            "                  <ort>Orten 1</ort>\n" +
            "                  <postnummer>10001</postnummer>\n" +
            "                  <telefon>010-0000001</telefon>\n" +
            "               </ordinartboendeinformation>\n" +
            "               <sarskiltboendeinformation>\n" +
            "                  <boendeenhetnamn></boendeenhetnamn>\n" +
            "                  <boendeenhetid></boendeenhetid>\n" +
            "                  <boendeenhetadress></boendeenhetadress>\n" +
            "                  <boendeenhetpostnummer></boendeenhetpostnummer>\n" +
            "                  <boendeenhetort></boendeenhetort>\n" +
            "                  <boendeenhetavdelning></boendeenhetavdelning>\n" +
            "                  <arbetsplatskod></arbetsplatskod>\n" +
            "                  <dosmottagareid></dosmottagareid>\n" +
            "                  <dosmottagarenamn></dosmottagarenamn>\n" +
            "               </sarskiltboendeinformation>\n" +
            "               <tillfalligadress>\n" +
            "                  <adress></adress>\n" +
            "                  <dosmottagareid></dosmottagareid>\n" +
            "                  <dosmottagarenamn></dosmottagarenamn>\n" +
            "                  <ort></ort>\n" +
            "                  <postnummer></postnummer>\n" +
            "                  <telefon></telefon>\n" +
            "                  <frandatum></frandatum>\n" +
            "                  <tilldatum></tilldatum>\n" +
            "               </tillfalligadress>\n" +
            "               <kontaktinformation>\n" +
            "                  <PALforskrivarkod>9674989</PALforskrivarkod>\n" +
            "                  <PALfornamn>D</PALfornamn>\n" +
            "                  <PALefternamn>E</PALefternamn>\n" +
            "                  <anhorigkontaktnamn>F</anhorigkontaktnamn>\n" +
            "                  <anhorigkontaktemail>G@prestandatest.se</anhorigkontaktemail>\n" +
            "                  <ansvarigkontaktnamn>H</ansvarigkontaktnamn>\n" +
            "                  <ansvarigkontaktemail>I@prestandatest.se</ansvarigkontaktemail>\n" +
            "                  <ansvarigkontaktadress>J</ansvarigkontaktadress>\n" +
            "                  <ansvarigkontaktpostnummer>57545</ansvarigkontaktpostnummer>\n" +
            "                  <ansvarigkontaktpostort>K</ansvarigkontaktpostort>\n" +
            "                  <ansvarigkontakttelefon1>070-45296778</ansvarigkontakttelefon1>\n" +
            "                  <ansvarigkontakttelefon2>070-45296778</ansvarigkontakttelefon2>\n" +
            "                  <vardandeenhetid>8942</vardandeenhetid>\n" +
            "                  <vardandeenhetnamn>L</vardandeenhetnamn>\n" +
            "                  <vardandeenhetpostort>M</vardandeenhetpostort>\n" +
            "                  <vardandeenhetpostnummer>83653</vardandeenhetpostnummer>\n" +
            "               </kontaktinformation>\n" +
            "               <vardtagarstatus>\n" +
            "                  <statuskod>AKTIV</statuskod>\n" +
            "                  <frantid>2018-11-02T00:00:00.000+01:00</frantid>\n" +
            "                  <tilltid>2018-11-02T00:00:00.000+01:00</tilltid>\n" +
            "               </vardtagarstatus>\n" +
            "            </vardtagarinformation>\n" +
            "            <akut>EJ_AKUT</akut>\n" +
            "            <meddelandetilldosapotek>Prestandatest_100000001</meddelandetilldosapotek>\n" +
            "            <behorigshetsInformationDosaktor>\n" +
            "               <fornamn>Sofia</fornamn>\n" +
            "               <efternamn>Pedersen</efternamn>\n" +
            "               <legitimationskod>709579</legitimationskod>\n" +
            "               <yrkesroll>AP</yrkesroll>\n" +
            "               <dosapotekid>1000000000017</dosapotekid>\n" +
            "            </behorigshetsInformationDosaktor>\n" +
            "         </arg0>\n" +
            "      </skap:skapaVardtagareDosaktor>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";


}

package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class SkapaOrdinationVard extends BaseXML {

    public SkapaOrdinationVard(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/SkapaOrdinationVard.properties");
    }

    public SkapaOrdinationVard() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/SkapaOrdinationVard.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en SkapaOrdinationVard skapas
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("SkapaOrdinationVard skapat OK ");
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

    private String soapEndpointUrl = "https://prestanda/apisp/SkapaOrdinationVard/V7";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "   <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <soapenv:Header>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn:SkapaOrdinationVard xmlns:urn=\"urn:riv:se.apotekensservice:or:SkapaOrdinationVardResponder:7\">\n" +
            "         <urn:helforpackningsordination>\n" +
            "            <urn1:artikelinformation xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
            "               <urn1:nplId></urn1:nplId>\n" +
            "               <urn1:nplPackId></urn1:nplPackId>\n" +
            "            </urn1:artikelinformation>\n" +
            "            <urn1:behandlingsinformation xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
            "               <urn1:andamalKlartext></urn1:andamalKlartext>\n" +
            "               <urn1:doseringstext1></urn1:doseringstext1>\n" +
            "               <urn1:sprakkod></urn1:sprakkod>\n" +
            "            </urn1:behandlingsinformation>\n" +
            "            <urn1:forskrivarKommentar xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:forskrivarKommentar>\n" +
            "            <urn1:helforpackningsregler xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
            "               <urn1:forman></urn1:forman>\n" +
            "               <urn1:forskrivetAntalUttag></urn1:forskrivetAntalUttag>\n" +
            "               <urn1:forstaUttagFore></urn1:forstaUttagFore>\n" +
            "               <urn1:sistaGiltighetsdag></urn1:sistaGiltighetsdag>\n" +
            "            </urn1:helforpackningsregler>\n" +
            "            <urn1:leveransMeddelande xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:leveransMeddelande>\n" +
            "            <urn1:mangdinformation xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
            "               <urn1:antalForpackningar></urn1:antalForpackningar>\n" +
            "               <urn1:ingenDygnsdos></urn1:ingenDygnsdos>\n" +
            "               <urn1:startforpackning></urn1:startforpackning>\n" +
            "            </urn1:mangdinformation>\n" +
            "            <urn1:notatOrdination xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:notatOrdination>\n" +
            "            <urn1:ordinationsinformation xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
            "               <urn1:ordinationstidpunkt></urn1:ordinationstidpunkt>\n" +
            "               <urn1:originalordinationsId></urn1:originalordinationsId>\n" +
            "               <urn1:patient>\n" +
            "                  <urn1:efternamn></urn1:efternamn>\n" +
            "                  <urn1:fornamn></urn1:fornamn>\n" +
            "                  <urn1:kontaktuppgift>\n" +
            "                     <urn1:adress1></urn1:adress1>\n" +
            "                     <urn1:adress2></urn1:adress2>\n" +
            "                     <urn1:postnummer></urn1:postnummer>\n" +
            "                     <urn1:postort></urn1:postort>\n" +
            "                     <urn1:telefonnummer1></urn1:telefonnummer1>\n" +
            "                     <urn1:telefonnummer2></urn1:telefonnummer2>\n" +
            "                  </urn1:kontaktuppgift>\n" +
            "                  <urn1:personnummer></urn1:personnummer>\n" +
            "               </urn1:patient>\n" +
            "               <urn1:testindikator></urn1:testindikator>\n" +
            "            </urn1:ordinationsinformation>\n" +
            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "         </urn:helforpackningsordination>\n" +
            "         <urn:ordinatorsKontaktuppgift>\n" +
            "            <urn1:adress1 xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:adress1>\n" +
            "            <urn1:adress2 xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:adress2>\n" +
            "            <urn1:postnummer xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:postnummer>\n" +
            "            <urn1:postort xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:postort>\n" +
            "            <urn1:telefonnummer1 xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:telefonnummer1>\n" +
            "            <urn1:telefonnummer2 xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:telefonnummer2>\n" +
            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
            "         </urn:ordinatorsKontaktuppgift>\n" +
            "         <urn:underlagsversion></urn:underlagsversion>\n" +
            "         <urn:orgNr></urn:orgNr>\n" +
            "         <urn:arbetsplatskod></urn:arbetsplatskod>\n" +
            "         <urn:yrkeskod></urn:yrkeskod>\n" +
            "         <urn:efternamn></urn:efternamn>\n" +
            "         <urn:fornamn></urn:fornamn>\n" +
            "         <urn:befattningskod></urn:befattningskod>\n" +
            "      </urn:SkapaOrdinationVard>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";


}

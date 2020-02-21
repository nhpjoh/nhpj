package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;


/**
 * @author Paj
 */
public class SkapaOrdinationApotek extends BaseXML {

    public SkapaOrdinationApotek(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/SkapaOrdinationApotek.properties");
    }

    public SkapaOrdinationApotek() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/SkapaOrdinationApotek.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att ett rescept skapas
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//felmeddelande");
        try {
            if (retVal == 0 ) {
                System.out.println("Ordination skapad OK, Afftatus: " + response.getTagValue("*//affResultat/affStatus") + " OrdinationsId: " +response.getTagValue("*//skapatOrdinationsId"));
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//felmeddelande") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }
    
    private String soapEndpointUrl = "https://prestanda/apisp/SkapaOrdinationApotekResponder/V7";
    
    private String XML =    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "<soapenv:Header>\n" +
                            "</soapenv:Header>\n" +
                            "<soapenv:Body>\n" +
                            "	<urn:SkapaOrdinationApotek xmlns:urn=\"urn:riv:se.apotekensservice:or:SkapaOrdinationApotekResponder:7\">\n" +
                            "		<urn:apoteketsKommentar></urn:apoteketsKommentar>\n" +
                            "		<urn:helforpackningsordination>\n" +
                            "			<urn1:artikelinformation xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "				<urn1:nplId></urn1:nplId>\n" +
                            "				<urn1:nplPackId></urn1:nplPackId>\n" +
                            "				<urn1:utbyttArtikel></urn1:utbyttArtikel>\n" +
                            "				<urn1:extension/>\n" +
                            "			</urn1:artikelinformation>\n" +
                            "			<urn1:behandlingsinformation xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "				<urn1:andamalKlartext></urn1:andamalKlartext>\n" +
                            "				<urn1:doseringstext1></urn1:doseringstext1>\n" +
                            "				<urn1:doseringstext2></urn1:doseringstext2>\n" +
                            "				<urn1:extension/>\n" +
                            "			</urn1:behandlingsinformation>\n" +
                            "			<urn1:forskrivarKommentar xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:forskrivarKommentar>\n" +
                            "			<urn1:helforpackningsregler xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "				<urn1:ejTillatenSubstitution></urn1:ejTillatenSubstitution>\n" +
                            "				<urn1:forman></urn1:forman>\n" +
                            "				<urn1:forskrivetAntalUttag></urn1:forskrivetAntalUttag>\n" +
                            "				<urn1:forstaUttagFore></urn1:forstaUttagFore>\n" +
                            "				<urn1:sistaGiltighetsdag></urn1:sistaGiltighetsdag>\n" +
                            "				<urn1:extension/>\n" +
                            "			</urn1:helforpackningsregler>\n" +
                            "			<urn1:leveransMeddelande xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
                            "			<urn1:mangdinformation xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "				<urn1:antalForpackningar></urn1:antalForpackningar>\n" +
                            "				<urn1:ingenDygnsdos></urn1:ingenDygnsdos>\n" +
                            "				<urn1:startforpackning></urn1:startforpackning>\n" +
                            "				<urn1:extension/>\n" +
                            "			</urn1:mangdinformation>\n" +
                            "			<urn1:ordinationsinformation xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "				<urn1:ordinationstidpunkt></urn1:ordinationstidpunkt>\n" +
                            "				<urn1:originalordinationsId></urn1:originalordinationsId>\n" +
                            "				<urn1:patient>\n" +
                            "					<urn1:efternamn></urn1:efternamn>\n" +
                            "					<urn1:fornamn></urn1:fornamn>\n" +
                            "					<urn1:personnummer></urn1:personnummer>\n" +
                            "					<urn1:extension/>\n" +
                            "				</urn1:patient>\n" +
                            "				<urn1:testindikator></urn1:testindikator>\n" +
                            "				<urn1:extension/>\n" +
                            "			</urn1:ordinationsinformation>\n" +
                            "			<urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
                            "		</urn:helforpackningsordination>\n" +
                            "		<urn:ordinator>\n" +
                            "			<urn1:arbetsplatskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:arbetsplatskod>\n" +
                            "			<urn1:befattningskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
                            "			<urn1:efternamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:efternamn>\n" +
                            "			<urn1:fornamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:fornamn>\n" +
                            "			<urn1:forskrivarkod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:forskrivarkod>\n" +
                            "			<urn1:kontaktuppgift xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "				<urn1:adress1></urn1:adress1>\n" +
                            "				<urn1:adress2></urn1:adress2>\n" +
                            "				<urn1:postnummer></urn1:postnummer>\n" +
                            "				<urn1:postort></urn1:postort>\n" +
                            "				<urn1:telefonnummer1></urn1:telefonnummer1>\n" +
                            "				<urn1:telefonnummer2></urn1:telefonnummer2>\n" +
                            "				<urn1:extension/>\n" +
                            "			</urn1:kontaktuppgift>\n" +
                            "			<urn1:legitimationskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:legitimationskod>\n" +
                            "			<urn1:yrkeskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:yrkeskod>\n" +
                            "			<urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
                            "		</urn:ordinator>\n" +
                            "		<urn:originalformat></urn:originalformat>\n" +
                            "		<urn:registrerandeApotekspersonal>\n" +
                            "			<urn1:apoteksId xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:apoteksId>\n" +
                            "			<urn1:befattningskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:befattningskod>\n" +
                            "			<urn1:efternamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:efternamn>\n" +
                            "			<urn1:fornamn xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:fornamn>\n" +
                            "			<urn1:legitimationskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:legitimationskod>\n" +
                            "			<urn1:yrkeskod xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:yrkeskod>\n" +
                            "			<urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
                            "		</urn:registrerandeApotekspersonal>\n" +
                            "		<urn:underlagsversion></urn:underlagsversion>\n" +
                            "		<urn:anvandare></urn:anvandare>\n" +
                            "		<urn:yrkeskod></urn:yrkeskod>\n" +
                            "		<urn:efternamn></urn:efternamn>\n" +
                            "		<urn:fornamn></urn:fornamn>\n" +
                            "		<urn:befattningskod></urn:befattningskod>\n" +
                            "		<urn:extension/>\n" +
                            "	</urn:SkapaOrdinationApotek>\n" +
                            "</soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    
}

package se.nhpj.soap.services;

import org.junit.Test;
import se.nhpj.rest.Base64Converter;
import se.nhpj.rest.RestCall;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import javax.xml.soap.SOAPMessage;

import static org.junit.Assert.*;

public class HamtaProduktionsunderlagTest {

//    String SERVICEENDPOINT      = "https://prestanda.systest.receptpartner.se";
    String SERVICEENDPOINT      = "https://test2.systest.receptpartner.se:19443";
//    String SERVICEENDPOINT      = "https://test6.systest.receptpartner.se:19443";
//    String SERVICEENDPOINT      = "https://test3.systest.receptpartner.se:19443";

    SoapResponseXML response;

    String  GLN	                = "7350045511997";  // DX
//    String  GLN	                = "7350045511119";      // AP

//    String  pnr                 = "197503259280";       // Med dosunderlag // prestanda
    String  pnr                 = "196010272661";       // Med dosunderlag

    // Farmaceut / apotekare
    String AP_fname             = "Inga-Lill";                  // FORS_PROD.APOTEKSPERSONAL.FORNAMN
    String AP_ename             = "Ingesson";                   // FORS_PROD.APOTEKSPERSONAL.EFTERNAMN
    String AP_kod               = "920007";                     // FORS_PROD.APOTEKSPERSONAL.INTR_ID
    String AP_yrkeskod	        = "AP";			                // FORS_PROD.FORS_KATEGORI.KATEGORI


    @Test
    public void checkResponseSambibiljett() {
        HamtaProduktionsunderlag hp = new HamtaProduktionsunderlag();
        hp.setSoapEndpointUrl( SERVICEENDPOINT + "/apisp/HamtaProduktionsunderlagResponder/V4");

        Tickets ticket = new Tickets();
        ticket.setHealthcareProfessionalLicense(AP_yrkeskod);
        ticket.setHealthcareProfessionalLicenseIdentityNumber(AP_kod);
        ticket.setPharmacyIdentifier(GLN);

        hp.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        hp.setTagValue("*//personnummer",pnr);

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hp.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hp.getXML() ))));
//        hp.checkResponse(response);

//        response.logXML();
        try {
            System.out.println("SAMBI: " + response.getTagValue("*//description"));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        hp.logXML();
    }

    @Test
    public void checkResponseOAuth() {
        // Skapar en SAMBI ticket
        Tickets t = new Tickets();
        t.setHealthcareProfessionalLicense(AP_yrkeskod);
        t.setHealthcareProfessionalLicenseIdentityNumber(AP_kod);
        t.setPharmacyIdentifier(GLN);

        // Hämtar sambiticket
        String ticket = t.getTicket();

        // Ränsar bort taggar från svar som ska bort...
        ticket = ticket.replaceAll("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">", "");
        ticket = ticket.replaceAll("</wsse:Security>", "");

        // Base64 krypterar sambiticket
        String encodedTicket = Base64Converter.encodeString(ticket);

        // Skapar ett RestCall object
        RestCall restcall = new RestCall();

        // Sätter testmiljön
        restcall.setUrl( SERVICEENDPOINT + "/oauth2/api/oauth/token");

        // Kollar att URL:en är satt
        //System.out.println(" URL:en är satt: " + restcall.getUrl());

        // Sätter Assertion objektet .. antingen en assertion(sambiticket) token eller ett refrech_token
        restcall.setAssertion(encodedTicket);
        try {
            // Gör restanropet
            restcall.runRestCall(restcall.ASSERTION);

            // skriver ut status på anropet
            //System.out.println(restcall.getStatusCode().toString());

            // skriver ut response
            //System.out.println(restcall.getResponse());

            // Svaret på restanropet kommer tillbaka i ett OauthToken eller ett OauthErrorTOken objekt beroände på statuskoden.
            if (restcall.getStatusCode() == 200 ) {
                //System.out.println("access_token: " +  restcall.getOauthToken().getAccess_token() );
                //System.out.println("expires_in: " +    restcall.getOauthToken().getExpires_in() );
                //System.out.println("token_type: " +    restcall.getOauthToken().getToken_type() );
                //System.out.println("refresh_token: " + restcall.getOauthToken().getRefresh_token() );
            } else {
                System.out.println("Något gick fel");
                System.out.println(restcall.getOauthToken().toString());
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaProduktionsunderlag using Access_token ----------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
        // Kollar att tidigare anrop gått bra annars avsluta innan tjensteanropet.
        if (restcall.getStatusCode() != 200) {  System.exit(restcall.getStatusCode()); }

        // Skapar en instans av enjänsteanrops class
        HamtaProduktionsunderlag hp = new HamtaProduktionsunderlag();
        hp.setSoapEndpointUrl( SERVICEENDPOINT + "/apisp/HamtaProduktionsunderlagResponder/V4");
        hp.setTagValue("*//personnummer",pnr);

        // Skapar soapMeddelandet från XML:en
        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString( hp.getXML() );

        // Lägger till token i meddelandets header i detta fall svaret från ett refreshToken
        soapmessage.getMimeHeaders().addHeader("Authorization", "Bearer " + restcall.getOauthToken().getAccess_token() );

        // skickar soap meddelandet
        SOAPMessage responsemessage = BaseXML.sendSoapRequest( hp.getSoapEndpointUrl(), soapmessage );

        // hämtar upp svaret
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( responsemessage ));

        // Kollar svaret med standardkontrollen
        //hp.checkResponse(response);

        // Loggar ut svaret
        //response.logXML();

        try {
            System.out.println("oAuth: " + response.getTagValue("*//description"));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        hp.logXML();

// -----------------------------------------------------------------------------------------------------------------------------------------------------------

    }

}
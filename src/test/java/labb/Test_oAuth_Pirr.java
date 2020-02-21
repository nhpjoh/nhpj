package labb;

import org.junit.Test;
import se.nhpj.rest.Base64Converter;
import se.nhpj.rest.RestCall;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.Prescription;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.Wsgeneric;

import javax.xml.soap.SOAPMessage;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.System.exit;

/**
 * @author nhpj
 */
public class Test_oAuth_Pirr {
    @Test
    public void test() throws Exception {
        SoapResponseXML response;

        String endpoint         = "https://test28.systest.receptpartner.se:19443";

        String  GLN		        = "7350045511119";

        String patient_pnr      = "189002289800";
        String patient_fnamn    = "Jörgen";                     // FOLK_PROD.SPAR_PERSON.FORNAMN
        String patient_enamn    = "Österling";                  // FOLK_PROD.SPAR_PERSON.EFTERNAMN
        String patient_adress   = "Testgatan 47337";            // FOLK_PROD.SPAR_ADRESS.GATUADRESS
        String patient_postnr   = "47337";                      // FOLK_PROD.SPAR_ADRESS.POSTNR
        String patient_ort      = "Testorten";                  // FOLK_PROD.SPAR_ADRESS.POSTORT
        String patient_sex      = "1";                          // Ta från personnummret :-)
        String patient_telnr    = "070-123456";                 // Slumpa fram

        // Läkemedel
        String antalUttag       = "3";
        String NumberOfPackages = "3";
        String nplId            = "20101029000041";             // 20160416000026
        String nplpackid        = "20130926100165";             // 20161130100023
        String varunr           = "103376";
        String antal_numeriskt  = "30";

        // Farmaceut / apotekare
        String AP_fname         = "Inga-Lill";                  // FORS_PROD.APOTEKSPERSONAL.FORNAMN
        String AP_ename         = "Ingesson";                   // FORS_PROD.APOTEKSPERSONAL.EFTERNAMN
        String AP_kod           = "920007";                     // FORS_PROD.APOTEKSPERSONAL.INTR_ID
        String AP_yrkeskod	    = "AP";				            // FORS_PROD.FORS_KATEGORI.KATEGORI

        // Läkare
        String LK_fname         = "Lars";                       // FORS_PROD.FORS.NAMN
        String LK_ename         = "Läkare";                     // FORS_PROD.FORS.NAMN
        String LK_kod           = "9000027";                    // FORS_PROD.FORS.KOD + KOD_CHECK
        String LK_kategori      = "LAK";                        // utfärdarkategori

        // Skjukhus - vårinrättnig
        String arbetsplats_kod      =   "4000000000000";        // ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD
        String arbetsplats_namn     =   "Sjukhus ett";          // ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN
        String arbetsplats_adress   =   "Gatan 1";              // ARKO_PROD.ARBETSPLATS.POSTADRESS
        String arbetsplats_ort      =   "Staden 1";             // ARKO_PROD.ARBETSPLATS.POSTORT
        String arbetsplats_postnr   =   "11111";                // ARKO_PROD.ARBETSPLATS.POSTNUMMER
        String arbetsplats_telnr1   =   "08-1000000";           // ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1


// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Skapa Ordination via Pirr ---- Skapar receptet ------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

        Prescription recept = new Prescription();
        recept.setStandardDefaultValues();
        recept.setTodaysDates();
        recept.setAllUUID();
        
        // Skjukhus - vårinrättnig
        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareAgentId[1]/Value", arbetsplats_kod);
        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareAgentId[2]/Value", LK_kod);
        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/HealthcarePerson/Name", LK_ename + " " + LK_fname);
        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/HealthcarePerson/Qualification",LK_kategori);
        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/Address/StructuredAddress/NumberOrNameOfHouse",arbetsplats_namn);
        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/Address/StructuredAddress/StreetName",arbetsplats_adress);
        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/Address/StructuredAddress/City",arbetsplats_ort);
        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/Telecommunication",arbetsplats_telnr1);

        // Läkare
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareAgentId[1]/Value", arbetsplats_kod);
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareAgentId[2]/Value", LK_kod);
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/HealthcarePerson/Name", LK_ename + " " + LK_fname);
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/HealthcarePerson/Qualification",LK_kategori);
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Address/PostalCode",arbetsplats_postnr);
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Address/StructuredAddress/NumberOrNameOfHouse",arbetsplats_namn);
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Address/StructuredAddress/StreetName",arbetsplats_adress);
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Address/StructuredAddress/City",arbetsplats_ort);
        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Telecommunication",arbetsplats_telnr1);

        // Patient
        recept.setTagValue("*//PatientMatchingInfo/Address/PostalCode",patient_postnr);
        recept.setTagValue("*//PatientMatchingInfo/Address/StructuredAddress/StreetName",patient_adress);
        recept.setTagValue("*//PatientMatchingInfo/Address/StructuredAddress/City",patient_ort);
        recept.setTagValue("*//PatientMatchingInfo/Sex",patient_sex);
        recept.setTagValue("*//FirstGivenName", patient_fnamn);
        recept.setTagValue("*//FamilyName", patient_enamn);
        recept.setPersonnummer(patient_pnr);

        // Läkemedel
        recept.setNplId(nplId);
        recept.setNplPackId(nplpackid);
        recept.setInstructionsForUse("Så här ska du ta din medusin");
        recept.setAntalUttag(antalUttag);
        recept.setNumberOfPackages(NumberOfPackages);

        recept.setXML(recept.getXML());

// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// Skapar en ticket
// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        Tickets t = new Tickets();
        t.setHealthcareProfessionalLicense("LK");
        t.setPersonalPrescriptionCode(LK_kod);

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
        restcall.setUrl(endpoint + "/oauth2/api/oauth/token");

        // Kollar att URL:en är satt
        System.out.println("140: URL:en " + restcall.getUrl());

        // Sätter Assertion objektet .. antingen en assertion(sambiticket) token eller ett refresh_token
        restcall.setAssertion(encodedTicket);

        try {
            // Gör restanropet
            restcall.runRestCall(restcall.ASSERTION);

            // skriver ut status på anropet
            System.out.println("150: RestCall response: " + restcall.getStatusCode());

            // skriver ut response
            System.out.println("153: RestCall response: " + restcall.getResponse());

            // Svaret på restanropet kommer tillbaka i ett OauthToken eller ett OauthErrorTOken objekt beroende på statuskoden.
            if (restcall.getStatusCode() == 200 ) {
                System.out.println("access_token: " +  restcall.getOauthToken().getAccess_token() );
                System.out.println("expires_in: " +    restcall.getOauthToken().getExpires_in() );
                System.out.println("token_type: " +    restcall.getOauthToken().getToken_type() );
                System.out.println("refresh_token: " + restcall.getOauthToken().getRefresh_token() );
            } else {
                System.out.println("Något gick fel");
                System.out.println(restcall.getOauthToken().toString());
            }

//            // Skickar in ett refresh token för att se att det fungerar
//            if (restcall.getStatusCode() == 200 ) {
//                restcall.setAssertion(restcall.getOauthToken().getRefresh_token()); // Sätter assertion med ett refreshToken
//                restcall.runRestCall(restcall.REFRESH_TOKEN);                       // Gör ett refresh token rest anrop
//                System.out.println("\n\n" + restcall.getOauthToken().toString());   // skriver ut svaret
//            }
        } catch (Exception ex) {
            System.out.println("173: " + ex);
        }

// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// Sänder in Receptet -------------------------------------------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        Wsgeneric pirr = new Wsgeneric();
        pirr.setSoapEndpointUrl(endpoint + "/apisp-pi-wsgeneric-1.3/wsgeneric");

        // Signerar och Base64 encoda receptet
        pirr.setDATA(pirr.encode(recept.getXML()));

        // Skapar soapMeddelandet från XML:en
        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString( pirr.getXML() );

        System.out.println("189: Request\n" + pirr.getXML() + "\n : 195 - slut Request\n");

        // Lägger till token i meddelandets header i detta fall svaret från ett refreshToken
        soapmessage.getMimeHeaders().addHeader("Authorization", "Bearer " + restcall.getOauthToken().getAccess_token() );

        // skickar soap meddelandet
        SOAPMessage responsemessage = BaseXML.sendPirrSoapRequest( pirr.getSoapEndpointUrl(), soapmessage );


        // hämtar upp svaret
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( responsemessage ));


        System.out.println("202 Responsen: " + response.getXML());
        if ( !response.getTagValue("*//message").contains("ccepterat") ) {
            System.out.println("204: \nCode: " + response.getTagValue("*//code") + " \nMessage: " + response.getTagValue("*//message")+"\n");
            if (response.getTagValue("*//code").contains("200")) {
                System.out.println("206: \n" + se.nhpj.rest.Base64Converter.decodeString(response.getTagValue("*//data")));
            }
            else System.out.println("208: \n" + pirr.decode( response.getTagValue("*//data")));
            exit(1);
        }
        System.out.println("211 data: " + response.getTagValue("*//data"));
        System.out.println("\n212: " + pirr.decode(response.getTagValue("*//data")));

        SoapResponseXML pirrRecept = new SoapResponseXML( pirr.decode( response.getTagValue("*//data") ) );  // Konvertera receptet tillbaka till läsbar text

        System.out.println("\n216: " + pirrRecept.getTagValue("*//Description") + " ( Läkare: " + LK_fname + " " + LK_ename + " kod: " + LK_kod + " Kategori: " + LK_kategori + ")");

        String retCode = pirrRecept.getTagValue("*//Description").substring(0, 1);
        if (!retCode.equals("0")) {
            System.out.println("220: Stoppar på grund av fel! ");
            System.out.println("221: \n" + pirrRecept.getXML());
            exit(0);
        }

        System.out.println("225: PrescriptionSetId: " + pirrRecept.getTagValue("*//PrescriptionSetId"));
        System.out.println("226: OrdinationsId: " + BaseXML.getOrdinationsId(pirrRecept.getTagValue("*//PrescriptionSetId"), "INT2"));
    }

}

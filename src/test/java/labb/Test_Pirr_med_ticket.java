package labb;

import java.io.UnsupportedEncodingException;
import static java.lang.System.exit;

import javax.xml.soap.SOAPMessage;
import org.junit.Test;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.Prescription;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.Wsgeneric;

import javax.xml.soap.MimeHeaders;

/**
 * @author nhpj
 */
public class Test_Pirr_med_ticket {
    @Test
    public void testNyttRecept() throws Exception {

        String endpoint         = "https://Pirr";       //
//        String endpoint         = "http://nll-transformator-logic-s7-test1-deploy1.test.ecp.systest.receptpartner.se";       //
//        String endpoint         = "https://sit1:19443"; //
        String oracleServiceName= "PTRR";
        SoapResponseXML response;
        Tickets ticket 	        = new Tickets();
        String GLN		        = "7350045511119";
        
        String patient_pnr      = "199108122384";                 // 199108122384 // 189002289800
        String patient_fnamn    = "Jörgen";                       // FOLK_PROD.SPAR_PERSON.FORNAMN
        String patient_enamn    = "Österling";                    // FOLK_PROD.SPAR_PERSON.EFTERNAMN
        String patient_adress   = "Testgatan 47337";              // FOLK_PROD.SPAR_ADRESS.GATUADRESS
        String patient_postnr   = "47337";                        // FOLK_PROD.SPAR_ADRESS.POSTNR
        String patient_ort      = "Testorten";                    // FOLK_PROD.SPAR_ADRESS.POSTORT
        String patient_sex      = "1";                            // Ta från personnummret :-)
        String patient_telnr    = "070-123456";                   // Slumpa fram
        
        // Läkemedel
        String antalUttag       = "3";
        String NumberOfPackages = "3";
        String nplId            = "20101029000041";               // 20160416000026
        String nplpackid        = "20130926100165";               // 20161130100023
        String varunr           = "103376";
        String antal_numeriskt  = "30";
        
        // Farmaceut / apotekare
        String AP_fname         = "Inga-Lill";                    // FORS_PROD.APOTEKSPERSONAL.FORNAMN
        String AP_ename         = "Ingesson";                     // FORS_PROD.APOTEKSPERSONAL.EFTERNAMN
        String AP_kod           = "920007";                       // FORS_PROD.APOTEKSPERSONAL.INTR_ID
       	String AP_yrkeskod	    = "AP";				              // FORS_PROD.FORS_KATEGORI.KATEGORI

        // Läkare
//        String LK_fname         = "Walter";                     // FORS_PROD.FORS.NAMN
//        String LK_ename         = "Graaf";                      // FORS_PROD.FORS.NAMN
//        String LK_kod           = "8880999";                    // FORS_PROD.FORS.KOD + KOD_CHECK
//        String LK_kategori      = "LAK";                        // utfärdarkategori

        // Läkare
        String LK_fname         = "Lars";                         // FORS_PROD.FORS.NAMN
        String LK_ename         = "Läkare";                       // FORS_PROD.FORS.NAMN
        String LK_kod           = "9000027";                      // FORS_PROD.FORS.KOD + KOD_CHECK
        String LK_kategori      = "LAK";                          // utfärdarkategori

        // TandLäkare
//        String LK_fname         = "Elina";                      // FORS_PROD.FORS.NAMN
//        String LK_ename         = "Stenlund";                   // FORS_PROD.FORS.NAMN
//        String LK_kod           = "8881005";                    // FORS_PROD.FORS.KOD + KOD_CHECK
//        String LK_kategori      = "TAN";                        // utfärdarkategori

//        // Skjukhus - vårinrättnig
//        String arbetsplats_kod      =   "0900000000002";        // ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD
//        String arbetsplats_namn     =   "Gotland Sjukhus 2";    // ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN
//        String arbetsplats_adress   =   "Gotlandgatan 2";       // ARKO_PROD.ARBETSPLATS.POSTADRESS
//        String arbetsplats_ort      =   "Gotland";              // ARKO_PROD.ARBETSPLATS.POSTORT
//        String arbetsplats_postnr   =   "90002";                // ARKO_PROD.ARBETSPLATS.POSTNUMMER
//        String arbetsplats_telnr1   =   "0498-1000002";         // ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1

        // Skjukhus - vårinrättnig
        String arbetsplats_kod      =   "4000000000000";          // ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD
        String arbetsplats_namn     =   "Sjukhus ett";            // ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN
        String arbetsplats_adress   =   "Gatan 1";                // ARKO_PROD.ARBETSPLATS.POSTADRESS
        String arbetsplats_ort      =   "Staden 1";               // ARKO_PROD.ARBETSPLATS.POSTORT
        String arbetsplats_postnr   =   "11111";                  // ARKO_PROD.ARBETSPLATS.POSTNUMMER
        String arbetsplats_telnr1   =   "08-1000000";             // ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1

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

//        System.out.println(recept.getXML());

// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------- Sender in Receptet --------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

        Wsgeneric pirr = new Wsgeneric();
        pirr.setSoapEndpointUrl(endpoint + "/apisp-pi-wsgeneric-1.3/wsgeneric");

        pirr.setDATA(pirr.encode(recept.getXML())); // Signerar och Base64 encoda receptet

        ticket.setHealthcareProfessionalLicense("LK");
        ticket.setPersonalPrescriptionCode(LK_kod);

        pirr.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

//        System.out.println("157: Request\n" + PirrHandler.getXML() + "\n : 157 - slut Request\n");

        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString(pirr.getXML());

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( pirr.getSoapEndpointUrl(), soapmessage ) ) );

//        System.out.println("163 Responsen: " + response.getXML());
        if ( !response.getTagValue("*//message").contains("ccepterat") ) {
            System.out.println("167: \nCode: " + response.getTagValue("*//code") + " \nMessage: " + response.getTagValue("*//message")+"\n");
            if (response.getTagValue("*//code").contains("200")) {
                System.out.println("169: \n" + se.nhpj.rest.Base64Converter.decodeString(se.nhpj.rest.Base64Converter.decodeString(response.getTagValue("*//data"))));
            }
            else System.out.println("171: \n" + pirr.decode( response.getTagValue("*//data")));
            exit(1); 
        }
//        System.out.println("172 data: " + response.getTagValue("*//data"));
//        System.out.println("\n173: " + PirrHandler.decode(response.getTagValue("*//data")));
        
        SoapResponseXML pirrRecept = new SoapResponseXML( pirr.decode( response.getTagValue("*//data") ) );  // Konvertera receptet tillbaka till läsbar text

        System.out.println("\n179: " + pirrRecept.getTagValue("*//Description") + " ( Läkare: " + LK_fname + " " + LK_ename + " kod: " + LK_kod + " Kategori: " + LK_kategori + ")");

        String retCode = pirrRecept.getTagValue("*//Description").substring(0, 1);
        if (!retCode.equals("0")) { 
            System.out.println("183: Stoppar på grund av fel! ");
            System.out.println("184: \n" + pirrRecept.getXML());
            exit(0); 
        }

        System.out.println("188: PrescriptionSetId: " + pirrRecept.getTagValue("*//PrescriptionSetId"));
        System.out.println("189: OrdinationsId: " + BaseXML.getOrdinationsId(pirrRecept.getTagValue("*//PrescriptionSetId"), oracleServiceName));

    }
    
}

package labb;

import se.nhpj.soap.*;
import se.nhpj.soap.services.Wsgeneric;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.HamtaExpeditionsId;
import se.nhpj.soap.services.HamtaHkdbKonto;
import se.nhpj.soap.services.HamtaSubstitution;
import se.nhpj.soap.services.Prescription;
import se.nhpj.soap.services.UppdateraForsaljningOppenvardForskrivning;
import static java.lang.System.exit;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;
import se.nhpj.database.DB_Access;
import se.nhpj.soap.utils.CurrentDateTime;
import se.nhpj.soap.utils.ManipuleraReceptDatum;
import static se.nhpj.soap.utils.ManipuleraReceptDatum.*;
import se.nhpj.testdata.RndData;
import se.nhpj.soap.services.*;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;
import se.nhpj.testdata.Telefonnummer;

import javax.xml.soap.SOAPMessage;

import static org.junit.Assert.*;

/**
 * @author nhpj
 */
public class Test_BaseXML {
    
    @Test
    public void test1() throws Exception {
        
        String SERVICEENDPOINT      = "https://prestanda";
        String PIRR_SERVICEENDPOINT = "https://PirrHandler";
        SoapResponseXML response;
        String ordId;
        String underlagsversion;
        String expeditionsId;
        String ackBrutto;
        String periodStart;
        String pris_aup;
        Tickets ticket 	= new Tickets();
        String  GLN	= "7350045511119";        
        Integer antalDagar = Integer.parseInt("-" + se.nhpj.testdata.RndData.getANumber(1, 360));
//
// Start LOOP
//
//
//        for (int i=0;i<1;i++) {
//        System.out.println("\n>>>>> Iter: " + (i+1) + " <<<<<<\n");
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Sätter testdata -------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

//        // Slumpad patient
//        Skatteverket sk         = new Skatteverket();
//        Person p                = sk.getSlumpadPerson(1890,1899);
//        String patient_pnr      = p.getPersonnummer();           
//        String patient_fnamn    = p.getFörnamn();                       // FOLK_PROD.SPAR_PERSON.FORNAMN
//        String patient_enamn    = p.getEfternamn();                     // FOLK_PROD.SPAR_PERSON.EFTERNAMN
//        String patient_adress   = p.getGatuAdress();                    // FOLK_PROD.SPAR_ADRESS.GATUADRESS
//        String patient_postnr   = p.getPostnummer();                    // FOLK_PROD.SPAR_ADRESS.POSTNR
//        String patient_ort      = p.getOrt();                           // FOLK_PROD.SPAR_ADRESS.POSTORT
//        String patient_sex;
//        if (p.isKvinna()) {patient_sex = "2";} else {patient_sex = "1";}// Ta från personnummret :-)
//        String patient_telnr    = Telefonnummer.getMobilNummer();       // Slumpat 


//        // Patient 1
//        String patient_pnr      = "189103049814";           // FOLK_PROD.SPAR_PERSON.PERSONNR  // "198903082389" // dos 189104159810 189008199805 // 189912319812 // 189007269807 // 189012299807
//        String patient_fnamn    = "Claes";                  // FOLK_PROD.SPAR_PERSON.FORNAMN
//        String patient_enamn    = "Bökman";                 // FOLK_PROD.SPAR_PERSON.EFTERNAMN
//        String patient_adress   = "Testgatan55";            // FOLK_PROD.SPAR_ADRESS.GATUADRESS
//        String patient_postnr   = "12345";                  // FOLK_PROD.SPAR_ADRESS.POSTNR
//        String patient_ort      = "Postort";                // FOLK_PROD.SPAR_ADRESS.POSTORT
//        String patient_sex      = "1";                      // Ta från personnummret :-)
//        String patient_telnr    = "070-123456";             // Slumpa fram


//        // Patient 2 DOSPATIENT
//        String patient_pnr      = "189001019802";           
//        String patient_fnamn    = "Izabella";              // FOLK_PROD.SPAR_PERSON.FORNAMN
//        String patient_enamn    = "Smäll";                 // FOLK_PROD.SPAR_PERSON.EFTERNAMN
//        String patient_adress   = "Testgatan 92267";       // FOLK_PROD.SPAR_ADRESS.GATUADRESS
//        String patient_postnr   = "92267";                 // FOLK_PROD.SPAR_ADRESS.POSTNR
//        String patient_ort      = "Testorten";             // FOLK_PROD.SPAR_ADRESS.POSTORT
//        String patient_sex      = "2";                     // Ta från personnummret :-)
//        String patient_telnr    = "070-123456";            // Slumpa fram
        
//        // Patient 3 DOSPATIENT
//        String patient_pnr      = "189001029819";           
//        String patient_fnamn    = "Hektor";              // FOLK_PROD.SPAR_PERSON.FORNAMN
//        String patient_enamn    = "Rendahl";             // FOLK_PROD.SPAR_PERSON.EFTERNAMN
//        String patient_adress   = "Testgatan 65823";     // FOLK_PROD.SPAR_ADRESS.GATUADRESS
//        String patient_postnr   = "65823";               // FOLK_PROD.SPAR_ADRESS.POSTNR
//        String patient_ort      = "Testorten";           // FOLK_PROD.SPAR_ADRESS.POSTORT
//        String patient_sex      = "1";                   // Ta från personnummret :-)
//        String patient_telnr    = "070-123456";          // Slumpa fram

//        // Patient 4 DOSPATIENT
//        String patient_pnr      = "189001039800";           
//        String patient_fnamn    = "Josefine";              // FOLK_PROD.SPAR_PERSON.FORNAMN
//        String patient_enamn    = "Moberg";             // FOLK_PROD.SPAR_PERSON.EFTERNAMN
//        String patient_adress   = "Testgatan 39318";     // FOLK_PROD.SPAR_ADRESS.GATUADRESS
//        String patient_postnr   = "39318";               // FOLK_PROD.SPAR_ADRESS.POSTNR
//        String patient_ort      = "Testorten";           // FOLK_PROD.SPAR_ADRESS.POSTORT
//        String patient_sex      = "2";                   // Ta från personnummret :-)
//        String patient_telnr    = "070-123456";          // Slumpa fram

        // Patient 5 - NLL test
        String patient_pnr      = "189001039800";           
        String patient_fnamn    = "Josefine";              // FOLK_PROD.SPAR_PERSON.FORNAMN
        String patient_enamn    = "Moberg";             // FOLK_PROD.SPAR_PERSON.EFTERNAMN
        String patient_adress   = "Testgatan 39318";     // FOLK_PROD.SPAR_ADRESS.GATUADRESS
        String patient_postnr   = "39318";               // FOLK_PROD.SPAR_ADRESS.POSTNR
        String patient_ort      = "Testorten";           // FOLK_PROD.SPAR_ADRESS.POSTORT
        String patient_sex      = "2";                   // Ta från personnummret :-)
        String patient_telnr    = "070-123456";          // Slumpa fram

        // Läkemedel
        String antalUttag       = "3";                     
        String NumberOfPackages = "3";
        String nplId            = "20160416000026";
        String nplPackId        = "20161130100023";
        String varunr           = "071156";
        String antal_numeriskt  = "30";

        String nplId_2          = "19541213000015";
        String nplPackId_2      = "10010101104849";

        String nplId_3          = "19541213000015";
        String nplPackId_3      = "10010101104832";

        // Farmaceut / apotekare
        String AP_fname          = "Inga-Lill";                  // FORS_PROD.APOTEKSPERSONAL.FORNAMN
        String AP_ename          = "Ingesson";                   // FORS_PROD.APOTEKSPERSONAL.EFTERNAMN
        String AP_kod            = "920007";                     // FORS_PROD.APOTEKSPERSONAL.INTR_ID
       	String AP_yrkeskod	 = "AP";			 // FORS_PROD.FORS_KATEGORI.KATEGORI
        String AP_befattningskod = "1234";
        
       // Läkare
//        String LK_fname         = "Lars";                     // FORS_PROD.FORS.NAMN
//        String LK_ename         = "Läkare";                   // FORS_PROD.FORS.NAMN
//        String LK_kod           = "9000027";                  // FORS_PROD.FORS.KOD + KOD_CHECK

        String LK_fname         = "Walter";                     // FORS_PROD.FORS.NAMN
        String LK_ename         = "Graaf";                      // FORS_PROD.FORS.NAMN
        String LK_kod           = "8880999";                    // FORS_PROD.FORS.KOD + KOD_CHECK
        String LK_yrkeskod      = "LK";                         // yrkeskod
        String LK_kategori      = "LAK";                        // utfärdarkategori

        // Skjukhus - vårinrättnig
        String arbetsplats_kod      =   "0900000000002";        // ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD
        String arbetsplats_namn     =   "Gotland Sjukhus 2";    // ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN
        String arbetsplats_adress   =   "Gotlandgatan 2";       // ARKO_PROD.ARBETSPLATS.POSTADRESS
        String arbetsplats_ort      =   "Gotland";              // ARKO_PROD.ARBETSPLATS.POSTORT
        String arbetsplats_postnr   =   "90002";                // ARKO_PROD.ARBETSPLATS.POSTNUMMER
        String arbetsplats_telnr1   =   "0498-1000002";         // ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1

    
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Skapa Ordination via Pirr --------------------------------(slf4j) logback ---------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        Prescription recept = new Prescription();
//        recept.setStandardDefaultValues();
//        recept.setTodaysDates();
//        recept.setAllUUID();
//
//        // Skjukhus - vårinrättnig
//        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareAgentId[1]/Value", arbetsplats_kod);
//        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareAgentId[2]/Value", LK_kod);
//        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/HealthcarePerson/Name", LK_ename + " " + LK_fname);
//        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/HealthcarePerson/Qualification",LK_kategori);
//        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/Address/StructuredAddress/NumberOrNameOfHouse",arbetsplats_namn);
//        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/Address/StructuredAddress/StreetName",arbetsplats_adress);
//        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/Address/StructuredAddress/City",arbetsplats_ort);
//        recept.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/Telecommunication",arbetsplats_telnr1);
//        // Läkare
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareAgentId[1]/Value", arbetsplats_kod);
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareAgentId[2]/Value", LK_kod);
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/HealthcarePerson/Name", LK_ename + " " + LK_fname);
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/HealthcarePerson/Qualification",LK_kategori);
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Address/PostalCode",arbetsplats_postnr);
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Address/StructuredAddress/NumberOrNameOfHouse",arbetsplats_namn);
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Address/StructuredAddress/StreetName",arbetsplats_adress);
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Address/StructuredAddress/City",arbetsplats_ort);
//        recept.setTagValue("*//Prescriber/HealthcareAgent/HealthcareParty/Telecommunication",arbetsplats_telnr1);
//        // Patient
//        recept.setTagValue("*//PatientMatchingInfo/Address/PostalCode",patient_postnr);
//        recept.setTagValue("*//PatientMatchingInfo/Address/StructuredAddress/StreetName",patient_adress);
//        recept.setTagValue("*//PatientMatchingInfo/Address/StructuredAddress/City",patient_ort);
//        recept.setTagValue("*//PatientMatchingInfo/Sex",patient_sex);
//        recept.setTagValue("*//FirstGivenName", patient_fnamn);
//        recept.setTagValue("*//FamilyName", patient_enamn);
//        recept.setPersonnummer(patient_pnr);
//
//        // Läkemedel
//        recept.setNplId(nplId);
//        recept.setNplPackId(nplPackId);
//        recept.setInstructionsForUse("Så här ska du ta din medusin");
//        recept.setAntalUttag(antalUttag);
//        recept.setNumberOfPackages(NumberOfPackages);
//
//        recept.setXML(recept.getXML());
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------        
// Extra rader till PIRR recepten ----------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------        
//        PrescriptionItemDetails receptDetaljer = new PrescriptionItemDetails();
//        receptDetaljer.setPrescriptionItemId("2-1");
//        receptDetaljer.setNplId(nplId_2);
//        receptDetaljer.setNplPackId(nplPackId_2);
//        receptDetaljer.setInstructionsForUse("Så här ska du INTE ta medusin");
//        
//        PrescriptionItemDetails receptDetaljer3 = new PrescriptionItemDetails();
//        receptDetaljer3.setPrescriptionItemId("3-1");
//        receptDetaljer3.setNplId(nplId_3);
//        receptDetaljer3.setNplPackId(nplPackId_3);
//        receptDetaljer3.setInstructionsForUse("Så här SKA du ta medusin");
//        
//        recept.insertXmlAfterTag("</PrescriptionItemDetails>", "\n" + receptDetaljer.getXML() + "\n" + receptDetaljer3.getXML());
//        recept.setXML(recept.getXML());
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------        
//  Sänder in receptet med WsGeneric ...
// -----------------------------------------------------------------------------------------------------------------------------------------------------------        
//        Wsgeneric pirr = new Wsgeneric();
//        //PirrHandler.setSoapEndpointUrl("https://pirr/pi-wsgeneric-1.3/wsgeneric");
//        pirr.setSoapEndpointUrl( PIRR_SERVICEENDPOINT + "/pi-wsgeneric-1.3/wsgeneric");
//        pirr.setDATA(pirr.encode(recept.getXML())); // Signerar och Base64 encoda receptet
//
//        // Detta tilllagt för att kunna köra utan ticket
//        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString(pirr.getXML());
//        String authorization = new sun.misc.BASE64Encoder().encode(("pirruser:pirruser1").getBytes());
//        soapmessage.getMimeHeaders().addHeader("Authorization", "Basic " + authorization);
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( pirr.getSoapEndpointUrl(), soapmessage ) ) );
//
//        SoapResponseXML pirrRecept = new SoapResponseXML( pirr.decode( response.getTagValue("*//data") ) );  // Konvertera receptet tillbaka till läsbar text
//
//        System.out.println(pirrRecept.getTagValue("*//Description") + " - patient: " + patient_pnr);
//
//        String retCode = pirrRecept.getTagValue("*//Description").substring(0, 1);
//        if (!retCode.equals("0")) {
//            System.out.println("Stoppar på grund av fel! ");
//            System.out.println(pirrRecept.getXML());
//            exit(0);
//        }
//        System.out.println("PrescriptionSetId: " + pirrRecept.getTagValue("*//PrescriptionSetId"));
//        System.out.println("OrdinationsId: " + BaseXML.getOrdinationsId(pirrRecept.getTagValue("*//PrescriptionSetId"), "PTRR"));
//        System.out.println("Är det en DOS patient: " + BaseXML.isDosPatient(patient_pnr, "PTRR" ) + "\n");
//        String prescriptionSetId = pirrRecept.getTagValue("*//PrescriptionSetId");
//        ordId = BaseXML.getOrdinationsId(prescriptionSetId, "PTRR");
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------        
// Extra anrop för att få ett dublett AFF-fel
// -----------------------------------------------------------------------------------------------------------------------------------------------------------        
//        System.out.println("\nDubblett\n");
//        Wsgeneric pirr2 = new Wsgeneric();
//        pirr2.setSoapEndpointUrl("https://pirr/pi-wsgeneric-1.3/wsgeneric");
//        pirr2.setDATA(pirr2.encode(recept.getXML())); // Signerar och Base64 encoda receptet
//        
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( pirr2.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString(pirr2.getXML()) ) ) );
//
//        SoapResponseXML pirrRecept2 = new SoapResponseXML( pirr2.decode( response.getTagValue("*//data") ) );  // Konvertera receptet tillbaka till läsbar text
//
//        System.out.println(pirrRecept2.getTagValue("*//Description") + " ( Läkare: " + LK_fname + " " + LK_ename + " kod: " + LK_kod + " Kategori: " + LK_kategori + " )");
//
//        retCode = pirrRecept2.getTagValue("*//Description").substring(0, 1);
//        if (!retCode.equals("0")) { 
//            System.out.println("Stoppar på grund av fel! "); 
//            System.out.println(pirrRecept2.getXML());
//            exit(0); 
//        }
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Cancelerar Ordination via Pirr --(217)---------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

// flyttar bakåt i tiden -------------------------------------------------------------------------------------------------------------------------------------
//        String receptId = ManipuleraReceptDatum.getEReceptid(ordId, "PTRR");
//        changeDateNyttRecept(antalDagar, patient_pnr, receptId, "PTRR" );
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        PrescriptionCancellation receptCancel = new PrescriptionCancellation();
//        receptCancel.setStandardDefaultValues();
//
//        receptCancel.setPersonnummer(patient_pnr);
//        receptCancel.setTodaysDates();
//        receptCancel.setTagValue("*//FirstGivenName", patient_fnamn);
//        receptCancel.setTagValue("*//FamilyName", patient_enamn);
//        receptCancel.setAllUUID();
//        receptCancel.setTagValue("*//MessageSender/HealthcareAgentInContextId/Value","CNR");
//        receptCancel.setTagValue("*//MessageSender/HealthcareAgent/HealthcareAgentId[1]/Value", arbetsplats_kod);
//        receptCancel.setTagValue("*//MessageSender/HealthcareAgent/HealthcareAgentId[2]/Value", LK_kod);
//        receptCancel.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/HealthcarePerson/Name", LK_ename + " " + LK_fname);
//
//        receptCancel.setTagValue("*//Cancellation/PrescriptionSetId",prescriptionSetId);
//        receptCancel.setTagValue("*//Cancellation/PrescriptionItemDetails/PrescriptionItemId","1");
//        receptCancel.setTagValue("*//Cancellation/PrescriptionItemDetails/Item/CauseCode","04");
//        receptCancel.setTagValue("*//Cancellation/PrescriptionItemDetails/Item/Cause","orsakskod 04");
//        receptCancel.setTagValue("*//Cancellation/PrescriptionItemDetails/Item/Consent/Value","1");
//        
//        PirrHandler = new Wsgeneric();
//        PirrHandler.setSoapEndpointUrl("https://pirr/pi-wsgeneric-1.3/wsgeneric");
//        PirrHandler.setSERVICE(Wsgeneric.MakuleraReceptService);       // Måste sättas för att makulering ska funka //
//        
//        PirrHandler.setDATA(PirrHandler.encode(receptCancel.getXML())); // Signerar och Base64 encoda receptet
//        
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( PirrHandler.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString(PirrHandler.getXML()) ) ) );
//
//        pirrRecept = new SoapResponseXML( PirrHandler.decode( response.getTagValue("*//data") ) );  // Konvertera receptet tillbaka till läsbar text
//
//        System.out.println("\n" + pirrRecept.getTagValue("*//Response") + "OrdinationsId: " + ordId + "\n");
//        retCode = pirrRecept.getTagValue("*//MessageStatus").substring(0, 1);
//        if (!retCode.equals("0")) { 
//            System.out.println("Stoppar på grund av fel! "); 
//            System.out.println(pirrRecept.getXML());
//            exit(0); 
//        }
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// UppdateraSamtycke -----------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//        ticket.setSoapEndpointUrl("http://TICGEN:17777/sambi/SambiTicketServlet");
//        ticket.setSoapEndpointUrl("http://192.168.37.128:17777/sambi/SambiTicketServlet");
//        ticket.setSoapEndpointUrl("http://localhost:17777/sambi/SambiTicketServlet");
//        ticket.setSoapEndpointUrl("http://localhost:64634/sambi/SambiTicketServlet");
//
//        UppdateraSamtycke us = new UppdateraSamtycke();
//        ticket.setHealthcareProfessionalLicense("AP");
//        ticket.setHealthcareProfessionalLicenseIdentityNumber(AP_kod);
//        ticket.setPharmacyIdentifier(GLN);
//        us.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
//        us.setStandardDefaultValues();
//        us.setPersonnummer(patient_pnr);
//        us.setSamtyckeEES(true);
//        us.setSamtyckeRR(true);
//        us.setSamtyckeRRD(true);
//        
////        us.logXML();
//        
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( us.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( us.getXML() ))));
//        
//        response.logXML();
//        us.checkResponse(response);
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HämtaGallandeUnderlagsversion -----------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaGallandeUnderlagsversion hgu = new HamtaGallandeUnderlagsversion();
//        ticket.setProfessionalLicence("AP");
//        ticket.setHealthcareProfessionalLicenseIdentityNumber(AP_kod);
//        ticket.setPharmacyIdentifier(GLN);
////        ticket.setLevelOfAssurance("http://id.sambi.se/loa/loa3");
////        ticket.setNameIdQualifier("http://www.ehalsomyndigheten.se");
////        ticket.setIssuer("http://www.ehalsomyndigheten.se/idp");
////        ticket.setAudience("http://www.ehalsomyndigheten.se");
////        ticket.setAudienceRestrictions("eHälsomyndigheten");
////        ticket.setNameId("Putte_999");
//        
//        
//        hgu.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
//        hgu.setStandardDefaultValues();
////        hgu.setSoapEndpointUrl("https://10.251.123.12:443/apisp/HamtaGallandeUnderlagsversionResponder/V5");
//        hgu.setSoapEndpointUrl( SERVICEENDPOINT + "/apisp/HamtaGallandeUnderlagsversionResponder/V5");
//
//        hgu.setTagValue("*//personnummer", patient_pnr);
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hgu.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hgu.getXML() ))));
//        
//        hgu.checkResponse(response);
//        
////        response.logXML();
//        
//        underlagsversion = response.getTagValue("*//underlagsversion");
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// SattUtOrdinationApotek ------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//    
//        if ( BaseXML.isDosPatient(patient_pnr, "PTRR") ) {
//            SattUtOrdinationApotek suoa = new SattUtOrdinationApotek();
//            suoa.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//            suoa.setStandardDefaultValues();
//            suoa.removeTag("*//utsattningstidpunkt");
//            suoa.setUnderlagsversion(underlagsversion);
//            suoa.setOrdinationsId(ordId);
//            suoa.setMomentanUtsattning(Boolean.TRUE); // För att det ska bli en 218
//            
//            suoa.setTagValue("*//forandrandeOrdinator/arbetsplatskod", arbetsplats_kod);
//            suoa.setTagValue("*//forandrandeOrdinator/fornamn", LK_fname);
//            suoa.setTagValue("*//forandrandeOrdinator/efternamn", LK_ename);
//            suoa.setTagValue("*//forandrandeOrdinator/forskrivarkod", LK_kod);
//            suoa.setTagValue("*//forandrandeOrdinator/legitimationskod", LK_kod);
//            suoa.setTagValue("*//forandrandeOrdinator/yrkeskod", LK_yrkeskod);
//            suoa.setTagValue("*//SattUtOrdinationApotek/fornamn", AP_fname);
//            suoa.setTagValue("*//SattUtOrdinationApotek/efternamn", AP_ename);
//            suoa.setTagValue("*//SattUtOrdinationApotek/anvandare", AP_kod);
//            suoa.setTagValue("*//SattUtOrdinationApotek/yrkeskod", AP_yrkeskod);
//            suoa.setTagValue("*//SattUtOrdinationApotek/befattningskod", AP_befattningskod);
//
//            response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( suoa.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( suoa.getXML() ))));
//
//            suoa.checkResponse(response);
//            
//        } else {
//            System.out.println("\nPatient: " + patient_pnr + " är inte doskund");
//        }
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// KorrigeraOrdinationApotek ---------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//        KorrigeraOrdinationApotek koa = new KorrigeraOrdinationApotek();
//        koa.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        koa.setStandardDefaultValues();
//        koa.setUnderlagsversion(underlagsversion);
//        koa.setOrdinationsId(ordId);
//        koa.setAntalForpackningar(NumberOfPackages);
//        koa.setTotalmangdKvar(NumberOfPackages);
//        koa.setResterandeAntalUttag("1");
//        koa.setNplId(nplId);
//        koa.setNplPackId(nplPackId);
//        koa.setForstaUttagFore(CurrentDateTime.getDate(+1) + "T00:00:00.000+02:00");
//        koa.setSistaGiltighetsdag(CurrentDateTime.getDate(+365) + "T00:00:00.000+02:00");
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/fornamn", AP_fname );
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/efternamn", AP_ename );
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/anvandare", AP_kod );
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/yrkeskod", AP_yrkeskod );
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/forandrandeOrdinator/arbetsplatskod","4000000000000");
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/forandrandeOrdinator/efternamn",LK_ename);
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/forandrandeOrdinator/fornamn",LK_fname);
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/forandrandeOrdinator/forskrivarkod",LK_kod);
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/forandrandeOrdinator/legitimationskod",AP_kod);
//        koa.setTagValue( "*//KorrigeraOrdinationApotek/forandrandeOrdinator/yrkeskod",LK_yrkeskod );
//
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( koa.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( koa.getXML() ))));
//
//        koa.checkResponse(response);
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaExpeditionsId ----------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

        HamtaExpeditionsId hei = new HamtaExpeditionsId();
        hei.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
        hei.setStandardDefaultValues();
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hei.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hei.getXML() ))));
        hei.checkResponse(response);

        expeditionsId = response.getTagValue("*//expeditionsId");

// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Godkänn Uttag ---------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        GodkannUttag gu = new GodkannUttag();
//        gu.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        gu.setStandardDefaultValues();
//        gu.setOrdinationsId(ordId);
//        gu.setPersonnummer(patient_pnr);
//        gu.setUnderlagsversion(underlagsversion);
//        gu.setExpeditionId(expeditionsId);
//        gu.setExpeditionsdatumNow();
//
//        gu.setTagValue("*//GodkannUttag/anvandare",GLN);
//        gu.setTagValue("*//dispenseratuttag/artikelinformation/nplId",nplId);
//        gu.setTagValue("*//helforpackningsuttag/artikelinformation/nplId",nplId);
//        gu.setTagValue("*//dispenseratuttag/artikelinformation/nplPackId",nplpackid);
//        gu.setTagValue("*//helforpackningsuttag/artikelinformation/nplPackId",nplpackid);
//        gu.setTagValue("*//helforpackningsuttag/expedieradMangd",antal_numeriskt);
//
//        gu.setTagValue("*//GodkannUttag/yrkeskod",AP_yrkeskod);
//        gu.setTagValue("*//GodkannUttag/efternamn",AP_ename);
//        gu.setTagValue("*//GodkannUttag/fornamn",AP_fname);
//        gu.setTagValue("*//GodkannUttag/befattningskod","1234"); // Kod från vården som inte kollas hos oss
//        
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( gu.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( gu.getXML() ))));
//        
//        gu.checkResponse(response);
//        ordId = response.getTagValue("*//ordinationsId");
//        
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaFolkInfo ---------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaFolkInfo fi = new HamtaFolkInfo();
//        fi.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        fi.setStandardDefaultValues();
//        fi.setPersonnummer(patient_pnr);
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( fi.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( fi.getXML() ))));
//
//        fi.checkResponse(response);
//        
//        System.out.println(response.getXML());
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// AnonymPrisfraga -------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        AnonymPrisfraga apf = new AnonymPrisfraga();
//        apf.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        apf.setStandardDefaultValues();
//        apf.setPersonnummer(patient_pnr);
//        apf.setTagValue("*//periodstart", CurrentDateTime.getTExtraLong()+ "+01:00");
//    
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( apf.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( apf.getXML() ))));
//    
//        apf.checkResponse(response);
//    
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// KontrolleraFormon -----------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        KontrolleraForman kf = new KontrolleraForman();
//        kf.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        kf.setStandardDefaultValues();
//        kf.setPersonnummer(patient_pnr);
//        kf.setTagValue("*//nplPackageId", nplpackid);
//        kf.setTagValue("*//varunr", varunr);
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( kf.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( kf.getXML() ))));
//
//        kf.checkResponse(response);
//    
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaIckeAktuellaOrdinationer ---------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaIckeAktuellaOrdinationer hiao = new HamtaIckeAktuellaOrdinationer();
//        hiao.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        hiao.setStandardDefaultValues();
//        hiao.setPersonnummer(patient_pnr);
//        
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hiao.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hiao.getXML() ))));
//        
//        hiao.checkResponse(response);
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaFullmakt ---------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaFullmakt hfm = new HamtaFullmakt();
//        hfm.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        hfm.setStandardDefaultValues();
//        hfm.setPersonnummer(patient_pnr);
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hfm.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hfm.getXML() ))));
//
//        hfm.checkResponse(response);
//    
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaFolkbokforingsinformation ----------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaFolkbokforingsinformation hf = new HamtaFolkbokforingsinformation();
//        hf.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        hf.setStandardDefaultValues();
//        hf.setPersonnummer(patient_pnr);
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hf.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hf.getXML() ))));
//
//        hf.checkResponse(response);
//        System.out.println(response.getXML());
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Skapa Ordination Apotek -----------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        SkapaOrdinationApotek soa = new SkapaOrdinationApotek();
//	  soa.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        soa.setStandardDefaultValues();
//        soa.setTagValue("*//patient/personnummer",patient_pnr);
//	  soa.setTagValue("*//SkapaOrdinationApotek/underlagsversion",response.getTagValue("*//underlagsversion"));
//	  soa.setTagValue("*//originalordinationsId",BaseXML.getUUID());
//	  response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( soa.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( soa.getXML() ))));
//		
//        soa.checkResponse(response);
//        ordId = response.getTagValue("*//skapatOrdinationsId");
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Hämta underlagsversion ------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        hgu = new HamtaGallandeUnderlagsversion();
//        hgu.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        hgu.setStandardDefaultValues();
//        hgu.setTagValue("*//personnummer", patient_pnr);
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hgu.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hgu.getXML() ))));
//        
//        hgu.checkResponse(response);
//        underlagsversion = response.getTagValue("*//underlagsversion");
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Hämta underlagsversion ------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaGallandeUnderlagsversion hgu = new HamtaGallandeUnderlagsversion();
//        hgu.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        hgu.setStandardDefaultValues();
//        hgu.setTagValue("*//personnummer", patient_pnr);
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hgu.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hgu.getXML() ))));
//        
//        hgu.checkResponse(response);
//        underlagsversion = response.getTagValue("*//underlagsversion");
//        
//        System.out.println(response.getXML());
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// MakuleraOrdinationApotek ----------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        MakuleraOrdinationApotek moa = new MakuleraOrdinationApotek();
//	      moa.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        moa.setStandardDefaultValues();
//        moa.setOrdinationsId(ordId);
//        moa.setUnderlagsversion(underlagsversion);
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( moa.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( moa.getXML() ))));
//
//        moa.checkResponse(response);
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaPatientInfo --------------- FORSKRIVARE --------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaPatientInfo hp = new HamtaPatientInfo();
//        ticket.setPersonalPrescriptionCode(LK_kod);
//        ticket.setHealthcareProfessionalLicense("LK");
//        hp.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
//        hp.setStandardDefaultValues();
//        hp.setPersonnummer(patient_pnr);
//    
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hp.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hp.getXML() ))));
//    
//        hp.checkResponse(response);
//        System.out.println(response.getXML());
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaKundInfo ---------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaKundInfo hki = new HamtaKundInfo();
//        hki.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        hki.setStandardDefaultValues();
//        hki.setPersonnummer(patient_pnr);
//    
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hki.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hki.getXML() ))));
//
//        hki.checkResponse(response);
//        System.out.println(response.getXML());
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// SkrivUtERecept ---209--------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        if ( !BaseXML.isDosPatient(pnr, "PTRR") ) {
//          SkrivUtERecept suer = new SkrivUtERecept();
//          suer.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//          suer.setStandardDefaultValues();
//          suer.setOrdinationsId(ordId);
//        
//          response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( suer.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( suer.getXML() ))));
//        
//          suer.checkResponse(response);
//        } else {
//            System.out.println("\nPatient: " + pnr + "är doskund och kan därför inte skriva ut receptet");
//        }
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaAktuellaOrdinationer ---------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaAktuellaOrdinationer hao = new HamtaAktuellaOrdinationer();
//        hao.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        hao.setStandardDefaultValues();
//        hao.setPersonnummer(patient_pnr);
//        hao.removeTag("*//ordinationsId");
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hao.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hao.getXML() ))));
//
//        hao.checkResponse(response);
//
//
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaOrdinationshistorik ----------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaOrdinationshistorik ho = new HamtaOrdinationshistorik();
//        ho.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        ho.setOrdinationsId(ordId);
//        
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( ho.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( ho.getXML() ))));
//        
//        ho.checkResponse(response);
//        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Prisfraga -------------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        Prisfraga pf = new Prisfraga();
//        pf.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        pf.setStandardDefaultValues();
//        pf.setPersonnummer(patient_pnr);
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( pf.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( pf.getXML() ))));
//
//        pf.checkResponse(response);
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaHkdbKonto --------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

        HamtaHkdbKonto hhk = new HamtaHkdbKonto();
        hhk.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
        hhk.setStandardDefaultValues();
        hhk.setPersonnummer(patient_pnr);

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hhk.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hhk.getXML() ))));

        hhk.checkResponse(response);

        ackBrutto = response.getTagValue("*//innevPeriod/*/brutto");
        periodStart = response.getTagValue("*//innevPeriod/start");
        System.out.println("ackBrutto: " + ackBrutto);
        System.out.println("periodStart: " + periodStart);

// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Hämta prisuppgifter från vara -----------------------------------------------------------------------------------------------------------------------------
// HämtaSubstitosion
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

        HamtaSubstitution hs = new HamtaSubstitution();
        hs.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
        hs.setStandardDefaultValues();
        hs.setForskrivningsDatum();
        hs.setNplPackageId(nplPackId);

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hs.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hs.getXML() ))));

        hs.checkResponse(response);

        pris_aup = response.getTagValue("*//utbytesgruppArtiklar[1]/aktuellPeriodsForsaljningspris[1]/aktuelltForsaljningsPris[1]");
        System.out.println("Priset: " + pris_aup);
//        assertEquals("Fel pris tillbaka på "+ nplPackId,pris_aup,"204.11");

// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// RegistreraHkdbTransaktion ---------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        RegistreraHkdbTransaktion rht = new RegistreraHkdbTransaktion();
//        rht.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        rht.setStandardDefaultValues();
//        rht.setPersonnummer(patient_pnr);
//
//        rht.setTagValue("*//expeditionsId", expeditionsId);
//        rht.setTagValue("*//ackBrutto", ackBrutto);
//        rht.setTagValue("*//bruttobelopp", pris_aup);
//        rht.setTagValue("*//periodStart", periodStart);
//        rht.setTagValue("*//transId", expeditionsId);
//        rht.setTagValue("*//ursprungligtApoteksaktorsExpeditionsId", expeditionsId);
//        rht.setTagValue("*//transTid", CurrentDateTime.getTodaysDate() + "T00:00:00.000+01:00");
//        rht.setTagValue("*//ursprungligTransTid", CurrentDateTime.getTodaysDate() + "T00:00:00.000+01:00");
//
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( rht.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( rht.getXML() ))));
//
//        rht.checkResponse(response);
//
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaHkdbInformation --------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//        HamtaHkdbInformation hhi = new HamtaHkdbInformation();
//        hhi.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//        hhi.setStandardDefaultValues();
//        hhi.setPersonnummer(patient_pnr);
//    
//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hhi.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hhi.getXML() ))));
//    
//        hhi.checkResponse(response);
//    
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// UppdateraForsaljningEgenvard ------------------------------------------------------------------------------------------------------------------------------
// UppdateraForsaljningSlutenvard ----------------------------------------------------------------------------------------------------------------------------
// UppdateraForsaljningOppenvardForskrivning -----------------------------------------------------------------------------------------------------------------
// FOTA trans ------------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

       UppdateraForsaljningOppenvardForskrivning ufof = new UppdateraForsaljningOppenvardForskrivning();
       ufof.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
       ufof.setStandardDefaultValues();
       ufof.setPersonnummer(patient_pnr);
       ufof.setTagValue("*//klientinformation/system", GLN);
       ufof.setTagValue("*//expoOrgNr", "5567634778");
       ufof.setTagValue("*//aktorExpeditionsId", expeditionsId);
       ufof.setTagValue("*//expeditionsId", expeditionsId);
       ufof.setTagValue("*//expoButiksId", expeditionsId);
       ufof.setTagValue("*//periodStartDatum", periodStart);
       ufof.setTagValue("*//avhamtadDatum", CurrentDateTime.getTodaysDate() + "T00:00:00.000+01:00");
       ufof.setTagValue("*//aktorTransId", expeditionsId);
       ufof.setTagValue("*//aktorReceptId", "1");
       ufof.setTagValue("*//aupExMomsAktor", pris_aup);
       ufof.setTagValue("*//formanExMoms", pris_aup);
       ufof.setTagValue("*//merkostnadExMoms", "0");
       ufof.setTagValue("*//nplPackid", nplPackId);
       ufof.setTagValue("*//receptId", expeditionsId);
       ufof.setTagValue("*//utfardarDatum", CurrentDateTime.getTodaysDate() + "T00:00:00.000+01:00");
       ufof.setTagValue("*//varuNr", varunr);

       response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( ufof.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( ufof.getXML() ))));

       System.out.println(response.getXML());

       ufof.checkResponse(response);

// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// RapporteraExpeditionLF ------ Ej om det är handelsvara ----------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//       RapporteraExpeditionLF relf = new RapporteraExpeditionLF();
//       relf.insertXmlAfterTag("<soapenv:Header>", Tickets.getTicket( Tickets.APOTEKARE ));
//       relf.setStandardDefaultValues();
//       relf.setPersonnummer(patient_pnr);
//       relf.setNamn(patient_fnamn,patient_enamn);
//       relf.setTagValue("*//aktorsExpeditionsid", expeditionsId);
//       relf.setTagValue("*//expeditionsid", expeditionsId);
//       relf.setTagValue("*//expeditionsdatum", CurrentDateTime.getTodaysDate() + "T00:00:00.000+01:00");
//       relf.setTagValue("*//nplPackid", nplpackid);
//
//       response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( relf.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( relf.getXML() ))));
//
//       relf.checkResponse(response);
//  
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// KorrigeraOrdinationApotek ----Extra tänk kring rrstatus 211------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------



// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Flytta recept tillbaka i tiden
//        String receptId = ManipuleraReceptDatum.getEReceptid(ordId, "PTRR");
//        System.out.println("\n\nFlytta i tiden\nordId: " + ordId + ",  ReceptId: " + receptId);
//
//        changeDateNyttRecept(antalDagar, patient_pnr, receptId, "PTRR" );
//        changeDateGodkannUttagPrepared(antalDagar, ordId, expeditionsId, "PTRR");
//        changeDateExpeditionLFPrepared(antalDagar, receptId, expeditionsId, "PTRR");
//        changeDateFotaPrepared(antalDagar, expeditionsId,"PTFOTA");
//
//
// Slut LOOP
//        }
    }
}






package labb;

import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.Prescription;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.Wsgeneric;

/**
 *
 * @author kundtest1
 */
public class Test_Pirr_Skapa_Recept {
    
    String SERVICEENDPOINT      = "https://prestanda";
    String PIRR_SERVICEENDPOINT = "https://PirrHandler";
    SoapResponseXML response;
    String ordId;

    
    // Patient 1 // MILJÖ TEST3
    String patient_pnr      = "191007259839";           // FOLK_PROD.SPAR_PERSON.PERSONNR  
    String patient_fnamn    = "Förnamn";                // FOLK_PROD.SPAR_PERSON.FORNAMN
    String patient_enamn    = "Efternamn";              // FOLK_PROD.SPAR_PERSON.EFTERNAMN
    String patient_adress   = "Testgatan55";            // FOLK_PROD.SPAR_ADRESS.GATUADRESS
    String patient_postnr   = "12345";                  // FOLK_PROD.SPAR_ADRESS.POSTNR
    String patient_ort      = "Postort";                // FOLK_PROD.SPAR_ADRESS.POSTORT
    String patient_sex      = "1";                      // Ta från personnummret :-)
    String patient_telnr    = "070-123456";             // Slumpa fram

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
    String AP_yrkeskod	 = "AP";                             // FORS_PROD.FORS_KATEGORI.KATEGORI
    String AP_befattningskod = "1234";

   // Läkare
//        String LK_fname         = "Lars";                     // FORS_PROD.FORS.NAMN
//        String LK_ename         = "Läkare";                   // FORS_PROD.FORS.NAMN
//        String LK_kod           = "9000027";                  // FORS_PROD.FORS.KOD + KOD_CHECK
    String LK_fname         = "Regina";                     // FORS_PROD.FORS.NAMN
    String LK_ename         = "Dahlberg";                      // FORS_PROD.FORS.NAMN
    String LK_kod           = "8880049";                    // FORS_PROD.FORS.KOD + KOD_CHECK

    String LK_yrkeskod      = "LK";                         // yrkeskod
    String LK_kategori      = "LAK";                        // utfärdarkategori

    // Skjukhus - vårinrättnig
    String arbetsplats_kod      =   "0900000000002";        // ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD
    String arbetsplats_namn     =   "Gotland Sjukhus 2";    // ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN
    String arbetsplats_adress   =   "Gotlandgatan 2";       // ARKO_PROD.ARBETSPLATS.POSTADRESS
    String arbetsplats_ort      =   "Gotland";              // ARKO_PROD.ARBETSPLATS.POSTORT
    String arbetsplats_postnr   =   "90002";                // ARKO_PROD.ARBETSPLATS.POSTNUMMER
    String arbetsplats_telnr1   =   "0498-1000002";         // ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1

    @Test
    public void rrStatus202() {
        /* LOOP */
        for (int i=0;i<1;i++) {
//            try {
//                long start = System.currentTimeMillis();
//                Thread.sleep(500);
//                System.out.println("Waited in "+(System.currentTimeMillis()-start)+ " ms\n");
//            } catch (InterruptedException ex) { System.out.println(ex); }
            System.out.println("\nIteration: " + i + " startad");
            // -----------------------------------------------------------------------------------------------------------------------------------------------------------
            // Skapa Ordination via Pirr ---------------------------------------------------------------------------------------------------------------------------------
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
            recept.setNplPackId(nplPackId);
            recept.setInstructionsForUse("Så här ska du ta din medusin");
            recept.setAntalUttag(antalUttag);
            recept.setNumberOfPackages(NumberOfPackages);

            recept.setXML(recept.getXML());

            // -----------------------------------------------------------------------------------------------------------------------------------------------------------        
            //  Sänder in receptet med WsGeneric ...
            // -----------------------------------------------------------------------------------------------------------------------------------------------------------      

            Wsgeneric pirr = new Wsgeneric();

            pirr.setSoapEndpointUrl( PIRR_SERVICEENDPOINT + "/apisp-pi-wsgeneric-1.3/wsgeneric");
            pirr.setDATA(pirr.encode(recept.getXML())); // Signerar och Base64 encoda receptet

            Tickets ticket = new Tickets();
            ticket.setHealthcareProfessionalLicense("LK");
            ticket.setPersonalPrescriptionCode(LK_kod);

            pirr.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());


            response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( pirr.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString(pirr.getXML()) ) ) );

            SoapResponseXML pirrRecept = null;  // Konvertera receptet tillbaka till läsbar text
            try {
                pirrRecept = new SoapResponseXML( pirr.decode( response.getTagValue("*//data") ) );
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println(pirrRecept.getTagValue("*//Description") + " - patient: " + patient_pnr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String retCode = null;
            try {
                retCode = pirrRecept.getTagValue("*//Description").substring(0, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!retCode.equals("0")) { 
                System.out.println("Stoppar på grund av fel! "); 
                System.out.println(pirrRecept.getXML());
                exit(0); 
            }
            String prescriptionSetId = null;
            try {
                prescriptionSetId = pirrRecept.getTagValue("*//PrescriptionSetId");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("PrescriptionSetId: " + prescriptionSetId);
//            System.out.println("OrdinationsId: " + BaseXML.getOrdinationsId(pirrRecept.getTagValue("*//PrescriptionSetId"), "PTRR"));
//            System.out.println("Är det en DOS patient: " + BaseXML.isDosPatient(patient_pnr, "PTRR" ) + "\n");
//            ordId = BaseXML.getOrdinationsId(prescriptionSetId, "PTRR");

            // -----------------------------------------------------------------------------------------------------------------------------------------------------------        
            System.out.println();
        } /* SLUT LOOP */
    }
    


    public Test_Pirr_Skapa_Recept() { }
    @BeforeClass
    public static void setUpClass() { }
    @AfterClass
    public static void tearDownClass() { }
    @Before
    public void setUp() { }
    @After
    public void tearDown() { }
    
}

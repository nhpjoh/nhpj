package NLL_migration;

import org.junit.Test;
import se.nhpj.pirr.PirrHandler;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Implementation;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.Prescription;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.Wsgeneric;
import se.nhpj.testdata.Transaction;

import javax.xml.soap.SOAPMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.exit;

public class test_PIRR_fromFile {
    /* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        Peka ut var listan/biblioteket med filer finns
        Skapa - Lista med filnamn, både pirrInFiler och AperakFiler
        Stega igenom listan
            hämta PIRR in_fil
            hämta motsvarande Aperak fil

            Exekvera PIRR fil

            Jämför StatusCode med Aperakfilens StatusCode
                raportera om de inte överensstämmer annars OK
        Nästa FIL i listan
     --------------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    String SERVICEENDPOINT_PIRR = "http://test29:10080";
//    String SERVICEENDPOINT_PIRR = "https://nll-transformator-pirr-sp-s17-test1-deploy2.test.ecp.systest.receptpartner.se/nll-transformator-pirr-sp";


    @Test
    public void test_Pirr_From_File() {
        PirrHandler pirrHandler = new PirrHandler();

        int rCnt = 7;
        String receptDir = "C:\\temp\\PIRR_RECEPT\\Prescription\\2019\\";
        String aperakDir = "C:\\temp\\PIRR_RECEPT\\Prescription_Aperak\\";
        pirrHandler.setServiceEndpoint(SERVICEENDPOINT_PIRR);

        ArrayList<String> pirrFiler =   pirrHandler.getFileListFromDirectory(receptDir); // 17 filer
        ArrayList<String> aperakFiler = pirrHandler.getFileListFromDirectory(aperakDir); // 320020 filer

        System.out.println(pirrFiler.size());

        // Loopa igenom alla filer i inKatalogen

        System.out.println( receptDir + pirrFiler.get(rCnt));

        // Hitta Sessionsnummret i PIRR filen
        String startSträng = "Prescription_"; // Det som står före sessionsNummret i filnamnet
        int startPos = pirrFiler.get(rCnt).indexOf(startSträng)+startSträng.length();
        int endPos = startPos+12;
        String sessionNumber = pirrFiler.get(rCnt).substring(startPos,endPos);

        // Hitta Aperakfilen till sessionsnummret
        String aperakFilen = pirrHandler.getLineFromList(aperakFiler,sessionNumber);
        System.out.println(aperakDir + aperakFilen);
        System.out.println(sessionNumber);

        // Pirranropet
        SoapResponseXML response = pirrHandler.fromFile(receptDir + pirrFiler.get(rCnt), true);

        // Första kontroll av svaret -- Inte dubbelkollat ännu
        if ( !response.getTagValue("*//message").contains("ccepterat") ) {
            System.out.println("\n71Code: " + response.getTagValue("*//code") + "\nMessage: " + response.getTagValue("*//message")+"\n");
            if (response.getTagValue("*//code").contains("200")) {
                System.out.println("\n73" + se.nhpj.rest.Base64Converter.decodeString(se.nhpj.rest.Base64Converter.decodeString(response.getTagValue("*//data"))));
            }
            else System.out.println("\n75" + response.decode( response.getTagValue("*//data")));
            exit(1);
        }

        // Hämtar upp datat i pirr svaret
        SoapResponseXML pirrRecept = new SoapResponseXML( response.decode( response.getTagValue("*//data") ) );  // Konvertera receptet tillbaka till läsbar text

        // Hämtar upp Statuskoden ifrån svaret
        String svar = pirrRecept.getTagValue("*//ApplicationAcknowledgeMessage/StatusInformation/StatusCode");

        // Hämtar upp StatusKoden från original aperakFilen
        Prescription aperak = new Prescription();
        String aperakXML = pirrHandler.fileToString(aperakDir + aperakFilen );
        aperak.setXML(aperakXML);
        String org =  aperak.getTagValue("*//ApplicationAcknowledgeMessage/StatusInformation/StatusCode");

        // Skriver ut Description från svaret (nya Aperaken)
        System.out.println("\n92 Svaret: " + pirrRecept.getTagValue("*//Description"));

        String retCode = pirrRecept.getTagValue("*//Description").substring(0, 1);
        if (!retCode.equals("0")) {
            if (svar.equalsIgnoreCase(org)) System.out.println("LIKA :-), StatusCode svar: " + svar + " Aperak: " + org);
            else {
                System.out.println("98 ERROR -> OLIKA :-(, StatusCode svar: " + svar + " Aperak: " + org);
                String anropXML = pirrHandler.fileToString(receptDir + pirrFiler.get(rCnt) );
                System.out.println("\nAnropet:\n" + anropXML);
                System.out.println("\nSvaret:\n" + pirrRecept.getXML());
                System.out.println("\nOriginalAkeraken:\n" + aperak.getXML());
                exit(1);
            }
        } else {
            System.out.println("102 PrescriptionSetId: " + pirrRecept.getTagValue("*//PrescriptionSetId"));
        }
    }





    /* -------------- Tester ------------------------------------------------------------------------------------------------------------------------------------------------- */

    @Test
    public void test_fileToString() throws FileNotFoundException {
        PirrHandler pirrHandler = new PirrHandler();
        System.out.println(pirrHandler.fileToString("C:\\temp\\PIRR_RECEPT\\Prescription\\2019\\2019-01-03T14-27_20170824_COSMIC_Prescription_p16813e599ff_001.xml"));
    }

    @Test
    public void test_ListFiles() {
        PirrHandler pirrHandler = new PirrHandler();
        ArrayList<String> listan = pirrHandler.getFileListFromDirectory("C:/temp/PIRR_RECEPT/Prescription");

        for (String rad : listan) {
            System.out.println(rad);
        }
    }

    @Test
    public void test_hitta_listRad_via_del_av_namn() throws FileNotFoundException{
        PirrHandler pirrHandler = new PirrHandler();

        ArrayList<String> listan = pirrHandler.getFileListFromDirectory("C:/temp/PIRR_RECEPT/Prescription");

        String delAvNamn = "p1607df4f8df";
        for ( String rad : listan) {
            if ( rad.contains(delAvNamn) ) System.out.println(rad);
        }

        System.out.println(pirrHandler.getLineFromList(listan,"p1607df4f312"));
    }

    @Test
    public void test_ta_fram_statusCode_från_AperakFil() throws FileNotFoundException {  // funkar
        PirrHandler pirrHandler = new PirrHandler();
        String aperakFil = "C:\\temp\\PIRR_RECEPT\\Prescription_Aperak\\2019-01-03T14-27_COSMIC_Prescription_Aperak_p16813e599ff_001.xml";

        String aperakXMLfil = null;

        aperakXMLfil = pirrHandler.fileToString(aperakFil);

        Prescription recept = new Prescription();
        recept.setXML(aperakXMLfil);

        System.out.println(recept.getTagValue("*//StatusInformation/StatusCode"));
    }




    /* ----- Gamla --------- Tester -------------------------------------------------------------------------------------------------------------------------------------- */

    @Test
    public void test_ListFiles2() {
        File directory = new File("C:/temp/PIRR_RECEPT/Prescription");
        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }

    @Test
    public void test_PIRR_from_file2() {
        String pathname = "C:/temp/PIRR_RECEPT/Prescription/2019";
        PirrHandler pirrHandler = new PirrHandler();

        File directory = new File(pathname);
        Transaction transaction = new Transaction();

        File[] files = directory.listFiles();

        System.out.println("Start : ");
        transaction.start("alla");
        int antal=1;
        for (File file : files) {
            if (file.isFile()) {
                String fullFileName = pathname + "/" + file.getName();
                System.out.println();
                System.out.println((antal++) + " : " + fullFileName);
                if ( !file.getName().startsWith("x",0) ) {
                    pirrHandler.fromFile( fullFileName , false );
                } else System.out.println("Bortkommernterad");
            }
        }
        transaction.stop("alla");
        System.out.println("\n\nEnd total response-time: " + transaction.getElapsedTime("alla"));
    }

    @Test
    public void test_hitta_fil_via_del_av_namn2() throws FileNotFoundException{
        PirrHandler pirrHandler = new PirrHandler();

        String delAvNamn = "p16813e599ff";

        String pathname = "C:/temp/PIRR_RECEPT/Prescription_Aperak";

        File directory = new File(pathname);

        File[] files = directory.listFiles();

        int antal=1;
        for (File file : files) {
            if (file.isFile()) {
                String fullFileName = pathname + "/" + file.getName();
                if ( file.getName().contains(delAvNamn) ) {
                    System.out.println( fullFileName );
                    BaseXML xml = new Implementation();
                    xml.setXML(pirrHandler.fileToString(fullFileName));
                    System.out.println(xml.getTagValue("*//StatusInformation/StatusCode"));
                }
            }
        }
    }

    // Skjukhus - vårinrättnig
    String arbetsplats_kod      = "4000000000000";          // ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD
    String arbetsplats_namn     = "Sjukhus ett";            // ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN
    String arbetsplats_adress   = "Gatan 1";                // ARKO_PROD.ARBETSPLATS.POSTADRESS
    String arbetsplats_ort      = "Staden 1";               // ARKO_PROD.ARBETSPLATS.POSTORT
    String arbetsplats_postnr   = "11111";                  // ARKO_PROD.ARBETSPLATS.POSTNUMMER
    String arbetsplats_telnr1   = "08-1000000";             // ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1

    // Läkare
    String LK_fname             = "ADAM";
    String LK_ename             = "BERTILSSON";
    String LK_kod               = "8880999";
    String LK_kategori          = "LAK";                      // utfärdarkategori

    // Patient
    String patient_pnr          = "198810032386";
    String patient_fnamn        = "Allie";
    String patient_enamn        = "Westergren";
    String patient_adress       = "Testgatan 47337";          // FOLK_PROD.SPAR_ADRESS.GATUADRESS
    String patient_postnr       = "47337";                    // FOLK_PROD.SPAR_ADRESS.POSTNR
    String patient_ort          = "Testorten";                // FOLK_PROD.SPAR_ADRESS.POSTORT

    public void PIRR_from_file_update(String filNamn) throws FileNotFoundException, UnsupportedEncodingException {
//        String filNamn = "c:/temp/2019-08-20T10-57_198810032386_nhpj_Prescription_p16cae3e9541_001.xml";
        SoapResponseXML response;
        PirrHandler pirrHandler = new PirrHandler();

        Prescription recept = new Prescription();
        recept.setXML( pirrHandler.fileToString(filNamn) );
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
        recept.setTagValue("*//PatientMatchingInfo/Sex",Integer.toString(3));
        recept.setTagValue("*//FirstGivenName", patient_fnamn);
        recept.setTagValue("*//FamilyName", patient_enamn);
        recept.setPersonnummer(patient_pnr);

        String lakare = null;
        try {
            lakare = recept.getTagValue("*//Prescriber/HealthcareAgent/HealthcareAgentId[2]/Value");
        } catch (Exception e) {
            e.printStackTrace();
        }


// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------- Sender in Receptet --------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
        String serviceendpoint = SERVICEENDPOINT_PIRR + "/apisp-pi-wsgeneric-1.3/wsgeneric";

        Tickets ticket  = new Tickets();
        Wsgeneric pirr = new Wsgeneric();
        pirr.setSoapEndpointUrl(serviceendpoint + "");

        pirr.setDATA(pirr.encode(recept.getXML())); // Signerar och Base64 encoda receptet

        ticket.setHealthcareProfessionalLicense("LK");
        ticket.setPersonalPrescriptionCode(lakare);

        pirr.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString(pirr.getXML());

//        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( PirrHandler.getSoapEndpointUrl(), soapmessage ) ) );
//
//
//
//        if ( !response.getTagValue("*//message").contains("ccepterat") ) {
//            System.out.println("\nCode: " + response.getTagValue("*//code") + " \nMessage: " + response.getTagValue("*//message")+"\n");
//            if (response.getTagValue("*//code").contains("200")) {
//                System.out.println("\n" + se.nhpj.rest.Base64Converter.decodeString(se.nhpj.rest.Base64Converter.decodeString(response.getTagValue("*//data"))));
//            }
//            else System.out.println("\n" + PirrHandler.decode( response.getTagValue("*//data")));
//            exit(1);
//        }
//        SoapResponseXML pirrRecept = new SoapResponseXML( PirrHandler.decode( response.getTagValue("*//data") ) );  // Konvertera receptet tillbaka till läsbar text
//
//        System.out.println("\nSkapat: " + pirrRecept.getTagValue("*//Description"));
//
//        String retCode = pirrRecept.getTagValue("*//Description").substring(0, 1);
//        if (!retCode.equals("0")) {
//            System.out.println("FEL: Stoppar på grund av fel! ");
//            System.out.println("\n" + pirrRecept.getXML());
//            System.exit(0);
//        }
//
//        System.out.println("PrescriptionSetId: " + pirrRecept.getTagValue("*//PrescriptionSetId"));
//
    }


} // End Of Class



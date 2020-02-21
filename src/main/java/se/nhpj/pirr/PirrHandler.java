package se.nhpj.pirr;

import se.ehm.testdata.Arbetsplats;
import se.ehm.testdata.Forskrivare;
import se.ehm.testdata.Lakemedel;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.Prescription;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.Wsgeneric;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Transaction;

import javax.xml.soap.SOAPMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.exit;

public class PirrHandler {

    private String SERVICEENDPOINT_PIRR = "http://test29:10080";
    private SoapResponseXML response;

    public void setServiceEndpoint(String serviceEndpoint) { SERVICEENDPOINT_PIRR = serviceEndpoint; }
    public String getServiceEndpoint() { return SERVICEENDPOINT_PIRR; }

    public SoapResponseXML add_pirr(Person person, Forskrivare forskrivare, Arbetsplats arbetsplats, Lakemedel lakemedel, String serviceendpoint) throws UnsupportedEncodingException {

//        String serviceendpoint = "https://pirr";;
        Boolean check;
        String transaktion;

        Tickets ticket 	        = new Tickets();

        // Skjukhus - vårinrättnig
        String arbetsplats_kod      =   arbetsplats.getKod();          // ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD
        String arbetsplats_namn     =   arbetsplats.getNamn();         // ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN
        String arbetsplats_adress   =   arbetsplats.getPostAdress();   // ARKO_PROD.ARBETSPLATS.POSTADRESS
        String arbetsplats_ort      =   arbetsplats.getPostOrt();      // ARKO_PROD.ARBETSPLATS.POSTORT
        String arbetsplats_postnr   =   arbetsplats.getPostNummer();   // ARKO_PROD.ARBETSPLATS.POSTNUMMER
        String arbetsplats_telnr1   =   arbetsplats.getTelefon1();     // ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1

        // Läkare
        String LK_fname         = forskrivare.getFornamn();
        String LK_ename         = forskrivare.getEfternamn();
        String LK_kod           = forskrivare.getForskrivarkod();
        String LK_kategori      = "LAK";                          // utfärdarkategori

        // Patient
        String patient_pnr      = person.getPersonnummer();             //"199108122384";                   // 199108122384 // 189002289800
        String patient_fnamn    = person.getFornamn();                  //"Jörgen";                       // FOLK_PROD.SPAR_PERSON.FORNAMN
        String patient_enamn    = person.getEfternamn();                //"Österling";                    // FOLK_PROD.SPAR_PERSON.EFTERNAMN
        String patient_adress   = person.getAdress().getGatuAdress();   //"Testgatan 47337";              // FOLK_PROD.SPAR_ADRESS.GATUADRESS
        String patient_postnr   = person.getAdress().getPostnummer();   //"47337";                        // FOLK_PROD.SPAR_ADRESS.POSTNR
        String patient_ort      = person.getAdress().getOrt();          //"Testorten";                    // FOLK_PROD.SPAR_ADRESS.POSTORT

        Integer sex = null;
        if(person.isMan()) {sex = 1;}
        else if (person.isKvinna()) { sex = 2; }
        else { sex=3;}
        String patient_sex      = sex.toString();                            // Ta från personnummret :-)

        // Läkemedel
        String antalUttag       = lakemedel.getAntalUttag();       //"13";
        String NumberOfPackages = lakemedel.getNumberOfPackages(); //"13";
        String nplId            = lakemedel.getNPLID();            //"20101029000041";               // 20160416000026
        String nplpackid        = lakemedel.getNPLPACKID();        //"20130926100165";               // 20161130100023

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
        pirr.setSoapEndpointUrl(serviceendpoint + "");
        System.out.println("(PirrHandler.java) ServiceEndpoint: " + pirr.getSoapEndpointUrl());

        pirr.setDATA(pirr.encode(recept.getXML())); // Signerar och Base64 encoda receptet

        ticket.setHealthcareProfessionalLicense("LK");
        ticket.setPersonalPrescriptionCode(LK_kod);

        pirr.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString(pirr.getXML());

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( pirr.getSoapEndpointUrl(), soapmessage ) ) );

        return response;
    }

    public SoapResponseXML add_pirrFoddat(Person person, Forskrivare forskrivare, Arbetsplats arbetsplats, String mottagandeApotek, Lakemedel lakemedel ,String serviceendpoint) throws UnsupportedEncodingException {

//        String serviceendpoint = "https://pirr";;
        Boolean check;
        String transaktion;

        Tickets ticket 	        = new Tickets();

        // Skjukhus - vårinrättnig
        String arbetsplats_kod      =   arbetsplats.getKod();          // ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD
        String arbetsplats_namn     =   arbetsplats.getNamn();         // ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN
        String arbetsplats_adress   =   arbetsplats.getPostAdress();   // ARKO_PROD.ARBETSPLATS.POSTADRESS
        String arbetsplats_ort      =   arbetsplats.getPostOrt();      // ARKO_PROD.ARBETSPLATS.POSTORT
        String arbetsplats_postnr   =   arbetsplats.getPostNummer();   // ARKO_PROD.ARBETSPLATS.POSTNUMMER
        String arbetsplats_telnr1   =   arbetsplats.getTelefon1();     // ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1

        // Läkare
        String LK_fname         = forskrivare.getFornamn();
        String LK_ename         = forskrivare.getEfternamn();
        String LK_kod           = forskrivare.getForskrivarkod();
        String LK_kategori      = "LAK";                          // utfärdarkategori

        // Patient
        String patient_pnr      = person.getPersonnummer();             //"199108122384";                   // 199108122384 // 189002289800
        String patient_foddat   = person.getFodelseDatum();             //"199108122384";                   // 199108122384 // 189002289800
        String patient_fnamn    = person.getFornamn();                  //"Jörgen";                       // FOLK_PROD.SPAR_PERSON.FORNAMN
        String patient_enamn    = person.getEfternamn();                //"Österling";                    // FOLK_PROD.SPAR_PERSON.EFTERNAMN
        String patient_adress   = person.getAdress().getGatuAdress();   //"Testgatan 47337";              // FOLK_PROD.SPAR_ADRESS.GATUADRESS
        String patient_postnr   = person.getAdress().getPostnummer();   //"47337";                        // FOLK_PROD.SPAR_ADRESS.POSTNR
        String patient_ort      = person.getAdress().getOrt();          //"Testorten";                    // FOLK_PROD.SPAR_ADRESS.POSTORT

        Integer sex = null;
        if(person.isMan()) {sex = 1;}
        else if (person.isKvinna()) { sex = 2; }
        else { sex=3;}
        String patient_sex      = sex.toString();                            // Ta från personnummret :-)

        // Läkemedel
        String antalUttag       = lakemedel.getAntalUttag();        //"13";
        String NumberOfPackages = lakemedel.getNumberOfPackages();  //"13";
        String nplId            = lakemedel.getNPLID();             //"20101029000041";               // 20160416000026
        String nplpackid        = lakemedel.getNPLPACKID();         //"20130926100165";               // 20161130100023

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
//        recept.setPersonnummer(patient_pnr);

        // Födelsedatum tricket
        recept.setTagValue("*//PatientMatchingInfo/PatientId/IdScheme","FDA");
        recept.setTagValue("*//PatientMatchingInfo/PatientId/IdValue",patient_foddat);
        recept.setTagValue("*//DesignatedMessageReceiver/HealthcareAgent/HealthcareAgentId/IdScheme","EAN");
        recept.setTagValue("*//DesignatedMessageReceiver/HealthcareAgent/HealthcareAgentId/Value",mottagandeApotek);


        // Läkemedel
        recept.setNplId(nplId);
        recept.setNplPackId(nplpackid);
        recept.setInstructionsForUse("Så här borde du ta din medusin :-)");
        recept.setAntalUttag(antalUttag);
        recept.setNumberOfPackages(NumberOfPackages);

        recept.setXML(recept.getXML());

//        System.out.println(recept.getXML());

// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------- Sender in Receptet --------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

        Wsgeneric pirr = new Wsgeneric();
        pirr.setSoapEndpointUrl(serviceendpoint + "");
        System.out.println("ServiceEndpoint: " + pirr.getSoapEndpointUrl());

        pirr.setDATA(pirr.encode(recept.getXML())); // Signerar och Base64 encoda receptet

        ticket.setHealthcareProfessionalLicense("LK");
        ticket.setPersonalPrescriptionCode(LK_kod);

        pirr.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString(pirr.getXML());

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( pirr.getSoapEndpointUrl(), soapmessage ) ) );

        return response;
    }

    public void pirr_check(SoapResponseXML response)  throws UnsupportedEncodingException {
        Wsgeneric wsgeneric = new Wsgeneric();
        try {
            if ( !response.getTagValue("*//message").contains("ccepterat") ) {
                System.out.println("235: \nCode: " + response.getTagValue("*//code") + " \nMessage: " + response.getTagValue("*//message")+"\n");
                if (response.getTagValue("*//code").contains("200")) {
                    System.out.println("237: \n" + se.nhpj.rest.Base64Converter.decodeString(se.nhpj.rest.Base64Converter.decodeString(response.getTagValue("*//data"))));
                }
                else System.out.println("239: \n" + wsgeneric.decode( response.getTagValue("*//data")));
                exit(1);
            }
            SoapResponseXML pirrRecept = new SoapResponseXML( wsgeneric.decode( response.getTagValue("*//data") ) );  // Konvertera receptet tillbaka till läsbar text

            System.out.println("\nSkapat: " + pirrRecept.getTagValue("*//Description"));

            String retCode = pirrRecept.getTagValue("*//Description").substring(0, 1);
            if (!retCode.equals("0")) {
                System.out.println("FEL: Stoppar på grund av fel! ");
                System.out.println("\n" + pirrRecept.getXML());
                System.exit(0);
            }

            System.out.println("PrescriptionSetId: " + pirrRecept.getTagValue("*//PrescriptionSetId"));

        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "SkapaOrdinationsAnsvarigEnhetDosaktor - checkResponse", ex);
        }
    }



    public SoapResponseXML fromFile(String filename, Boolean setTodayDate) {
        Transaction transaction = new Transaction();

        Prescription recept = new Prescription();
        recept.setXML( fileToString(filename) );
        String lakare = null;
        try {
            lakare = recept.getTagValue("*//Prescriber/HealthcareAgent/HealthcareAgentId[2]/Value");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (setTodayDate)
            recept.setTodaysDates();

        System.out.println("Arbetsplats: " + recept.getTagValue("*//NewPrescriptionMessage/PrescriptionMessage/MessageSender/HealthcareAgent/HealthcareAgentId[1]/Value"));
        System.out.println("Apotek: " + recept.getTagValue("*//NewPrescriptionMessage/PrescriptionMessage/DesignatedMessageReceiver/HealthcareAgent/HealthcareAgentId/Value"));
        System.out.println("Förskrivarkod: " + recept.getTagValue("*//NewPrescriptionMessage/PrescriptionMessage/Prescriber/HealthcareAgent/HealthcareAgentId[2]/Value"));

        String serviceendpoint = SERVICEENDPOINT_PIRR + "/apisp-pi-wsgeneric-1.3/wsgeneric";

        Tickets ticket  = new Tickets();
        Wsgeneric pirr = new Wsgeneric();
        pirr.setSoapEndpointUrl(serviceendpoint + "");

        System.out.println("SERVICEENDPOINT: " + pirr.getSoapEndpointUrl());

        pirr.setDATA(pirr.encode(recept.getXML())); // Signerar och Base64 encoda receptet

        ticket.setHealthcareProfessionalLicense("LK");
        ticket.setPersonalPrescriptionCode(lakare);

        pirr.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString(pirr.getXML());

        transaction.start("PIRR_from_File");
            response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendPirrSoapRequest( pirr.getSoapEndpointUrl(), soapmessage ) ) );
        transaction.stop("PIRR_from_File");
        transaction=null;

        return response;
    }

    public String fileToString( String filename){
        String xml = null;
        try {
            StringBuilder result = new StringBuilder();
            File initialFile = new File(filename);
            InputStream inStream = null;
                inStream = new FileInputStream(initialFile);
            for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
            {
                String line = sc.nextLine();
                result.append(line);
            }
            xml = result.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public String getLineFromList(List<String> lista, String delAvString ) {
        String listRaden = null;
        for ( String rad : lista) {
            if ( rad.contains(delAvString) ) {
                listRaden = rad;
            }
        }
        return listRaden;
    }

    public ArrayList getFileListFromDirectory( String directory ) {
        ArrayList<String> listan = new ArrayList<>();
        File dir = new File(directory);
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                listan.add(file.getName());
            }
        }
        return listan;
    }

}

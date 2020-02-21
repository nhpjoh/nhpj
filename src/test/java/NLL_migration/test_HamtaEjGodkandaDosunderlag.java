package NLL_migration;

import org.junit.Test;
import se.ehm.testdata.FOLK.SparPerson;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.*;
import se.nhpj.soap.utils.XmlFormatter;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;
import se.nhpj.testdata.Transaction;

import javax.xml.soap.SOAPMessage;

public class test_HamtaEjGodkandaDosunderlag {

    String SERVICEENDPOINT_STP = "http://test28:10080/apisp"; // ej OR tjänster

//    String SERVICEENDPOINT = "http://test28:10080/apisp";
    String SERVICEENDPOINT = "https://nll-transformator-api-sp-s17-test1-deploy2.test.ecp.systest.receptpartner.se/nll-transformator-wsp";

    SoapResponseXML response;









    @Test
    public void test_HamtaEjGodkandaDosunderlag() {
        Transaction transaction = new Transaction();
        HamtaEjGodkandaDosunderlag hegd = new HamtaEjGodkandaDosunderlag();

        hegd.setSoapEndpointUrl(SERVICEENDPOINT + "/HamtaEjGodkandaDosunderlagResponder/V4");
        System.out.println("Endpoint: " + hegd.getSoapEndpointUrl());

        // Skapa en ticket
        Tickets ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511997");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");

        // Lägger till en ticket till anropet
        hegd.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken fil?: "+hegd.getStandardDefaultFileName());
        hegd.setStandardDefaultValues();
        hegd.setApoteksid("7350045511997");

        transaction.start("HamtaEjGodkandaDosunderlag");
            response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hegd.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hegd.getXML() ))));
        transaction.stop("HamtaEjGodkandaDosunderlag");

        Integer antalDosunderlag = response.getTagCount("*//dosunderlagslista");
        if(antalDosunderlag == 0 ) {
            System.out.println("Felaktigt svar: \n");
            response.logXML();
        }

        System.out.println( "\nSvarstid: " + transaction.getElapsedTime("HamtaEjGodkandaDosunderlag") + " som hämtat " + antalDosunderlag );

        System.out.println(response.getXML().length() + " bytes");

    }
















// Bygga förutsättningar för HEGD //
    @Test
    public void runPreReq() {
        for (int i=0 ; i < 0 ; i++ ){
            PreReq();
            System.out.println();
            System.out.println((i+1) + " : done!");
            System.out.println();
        }
    }
















    public void PreReq() {
//        Skatteverket skatteverket = new Skatteverket();
//        Person person = skatteverket.getPerson("189008179807");

        SparPerson sparperson = new SparPerson();
        sparperson.addRNDPerson("INT28");
        System.out.println(sparperson);

        Person person = new Person();
        person.setPersonnummer(sparperson.getPersonnr());

        response = UppdateraSamtycke(person);
        response = SkapaHkdbkonto(person);

        String underlagsversion =    HamtaGallandeUnderlagsversion( person.getPersonnummer() ).getTagValue("*//underlagsversion");
        if ( HamtaGallandeUnderlagsversion( person.getPersonnummer() ).getTagCount("*//dosunderlagsstatus") > 0 ) {
            String dosunderlagsversion = HamtaGallandeUnderlagsversion(person.getPersonnummer()).getTagValue("*//dosunderlagsversion");
            String dosunderlagsstatus = HamtaGallandeUnderlagsversion(person.getPersonnummer()).getTagValue("*//dosunderlagsstatus");
        } else { System.out.println("HamtaGallandeUnderlagsversion: " + person.getPersonnummer() + " är inte doskund :-( i " + SERVICEENDPOINT ); }

        Integer dosUnderlagSkapat = SkapaDosunderlagVard(person.getPersonnummer()).getTagCount("*//faultcode"); // 0 = OK
        System.out.println("SkapaDosunderlagVard: " + dosUnderlagSkapat + " - 0=OK");

        String org_dosproducent = "";
        if ( HamtaPatientInfo(person.getPersonnummer()).getTagCount("*//dosproducent") > 0 ) {
            org_dosproducent = HamtaPatientInfo(person.getPersonnummer()).getTagValue("*//dosproducent"); // kanske "7350045511997" //
            System.out.println("HamtaPatientInfo: Orginal dos producent: " + org_dosproducent);
        }

        Integer uppdaterat_dostillhorighet = UppdateraDostillhorighet(person.getPersonnummer(),org_dosproducent).getTagCount("*//faultcode"); // 0 = OK
        System.out.println("UppdateraDostillhorighet: " + uppdaterat_dostillhorighet + " - 0=Uppdaterat");

    }


    // UppdateraSamtycke //
    public SoapResponseXML UppdateraSamtycke(Person person) {
        SoapResponseXML response;

        Tickets ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");

        UppdateraSamtycke us = new UppdateraSamtycke();
        us.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        us.setStandardDefaultValues();
        us.setPersonnummer(person.getPersonnummer());
        us.setSamtyckeEES(true);
        us.setSamtyckeRR(true);
        us.setSamtyckeRRD(true);

        // Sätter miljö
        us.setSoapEndpointUrl(SERVICEENDPOINT_STP + "/UppdateraSamtyckeResponder/V4");

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( us.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( us.getXML() ))));

        System.out.print("UppdateraSamtycke: " + person.getPersonnummer() + " : ");
//        response.logXML();
        us.checkResponse(response);

        return response;
    }

    // SkapaHkdbkonto //
    public SoapResponseXML SkapaHkdbkonto(Person person){
        XmlFormatter xmlFormatter = new XmlFormatter();

        SoapResponseXML response;

        Tickets ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");

        // -----------------------------------------------------------------------------------------------------------------
        // -    HamtaHkdbInformation
        // -----------------------------------------------------------------------------------------------------------------
        HamtaHkdbInformation hhi = new HamtaHkdbInformation();
        hhi.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        hhi.setStandardDefaultValues();
        hhi.setPersonnummer(person.getPersonnummer());

        // Sätter miljö
        hhi.setSoapEndpointUrl(SERVICEENDPOINT_STP + "/HamtaHkdbInformationResponder/V1");

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hhi.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hhi.getXML() ))));

//        System.out.println(xmlFormatter.format(response.getXML()));

        String ansluten = response.getTagValue("*//ansluten");

        System.out.println("SkapaHkdbkonto: " + person.getPersonnummer() + " : Anslutem = " + ansluten);

        if(!ansluten.equalsIgnoreCase("true")) {

            // -----------------------------------------------------------------------------------------------------------------
            // -   SkapaHkdbInformation
            // -----------------------------------------------------------------------------------------------------------------
            SkapaHkdbKonto shk = new SkapaHkdbKonto();
            shk.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
            shk.setStandardDefaultValues();
            shk.setPersonnummer(person.getPersonnummer());

            // Sätter miljö
            shk.setSoapEndpointUrl(SERVICEENDPOINT_STP + "/SkapaHkdbKontoResponder/V4");

            response = new SoapResponseXML(BaseXML.SoapResponseMsgToString(BaseXML.sendSoapRequest(shk.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString(shk.getXML()))));

            shk.checkResponse(response);

            System.out.println("HKDBKonto skapat");
        }
        return response;
    }

    // HamtaPatientInfo //
    public SoapResponseXML HamtaPatientInfo( String personnummer ) {
        HamtaPatientInfo hp = new HamtaPatientInfo();
        hp.setSoapEndpointUrl(SERVICEENDPOINT_STP + "/HamtaPatientInfoResponder/V6");
        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode("9000027");
        ticket.setHealthcareProfessionalLicense("LK");
        hp.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        hp.setStandardDefaultValues();
        hp.setPersonnummer(personnummer);

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hp.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hp.getXML() ))));

        return response;
    }

    // HamtaGallandeUnderlagsversion //
    public SoapResponseXML HamtaGallandeUnderlagsversion( String personnummer ) {
        HamtaGallandeUnderlagsversion hgu = new HamtaGallandeUnderlagsversion();
        hgu.setSoapEndpointUrl(SERVICEENDPOINT + "/HamtaGallandeUnderlagsversionResponder/V5");
        Tickets ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");
        hgu.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        hgu.setStandardDefaultValues();
        hgu.setTagValue("*//personnummer", personnummer);

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hgu.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hgu.getXML() ))));

        return response;
    }

    // SkapaDosunderlagVard
    public SoapResponseXML SkapaDosunderlagVard( String personnummer ) {
        SkapaDosunderlagVard sdv = new SkapaDosunderlagVard();
        sdv.setSoapEndpointUrl(SERVICEENDPOINT + "/SkapaDosunderlagVardResponder/V4");
        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode("9000027");
        ticket.setHealthcareProfessionalLicense("LK");
        sdv.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        sdv.setStandardDefaultValues();

        sdv.setPersonnummer(personnummer);
        sdv.setSamtyckeDos("true");
        sdv.setArbetsplatskod("4000000000000");
        sdv.setYrkeskod("LK");
        sdv.setFornamn("Lars");
        sdv.setEfternamn("Läkare");

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( sdv.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( sdv.getXML() ))));

        if (response.getTagCount("*//faultcode") != 0) System.out.println("Error: " + response.getTagValue("*//description"));
        return response;
    }

    // UppdateraDostillhörighet //
    public SoapResponseXML UppdateraDostillhorighet( String personnummer, String franApoteskId ) {
        UppdateraDostillhorighet ud = new UppdateraDostillhorighet();

        ud.setSoapEndpointUrl(SERVICEENDPOINT + "/UppdateraDostillhorighetResponder/V4");
        Tickets ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511997");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");
        ud.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // om framApoteskId = null ta bort taggen framApoteskId
        if ( franApoteskId.length() == 0) {
            ud.removeTag("*//UppdateraDostillhorighet/franApoteksId");
        } else {
            ud.setFranApoteksId(franApoteskId);
        }
//        ud.setStandardDefaultValues();
        ud.setPersonnummer(personnummer);
        ud.setTillApoteksId("7350045511997");

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( ud.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( ud.getXML() ))));
        if (response.getTagCount("*//faultcode") != 0) System.out.println("Error: " + response.getTagValue("*//description"));

        return response;
    }

    // GodkannDosunderlag //
    public SoapResponseXML GodkannDosunderlag( String personnummer, String underlagsversion ) {
        GodkannDosunderlag gd = new GodkannDosunderlag();

        gd.setSoapEndpointUrl(SERVICEENDPOINT + "/GodkannDosunderlagResponder/V5");
        Tickets ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511997");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");
        gd.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        gd.setStandardDefaultValues();
        gd.setPersonnummer(personnummer);
        gd.setUnderlagsVersion(underlagsversion);

//        gd.logXML();

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( gd.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( gd.getXML() ))));
        if (response.getTagCount("*//faultcode") != 0) System.out.println("Error: " + response.getTagValue("*//description"));

        return response;
    }


    // SkapaOrdinationVard DISPAD AKUT
    // Fixat i LR

    @Test
    public void PreReqSingelPerson() {
//        Skatteverket skatteverket = new Skatteverket();
//        Person person = skatteverket.getPerson("189008179807");

//        SparPerson sparperson = new SparPerson();
//        sparperson.addRNDPerson("INT28");
//        System.out.println(sparperson);

        Person person = new Person();
//        person.setPersonnummer(sparperson.getPersonnr());

        person.setPersonnummer("195509168240");
        person.setFornamn("Leona");
        person.setEfternamn("Testarrot");

//        response = UppdateraSamtycke(person);
//        response = SkapaHkdbkonto(person);

        String underlagsversion =    HamtaGallandeUnderlagsversion( person.getPersonnummer() ).getTagValue("*//underlagsversion");

        if ( HamtaGallandeUnderlagsversion( person.getPersonnummer() ).getTagCount("*//dosunderlagsstatus") > 0 ) {
            String dosunderlagsversion = HamtaGallandeUnderlagsversion(person.getPersonnummer()).getTagValue("*//dosunderlagsversion");
            String dosunderlagsstatus = HamtaGallandeUnderlagsversion(person.getPersonnummer()).getTagValue("*//dosunderlagsstatus");
        } else { System.out.println("HamtaGallandeUnderlagsversion: " + person.getPersonnummer() + " är inte doskund :-( i " + SERVICEENDPOINT ); }

        Integer dosUnderlagSkapat = SkapaDosunderlagVard(person.getPersonnummer()).getTagCount("*//faultcode"); // 0 = OK

        System.out.println("SkapaDosunderlagVard: " + dosUnderlagSkapat + " - 0=OK");

        String org_dosproducent = "";
        if ( HamtaPatientInfo(person.getPersonnummer()).getTagCount("*//dosproducent") > 0 ) {
            org_dosproducent = HamtaPatientInfo(person.getPersonnummer()).getTagValue("*//dosproducent"); // kanske "7350045511997" //
            System.out.println("HamtaPatientInfo: Orginal dos producent: " + org_dosproducent);
        }

        Integer uppdaterat_dostillhorighet = UppdateraDostillhorighet(person.getPersonnummer(),org_dosproducent).getTagCount("*//faultcode"); // 0 = OK
        System.out.println("UppdateraDostillhorighet: " + uppdaterat_dostillhorighet + " - 0=Uppdaterat");

        underlagsversion = HamtaGallandeUnderlagsversion( person.getPersonnummer() ).getTagValue("*//underlagsversion");
        System.out.println("Ny underlagsversion: " + underlagsversion);

        response = GodkannDosunderlag(person.getPersonnummer(), underlagsversion);
        Integer status = response.getTagCount("*//dosunderlagsstatus/status");
        if ( status != 0) {
            System.out.println(response.getTagValue("*//dosunderlagsstatus/status"));
        }
        //response.logXML();
    }

    @Test
    public void test_GodkannDosunderlag() {
        String underlagsversion = HamtaGallandeUnderlagsversion( "198702065957" ).getTagValue("*//underlagsversion");

        response = GodkannDosunderlag("198702065957", underlagsversion);

        Integer status = response.getTagCount("*//dosunderlagsstatus/status");
        if ( status != 0) {
            System.out.println(response.getTagValue("*//dosunderlagsstatus/status"));
        }
    }


    @Test
    public void runSlask() {
        for (int i=0 ; i < 1 ; i++ ) {
            Slask();
            System.out.println();
            System.out.println((i+1) + " : done!");
            System.out.println();
        }
    }

    public void Slask() {
        SparPerson sparperson = new SparPerson();
        sparperson.addRNDPerson("INT28");
        System.out.println(sparperson);

        Person person = new Person();
        person.setPersonnummer(sparperson.getPersonnr());

        response = UppdateraSamtycke(person);
        response = SkapaHkdbkonto(person);
    }

}


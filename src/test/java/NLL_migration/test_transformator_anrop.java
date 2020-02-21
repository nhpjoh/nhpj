package NLL_migration;

import org.junit.Test;
import se.ehm.testdata.*;
import se.nhpj.LoadRunner.lr;
import se.nhpj.pirr.PirrHandler;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.*;
import se.nhpj.soap.utils.XmlFormatter;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;
import se.nhpj.testdata.Transaction;
import javax.xml.soap.SOAPMessage;
import java.io.UnsupportedEncodingException;


public class test_transformator_anrop {

//    String SERVICEENDPOINT_STP_PIRR = "https://pirr";
    String SERVICEENDPOINT_STP_PIRR = "http://test28:10080/apisp-pi-wsgeneric-1.3";
    String SERVICEENDPOINT_STP      = "http://test28:10080/apisp";
    String SERVICEENDPOINT_NLL      = "https://nll-transformator-api-sp-s17-test1-deploy2.test.ecp.systest.receptpartner.se/nll-transformator-wsp";
    String SERVICEENDPOINT_NLL_PIRR = "https://nll-transformator-pirr-sp-s17-test1-deploy2.test.ecp.systest.receptpartner.se/nll-transformator-pirr-sp/pi-wsgeneric-1.3";
    //  String SERVICEENDPOINT      = "https://prestanda";

    SoapResponseXML response;

    @Test
    public void test_SkapaOrdinationApotek() {
        // Skapar anropets XML
        SkapaOrdinationApotek soa = new SkapaOrdinationApotek();
        // Sätter miljö
        soa.setSoapEndpointUrl(SERVICEENDPOINT_NLL + "/SkapaOrdinationApotekResponder/V7");
        System.out.println("Endpoint: " + soa.getSoapEndpointUrl());

        // Skapa en ticket
        Tickets ticket = new Tickets();
        Farmaceut farmaceut = FarmaceutHandler.getRndFarmaceut("INT28");
        System.out.println("Apotekare: " + farmaceut);
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber(farmaceut.getLegitimationskod());
        ticket.setHealthcareProfessionalLicense("AP");

        // Lägger till en ticket till anropet
        soa.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken fil?: "+soa.getStandardDefaultFileName());
        soa.setStandardDefaultValues();

        soa.setTagValue("*//originalordinationsId",BaseXML.getUUID());
        soa.setTagValue("*//forstaUttagFore", se.nhpj.soap.utils.CurrentDateTime.getDate(10) + "T00:00:00.000+02:00");
        soa.setTagValue("*//sistaGiltighetsdag", se.nhpj.soap.utils.CurrentDateTime.getDate(360) + "T00:00:00.000+02:00");
        soa.setTagValue("*//ordinationstidpunkt", se.nhpj.soap.utils.CurrentDateTime.getDate(0) + "T00:00:00.000+02:00");

        soa.setTagValue("*//underlagsversion","0");

        System.out.println(soa.getXML());

        // Anropets URL
        String soapEndpointUrl = soa.getSoapEndpointUrl();
        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(soa.getXML());
        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);
        // Convert the response to XML response objekt
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );
        // kontrolerar svaret
        //soa.checkResponse(response);
        // Skriver ut svaret
        System.out.println("\n\n"+response.getXML());

    }

    @Test
    public void test_SkapaOrdinationVard() {
        // Skapar anropets XML
        SkapaOrdinationVard sov = new SkapaOrdinationVard();

        // Sätter miljö
        sov.setSoapEndpointUrl(SERVICEENDPOINT_NLL + "/SkapaOrdinationVardResponder/V7");
        System.out.println("Endpoint: " + sov.getSoapEndpointUrl());

        // Skapa en ticket
        Forskrivare forskrivare = ForskrivareHandler.getRndForskrivare("INT28");
        System.out.println("Förskrivare: " + forskrivare);
        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode(forskrivare.getForskrivarkod());
        ticket.setHealthcareProfessionalLicense("LK");

        // Lägger till en ticket till anropet
        sov.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken fil?: "+sov.getStandardDefaultFileName());
        sov.setStandardDefaultValues();

        sov.setTagValue("*//originalordinationsId",BaseXML.getUUID());
        sov.setTagValue("*//forstaUttagFore", se.nhpj.soap.utils.CurrentDateTime.getDate(10) + "T00:00:00.000+02:00");
        sov.setTagValue("*//sistaGiltighetsdag", se.nhpj.soap.utils.CurrentDateTime.getDate(360) + "T00:00:00.000+02:00");
        sov.setTagValue("*//ordinationstidpunkt", se.nhpj.soap.utils.CurrentDateTime.getDate(0) + "T00:00:00.000+02:00");

        sov.setTagValue("*//underlagsversion","1");

        System.out.println(sov.getXML());

        // Anropets URL
        String soapEndpointUrl = sov.getSoapEndpointUrl();
        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(sov.getXML());
        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);
        // Convert the response to XML response objekt
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );
        // kontrolerar svaret
        //soa.checkResponse(response);
        // Skriver ut svaret
        System.out.println("\n\n"+response.getXML());
    }

    @Test
    public void test_HamtaGallandeUnderlagsversion() {

        // Skapar anropets XML
        HamtaGallandeUnderlagsversion hgu = new HamtaGallandeUnderlagsversion();

        // Sätter miljö
        hgu.setSoapEndpointUrl(SERVICEENDPOINT_NLL + "/HamtaGallandeUnderlagsversionResponder/V5");
        System.out.println("Endpoint: " + hgu.getSoapEndpointUrl());

        // Skapa en ticket
        Forskrivare forskrivare = ForskrivareHandler.getRndForskrivare("INT28");
        System.out.println("Förskrivare: " + forskrivare);
        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode(forskrivare.getForskrivarkod());
        ticket.setHealthcareProfessionalLicense("LK");

        // Lägger till en ticket till anropet
        hgu.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken fil?: "+hgu.getStandardDefaultFileName());
        hgu.setStandardDefaultValues();
        try {
            System.out.println("Värdet från properties är satt till: " + hgu.getTagValue("*//personnummer"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Uppdatera/sätter indata
        System.out.println("Värde sätts nu till: 189001019802");
        hgu.setTagValue("*//personnummer", "189001019802"); // orgVal 189910109819

        // Anropets URL
        String soapEndpointUrl = hgu.getSoapEndpointUrl();

        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(hgu.getXML());

        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);

        // Convert the response to XML response objekt
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );

        // kontrolerar svaret
//        hgu.checkResponse(response);

        // Skriver ut svaret
        System.out.println("Response:\n" + response.getXML());

        // Leta i XML svaret
//        System.out.println( "Underlagsversion: " + response.getTagValue("*//underlagsversion") );

        // Logga svaret
        //response.logXML();

    }

    @Test
    public void test_HamtaGallandeOrdinationsversion() {
        // Skapar anropets XML
        HamtaGallandeOrdinationsversion hgo = new HamtaGallandeOrdinationsversion();

        // Sätter miljö
        hgo.setSoapEndpointUrl(SERVICEENDPOINT_NLL + "/HamtaGallandeOrdinationsversionResponder/V4");
        System.out.println("Endpoint: " + hgo.getSoapEndpointUrl());

        // Skapa en ticket
        Tickets ticket = new Tickets();
        Farmaceut farmaceut = FarmaceutHandler.getRndFarmaceut("INT28");
        System.out.println("Apotekare: " + farmaceut);
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber(farmaceut.getLegitimationskod());
        ticket.setHealthcareProfessionalLicense("AP");

        // Lägger till en ticket till anropet
        hgo.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken fil?: "+hgo.getStandardDefaultFileName());
        hgo.setStandardDefaultValues();

        hgo.setOrdinationsId("422901");

        // Anropets URL
        String soapEndpointUrl = hgo.getSoapEndpointUrl();

        // Gör om XMLSträngen till ett soapMessage
        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(hgo.getXML());

        // Skickar anropet
        SOAPMessage soapResponse = BaseXML.sendSoapRequest(soapEndpointUrl, soapMessage);

        // Convert the response to XML response objekt
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );

        // kontrolerar svaret
        hgo.checkResponse(response);

        // Skriver ut svaret
        System.out.println("Response:\n" + response.getXML());

    }

    @Test
    public void test_HamtaAktuellaOrdinationer_ordinationsId() {
        /*
            select i.identitet personnummer, f.forskrivning_id
              from personidentitet@int28 i
              join person@int28 p
                on p.person_id = i.person_id
              join nll_data.lakemedelslista l
                on l.patient_ref = p.logiskt_id
              join nll_data.forskrivning f
                on f.lakemedelslista_ref = l.logiskt_id
             order by 1, 2;
         */

        se.nhpj.soap.utils.XmlFormatter xmlFormatter = new XmlFormatter();
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------
        // HamtaAktuellaOrdinationer ---------------------------------------------------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------
        HamtaAktuellaOrdinationer hao = new HamtaAktuellaOrdinationer();

        // Skapa en ticket
        Tickets ticket = new Tickets();
        Farmaceut farmaceut = FarmaceutHandler.getRndFarmaceut("INT28");
        System.out.println("Apotekare: " + farmaceut);
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber(farmaceut.getLegitimationskod());
        ticket.setHealthcareProfessionalLicense("AP");

        hao.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        hao.setStandardDefaultValues();
        hao.setPersonnummer("190907019806");
        hao.setOrdinationsId("230");

        // Sätter miljö
        hao.setSoapEndpointUrl(SERVICEENDPOINT_NLL + "/HamtaAktuellaOrdinationerResponder/V5");

        System.out.println("Endpoint: " + hao.getSoapEndpointUrl());

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hao.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hao.getXML() ))));

        hao.checkResponse(response);

//        System.out.println("\n");
//        System.out.println(xmlFormatter.format(response.getXML()) + "\n\n");

    }

    @Test
    public void run_test_HamtaAktuellaOrdinationer() { // 189001019802 30st // 191010189809 10 st //
        for( int i=0;i<1;i++) {
            test_HamtaAktuellaOrdinationer("189001109819", SERVICEENDPOINT_NLL, false, "NLL");
            test_HamtaAktuellaOrdinationer("189001109819", SERVICEENDPOINT_STP, false, "STP");
        }
//        for( int i=0;i<1;i++) {
//            test_HamtaAktuellaOrdinationer("199007119804", SERVICEENDPOINT_STP, false, "STP");
//            test_HamtaAktuellaOrdinationer("199007119804", SERVICEENDPOINT_NLL, false, "NLL");
//        }
    }

    public void test_HamtaAktuellaOrdinationer(String personnummer, String serviceendpoint, Boolean check, String transaktion) {
        se.nhpj.testdata.Transaction transaction = new Transaction();
        se.nhpj.soap.utils.XmlFormatter xmlFormatter = new XmlFormatter();

        // -----------------------------------------------------------------------------------------------------------------------------------------------------------
        // HamtaAktuellaOrdinationer ---------------------------------------------------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------
        HamtaAktuellaOrdinationer hao = new HamtaAktuellaOrdinationer();

        // Skapa en ticket
        Tickets ticket = new Tickets();
        Farmaceut farmaceut = FarmaceutHandler.getRndFarmaceut("INT28");
        System.out.println("Apotekare: " + farmaceut);
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber(farmaceut.getLegitimationskod());
        ticket.setHealthcareProfessionalLicense("AP");

        hao.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        hao.setStandardDefaultValues();
        hao.setPersonnummer(personnummer);
        hao.removeTag("*//ordinationsId");

        // Sätter miljö
        hao.setSoapEndpointUrl(serviceendpoint + "/HamtaAktuellaOrdinationerResponder/V5");
        if(check)
            System.out.println("Endpoint: " + hao.getSoapEndpointUrl());

        transaction.start(transaktion);
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hao.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hao.getXML() ))));
        transaction.stop(transaktion);

        hao.checkResponse(response);

        if(check) {
            System.out.println("\n");
            System.out.println(xmlFormatter.format(response.getXML()) + "\n\n");
        }
        System.out.println("Svarstid_"+ transaktion +": " + transaction.getElapsedTime(transaktion));
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

        // -----------------------------------------------------------------------------------------------------------------------------------------------------------

    }

    @Test
    public void run_test_pirr() throws UnsupportedEncodingException {
        PirrHandler pirr = new PirrHandler();
        Skatteverket skatten = new Skatteverket();
        LakemedelHandler lakemedelHandler = new LakemedelHandler();
        Person person=null;

        for ( int i = 0 ; i < 10 ; i++ ) {
            person = skatten.getSlumpadPerson();
//            person = skatten.getPerson("199607212397");
//            person.setPersonnummer("189002119817"); // person saknas i skattenListan
            Forskrivare forskrivare = se.ehm.testdata.ForskrivareHandler.getRndForskrivare("TEST28");
            Arbetsplats arbetsplats = se.ehm.testdata.ArbetsplatsHandler.getRndArbetsplats("TEST28");
//            Lakemedel lakemedel = lakemedelHandler.getRndLakemedel();
            Lakemedel lakemedel = se.ehm.testdata.LakemedelHandler.getRndLakemedel("TEST28");
            lakemedel.setAntalUttag(String.valueOf(se.nhpj.testdata.RndData.getNumber(5,20)));
            lakemedel.setNumberOfPackages(String.valueOf(se.nhpj.testdata.RndData.getNumber(5,20)));

            System.out.println("\n"+person);
            System.out.println(forskrivare);
            System.out.println(arbetsplats);
            System.out.println(lakemedel);
            System.out.print("\n\n"+(i+1) + " : ");

            response = pirr.add_pirr(person,forskrivare, arbetsplats, lakemedel,SERVICEENDPOINT_STP_PIRR + "/wsgeneric");
            pirr.pirr_check(response);
//            response = PirrHandler.add_pirr(person,forskrivare, arbetsplats, lakemedel,SERVICEENDPOINT_NLL_PIRR + "/pi-wsgeneric-1.3/wsgeneric");
//            PirrHandler.pirr_check(response);

            lr.think_time(0.250);
        }
    }

    @Test
    public void run_testFodDat_pirr() throws UnsupportedEncodingException {
        PirrHandler pirr = new PirrHandler();
        Skatteverket skatten = new Skatteverket();
        LakemedelHandler lakemedelHandler = new LakemedelHandler();
        Person person=null;
        for ( int i=0 ; i < 10 ; i++ ) {
            person = skatten.getSlumpadPerson();
            Forskrivare forskrivare = se.ehm.testdata.ForskrivareHandler.getRndForskrivare("TEST28");
            Apotek apotek = se.ehm.testdata.ApotekHandler.getRndApotek("TEST28");
            Arbetsplats arbetsplats = se.ehm.testdata.ArbetsplatsHandler.getRndArbetsplats("TEST28");
//            Lakemedel lakemedel = lakemedelHandler.getRndLakemedel();
            Lakemedel lakemedel = se.ehm.testdata.LakemedelHandler.getRndLakemedel("TEST28");
            lakemedel.setAntalUttag(String.valueOf(se.nhpj.testdata.RndData.getNumber(5,20)));
            lakemedel.setNumberOfPackages(String.valueOf(se.nhpj.testdata.RndData.getNumber(5,20)));

            System.out.println("\nNytt Recept:");
            System.out.println(person);
            System.out.println(forskrivare);
            System.out.println(arbetsplats);
            System.out.println(apotek);
            System.out.println(lakemedel);

            System.out.print("\n\n"+(i+1) + " : ");

//            response = PirrHandler.add_pirrFoddat(person,forskrivare,arbetsplats,apotek.getGLN(),lakemedel,SERVICEENDPOINT_STP_PIRR + "/wsgeneric");
//            PirrHandler.pirr_check(response);
            response = pirr.add_pirrFoddat(person,forskrivare,arbetsplats,apotek.getGLN(),lakemedel,SERVICEENDPOINT_NLL_PIRR + "/wsgeneric");
            pirr.pirr_check(response);
//            lr.think_time(0.100);
        }
    }
}

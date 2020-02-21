package NLL_migration;

import org.junit.Test;
import se.ehm.testdata.*;
import se.nhpj.LoadRunner.lr;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.*;
import se.nhpj.soap.utils.XmlFormatter;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;
import se.nhpj.testdata.Transaction;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Response;
import java.util.List;

public class test_Förutsättningar {

    String SERVICEENDPOINT_STP      = "http://test28:10080/apisp";
    String SERVICEENDPOINT_NLL      = "https://nll-transformator-api-sp-s16-test1-deploy2.test.ecp.systest.receptpartner.se/nll-transformator-wsp";

    // -----------------------------------------------------------------------------------------------------------------
    // -                                   Skapa förutsättningar för andra tester                                      -
    // -----------------------------------------------------------------------------------------------------------------

    // Sätta samtycke
    @Test
    public void test_UppdateraSamtycke_en_person() {
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getPerson("189001109819");
        UppdateraSamtycke(person);
    }

    @Test
    public void test_UppdateraSamtycke_lista() {
        Skatteverket skatteverket = new Skatteverket();
        List<Person> personer = skatteverket.getPersons();
        personer.forEach( person -> UppdateraSamtycke(person));
    }

    public void UppdateraSamtycke(Person person) {
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

        System.out.print(person.getPersonnummer() + " : ");
//        response.logXML();
        us.checkResponse(response);
    }

    // Skapa HKDBkonto
    @Test
    public void test_SkapaHkdbkonto_en_person(){
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getPerson("189001109819");
        SkapaHkdbkonto(person);
    }

    @Test
    public void test_SkapaHkdbkonto_lista(){
        Skatteverket skatteverket = new Skatteverket();
        List<Person> personer = skatteverket.getPersons();
        personer.forEach( person -> SkapaHkdbkonto(person));
    }

    public void SkapaHkdbkonto(Person person){
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

        System.out.println(xmlFormatter.format(response.getXML()));

        String ansluten = response.getTagValue("*//ansluten");

        System.out.println(person.getPersonnummer() + " : Anslutem = " + ansluten);

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
    }

    // Skapa Dosunderlag

    @Test
    public void test_SkapaDosunderlagVard() {
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getPerson("189001109819");

        XmlFormatter xmlFormatter = new XmlFormatter();
        SoapResponseXML response;

        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode("9000027");
        ticket.setHealthcareProfessionalLicense("LK");

        SkapaDosunderlagVard sdv = new SkapaDosunderlagVard();
        sdv.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        sdv.setStandardDefaultValues();
        sdv.setPersonnummer(person.getPersonnummer());

        // Sätter miljö
        sdv.setSoapEndpointUrl(SERVICEENDPOINT_STP + "/SkapaDosunderlagVardResponder/V4");

        response = new SoapResponseXML(BaseXML.SoapResponseMsgToString(BaseXML.sendSoapRequest(sdv.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString(sdv.getXML()))));

        sdv.checkResponse(response);
    }

    // AvregistreraDosunderlag
    @Test
    public void test_AvregistreraDosunderlag() {
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getPerson("189001109819");

        XmlFormatter xmlFormatter = new XmlFormatter();
        SoapResponseXML response;

        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode("9000027");
        ticket.setHealthcareProfessionalLicense("LK");

        AvregistreraDosunderlag ad = new AvregistreraDosunderlag();
        ad.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        ad.setStandardDefaultValues();
        ad.setPersonnummer(person.getPersonnummer());

        // Sätter miljö
        ad.setSoapEndpointUrl(SERVICEENDPOINT_STP + "/AvregistreraDosunderlagResponder/V4");

        response = new SoapResponseXML(BaseXML.SoapResponseMsgToString(BaseXML.sendSoapRequest(ad.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString(ad.getXML()))));

        ad.checkResponse(response);
    }



}

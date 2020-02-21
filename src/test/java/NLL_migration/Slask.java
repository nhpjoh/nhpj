package NLL_migration;

import org.junit.Test;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Implementation;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.HamtaAktuellaOrdinationer;
import se.nhpj.soap.services.Prescription;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.utils.XmlFormatter;

import java.awt.*;
import java.io.*;


public class Slask {
    @Test
    public void test() throws Exception {
        Implementation imp = new Implementation();
        System.out.println(imp.getTagValue("*//person"));
        imp.logXML();
    }

    @Test
    public void logXML() {
        Prescription prescription = new Prescription();

        XmlFormatter f = new XmlFormatter();
        try {
            File file = new File("BaseXML.log");

            FileWriter fileWriter = new FileWriter(file, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            printWriter.println(f.format(prescription.getXML()));
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch ( java.io.IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void test2() {
        System.out.println(HamtaAktuellaOrdinationer("189205109805","48455877511"));
    }

    public String HamtaAktuellaOrdinationer(String personnummer, String ordinationsId) {

        se.nhpj.soap.utils.XmlFormatter xmlFormatter = new XmlFormatter();
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------
        // HamtaAktuellaOrdinationer ---------------------------------------------------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------
        HamtaAktuellaOrdinationer hao = new HamtaAktuellaOrdinationer();
        SoapResponseXML response;

        // Skapa en ticket
        Tickets ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");

        hao.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        hao.setStandardDefaultValues();
        hao.setPersonnummer(personnummer);
        hao.setOrdinationsId(ordinationsId);

        // Sätter miljö
        hao.setSoapEndpointUrl("http://test28:10080/apisp/HamtaAktuellaOrdinationerResponder/V5");

        System.out.println("HamtaAktuellaOrdinationer Endpoint: " + hao.getSoapEndpointUrl());

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hao.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hao.getXML() ))));

        //hao.checkResponse(response);

//        System.out.println("\n");
//        System.out.println(xmlFormatter.format(response.getXML()) + "\n\n");
        return response.getXML();
    }

    @Test
    public void test3() {
        String s = "abcdefghijklmnopqrstuvxyzåäö";
        System.out.println(s.substring(0,10));
        System.out.println(s.substring(10,s.length()));
    }
}

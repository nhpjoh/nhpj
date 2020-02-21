package labb;

import org.junit.Test;
import se.ehm.testdata.FOLK.SparPerson;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.*;
import se.nhpj.soap.*;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.RndData;
import se.nhpj.testdata.Skatteverket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Test_SparPerson {

    @Test
    public void addPerson() {
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getPerson("189001109819");
        SparPerson sparperson = new SparPerson();
        System.out.println(sparperson.addPerson(person,"TEST28"));
    }

    @Test
    public void addRNDPerson() {
        SparPerson sparperson = new SparPerson();
        int antalTestpersoner = 1;

        for ( int i = 0 ; i < antalTestpersoner ; i++) {
            System.out.println("Adderat person: " + sparperson.addRNDPerson("INT28"));
        }

    }

    @Test
    public void skapaNyaTestPersoner_i_prestanda() {
        int antalTestpersoner = 0;
        SparPerson sparperson = new SparPerson();

        for ( int i = 0 ; i < antalTestpersoner ; i++) {
            System.out.println("Adderat person: " + sparperson.addRNDPerson(1890,1899,"PTGD"));
        }

    }

    @Test
    public void test_stega_i_testnummerfil_och_uppdatera_samtycke() {

        try {
            File f = new File("c:/temp/testnummer.dat");
            for(Scanner sc = new Scanner(f); sc.hasNext(); )
            {
                String line = sc.nextLine();
                String pnr = line;
                System.out.println(pnr);
                uppdateraSamtycke(pnr);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test_SparPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void uppdateraSamtycke(String pnr) {

        Tickets ticket 	            = new Tickets();
        SoapResponseXML response;
        String  GLN	                = "7350045511119";
        String AP_kod               = "920007";
        String patient_pnr          = pnr;

        // -----------------------------------------------------------------------------------------------------------------------------------------------------------
        // UppdateraSamtycke -----------------------------------------------------------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------
        ticket.setSoapEndpointUrl("http://TICGEN:17777/sambi/SambiTicketServlet");

        UppdateraSamtycke us = new UppdateraSamtycke();
        ticket.setHealthcareProfessionalLicense("AP");
        ticket.setHealthcareProfessionalLicenseIdentityNumber(AP_kod);
        ticket.setPharmacyIdentifier(GLN);
        us.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        us.setStandardDefaultValues();
        us.setPersonnummer(patient_pnr);
        us.setSamtyckeEES(true);
        us.setSamtyckeRR(true);
        us.setSamtyckeRRD(true);

//        us.logXML();

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( us.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( us.getXML() ))));

//        response.logXML();

        us.checkResponse(response);


    }
}

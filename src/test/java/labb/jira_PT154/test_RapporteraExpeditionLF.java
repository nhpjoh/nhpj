package labb.jira_PT154;

import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import se.nhpj.database.DB_Access;
import se.nhpj.soap.*;
import se.nhpj.soap.services.*;
import java.sql.Connection;
import se.nhpj.LoadRunner.*;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;

public class test_RapporteraExpeditionLF {
    @Test
    public void testRapporteraExpeditionLF() {
        String SERVICEENDPOINT = "https://TEST2:19443/apisp";
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getSlumpadPerson();
        String patient_pnr   = person.getPersonnummer();
        String patient_fnamn = person.getFornamn();
        String patient_enamn = person.getEfternamn();
        String expeditionsId = "ZZZ";
        String rnd = se.nhpj.testdata.RndData.getChars(3) + se.nhpj.testdata.RndData.getNumbers(6);
        SoapResponseXML response;
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// RapporteraExpeditionLF ------ Ej om det är handelsvara ----------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
        Tickets ticket  = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");


        lr.start_transaction("LF trans");
        RapporteraExpeditionLF relf = new RapporteraExpeditionLF();
        relf.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        relf.setStandardDefaultValues();
        relf.setSoapEndpointUrl( SERVICEENDPOINT + "/RapporteraExpeditionLFResponder/V5");
        System.out.println(relf.getSoapEndpointUrl());
        relf.setPersonnummer(patient_pnr);
        relf.setNamn(patient_fnamn,patient_enamn);
        relf.setTagValue("*//aktorsExpeditionsid", expeditionsId + "_" + rnd);
        relf.setTagValue("*//expeditionsid", expeditionsId);
        relf.setTagValue("*//expeditionsdatum", se.nhpj.soap.utils.CurrentDateTime.getTodaysDate());
//        relf.setTagValue("*//nplPackid", nplPackId);

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( relf.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( relf.getXML() ))));

        relf.checkResponse(response);
        lr.end_transaction("LF trans", lr.AUTO);
    }


}


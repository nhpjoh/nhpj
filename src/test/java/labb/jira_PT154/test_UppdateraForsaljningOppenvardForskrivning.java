package labb.jira_PT154;

import org.junit.Test;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.UppdateraForsaljningOppenvardForskrivning;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;

public class test_UppdateraForsaljningOppenvardForskrivning {

    @Test
    public void loop() {
        for (int i = 0; i < 77 ; i++ ) {
            System.out.print(i + "\t");
            testUppdateraForsaljningOppenvardForskrivning();
            se.nhpj.LoadRunner.lr.think_time(0.500);
        }
    }

    @Test
    public void testUppdateraForsaljningOppenvardForskrivning() {
        String SERVICEENDPOINT = "https://TEST2:19443/apisp";
//        String SERVICEENDPOINT = "https://prestanda/apisp";
        Skatteverket skatteverket = new Skatteverket();
        Person person = skatteverket.getSlumpadPerson();
        String patient_pnr   = person.getPersonnummer();
        String patient_fnamn = person.getFornamn();
        String patient_enamn = person.getEfternamn();
        String expeditionsId = "ZZZ" + se.nhpj.testdata.RndData.getChars(4);
        String rnd = se.nhpj.testdata.RndData.getChars(3) + se.nhpj.testdata.RndData.getNumbers(6);
        SoapResponseXML response;


// UppdateraForsaljningOppenvardForskrivning -----------------------------------------------------------------------------------------------------------------
// FOTA trans ------------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
        Tickets ticket  = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");

        UppdateraForsaljningOppenvardForskrivning ufof = new UppdateraForsaljningOppenvardForskrivning();
        ufof.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
        ufof.setStandardDefaultValues();

        ufof.setSoapEndpointUrl( SERVICEENDPOINT + "/UppdateraForsaljningOppenvardForskrivningResponder/V6");
        System.out.print(ufof.getSoapEndpointUrl());

        ufof.setPersonnummer(patient_pnr);
        ufof.setTagValue("*//klientinformation/system", "7350045511119");
        ufof.setTagValue("*//expoOrgNr", "5567634778");
        ufof.setTagValue("*//aktorExpeditionsId", expeditionsId);
        ufof.setTagValue("*//expeditionsId", expeditionsId);
        ufof.setTagValue("*//expoButiksId", expeditionsId);
        ufof.setTagValue("*//periodStartDatum", se.nhpj.soap.utils.CurrentDateTime.getTodaysDate()+"T00:00:00.000+01:00");
        ufof.setTagValue("*//avhamtadDatum", se.nhpj.soap.utils.CurrentDateTime.getTodaysDate()+"T00:00:00.000+01:00");
        ufof.setTagValue("*//aktorTransId", expeditionsId + "_" + rnd);
        ufof.setTagValue("*//aktorReceptId", "1");
//        ufof.setTagValue("*//aupExMomsAktor", pris_aup);
//        ufof.setTagValue("*//formanExMoms", pris_aup);
        ufof.setTagValue("*//merkostnadExMoms", "0");
//        ufof.setTagValue("*//nplPackid", nplPackId);
        ufof.setTagValue("*//receptId", "1");
        ufof.setTagValue("*//utfardarDatum", se.nhpj.soap.utils.CurrentDateTime.getTodaysDate()+"T00:00:00.000+01:00");
//        ufof.setTagValue("*//varuNr", varuNr);

        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( ufof.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( ufof.getXML() ))));

        ufof.checkResponse(response);

    }
}

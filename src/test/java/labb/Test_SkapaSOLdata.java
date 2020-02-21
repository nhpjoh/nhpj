package labb;

import org.junit.Test;
import se.ehm.sol.soap.services.*;
import se.ehm.testdata.SOL.Dosapotek;
import se.ehm.testdata.SOL.Dosapotek_token;
import se.nhpj.soap.services.SkapaDosmottagare;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.utils.XmlFormatter;
import se.nhpj.testdata.*;

public class Test_SkapaSOLdata {

/*
    SkapaDosmottagare();
    SkapaProduktionsinformationDosaktor();
    SkapaOrdinationsAnsvarigEnhetDosaktor();
    SkapaSarskiltBoende();
    SkapaVardtagareDosaktor();
*/

    @Test
    public void loopSkapaSOLdata() throws Exception {
        for (int i = 0; i < 127 ; i++ ) {
            System.out.println("Loop: " + i);
            skapaSOLdata();
        }
    }

    @Test
    public void skapaSOLdata() throws Exception {

        String endpoint = "https://prestanda/";
        String soapEndpointUrl;
        SoapResponseXML response;
        Tickets ticket;

        Dosapotek_token dosT            = Dosapotek.rndDosapotek(); // Dosapotek.getDosapotek("7310000000015"); //
        String dosapotekId              = dosT.getDOSAPOTEKID();
        String dosapotekNamn            = dosT.getDOSAPOTEK();
        String dosmottagareid           = "999" + se.nhpj.testdata.RndData.getNumbers(10);
        KommunLandsting kommunlandsting = new KommunLandsting().getSlumpad();
        Skatteverket skatteverket       = new Skatteverket();
        Person patient                  = skatteverket.getSlumpadPerson();
        Adress adress                   = new Adress().getSwedishAdress();
        Namn namn                       = new Namn();

        //SkapaDosmottagare
        String dosmottagare_dosmottagareid          = dosmottagareid;
        String dosmottagare_adress                  = "Prestandatestargatan " + se.nhpj.testdata.RndData.getNumbers(20);
        String dosmottagare_apoteksIdDosmottagare   = dosapotekId;//"7350045511997";
        String dosmottagare_apoteksIdDosproducent   = dosapotekId;//"7350045511997";
        String dosmottagare_arbetsplatskod          = "KOD " + se.nhpj.testdata.RndData.getNumbers(10);
        String dosmottagare_avdelning               = "Avdelning " + se.nhpj.testdata.RndData.getNumbers(10);
        String dosmottagare_kommunkod               = kommunlandsting.getKOMMUNKOD(); //"08";
        String dosmottagare_lanskod                 = kommunlandsting.getLANSKOD();   //"01";
        String dosmottagare_mottagarnamn            = "PrestandaCentralen " + se.nhpj.testdata.RndData.getNumbers(20);
        String dosmottagare_postnummer              = se.nhpj.testdata.RndData.getNumbers(5);
        String dosmottagare_postort                 = "Prestandastaden" + se.nhpj.testdata.RndData.getNumbers(15);

        //SkapaProduktionsinformationDosaktor
        String behorighetsinformationDosaktor_dosapotekid               = dosapotekId;
        String produktionsinformation_dosapotekid                       = dosapotekId;
        String produktionsinformation_stopptidbestallning               = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2018-10-30T00:00:00.000+01:00";
        String produktionsinformation_stopptidordination                = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2018-10-30T00:00:00.000+01:00";
        String produktionsinformation_forstadosdag                      = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2018-10-30T00:00:00.000+01:00";
        String produktionsinformation_dosvecka                          = RndData.rndFrom( new String[]{"U","J","V"} ); // Udda, Jämn, Varje
        String produktionsinformation_standardschema_periodlangd        = se.nhpj.testdata.RndData.getANumber(1,12);  //"1";
        String produktionsinformation_standardschema_intagstillfalleKl  = se.nhpj.testdata.RndData.getANumber(10,17); //"12";
        String produktionsinformation_dosmottagareid                    = dosmottagare_dosmottagareid;

        //SkapaVardtagareDosaktor
        String skapaVardtagareDosaktor_Glnkod=dosapotekId;
        String skapaVardtagareDosaktor_vardtagarinformation_hemmaboende                                 = "JA";
        String skapaVardtagareDosaktor_vardtagarinformation_dosapoteksid                                = dosapotekId;    //"1000000000017";
        String skapaVardtagareDosaktor_vardtagarinformation_dosapoteknamn                               = dosapotekNamn; //"Apotek 17";
        String skapaVardtagareDosaktor_vardtagarinformation_forstadosdag                                = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2017-07-17T00:00:00+02:00";
        String skapaVardtagareDosaktor_vardtagarinformation_avvikandedosschema                          = "NEJ";
        String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_fornamn                  = patient.getFornamn(); //"Förnamn";
        String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_mellannamn               = se.nhpj.testdata.RndData.getChars(10); //"MellanNamn";
        String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_efternamn                = patient.getEfternamn(); //"Efternamn";
        String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_identitetstyp            ="P";
        String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_personid                 = patient.getPersonnummer(); //"111111111122";
        String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_lanskod                  = kommunlandsting.getLANSKOD();   //"01";
        String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_kommunkod                = kommunlandsting.getKOMMUNKOD(); //"08";
        String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_adress            = adress.getGatuAdress(); //"Gatan";
        String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagareid    = dosmottagareid; //"4887152666838";
        String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagarenamn  = "DosMotNamn " + se.nhpj.testdata.RndData.getChars(10);
        String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_ort               = adress.getOrt(); //"Orten 1";
        String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_postnummer        = adress.getPostnummer(); //"10001";
        String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_telefon           = "010-" + se.nhpj.testdata.RndData.getNumbers(8);
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALforskrivarkod         = "9674989";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALfornamn               = "PALfornamn";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALefternamn             = "PALefternamn";
        String anhöringFnamn = namn.getFirstNameFemale();
        String anhöringEnamn = namn.getLastNameTest();
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_anhorigkontaktnamn       = anhöringFnamn + " " + anhöringEnamn; //"Anhörig Förnamn Efternamn";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_anhorigkontaktemail      = anhöringFnamn +"."+ anhöringEnamn + "@prestandatest.se";
        String ansvarigFnamn = namn.getFirstNameFemale();
        String ansvarigEnamn = namn.getLastNameTest();
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktnamn      = ansvarigFnamn + " " + ansvarigEnamn; //"Ansvarig Förnamn Efternamn";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktemail     = ansvarigFnamn + "." + ansvarigEnamn + "@prestandatest.se";
        Adress ansvarigadress = new Adress().getSwedishAdress();
        String ansvarigPostNr = ansvarigadress.getPostnummer();
        String trimmad = ansvarigPostNr.substring(0,2)+ansvarigPostNr.substring(4,5);
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktadress    = ansvarigadress.getGatuAdress(); //"Ansvarig Adress";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktpostnummer= trimmad; //"57545";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktpostort   = ansvarigadress.getOrt(); //"Ansvarig postort";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontakttelefon1  = "070-" + se.nhpj.testdata.RndData.getNumbers(8);
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontakttelefon2  = "070-" + se.nhpj.testdata.RndData.getNumbers(8);
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetid          = "XXXXX9999999999999"; // denna kommer ifrån SkapaOrdinationsAnsvarigEnhet -> vardandeenhetid  //
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetnamn        = "PrestandaCentralen " + se.nhpj.testdata.RndData.getNumbers(20); //"L";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetpostort     = "Prestandastaden" + se.nhpj.testdata.RndData.getNumbers(15); //"M";
        String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetpostnummer  = se.nhpj.testdata.RndData.getNumbers(5); //"83653";
        String skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_statuskod                   = "AKTIV";
        String skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_frantid                     = "2017-11-02T00:00:00.000+01:00";
        String skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_tilltid                     = "2022-11-02T00:00:00.000+01:00";
        String skapaVardtagareDosaktor_akut                                                             = "EJ_AKUT";
        String skapaVardtagareDosaktor_meddelandetilldosapotek                                          = "Prestandatest_100000001";
        String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_fornamn                          = "Sofia";
        String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_efternamn                        = "Pedersen";
        String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_legitimationskod                 = "709579";
        String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_yrkesroll                        = "AP";
        String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_dosapotekid                      = dosapotekId;

        //SkapaSarskiltBoende
        String boendeenhetid = se.nhpj.testdata.RndData.getNumbers(13);


// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// SkapaDosmottagare ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("\n\nSkapaDosmottagare:\n");

        SkapaDosmottagare sd = new SkapaDosmottagare();

        // Anropets URL
        sd.setSoapEndpointUrl(endpoint + "/apisp/SkapaDosmottagareResponder/V4");
        System.out.println("Sätter endpoint till " + sd.getSoapEndpointUrl());
        soapEndpointUrl = sd.getSoapEndpointUrl();

        // Skapa en ticket
        ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");

        // Lägger till en ticket till anropet
        sd.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + sd.getStandardDefaultFileName());
        sd.setStandardDefaultValues();

        // Uppdatera/sätter indata
        System.out.println("Värden sätts: \ndosmottagareid: " + dosmottagare_dosmottagareid);
        System.out.println("apoteksIdDosmottagare: " + dosmottagare_apoteksIdDosmottagare );

        sd.setTagValue("*//dosmottagarId",dosmottagare_dosmottagareid);

        sd.setTagValue("*//adress",dosmottagare_adress);
        sd.setTagValue("*//apoteksIdDosmottagare",dosmottagare_apoteksIdDosmottagare);
        sd.setTagValue("*//apoteksIdDosproducent",dosmottagare_apoteksIdDosproducent);
        sd.setTagValue("*//arbetsplatskod",dosmottagare_arbetsplatskod);
        sd.setTagValue("*//avdelning",dosmottagare_avdelning);
        sd.setTagValue("*//kommunkod",dosmottagare_kommunkod);
        sd.setTagValue("*//lanskod",dosmottagare_lanskod);
        sd.setTagValue("*//mottagarnamn",dosmottagare_mottagarnamn);
        sd.setTagValue("*//postnummer",dosmottagare_postnummer);
        sd.setTagValue("*//postort",dosmottagare_postort);

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
//        System.out.println(sd.getXML());
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + sd.getSoapEndpointUrl());
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(sd.getXML()) ) ) );

        // kontrolerar svaret
        sd.checkResponse(response);

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// SkapaProduktionsinformationDosaktor ---------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("\n\nSkapaProduktionsinformationDosaktor:\n");
        SkapaProduktionsinformationDosaktor spid = new SkapaProduktionsinformationDosaktor();

        // Anropets URL
        spid.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaProduktionsinformationDosaktorService/V1");
        System.out.println("Sätter endpoint till " + spid.getSoapEndpointUrl());
        soapEndpointUrl = spid.getSoapEndpointUrl();

        // Skapa en ticket
        ticket = new Tickets();
        ticket.setPharmacyIdentifier("7350045511119");
        ticket.setHealthcareProfessionalLicenseIdentityNumber("920007");
        ticket.setHealthcareProfessionalLicense("AP");

        // Lägger till en ticket till anropet
        spid.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + spid.getStandardDefaultFileName());
        spid.setStandardDefaultValues();

        // Uppdatera/sätter indata
        System.out.println("Värden sätts:");
        System.out.println("dosmottagareid: " + dosmottagare_dosmottagareid);
        System.out.println("dosapotekid: " + dosapotekId);
        System.out.println("dosapotekNamn: " + dosapotekNamn);

        spid.setTagValue("*//produktionsinformation/dosmottagareid",dosmottagare_dosmottagareid);
        spid.setTagValue("*//behorighetsinformationDosaktor/dosapotekid",behorighetsinformationDosaktor_dosapotekid);
        spid.setTagValue("*//produktionsinformation/dosapotek",dosapotekNamn);
        spid.setTagValue("*//produktionsinformation/dosapotekid",produktionsinformation_dosapotekid);
        spid.setTagValue("*//produktionsinformation/stopptidbestallning",produktionsinformation_stopptidbestallning);
        spid.setTagValue("*//produktionsinformation/stopptidordination",produktionsinformation_stopptidordination);
        spid.setTagValue("*//produktionsinformation/forstadosdag",produktionsinformation_forstadosdag);
        spid.setTagValue("*//produktionsinformation/dosvecka",produktionsinformation_dosvecka);
        spid.setTagValue("*//produktionsinformation/standardschema/periodlangd",produktionsinformation_standardschema_periodlangd);
        spid.setTagValue("*//produktionsinformation/standardschema/intagstillfalle/intagstillfalleKl",produktionsinformation_standardschema_intagstillfalleKl);
        spid.setTagValue("*//produktionsinformation/dosmottagarenamn",produktionsinformation_dosmottagareid);

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + spid.getSoapEndpointUrl());
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(spid.getXML()) ) ) );

        // kontrolerar svaret
        spid.checkResponse(response);

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// SkapaOrdinationsAnsvarigEnhetDosaktor -------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("\n\nSkapaOrdinationsAnsvarigEnhetDosaktor:\n");
        SkapaOrdinationsAnsvarigEnhetDosaktor soaed = new SkapaOrdinationsAnsvarigEnhetDosaktor();

        // Anropets URL
        soaed.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaOrdinationsAnsvarigEnhetDosaktorService/V1");
        System.out.println("Sätter endpoint till " + soaed.getSoapEndpointUrl());
        soapEndpointUrl = soaed.getSoapEndpointUrl();


        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + soaed.getStandardDefaultFileName());
        soaed.setStandardDefaultValues();

        // Uppdatera/sätter indata
        System.out.println("Sätter värden/indata: ");
        soaed.setTagValue("*//skapaOrdinationsAnsvarigEnhetDosaktor/arg0/ordinationsansvarigenhet/vardandeenhetid", se.nhpj.testdata.RndData.getChars(5)+ dosmottagareid);
        soaed.setTagValue("*//skapaOrdinationsAnsvarigEnhetDosaktor/arg0/ordinationsansvarigenhet/vardandeenhetnamn","PrestandaCentralen " + se.nhpj.testdata.RndData.getNumbers(20));
        soaed.setTagValue("*//skapaOrdinationsAnsvarigEnhetDosaktor/arg0/ordinationsansvarigenhet/vardandeenhetpostort","Prestandastaden " + se.nhpj.testdata.RndData.getNumbers(15));
        soaed.setTagValue("*//skapaOrdinationsAnsvarigEnhetDosaktor/arg0/ordinationsansvarigenhet/Glnkod",dosapotekId);
        soaed.setTagValue("*//skapaOrdinationsAnsvarigEnhetDosaktor/arg0/ordinationsansvarigenhet/dosapoteknamn",dosapotekNamn);
        soaed.setTagValue("*//skapaOrdinationsAnsvarigEnhetDosaktor/arg0/behorigshetsInformationDosaktor/dosapotekid",dosapotekId);

        System.out.println("vardandeenhetid: " + soaed.getTagValue("*//vardandeenhetid"));
        System.out.println("Glnkod: " + soaed.getTagValue("*//Glnkod"));

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + soaed.getSoapEndpointUrl());
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(soaed.getXML()) ) ) );

        // kontrolerar svaret
        soaed.checkResponse(response);

        String vardandeenhetid = response.getTagValue("*//vardandeenhetid");
        skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetid = vardandeenhetid; // gemensam variabel
        System.out.println("Skapat vardandeenhet med id: " + vardandeenhetid);


// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// SkapaSarskiltBoende ---------------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("\n\nSkapaSarskiltBoende:\n");
        SkapaSarskiltBoende ssb = new SkapaSarskiltBoende();

        // Anropets URL
        ssb.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaSarskiltBoendeService/V1");
        System.out.println("Sätter endpoint till " + ssb.getSoapEndpointUrl());
        soapEndpointUrl = ssb.getSoapEndpointUrl();

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + ssb.getStandardDefaultFileName());
        ssb.setStandardDefaultValues();

        // Uppdatera/sätter indatavd
        System.out.println("Sätter värden/indata:");

        ssb.setTagValue("*//skapaSarskiltBoende/arg0/behorighetsInformationdosaktor/dosapotekid", dosapotekId);

        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/boendeenhetnamn","PRESTANDA" + se.nhpj.testdata.RndData.getNumbers(10));
        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/boendeenhetid", boendeenhetid);
        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/boendeenhetadress","GATAN" +se.nhpj.testdata.RndData.getNumbers(10));
        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/boendeenhetpostnummer","12345");
        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/boendeenhetort","ORT" + se.nhpj.testdata.RndData.getNumbers(10));
        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/boendeenhetavdelning",se.nhpj.testdata.RndData.getNumbers(10));
        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/arbetsplatskod","888" + se.nhpj.testdata.RndData.getNumbers(10));
        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/dosmottagareid",dosmottagareid);
        ssb.setTagValue("*//skapaSarskiltBoende/arg0/sarskiltboende/dosmottagarenamn", dosmottagare_mottagarnamn);

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + ssb.getSoapEndpointUrl());
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(ssb.getXML()) ) ) );

        // kontrolerar svaret
        ssb.checkResponse(response);

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// SkapaVardtagareDosaktor ---------------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("\n\nSkapaVardtagareDosaktor:\n");
        SkapaVardtagareDosaktor svd = new SkapaVardtagareDosaktor();

        // Anropets URL
        svd.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaVardtagareDosaktorService/V1");
        System.out.println("Sätter endpoint till " + svd.getSoapEndpointUrl());
        soapEndpointUrl = svd.getSoapEndpointUrl();

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + svd.getStandardDefaultFileName());
        svd.setStandardDefaultValues();

        // Uppdatera/sätter indatavd
        System.out.println("Sätter värden/indata:");

        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/Glnkod",skapaVardtagareDosaktor_Glnkod);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/hemmaboende",skapaVardtagareDosaktor_vardtagarinformation_hemmaboende);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/dosapoteksid",skapaVardtagareDosaktor_vardtagarinformation_dosapoteksid);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/dosapoteknamn",skapaVardtagareDosaktor_vardtagarinformation_dosapoteknamn); //Apotek 17"
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/forstadosdag",skapaVardtagareDosaktor_vardtagarinformation_forstadosdag);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/avvikandedosschema",skapaVardtagareDosaktor_vardtagarinformation_avvikandedosschema);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/patientinformation/fornamn",skapaVardtagareDosaktor_vardtagarinformation_patientinformation_fornamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/patientinformation/mellannamn",skapaVardtagareDosaktor_vardtagarinformation_patientinformation_mellannamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/patientinformation/efternamn",skapaVardtagareDosaktor_vardtagarinformation_patientinformation_efternamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/patientinformation/identitetstyp",skapaVardtagareDosaktor_vardtagarinformation_patientinformation_identitetstyp);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/patientinformation/personid",skapaVardtagareDosaktor_vardtagarinformation_patientinformation_personid);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/patientinformation/lanskod",skapaVardtagareDosaktor_vardtagarinformation_patientinformation_lanskod);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/patientinformation/kommunkod",skapaVardtagareDosaktor_vardtagarinformation_patientinformation_kommunkod);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/ordinartboendeinformation/adress",skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_adress);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/ordinartboendeinformation/dosmottagareid",skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagareid);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/ordinartboendeinformation/dosmottagarenamn",skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagarenamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/ordinartboendeinformation/ort",skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_ort);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/ordinartboendeinformation/postnummer",skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_postnummer);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/ordinartboendeinformation/telefon",skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_telefon);

        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/boendeenhetnamn", se.nhpj.testdata.RndData.getChars(10));
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/boendeenhetadress", se.nhpj.testdata.RndData.getChars(10));
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/boendeenhetort", se.nhpj.testdata.RndData.getChars(10));
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/boendeenhetavdelning", se.nhpj.testdata.RndData.getChars(10));
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/arbetsplatskod", se.nhpj.testdata.RndData.getChars(10));
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/dosmottagarenamn", se.nhpj.testdata.RndData.getChars(10));

        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/dosmottagareid", skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagareid );

        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/boendeenhetid",boendeenhetid ); // Från skapa testSarskiltBoende()

        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/tillfalligadress/dosmottagareid",skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagareid );
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/tillfalligadress/adress",se.nhpj.testdata.RndData.getChars(10));
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/tillfalligadress/ort",se.nhpj.testdata.RndData.getChars(10));
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/tillfalligadress/telefon",se.nhpj.testdata.RndData.getNumbers(10));


        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/PALforskrivarkod",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALforskrivarkod);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/PALfornamn",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALfornamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/PALefternamn",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALefternamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/anhorigkontaktnamn",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_anhorigkontaktnamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/anhorigkontaktemail",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_anhorigkontaktemail);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/ansvarigkontaktnamn",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktnamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/ansvarigkontaktemail",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktemail);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/ansvarigkontaktadress",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktadress);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/ansvarigkontaktpostnummer",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktpostnummer);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/ansvarigkontaktpostort",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktpostort);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/ansvarigkontakttelefon1",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontakttelefon1);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/ansvarigkontakttelefon2",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontakttelefon2);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/vardandeenhetid",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetid);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/vardandeenhetnamn",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetnamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/vardandeenhetpostort",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetpostort);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/kontaktinformation/vardandeenhetpostnummer",skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetpostnummer);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/vardtagarstatus/statuskod",skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_statuskod);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/vardtagarstatus/frantid",skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_frantid);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/vardtagarstatus/tilltid",skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_tilltid);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/akut","EJ_AKUT");
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/meddelandetilldosapotek","Prestandatest_100000001");
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/behorigshetsInformationDosaktor/fornamn",skapaVardtagareDosaktor_behorigshetsInformationDosaktor_fornamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/behorigshetsInformationDosaktor/efternamn",skapaVardtagareDosaktor_behorigshetsInformationDosaktor_efternamn);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/behorigshetsInformationDosaktor/legitimationskod",skapaVardtagareDosaktor_behorigshetsInformationDosaktor_legitimationskod);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/behorigshetsInformationDosaktor/yrkesroll",skapaVardtagareDosaktor_behorigshetsInformationDosaktor_yrkesroll);
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/behorigshetsInformationDosaktor/dosapotekid",skapaVardtagareDosaktor_behorigshetsInformationDosaktor_dosapotekid);

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + svd.getSoapEndpointUrl());
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(svd.getXML()) ) ) );

        // kontrolerar svaret
        svd.checkResponse(response);

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// Klar ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("\nKlar :-)\n");
    }

    @Test
    public void loopSkapaBestallningar() throws Exception {
        for (int i = 0; i < 10 ; i++ ) {
            System.out.println("Loop: " + i);
            skapaBestallningar();
        }
    }

    @Test
    public void skapaBestallningar() throws Exception {
        String endpoint = "https://prestanda/";
        String soapEndpointUrl;
        SoapResponseXML response;

        Dosapotek_token dosT            = Dosapotek.rndDosapotek(); // Dosapotek.getDosapotek("7310000000015"); //
        String dosapotekId              = dosT.getDOSAPOTEKID();
//        String dosapotekNamn            = dosT.getDOSAPOTEK();
        String dosmottagareid           = null;
        KommunLandsting kommunlandsting = new KommunLandsting().getSlumpad();
        Skatteverket skatteverket       = new Skatteverket();
        Person patient                  = skatteverket.getSlumpadPerson();
//        Adress adress                   = new Adress().getSwedishAdress();
//        Namn namn                       = new Namn();


// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaVardtagareinformation ------------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("\nHamtaVardtagareinformation\n");
        HamtaVardtagareinformation hv = new HamtaVardtagareinformation();

        // Anropets URL
        hv.setSoapEndpointUrl(endpoint + "sol-service-provider/HamtaVardtagareinformationResponderService/V1");
        System.out.println("Sätter endpoint till " + hv.getSoapEndpointUrl());
        soapEndpointUrl = hv.getSoapEndpointUrl();

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + hv.getStandardDefaultFileName());
        hv.setStandardDefaultValues();

        // Uppdatera/sätter indatavd
        System.out.println("Sätter värden/indata:");

        hv.setTagValue("*//HamtaVardtagareinformation/glnkod", dosapotekId );
        hv.setTagValue("*//HamtaVardtagareinformation/personid", patient.getPersonnummer() );
        // Tar bort taggar som inte ska med
        hv.removeTag("*//Behorighetsinformation/hsaid");
        hv.removeTag("*//Behorighetsinformation/personnummer");
        hv.removeTag("*//Behorighetsinformation/organisationsnummer");

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + hv.getSoapEndpointUrl());
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(hv.getXML()) ) ) );

        // kontrolerar svaret
        hv.checkResponse(response);

        String resultatkod = response.getTagValue("*//resultatkod");
        String patient_dosapoteksid = null;

        System.out.println( "resultatkod: " + resultatkod );
        if (resultatkod.contains("1")) {
            se.nhpj.soap.utils.XmlFormatter formatter = new XmlFormatter();
//            System.out.println(formatter.format(response.getXML()));
            patient_dosapoteksid = response.getTagValue("*//dosapoteksid");
            dosmottagareid = response.getTagValue("*//Hemmaboendeinformation/dosmottagareid");
            System.out.println( "personnummer: " + response.getTagValue("*//personid"));
            System.out.println( "dosapoteksid: " + patient_dosapoteksid );
            System.out.println( "dosmottagareid: " + dosmottagareid );
        }

        if (resultatkod.contains("1")){
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // BestallOriginalforpackning ------------------------------------------------------------------------------------------------------------------------------------------------------------
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            System.out.println("\nBestallOriginalforpackning\n");
            BestallOriginalforpackning bo = new BestallOriginalforpackning();

            // Anropets URL
            bo.setSoapEndpointUrl(endpoint + "sol-service-provider/BestallOriginalforpackningResponderService/V1");
            System.out.println("Sätter endpoint till " + bo.getSoapEndpointUrl());
            soapEndpointUrl = bo.getSoapEndpointUrl();

            // Sätter alla värden från standard propperties filen
            System.out.println("Vilken propperties fil?: " + bo.getStandardDefaultFileName());
            bo.setStandardDefaultValues();

            // Uppdatera/sätter indatavd
            System.out.println("Sätter värden/indata:");

            bo.setTagValue("*//BestallOriginalforpackning/glnkod", patient_dosapoteksid);

            bo.setTagValue("*//BestallOriginalforpackning/Behorighetsinformation/fornamn","Sofia");
            bo.setTagValue("*//BestallOriginalforpackning/Behorighetsinformation/efternamn","Pedersen");
            bo.setTagValue("*//BestallOriginalforpackning/Behorighetsinformation/forskrivarkod","7095797");
            bo.setTagValue("*//BestallOriginalforpackning/Behorighetsinformation/yrkeskod","LK");
            bo.setTagValue("*//BestallOriginalforpackning/Behorighetsinformation/arbetsplatskod","4000000000000");

            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/fornamn", patient.getFornamn());
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/mellannamn", se.nhpj.testdata.RndData.getChars(10));
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/efternamn", patient.getEfternamn());
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/identitetstyp", "P");
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/personid", patient.getPersonnummer());
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/lanskod", kommunlandsting.getKOMMUNKOD());
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/kommunkod", kommunlandsting.getLANSKOD());
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/bestallningsid", se.nhpj.testdata.RndData.getNumbers(36));
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/radid", "1");
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/NPLpackid", "9456418196319");                                // slumpa från någon bra lista ...);
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/receptid", se.nhpj.testdata.RndData.getNumbers(10));        // Borde hämtas från OR
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/ordinationsid", se.nhpj.testdata.RndData.getNumbers(10));   // Borde hämtas från OR
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/antalforpackningar", "1");
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/akutbestallning", "false");
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/maxveckodos", se.nhpj.testdata.RndData.getANumber(7, 21));
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/maxdygnsdos", se.nhpj.testdata.RndData.getANumber(1, 3));
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/dosmottagareid", dosmottagareid);
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/dosmottagarenamn", "PrestandaCentralen " + se.nhpj.testdata.RndData.getNumbers(20));
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/meddelandetillapotek", "PRESTANDATEST");
            bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/onskadleveransdatum", se.nhpj.soap.utils.CurrentDateTime.getTExtraLong());
            // Tar bort taggar som inte ska med
            bo.removeTag("*//BestallOriginalforpackning/Bestallningsinfo/varunummer");
            bo.removeTag("*//BestallOriginalforpackning/Bestallningsinfo/dosunderlagsversion");
            bo.removeTag("*//BestallOriginalforpackning/Behorighetsinformation/hsaid");
            bo.removeTag("*//BestallOriginalforpackning/Behorighetsinformation/personnummer");
            bo.removeTag("*//BestallOriginalforpackning/Behorighetsinformation/organisationsnummer");


            // Gör om XMLSträngen till ett soapMessage
            // Skickar anropet
            // Convert the response to XML response objekt
            System.out.println("Skickar Anropet: " + bo.getSoapEndpointUrl());
            response = new SoapResponseXML(BaseXML.SoapResponseMsgToString(BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(bo.getXML()))));

            // kontrolerar svaret
            bo.checkResponse(response);
        }
    }

    @Test
    public void loopSkickaMeddelanden() throws Exception {
        for (int i = 0; i < 274 ; i++ ) {
            System.out.println("Loop: " + i);
            SkickaMeddelanden();
        }
    }

    @Test
    public void SkickaMeddelanden() throws Exception {
        String endpoint = "https://prestanda/";
        String soapEndpointUrl;
        SoapResponseXML response;

        Dosapotek_token dosT            = Dosapotek.rndDosapotek(); // Dosapotek.getDosapotek("7310000000015"); //
        String dosapotekId              = dosT.getDOSAPOTEKID();
        String dosmottagareid           = null;
        Skatteverket skatteverket       = new Skatteverket();
        Person patient                  = skatteverket.getSlumpadPerson();

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaVardtagareinformation ------------------------------------------------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("\nHamtaVardtagareinformation\n");
        HamtaVardtagareinformation hv = new HamtaVardtagareinformation();

        // Anropets URL
        hv.setSoapEndpointUrl(endpoint + "sol-service-provider/HamtaVardtagareinformationResponderService/V1");
        System.out.println("Sätter endpoint till " + hv.getSoapEndpointUrl());
        soapEndpointUrl = hv.getSoapEndpointUrl();

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + hv.getStandardDefaultFileName());
        hv.setStandardDefaultValues();

        // Uppdatera/sätter indatavd
        System.out.println("Sätter värden/indata:");

        hv.setTagValue("*//HamtaVardtagareinformation/glnkod", dosapotekId );
        hv.setTagValue("*//HamtaVardtagareinformation/personid", patient.getPersonnummer() );
        // Tar bort taggar som inte ska med
        hv.removeTag("*//Behorighetsinformation/hsaid");
        hv.removeTag("*//Behorighetsinformation/personnummer");
        hv.removeTag("*//Behorighetsinformation/organisationsnummer");

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + hv.getSoapEndpointUrl());
        response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(hv.getXML()) ) ) );

        // kontrolerar svaret
        hv.checkResponse(response);

        String resultatkod = response.getTagValue("*//resultatkod");
        String patient_dosapoteksid = null;

        System.out.println( "resultatkod: " + resultatkod );
        if (resultatkod.contains("1")) {
            se.nhpj.soap.utils.XmlFormatter formatter = new XmlFormatter();
//            System.out.println(formatter.format(response.getXML()));
            patient_dosapoteksid = response.getTagValue("*//dosapoteksid");
            dosmottagareid = response.getTagValue("*//Hemmaboendeinformation/dosmottagareid");
            System.out.println( "personnummer: " + response.getTagValue("*//personid"));
            System.out.println( "dosapoteksid: " + patient_dosapoteksid );
            System.out.println( "dosmottagareid: " + dosmottagareid );
        }

        if (resultatkod.contains("1")) {
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // SkickaMeddelanden ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            SkickaMeddelanden sm = new SkickaMeddelanden();

            // Anropets URL
            sm.setSoapEndpointUrl(endpoint + "/sol-service-provider/SkickaMeddelandenResponderService/V1");
            System.out.println("Sätter endpoint till " + sm.getSoapEndpointUrl());
            soapEndpointUrl = sm.getSoapEndpointUrl();

            // Sätter alla värden från standard propperties filen
            System.out.println("Vilken propperties fil?: " + sm.getStandardDefaultFileName());
            sm.setStandardDefaultValues();

            // Uppdatera/sätter indatavd
            System.out.println("Sätter värden/indata:");

            sm.setTagValue("*//SkickaMeddelanden/glnkod", patient_dosapoteksid);
            sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/Patientinformation/personid", patient.getPersonnummer());
            sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/Patientinformation/fornamn", patient.getFornamn());
            sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/Patientinformation/efternamn", patient.getEfternamn());
            sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/glnkod", patient_dosapoteksid);
            sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/sandningstidpunkt", se.nhpj.soap.utils.CurrentDateTime.getTExtraLong()); //"2018-03-01T00:00:00.000+01:00");
            sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/rubrik", "PRESTANDA rubrik " + se.nhpj.testdata.RndData.getNumbers(8));
            sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/prioritet", "L");
            sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/meddelande", "PRESTANDA meddelande " + se.nhpj.testdata.RndData.getNumbers(8));

            // Tar bort taggar som inte ska med

            // Gör om XMLSträngen till ett soapMessage
            // Skickar anropet
            // Convert the response to XML response objekt
            System.out.println("Skickar Anropet: " + sm.getSoapEndpointUrl());
            response = new SoapResponseXML(BaseXML.SoapResponseMsgToString(BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(sm.getXML()))));

            // kontrolerar svaret
            sm.checkResponse(response);
        }
    }
}

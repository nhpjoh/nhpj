package labb;

import org.junit.Before;
import org.junit.Test;
import se.ehm.sol.soap.services.*;
import se.ehm.testdata.SOL.Dosapotek;
import se.ehm.testdata.SOL.Dosapotek_token;
import se.nhpj.soap.services.SkapaDosmottagare;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.testdata.*;


/* När det jävlas med anropen ...
logga in som httpd : httpdT
gå till: cd 2.4.3/virtualhosts
titta i test10_vh.config och kolla ...

 <LocationMatch "^/apisp/.*">
  SSLVerifyClient optional ---->>> none
  SSLVerifyDepth 2
  ProxyPass balancer://eap6
  ProxyPassReverse balancer://eap6
</LocationMatch>

 */

import static se.nhpj.testdata.RndData.rndFrom;

public class Test_SOL {

    // Gemensamma variabler
    private String endpoint = "https://prestanda/";

    private Dosapotek_token dosT            = Dosapotek.getDosapotek("7310000000015"); // Dosapotek.rndDosapotek(); //
    private String dosapotekId              = dosT.getDOSAPOTEKID();
    private String dosapotekNamn            = dosT.getDOSAPOTEK();
    private String dosmottagareid           = "999" + se.nhpj.testdata.RndData.getNumbers(10);
    private KommunLandsting kommunlandsting = new KommunLandsting().getSlumpad();
    private Skatteverket skatteverket       = new Skatteverket();
    private Person patient                  = skatteverket.getSlumpadPerson();
    private Adress adress                   = new Adress().getSwedishAdress();
    private Namn namn                       = new Namn();

    //SkapaDosmottagare
    private String dosmottagare_dosmottagareid          = dosmottagareid;
    private String dosmottagare_adress                  = "Prestandatestargatan " + se.nhpj.testdata.RndData.getNumbers(20);
    private String dosmottagare_apoteksIdDosmottagare   = dosapotekId;//"7350045511997";
    private String dosmottagare_apoteksIdDosproducent   = dosapotekId;//"7350045511997";
    private String dosmottagare_arbetsplatskod          = "KOD " + se.nhpj.testdata.RndData.getNumbers(10);
    private String dosmottagare_avdelning               = "Avdelning " + se.nhpj.testdata.RndData.getNumbers(10);
    private String dosmottagare_kommunkod               = kommunlandsting.getKOMMUNKOD(); //"08";
    private String dosmottagare_lanskod                 = kommunlandsting.getLANSKOD();   //"01";
    private String dosmottagare_mottagarnamn            = "PrestandaCentralen " + se.nhpj.testdata.RndData.getNumbers(20);
    private String dosmottagare_postnummer              = se.nhpj.testdata.RndData.getNumbers(5);
    private String dosmottagare_postort                 = "Prestandastaden" + se.nhpj.testdata.RndData.getNumbers(15);

    //SkapaProduktionsinformationDosaktor
    private String behorighetsinformationDosaktor_dosapotekid               = dosapotekId;
    private String produktionsinformation_dosapotekid                       = dosapotekId;
    private String produktionsinformation_stopptidbestallning               = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2018-10-30T00:00:00.000+01:00";
    private String produktionsinformation_stopptidordination                = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2018-10-30T00:00:00.000+01:00";
    private String produktionsinformation_forstadosdag                      = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2018-10-30T00:00:00.000+01:00";
    private String produktionsinformation_dosvecka                          = rndFrom( new String[]{"U","J","V"} ); // Udda, Jämn, Varje
    private String produktionsinformation_standardschema_periodlangd        = se.nhpj.testdata.RndData.getANumber(1,12);  //"1";
    private String produktionsinformation_standardschema_intagstillfalleKl  = se.nhpj.testdata.RndData.getANumber(10,17); //"12";
    private String produktionsinformation_dosmottagareid                    = dosmottagare_dosmottagareid;

    //SkapaVardtagareDosaktor
    private String skapaVardtagareDosaktor_Glnkod=dosapotekId;
    private String skapaVardtagareDosaktor_vardtagarinformation_hemmaboende                                 = "JA";
    private String skapaVardtagareDosaktor_vardtagarinformation_dosapoteksid                                = dosapotekId;    //"1000000000017";
    private String skapaVardtagareDosaktor_vardtagarinformation_dosapoteknamn                               = dosapotekNamn; //"Apotek 17";
    private String skapaVardtagareDosaktor_vardtagarinformation_forstadosdag                                = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2017-07-17T00:00:00+02:00";
    private String skapaVardtagareDosaktor_vardtagarinformation_avvikandedosschema                          = "NEJ";
    private String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_fornamn                  = patient.getFornamn(); //"Förnamn";
    private String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_mellannamn               = se.nhpj.testdata.RndData.getChars(10); //"MellanNamn";
    private String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_efternamn                = patient.getEfternamn(); //"Efternamn";
    private String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_identitetstyp            ="P";
    private String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_personid                 = patient.getPersonnummer(); //"111111111122";
    private String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_lanskod                  = kommunlandsting.getLANSKOD();   //"01";
    private String skapaVardtagareDosaktor_vardtagarinformation_patientinformation_kommunkod                = kommunlandsting.getKOMMUNKOD(); //"08";
    private String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_adress            = adress.getGatuAdress(); //"Gatan";
    private String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagareid    = dosmottagareid; //"4887152666838";
    private String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagarenamn  = "DosMotNamn " + se.nhpj.testdata.RndData.getChars(10);
    private String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_ort               = adress.getOrt(); //"Orten 1";
    private String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_postnummer        = adress.getPostnummer(); //"10001";
    private String skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_telefon           = "010-" + se.nhpj.testdata.RndData.getNumbers(8);
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALforskrivarkod         = "9674989";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALfornamn               = "PALfornamn";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_PALefternamn             = "PALefternamn";
    private String anhöringFnamn = namn.getFirstNameFemale();
    private String anhöringEnamn = namn.getLastNameTest();
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_anhorigkontaktnamn       = anhöringFnamn + " " + anhöringEnamn; //"Anhörig Förnamn Efternamn";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_anhorigkontaktemail      = anhöringFnamn +"."+ anhöringEnamn + "@prestandatest.se";
    private String ansvarigFnamn = namn.getFirstNameFemale();
    private String ansvarigEnamn = namn.getLastNameTest();
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktnamn      = ansvarigFnamn + " " + ansvarigEnamn; //"Ansvarig Förnamn Efternamn";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktemail     = ansvarigFnamn + "." + ansvarigEnamn + "@prestandatest.se";
    private Adress ansvarigadress = new Adress().getSwedishAdress();
    private String ansvarigPostNr = ansvarigadress.getPostnummer();
    String trimmad = ansvarigPostNr.substring(0,2)+ansvarigPostNr.substring(4,5);
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktadress    = ansvarigadress.getGatuAdress(); //"Ansvarig Adress";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktpostnummer= trimmad; //"57545";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontaktpostort   = ansvarigadress.getOrt(); //"Ansvarig postort";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontakttelefon1  = "070-" + se.nhpj.testdata.RndData.getNumbers(8);
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_ansvarigkontakttelefon2  = "070-" + se.nhpj.testdata.RndData.getNumbers(8);
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetid          = "XXXXX9999999999999"; // denna kommer ifrån SkapaOrdinationsAnsvarigEnhet -> vardandeenhetid  //
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetnamn        = "PrestandaCentralen " + se.nhpj.testdata.RndData.getNumbers(20); //"L";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetpostort     = "Prestandastaden" + se.nhpj.testdata.RndData.getNumbers(15); //"M";
    private String skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetpostnummer  = se.nhpj.testdata.RndData.getNumbers(5); //"83653";
    private String skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_statuskod                   = "AKTIV";
    private String skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_frantid                     = "2017-11-02T00:00:00.000+01:00";
    private String skapaVardtagareDosaktor_vardtagarinformation_vardtagarstatus_tilltid                     = "2022-11-02T00:00:00.000+01:00";
    private String skapaVardtagareDosaktor_akut                                                             = "EJ_AKUT";
    private String skapaVardtagareDosaktor_meddelandetilldosapotek                                          = "Prestandatest_100000001";
    private String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_fornamn                          = "Sofia";
    private String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_efternamn                        = "Pedersen";
    private String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_legitimationskod                 = "709579";
    private String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_yrkesroll                        = "AP";
    private String skapaVardtagareDosaktor_behorigshetsInformationDosaktor_dosapotekid                      = dosapotekId;

    //SkapaSarskiltBoende
    private String boendeenhetid = se.nhpj.testdata.RndData.getNumbers(13);

    //BestallOriginalforpackning
        // Ta reda på patientes dosApotek
        private String BestallOriginalforpackning_glnkod = dosapotekId;

    private String BestallOriginalforpackning_Bestallningsinfo_Patientinformation_fornamn       = patient.getFornamn(); //"Sarah";
    private String BestallOriginalforpackning_Bestallningsinfo_Patientinformation_mellannamn    = se.nhpj.testdata.RndData.getChars(8);
    private String BestallOriginalforpackning_Bestallningsinfo_Patientinformation_efternamn     = patient.getEfternamn(); //"Broberg";
    private String BestallOriginalforpackning_Bestallningsinfo_Patientinformation_identitetstyp = "P";
    private String BestallOriginalforpackning_Bestallningsinfo_Patientinformation_personid      = patient.getPersonnummer(); //"198701022389";
    private String BestallOriginalforpackning_Bestallningsinfo_Patientinformation_lanskod       = dosmottagare_kommunkod;
    private String BestallOriginalforpackning_Bestallningsinfo_Patientinformation_kommunkod     = dosmottagare_lanskod;
    private String BestallOriginalforpackning_Bestallningsinfo_bestallningsid                   = se.nhpj.testdata.RndData.getNumbers(36); //"900007";
    private String BestallOriginalforpackning_Bestallningsinfo_radid                            = "1";
    private String BestallOriginalforpackning_Bestallningsinfo_NPLpackid                        = "9456418196319"; // slumpa från någon bra lista ...
    private String BestallOriginalforpackning_Bestallningsinfo_receptid                         = se.nhpj.testdata.RndData.getNumbers(10);
    private String BestallOriginalforpackning_Bestallningsinfo_ordinationsid                    = se.nhpj.testdata.RndData.getNumbers(10);
    private String BestallOriginalforpackning_Bestallningsinfo_antalforpackningar               = "1";
    private String BestallOriginalforpackning_Bestallningsinfo_akutbestallning                  = "false";
    private String BestallOriginalforpackning_Bestallningsinfo_maxveckodos                      = se.nhpj.testdata.RndData.getANumber(7,21);
    private String BestallOriginalforpackning_Bestallningsinfo_maxdygnsdos                      = se.nhpj.testdata.RndData.getANumber(1,3);
    private String BestallOriginalforpackning_Bestallningsinfo_dosmottagareid                   = dosmottagareid;
    private String BestallOriginalforpackning_Bestallningsinfo_dosmottagarenamn                 = dosmottagare_mottagarnamn;
    private String BestallOriginalforpackning_Bestallningsinfo_meddelandetillapotek             = "PRESTANDATEST";
    private String BestallOriginalforpackning_Bestallningsinfo_onskadleveransdatum              = se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(); //"2018-11-30T00:00:00.000+01:00";


    @Before
    public void setUp() {
    }

    @Test
    public void testSkickaMeddelanden() {
        SkickaMeddelanden sm = new SkickaMeddelanden();

        // Anropets URL
        sm.setSoapEndpointUrl(endpoint + "/sol-service-provider/SkickaMeddelandenResponderService/V1");
        System.out.println("Sätter endpoint till " + sm.getSoapEndpointUrl());
        String soapEndpointUrl = sm.getSoapEndpointUrl();

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + sm.getStandardDefaultFileName());
        sm.setStandardDefaultValues();

        // Uppdatera/sätter indatavd
        System.out.println("Sätter värden/indata:");

        sm.setTagValue("*//SkickaMeddelanden/glnkod",dosapotekId);
        sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/Patientinformation/personid", patient.getPersonnummer() );
        sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/Patientinformation/fornamn",patient.getFornamn());
        sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/Patientinformation/efternamn",patient.getEfternamn());
        sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/glnkod",dosapotekId);
        sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/sandningstidpunkt",se.nhpj.soap.utils.CurrentDateTime.getTExtraLong()); //"2018-03-01T00:00:00.000+01:00");
        sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/rubrik","PRESTANDA rubrik " + se.nhpj.testdata.RndData.getNumbers(8));
        sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/prioritet","L");
        sm.setTagValue("*//SkickaMeddelanden/Meddelandeninfo/meddelande","PRESTANDA meddelande " + se.nhpj.testdata.RndData.getNumbers(8));

        // Tar bort taggar som inte ska med

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + sm.getSoapEndpointUrl());
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(sm.getXML()) ) ) );

        // kontrolerar svaret
        sm.checkResponse(response);
    }

    @Test
    public void testHamtaVardtagareinformation() {
        HamtaVardtagareinformation hv = new HamtaVardtagareinformation();

        // Anropets URL
        hv.setSoapEndpointUrl(endpoint + "sol-service-provider/HamtaVardtagareinformationResponderService/V1");
        System.out.println("Sätter endpoint till " + hv.getSoapEndpointUrl());
        String soapEndpointUrl = hv.getSoapEndpointUrl();

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + hv.getStandardDefaultFileName());
        hv.setStandardDefaultValues();

        // Uppdatera/sätter indatavd
        System.out.println("Sätter värden/indata:");

        hv.setTagValue("*//HamtaVardtagareinformation/glnkod", dosapotekId );
        hv.setTagValue("*//HamtaVardtagareinformation/personid", patient.getPersonnummer() );
//        hv.setTagValue("*//HamtaVardtagareinformation/personid", "199208222399");
        // Tar bort taggar som inte ska med
        hv.removeTag("*//Behorighetsinformation/hsaid");
        hv.removeTag("*//Behorighetsinformation/personnummer");
        hv.removeTag("*//Behorighetsinformation/organisationsnummer");

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + hv.getSoapEndpointUrl());
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(hv.getXML()) ) ) );

        // kontrolerar svaret
        hv.checkResponse(response);

        String resultatkod = null;
        try {
            resultatkod = response.getTagValue("*//resultatkod");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dosapoteksid = null;

        System.out.println( "resultatkod: " + resultatkod );
        if (resultatkod.contains("1")) {
            try {
                dosapoteksid = response.getTagValue("*//dosapoteksid");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println( "dosapoteksid: " + dosapoteksid );

    }

    @Test
    public void testBestallOriginalforpackning() {
        BestallOriginalforpackning bo = new BestallOriginalforpackning();

        // Anropets URL
        bo.setSoapEndpointUrl(endpoint + "sol-service-provider/BestallOriginalforpackningResponderService/V1");
        System.out.println("Sätter endpoint till " + bo.getSoapEndpointUrl());
        String soapEndpointUrl = bo.getSoapEndpointUrl();

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + bo.getStandardDefaultFileName());
        bo.setStandardDefaultValues();

        // Uppdatera/sätter indatavd
        System.out.println("Sätter värden/indata:");

        bo.setTagValue("*//BestallOriginalforpackning/glnkod", BestallOriginalforpackning_glnkod );

        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/fornamn", BestallOriginalforpackning_Bestallningsinfo_Patientinformation_fornamn);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/mellannamn", BestallOriginalforpackning_Bestallningsinfo_Patientinformation_mellannamn);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/efternamn", BestallOriginalforpackning_Bestallningsinfo_Patientinformation_efternamn);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/identitetstyp", BestallOriginalforpackning_Bestallningsinfo_Patientinformation_identitetstyp);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/personid", BestallOriginalforpackning_Bestallningsinfo_Patientinformation_personid);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/lanskod", BestallOriginalforpackning_Bestallningsinfo_Patientinformation_lanskod);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/Patientinformation/kommunkod", BestallOriginalforpackning_Bestallningsinfo_Patientinformation_kommunkod);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/bestallningsid", BestallOriginalforpackning_Bestallningsinfo_bestallningsid);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/radid", BestallOriginalforpackning_Bestallningsinfo_radid);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/NPLpackid", BestallOriginalforpackning_Bestallningsinfo_NPLpackid);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/receptid", BestallOriginalforpackning_Bestallningsinfo_receptid);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/ordinationsid", BestallOriginalforpackning_Bestallningsinfo_ordinationsid);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/antalforpackningar", BestallOriginalforpackning_Bestallningsinfo_antalforpackningar);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/akutbestallning", BestallOriginalforpackning_Bestallningsinfo_akutbestallning);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/maxveckodos", BestallOriginalforpackning_Bestallningsinfo_maxveckodos);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/maxdygnsdos", BestallOriginalforpackning_Bestallningsinfo_maxdygnsdos);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/dosmottagareid", BestallOriginalforpackning_Bestallningsinfo_dosmottagareid);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/dosmottagarenamn", BestallOriginalforpackning_Bestallningsinfo_dosmottagarenamn);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/meddelandetillapotek", BestallOriginalforpackning_Bestallningsinfo_meddelandetillapotek);
        bo.setTagValue("*//BestallOriginalforpackning/Bestallningsinfo/onskadleveransdatum", BestallOriginalforpackning_Bestallningsinfo_onskadleveransdatum);
        // Tar bort taggar som inte ska med
        bo.removeTag("*//BestallOriginalforpackning/Bestallningsinfo/varunummer");
        bo.removeTag("*//BestallOriginalforpackning/Bestallningsinfo/dosunderlagsversion");


        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + bo.getSoapEndpointUrl());
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(bo.getXML()) ) ) );

        // kontrolerar svaret
        bo.checkResponse(response);
        response.logXML();

    }

    @Test
    public void testSkapaDosmottagare() {
        // Skapar anropets XML
        SkapaDosmottagare sd = new SkapaDosmottagare();

        // Anropets URL
        sd.setSoapEndpointUrl(endpoint + "/apisp/SkapaDosmottagareResponder/V4");
        System.out.println("Sätter endpoint till " + sd.getSoapEndpointUrl());
        String soapEndpointUrl = sd.getSoapEndpointUrl();

        // Skapa en ticket
        Tickets ticket = new Tickets();
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
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(sd.getXML()) ) ) );

        // kontrolerar svaret
        sd.checkResponse(response);

        // Skriver ut svaret
        //System.out.println(response.getXML());

        // Leta i XML svaret
//        System.out.println( "Underlagsversion: " + response.getTagValue("*//underlagsversion") );

        // Logga svaret
        //response.logXML();

    }

    @Test
    public void testSkapaProduktionsinformationDosaktor() {
        // Skapar anropets XML
        SkapaProduktionsinformationDosaktor spid = new SkapaProduktionsinformationDosaktor();

        // Anropets URL
        spid.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaProduktionsinformationDosaktorService/V1");
        System.out.println("Sätter endpoint till " + spid.getSoapEndpointUrl());
        String soapEndpointUrl = spid.getSoapEndpointUrl();

        // Skapa en ticket
        Tickets ticket = new Tickets();
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
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(spid.getXML()) ) ) );

        // kontrolerar svaret
        spid.checkResponse(response);

        // Skriver ut svaret
        //System.out.println(response.getXML());

        // Leta i XML svaret
//        System.out.println( "Underlagsversion: " + response.getTagValue("*//underlagsversion") );

        // Logga svaret
        //response.logXML();

    }

    @Test
    public void testSkapaOrdinationsAnsvarigEnhetDosaktor() throws Exception {
        SkapaOrdinationsAnsvarigEnhetDosaktor soaed = new SkapaOrdinationsAnsvarigEnhetDosaktor();

        // Anropets URL
        soaed.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaOrdinationsAnsvarigEnhetDosaktorService/V1");
        System.out.println("Sätter endpoint till " + soaed.getSoapEndpointUrl());
        String soapEndpointUrl = soaed.getSoapEndpointUrl();


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
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(soaed.getXML()) ) ) );

        // kontrolerar svaret
        soaed.checkResponse(response);

        String vardandeenhetid = response.getTagValue("*//vardandeenhetid");
        skapaVardtagareDosaktor_vardtagarinformation_kontaktinformation_vardandeenhetid = vardandeenhetid; // gemensam variabel
        System.out.println("Skapat vardandeenhet med id: " + vardandeenhetid);
    }

    @Test
    public void testSkapaVardtagareDosaktor() {
        SkapaVardtagareDosaktor svd = new SkapaVardtagareDosaktor();

        // Anropets URL
        svd.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaVardtagareDosaktorService/V1");
        System.out.println("Sätter endpoint till " + svd.getSoapEndpointUrl());
        String soapEndpointUrl = svd.getSoapEndpointUrl();

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

        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/sarskiltboendeinformation/boendeenhetid",boendeenhetid ); // Från skapa testSarskiltBoende()
        svd.setTagValue("*//skapaVardtagareDosaktor/arg0/vardtagarinformation/tillfalligadress/dosmottagareid",skapaVardtagareDosaktor_vardtagarinformation_ordinartboendeinformation_dosmottagareid);

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
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(svd.getXML()) ) ) );

        // kontrolerar svaret
        svd.checkResponse(response);

        //response.logXML();
    }

    @Test
    public void testSkapaVardtagare() {
        SkapaVardtagare sv = new SkapaVardtagare();

        // Anropets URL
        sv.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaVardtagareResponderService/V1");
        System.out.println("Sätter endpoint till " + sv.getSoapEndpointUrl());
        String soapEndpointUrl = sv.getSoapEndpointUrl();

        // Sätter alla värden från standard propperties filen
        System.out.println("Vilken propperties fil?: " + sv.getStandardDefaultFileName());
        sv.setStandardDefaultValues();

        // Uppdatera/sätter indatavd
        System.out.println("Sätter värden/indata:");

        // Gör om XMLSträngen till ett soapMessage
        // Skickar anropet
        // Convert the response to XML response objekt
        System.out.println("Skickar Anropet: " + sv.getSoapEndpointUrl());
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(sv.getXML()) ) ) );

        // kontrolerar svaret
        sv.checkResponse(response);
    }

    @Test
    public void testSkapaSarskiltBoende() {
        SkapaSarskiltBoende ssb = new SkapaSarskiltBoende();

        // Anropets URL
        ssb.setSoapEndpointUrl(endpoint + "sol-service-provider/SkapaSarskiltBoendeService/V1");
        System.out.println("Sätter endpoint till " + ssb.getSoapEndpointUrl());
        String soapEndpointUrl = ssb.getSoapEndpointUrl();

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
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest(soapEndpointUrl, BaseXML.getSoapMessageFromString(ssb.getXML()) ) ) );

        // kontrolerar svaret
        ssb.checkResponse(response);

    }

}
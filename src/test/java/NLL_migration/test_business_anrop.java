package NLL_migration;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import se.ehm.testdata.Forskrivare;
import se.nhpj.rest.AccessTokenHandler;
import se.nhpj.rest.BaseRest;
import se.nhpj.rest.RestCall;
import se.nhpj.soap.utils.CurrentDateTime;
import se.nhpj.testdata.*;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class test_business_anrop {

//    private String endpoint_business = "https://nll-businesslogic-test1-deploy2-s9.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-businesslogic-s10-test1-deploy1.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-businesslogic-s10-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-businesslogic-s11-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-businesslogic-s12-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-businesslogic-s13-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-businesslogic-s14-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-businesslogic-s15-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-businesslogic-s16-test1-deploy2.test.ecp.systest.receptpartner.se";
    private String endpoint_business = "https://nll-businesslogic-s17-test1-deploy2.test.ecp.systest.receptpartner.se";

    // Förskrivning //
    @Test
    public void GET_businesslogic_forskrivning_logiskt_ID() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/forskrivning/a2f1750b-d268-46d3-8658-fa8e6edd1428");
        System.out.println(response);
    }

    @Test
    public void GET_businesslogic_forskrivning_personRef() {
        Transaction t = new Transaction();  // Tidtagning
        String response;
        BaseRest br = new BaseRest();

        String UUID_personnummer = test_FHIR_anrop.getPatientUUID("https://nll-fhir-server-s17-test1-deploy2.test.ecp.systest.receptpartner.se", "199310012381" );

        t.start("get"); // Startar klockan

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/forskrivning?personRef=" + UUID_personnummer);
//        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/forskrivning?personRef=2b2049d5-f4c1-401e-a43f-c9c0725dc6b8");

        t.stop("get"); // Stoppar klockan

        System.out.println(response);

        // Lista ut antalet recept för personen
        JSONArray jsonArray = JsonPath.read(response, "$..behandlingsinformation");
        System.out.println("\nAntal recept: " + jsonArray.size());

        System.out.println("RespTime: " + t.getElapsedTime("get", Transaction.MILLISECONDS));  // Skriver ut tiden i millisekunder

    }

    @Test  // ToDo //
    public void PUT_businesslogic_forskrivning_forskrivninga_id() {}

    @Test // Funkar inte i S15/s16
    public void POST_businesslogic_forskrivning() {
        String response;
        BaseRest br = new BaseRest();

        String json = "{" +
                "      'behandlingsinformation' : {" +
//                "         'senasteDatumForAvslut' : '2020-01-31'," +
                "         'senasteDatumForUppfoljning' : '2019-08-31'," +
                "         'behandlingsandamal' : 'Bota huvudvärk'," +


                "    'dosering': {" +
                "      'doseringsstegLista': [" +
                "        {" +
                "          'startdatum': '2019-08-07T10:20:42.228Z'," +
                "          'slutdatum': '2019-08-07T10:20:42.228Z'," +
                "          'tidsangivelse': {" +
                "            'frekvens': 0," +
                "            'klockslag': [" +
                "              '12:00'" +
                "            ]," +
                "            'period': 0," +
                "            'periodEnhet': 'd'," +
                "            'antalUpprepningar': 0," +
                "            'veckodag': [" +
                "              'MAN'" +
                "            ]" +
                "          }," +
//                "          'dos': {" +
//                "            'enhet': 'a'" +
//                "          }," +
                "          'frekvens': 0," +
                "          'sekvens': 0," +
                "          'fritextdosering': 'ibland'," +
                "          'maxDos': {" +
                "            'mangd': 0," +
                "            'mangdEnhet': 'mg'," +
                "            'period': 0," +
                "            'periodEnhet': 'v'" +
                "          }," +
                "          'langd': 0," +
                "          'langdEnhet': 'v'," +
                "          'observandumDoseringOverstigerRek': true," +
                "          'vidBehov': true" +
                "        }" +
                "      ]" +
                "    }," +

                "         'behandlingsorsakList': [ " +
                "          {" +
                "            'snomedCT' : '38341003'," +
                "            'behandlingsorsak' : 'hypertoni'," +
                "            'annanBehandlingsorsak' : 'test'" +
                "          }" +
                "         ]" +
                "      }," +
                "      'patient' : {" +
                "        'typ' : 'PatientMedPersonId'," +
                "        'lakemedelslisteversion' : 0," +
                "        'patientRef' : '4901c1a5-0e00-4846-a277-b898a190dcb4'" +
                "      }," +
                "      'forskrivenResurs' : {" +
                "         'nplPackId' : '20140115100371'," +
                "         'nplId' : '20131024000104'," +
                "         'varunr' : null," +
                "         'produktNamn' : 'Alvedon'" +
                "      }," +
                "      'sparrinformationLista' : [ ]," +
                "      'forskrivningsversion': 0," +
                "      'forskrivningsid' : 0," +
                "      'expedieringsdetaljer' : {" +
                "         'antalForpackningar' : '2'," +
                "         'antalUttag' : 0," +
                "         'sistaGiltighetsdag' : ''," +
                "         'startForpackning' : false," +
                "         'arbetsplatskod' : '0100000000040'," +
                "         'formanstyp' : 'R'," +
                "         'forskrivarensKommentar': 'en om dagen'," +
                "         'substitutionEjTillaten': false," +
                "         'forskrivenMangdPerUttag': 100," +
                "         'uttagKvar': 0," +
                "         'mangdKvar': 0," +
                "         'forskrivenMangd': 400," +
                "         'dosdispensering': false, " +
                "         'patientFormansberattigad' : true " +
                "      }," +
                "      'forskrivare' : {" +
                "         'forskrivarkod' : '1664960'," +
                "         'fornamn' : 'Lars'," +
                "         'efternamn' : 'Lakare'," +
                "         'arbetsplats': {" +
                "            'arbetsplatsIdTyp': 'ORGNR'," +
                "            'namn': 'Sjukhuset'," +
                "            'telefonnummer': {" +
                "               'telefonnummer': '08-12345678'," +
                "               'typ': 'DIREKT'" +
                "            }," +
                "            'adress': {" +
                "                'adress': 'Adressen'," +
                "                'postnummer': '12345'," +
                "                'postort': 'Orten'," +
                "                'typ': 'POST'" +
                "            }" +
                "         }" +
                "      }," +
                "      'lagringstidpunkt' : '2018-12-11T14:29:38.708Z'," +
                "      'utfardandedatum' : '2019-07-30'," +
                "      'status': 'EXPEDIERBAR'" +
                "      }" +
                "}";

        json = json.replace("'", "\"");

        System.out.println(endpoint_business);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/forskrivning", json);
        System.out.println("\n\n"+response);
    }




    // Lakemedelslista //
    @Test
    public void GET_businesslogic_lakemedelslista() {
        String response;
        BaseRest br = new BaseRest();

        String UUID_personnummer = test_FHIR_anrop.getPatientUUID("https://nll-fhir-server-s17-test1-deploy2.test.ecp.systest.receptpartner.se", "200702072380" );

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/lakemedelslista?patientRef=" + UUID_personnummer);
        System.out.println(response);

        // Lista ut antalet recept för personen
        JSONArray jsonArray = JsonPath.read(response, "$..lakemedelslisteversion");
        if (jsonArray.size() > 0 )
            System.out.println("\nlakemedelslisteversion: " + jsonArray.get(0).toString());
        else
            System.out.println("\nlakemedelslisteversion: 0");

    }


    // Fullmakt //
    @Test
    public void POST_businesslogic_fullmakt() {
        String response;
        BaseRest br = new BaseRest();
        String dagensDatum = se.nhpj.soap.utils.CurrentDateTime.getTShort();

        String json = "{" +
                "  'registreringstidpunkt' : '"+ dagensDatum +"'," +
                "  'giltigFrom': '"+ dagensDatum +"'," +
                "  'giltigTom': '2020-05-06T16:41:01.241Z'," +
                "  'logisktId': ''," +
                "  'fullmaktsid': 0," +
                "  'registrerandeOrganisatoriskEnhet': '7350045511999'," +
                "  'underlag': 'DIGITAL'," +
                "  'fullmaktstagare': {" +
                "    'personRef' : 'dca4d27c-1a59-45fd-a888-f10ae5893ed7'" +
                "  }," +
                "  'fullmaktsgivareLista': [" +
                "    {" +
                "      'personRef': '2545dd4f-030f-49bd-9879-60ad4aaef45c'," +
                "      'roll': 'FORVALTARE'" +
                "    }" +
                "  ]," +
                "  'typ': 'HANDEL_PRIVATPERSON'," +
                "  'inlamnadAv': 'GIVARE'," +
                "  'patientRef': 'd2736633-c54c-4885-9eb9-2d31013d15c7'" +
                "}";

        json = json.replace("'", "\"");

        System.out.println(endpoint_business);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/fullmakt", json);

        System.out.println("\n\n"+response);

    }

    @Test
    public void GET_businesslogic_fullmakt() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/fullmakt/5aaef5be-f016-4bd4-8017-0974346e2bfb");
        System.out.println(response);
    }


    // Samtycke //
    @Test
    public void POST_businesslogic_samtycke() {

        POST_businesslogic_samtycke("2b2049d5-f4c1-401e-a43f-c9c0725dc6b8",true);
    }

    public void POST_businesslogic_samtycke(String logisktPersonId, Boolean debug) {
        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        String response;
        BaseRest br = new BaseRest();

        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getInternalAccessToken(AccessTokenHandler.FORSKRIVARE));

        String json = "{" +
                "   'handelse' : {" +
                "      'ansvarig' : {" +
                "         'type' : 'Forskrivare'," +
                "         'forskrivarkod' : '8880999'," +
                "         'anvandarId' : 'string'," +
                "         'arbetsplats' : {" +
                "            'arbetsplatsId' : null," +
                "            'arbetsplatsIdTyp' : 'ORGNR'," +
                "            'arbetsplatskod' : '123'," +
                "            'namn' : 'string'," +
                "            'organisationstyp' : 'VARD'" +
                "         }," +
                "         'efternamn' : 'Läkare'," +
                "         'fornamn' : 'Lars'," +
                "         'yrkeskod' : 'LK'" +
                "      }," +
                "      'beskrivningOrsak' : null," +
                "      'orsak' : null," +
                "      'typ' : 'REGISTRERA_DOSSAMTYCKE'" +
                "   }," +
                "   'patientRef' : '" + logisktPersonId + "'," +
                "   'typ' : 'DOSPATIENT'" +
                "}";

        json = json.replace("'", "\"");

        if(debug) {
            System.out.println(endpoint_business);
            System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
        }
        response = br.postSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/samtycke", json);
        //        System.out.println(br.getOutHeaders());
        System.out.println("\n\n"+response);
    }

    @Test
    public void GET_businesslogic_samtycke_patientRef() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/samtycke/?patientRef=f8fd2b30-a792-402a-a138-c650c34c56a9");
        System.out.println(response);
    }

    @Test
    public void PUT_businesslogic_samtycke() {
        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        String response;
        BaseRest br = new BaseRest();

        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getInternalAccessToken(AccessTokenHandler.FORSKRIVARE));

        String json = "{" +
                "   'id' : 'c02bff1a-d359-45b0-9c80-930145d7e4da'," +
                "   'patientRef' : '4885c634-1328-4f1a-9423-1dcf2132d3bf'," +
                "   'typ' : 'DOSPATIENT'," +
                "   'giltigFrom' : '2019-10-09'," +
                "   'giltigTom' : '2019-12-31'" +
                "}";

        json = json.replace("'", "\"");

            System.out.println(endpoint_business);
            System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.putSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/samtycke/c02bff1a-d359-45b0-9c80-930145d7e4da", json);
        System.out.println("\n\n"+response+"\n\n"+br.getHeaders());
    }

    @Test
    public void GET_businesslogic_samtycke_logisktId() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/samtycke/61d60569-6c29-4dce-83b2-9d927a36ad0e");
        System.out.println(response);
    }

    // Snurra som sätter samtycke på alla skatteverkets personer som ska vara med i dosunderlagstesterna // fixat i LR oxå
    @Test
    public void POST_businesslogic_samtycke_alla() {
        Skatteverket skatteverket = new Skatteverket();
        List<Person> listan = skatteverket.getPersons();

        Iterator itr = listan.iterator();
        int antal = listan.size();

        while (itr.hasNext()) {
            System.out.print((antal--) +" : ");
            Person person = (Person) itr.next();
            System.out.println(person.getPersonnummer());
            String logisktId = test_FHIR_anrop.getPatientUUID("https://nll-fhir-server-s15-test1-deploy2.test.ecp.systest.receptpartner.se", person.getPersonnummer() );
            POST_businesslogic_samtycke(logisktId,false);
        }
    }




    // Dosunderlag //
    @Test
    public void POST_businesslogic_dosunderlag() {
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getInternalAccessToken(AccessTokenHandler.FORSKRIVARE));

        String json = "{" +
                "  'patientRef': '4901c1a5-0e00-4846-a277-b898a190dcb4'," +
                "  'senastAndrad': null," +
                "  'status': 'GODKANT'," +
                "  'version': 0," +
                "  'logisktId': null" +
                "}";

        json = json.replace("'", "\"");

        System.out.println(endpoint_business);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/dosunderlag", json);
        System.out.println("\n\n"+response);
    }

    @Test
    public void GET_businesslogic_dosunderlag_logiskt_ID() {
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getInternalAccessToken(AccessTokenHandler.FORSKRIVARE));

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/dosunderlag/74d60639-519e-4a3d-b1cb-e623c5326d47");
        System.out.println(response);
    }

    @Test
    public void GET_businesslogic_dosunderlag_patientRef() {
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getInternalAccessToken(AccessTokenHandler.FORSKRIVARE));

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/dosunderlag/?patientRef=6988e574-e5e7-4547-9a62-5950b6d7da04");
        System.out.println(response);
    }

    @Test
    public void PUT_businesslogic_dosunderlag() {
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getInternalAccessToken(AccessTokenHandler.FORSKRIVARE));

        String json = "{" +
                "   'patientRef': '2b2049d5-f4c1-401e-a43f-c9c0725dc6b8'," +
//                "   'status' : 'GODKANT'," +
                "   'status' : 'EJ_GODKANT'," +
//                "   'status' : 'AVREGISTRERAD'," +
                "   'version' : '1'," +
                "   'logisktId' : '1f652881-2c0a-41b6-a680-3b7dafb32b28'" +
                "}";
        
        json = json.replace("'", "\"");

        System.out.println(endpoint_business);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.putSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/dosunderlag/1f652881-2c0a-41b6-a680-3b7dafb32b28", json);
        System.out.println("\n\n"+response+"\n\n"+br.getHeaders());
    }



    // Uttag //
    @Test
    public void POST_businesslogic_uttag() {
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getInternalAccessToken(AccessTokenHandler.FARMACEUT));

        String json = "{" +
                "   'uttagList' : [" +
                "      {" +
                "         'aktorExpedieringsId' : 'string'," +
                "         'expedieradMangd' : '30'," +
                "         'expedieradArtikel' : {" +
                "            'nplPackId' : '19880401100022'," +
                "            'nplId' : '19360121000012'" +
                "         }," +
                "         'expedieringstidpunkt' : '2019-11-11'," +
                "         'expeditionsradId' : null," +
                "         'farmaceutsNotering' : 'Notering'," +
                "         'formansval' : 'R'," +
                "         'expedieratAntalForpackningar' : null," +
                "         'forskrivningRef' : 'bb53171a-3c5a-4a5f-99d4-3cd35206cf6c'," +
                "         'slutexpedierad' : false," +
                "         'generiskSubstitutionUtford' : 'false'," +
                "         'type' : 'Dosdispenseringsuttag'," +
                "         'typ' : 'REGISTERA'," +
                "         'handelse' : {" +
                "            'typ': 'EXPEDIERA'," +
                "            'ansvarig' : null," +
                "            'andratAv' : {" +
                "               'type' : 'Farmaceut'," +
                "               'legitimationskod' : '920007'," +
                "               'yrkeskod' : 'AP'," +
                "               'fornamn' : 'Inga-Lill'," +
                "               'efternamn' : 'Ingesson'," +
                "               'arbetsplats' : {" +
                "               'arbetsplatsId' : '7350045511997'," +
                "               'arbetsplatsIdTyp' : 'GLN'," +
                "               'arbetsplatskod' : '123'," +
                "               'kontaktuppgiftList' : [" +
                "                   {" +
                "                   'type' : 'Adress'," +
                "                   'tillgangligForPatient' : true," +
                "                   'adress' : 'Testgatan 1'," +
                "                   'postnummer' : '11122'," +
                "                   'postort' : 'Kalmar'," +
                "                   'typ' : 'BESOK'" +
                "                   }" +
                "               ]," +
                "               'namn' : 'Doris öppenvård'," +
                "               'organisationstyp' : 'APOTEK'" +
                "            }," +
                "            'kontaktmetodLista' : null" +
                "            }" +
                "         }" +

                "      }" +
                "   ]" +
                "}";

        json = json.replace("'", "\"");

        System.out.println(endpoint_business);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/uttag", json);
        System.out.println("\n\n"+response);

    }

    @Test
    public void GET_businesslogic_uttag_forskrivningref() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/uttag?forskrivningRef=66ceacfe-b10e-4af7-bed2-f7cd1520102a");
        System.out.println("\n\n"+response);
    }

    @Test
    public void GET_businesslogic_uttag_logisktId() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/uttag/109a4b9f-6ba4-41e5-af02-e4a2ce619113");
        System.out.println("\n\n"+response);
    }


    // Händelse
    @Test
    public void GET_businesslogic_Handelse() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/forskrivning/8449deee-3727-424c-a977-cb0fd08776d7/handelse");
        System.out.println("\n\n"+response);
    }

    // LFPost //

    // LogicalIdentifier //

    // Vardomsorgsenhet //


    @Test
    public void UUID_oracle_raw() {
        String raw = "AEDFC70F2AFA4B4C9E826F0CCB30F192";
        String string = se.nhpj.rest.RestFormatter.rawUUID2String(raw);

        System.out.println(string);

        System.out.println(se.nhpj.rest.RestFormatter.stringUUID2Raw(string));

        String str = "52295921-8c8f-4db7-bd7b-fd70ba8959dd";
        System.out.println(se.nhpj.rest.RestFormatter.stringUUID2Raw(str));
    }

    /* ---------------------------------------------------------------------------- */
    /* -                                   Flöden                                 - */
    /* ---------------------------------------------------------------------------- */

    @Test
    public void POST_businesslogic_forskrivning_run() {
        Skatteverket skatten = new Skatteverket();
        Transaction transaction = new Transaction();

        for (int i = 0; i < 1; i++) {
            Person person = skatten.getSlumpadPerson();
//            String personnummer = person.getPersonnummer();
            String personnummer = "199802262387";
            System.out.print( "Iter: "+ (i+1) +" : " + personnummer + " : ");
            transaction.start("BL_POST_forskrivning");
                POST_businesslogic_forskrivning_1(personnummer);
            transaction.stop("BL_POST_forskrivning");
            System.out.println(" - Svarstid: " + transaction.getElapsedTime("BL_POST_forskrivning"));
        }
    }

    public void POST_businesslogic_forskrivning_1(String personnummer) {
        String response;
        BaseRest br = new BaseRest();
        String patientRef = test_FHIR_anrop.getPatientUUID("https://nll-fhir-server-s13-test1-deploy2.test.ecp.systest.receptpartner.se",personnummer);

        Forskrivare forskrivare = se.ehm.testdata.ForskrivareHandler.getRndForskrivare("INT28");
        String forskrivarkod = forskrivare.getForskrivarkod();
        String forskrivare_fornamn = forskrivare.getFornamn();
        String forskrivare_efternamn = forskrivare.getEfternamn();

        String json = "{" +
                "      'behandlingsinformation' : {" +
//                "         'senasteDatumForAvslut' : '2020-01-31'," +
                "         'senasteDatumForUppfoljning' : '"+se.nhpj.soap.utils.CurrentDateTime.getDate(+14 )+"'," +
                "         'behandlingsandamal' : 'Bota huvudvärk'," +
                "    'dosering': {" +
                "      'doseringsstegLista': [" +
                "        {" +
                "          'startdatum': '"+ se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(+0) +"Z'," +
                "          'slutdatum': '"+se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(+10)+"Z'," +
                "          'tidsangivelse': {" +
                "            'frekvens': 0," +
                "            'klockslag': [" +
                "              '12:00'" +
                "            ]," +
                "            'period': 0," +
                "            'periodEnhet': 'd'," +
                "            'antalUpprepningar': 0," +
                "            'veckodag': [" +
                "              'MAN'" +
                "            ]" +
                "          }," +
//                "          'dos': {" +
//                "            'enhet': 'a'" +
//                "          }," +
                "          'frekvens': 0," +
                "          'sekvens': 0," +
                "          'fritextdosering': 'ibland'," +
                "          'maxDos': {" +
                "            'mangd': 0," +
                "            'mangdEnhet': 'mg'," +
                "            'period': 0," +
                "            'periodEnhet': 'v'" +
                "          }," +
                "          'langd': 0," +
                "          'langdEnhet': 'v'," +
                "          'observandumDoseringOverstigerRek': true," +
                "          'vidBehov': true" +
                "        }" +
                "      ]" +
                "    }," +

                "         'behandlingsorsakList': [ " +
                "          {" +
                "            'snomedCT' : '38341003'," +
                "            'behandlingsorsak' : 'hypertoni'," +
                "            'annanBehandlingsorsak' : 'test'" +
                "          }" +
                "         ]" +
                "      }," +
                "      'patient' : {" +
                "        'typ' : 'PatientMedPersonId'," +
                "        'lakemedelslisteversion' : 0," +
                "        'patientRef' : '" + patientRef + "'" +
                "      }," +
                "      'forskrivenResurs' : {" +
                "         'nplPackId' : '20140115100371'," +
                "         'nplId' : '20131024000104'," +
                "         'varunr' : null," +
                "         'produktNamn' : 'Alvedon'" +
                "      }," +
                "      'sparrinformationLista' : [ ]," +
                "      'forskrivningsversion': 0," +
                "      'forskrivningsid' : 0," +
                "      'expedieringsdetaljer' : {" +
                "         'antalForpackningar' : '5'," +
                "         'antalUttag' : 0," +
                "         'sistaGiltighetsdag' : ''," +
                "         'startForpackning' : false," +
                "         'arbetsplatskod' : '0100000000040'," +
                "         'formanstyp' : 'R'," +
                "         'forskrivarensKommentar': 'en om dagen'," +
                "         'substitutionEjTillaten': false," +
                "         'forskrivenMangdPerUttag': 100," +
                "         'uttagKvar': 0," +
                "         'mangdKvar': 0," +
                "         'forskrivenMangd': 400," +
                "         'dosdispensering': false, " +
                "         'patientFormansberattigad' : true " +
                "      }," +
                "      'forskrivare' : {" +
                "         'forskrivarkod' : '" + forskrivarkod + "'," +
                "         'fornamn' : '" + forskrivare_fornamn + "'," +
                "         'efternamn' : '" + forskrivare_efternamn + "'," +
                "         'arbetsplats': {" +
                "            'arbetsplatsIdTyp': 'ORGNR'," +
                "            'namn': 'Sjukhuset'," +
                "            'telefonnummer': {" +
                "               'telefonnummer': '08-12345678'," +
                "               'typ': 'DIREKT'" +
                "            }," +
                "            'adress': {" +
                "                'adress': 'Adressen'," +
                "                'postnummer': '12345'," +
                "                'postort': 'Orten'," +
                "                'typ': 'POST'" +
                "            }" +
                "         }" +
                "      }," +
                "      'lagringstidpunkt' : '"+se.nhpj.soap.utils.CurrentDateTime.getTExtraLong()+"Z'," +
                "      'utfardandedatum' : '"+se.nhpj.soap.utils.CurrentDateTime.getTodaysDate()+"'," +
                "      'status': 'EXPEDIERBAR'" +
                "      }" +
                "}";

        json = json.replace("'", "\"");

//        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/forskrivning", json);
        System.out.print(response);
    }

    @Test
    public void labb() {
        System.out.println(se.nhpj.soap.utils.CurrentDateTime.getTodaysDate());
        System.out.println(se.nhpj.soap.utils.CurrentDateTime.getDate(+7));
        System.out.println(se.nhpj.soap.utils.CurrentDateTime.getTExtraLong());
        System.out.println(se.nhpj.soap.utils.CurrentDateTime.getTExtraLong(+7));
    }


    // OLD STUFF //
    @Test // S10
    public void GET_businesslogic_forskrivning_personnummer_S10() {
        Transaction t = new Transaction();  // Tidtagning
        String response;
        BaseRest br = new BaseRest();

        t.start("get"); // Startar klockan

        response = br.getSSL(endpoint_business + "/nll-businesslogic/rest/businesslogic/forskrivning?" +
//                "datumFran=2019-02-10&" +
//                "datumTill=2019-05-29&"
                        "personId=191010189809&" +
                        "personIdTyp=PNR&" +
                        "andamal=EXP"
        );

        t.stop("get"); // Stoppar klockan

        System.out.println(response);

        // Lista ut antalet recept för personen
        JSONArray jsonArray = JsonPath.read(response, "$..behandlingsinformation");
        System.out.println("\nAntal recept: " + jsonArray.size());

        System.out.println("RespTime: " + t.getElapsedTime("get", Transaction.MILLISECONDS));  // Skriver ut tiden i millisekunder

    }

}

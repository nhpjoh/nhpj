package NLL_migration;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import se.nhpj.rest.BaseRest;
import se.nhpj.testdata.Transaction;


public class test_persistence_anrop {

//    private String endpoint_business = "https://nll-persistence-s9-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-persistence-s10-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-persistence-s11-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-persistence-s12-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-persistence-s13-test1-deploy1.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-persistence-s14-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-persistence-s15-dev1-build1.utv.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-persistence-s16-test1-deploy2.test.ecp.systest.receptpartner.se";
    private String endpoint_business = "https://nll-persistence-s17-test1-deploy2.test.ecp.systest.receptpartner.se";

    // LogicalIdentifier //

    // Fullmakt //
    @Test  // Funkar inte ToDo
    public void POST_Persistence_Fullmakt() {
        String response;
        BaseRest br = new BaseRest();

        String json = "{" +
                "  'status': 'Bra_Och_Ha'," +
                "  'handelse': {" +
                "    'tidpunkt': '2019-08-26T11:32:50.731Z'," +
                "    'typ': ''" +
                "  }," +
                "  'giltigFrom': '2019-08-26T11:32:50.731Z'," +
                "  'giltigTom': '2019-08-26T11:32:50.731Z'," +
                "  'logisktId': ''," +
                "  'fullmaktsid': 0," +
                "  'registrerandeOrganisatoriskEnhet': '7350045511999'," +
                "  'underlag': 'DIGITAL'," +
                "  'fullmaktstagare': {" +
                "    'personRef': 'dca4d27c-1a59-45fd-a888-f10ae5893ed7', " +
                "    'enhetRef': '4901c1a5-0e00-4846-a277-b898a190dcb4'," +
                "    'tidpunktSamtycke': '2019-08-26T11:32:50.731Z'" +
                "  }," +
                "  'fullmaktsgivareLista': [" +
                "    {" +
                "      'personRef': '2545dd4f-030f-49bd-9879-60ad4aaef45c'," +
                "      'roll': 'FORVALTARE' ," +
                "      'tidpunktSamtycke': '2019-10-28T11:50:51.125Z'" +
                "    }" +
                "  ]," +
                "  'typ': 'HANDEL_PRIVATPERSON'," +
                "  'inlamnadAv': 'GIVARE'," +
                "  'patientRef': 'd2736633-c54c-4885-9eb9-2d31013d15c7'" +
                "}";

        json = json.replace("'", "\"");

        System.out.println(endpoint_business);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint_business + "/nll-persistence/rest/nllstore/fullmakt", json);

        System.out.println("\n\n"+response);

    }
    
    @Test
    public void GET_Persistence_Fullmakt_Search() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/fullmakt/search?personRef=4901c1a5-0e00-4846-a277-b898a190dcb4&fullmaktTyp=HANDEL_PRIVATPERSON");
        System.out.println(response);
    }

    @Test
    public void GET_Persistence_Fullmakt_logisktId() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/fullmakt/4901c1a5-0e00-4846-a277-b898a190dcb4");
        System.out.println(response);
    }

    
    // Dosunderlag //
    @Test
    public void GET_Persistence_Dosunderlag_logisktId() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/dosunderlag/74d60639-519e-4a3d-b1cb-e623c5326d47");
        System.out.println(response);
    }

    @Test
    public void GET_Persistence_Dosunderlag_personRef() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/dosunderlag?dosproducent=7350045511997&patientRef=2A5757DA-7F59-4D62-B2E1-6BABB3E661F4");
        System.out.println(response);
    }

    
    // Uttag //
    @Test
    public void GET_Persistence_Uttag() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/uttag?forskrivningRef=E016BF44-BA0D-4227-BF17-C37C0AB39F8A");
        System.out.println(response);
    }


    // Samtycke //
    @Test
    public void GET_Persistence_Samtycke_patientRef() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/samtycke?patientRef=94EB5E01-EDE1-475B-A10E-AC1575147951");
        System.out.println(response);
    }
    @Test
    public void GET_Persistence_Samtycke_logisktId() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/samtycke/F98BE64A-E87A-46EE-AA07-7BA363ACACFD");
        System.out.println(response);
    }


    // Forskrivning //
    @Test
    public void GET_Persistence_Forskrivning_logisktId() {
        Transaction transaction = new Transaction();
        String response;
        BaseRest br = new BaseRest();

        transaction.start("get");
//        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/forskrivning/01c3eff9-7646-4b53-9008-efd2f69bdb4c"); // S17-Deploy1
        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/forskrivning/61fd45c0-bb2f-4ace-97aa-2acab7e9d522"); // S17-Deploy2
//        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/forskrivning/15E593ED-282F-4B2D-9B6A-8111BF4A9FA8"); // S16
        transaction.stop("get");
        System.out.println(response + " : " + transaction.getElapsedTime("get"));

    }

    @Test
    public void GET_Persistence_Forskrivning_search() {
        Transaction transaction = new Transaction();
        String response;
        BaseRest br = new BaseRest();

        String UUID_personnummer = test_FHIR_anrop.getPatientUUID("https://nll-fhir-server-s17-test1-deploy2.test.ecp.systest.receptpartner.se", "200702072380" );

        transaction.start("anrop");
//        response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/forskrivning/search?personRef="+UUID_personnummer+"&sokEndastSistaRevision=true");
            response = br.getSSL(endpoint_business + "/nll-persistence/rest/nllstore/forskrivning/search?personRef=abbe5e4b-948e-40cd-8309-d2d468771324&sokEndastSistaRevision=true");
        transaction.stop("anrop");

        System.out.println(response);

        // Lista ut antalet recept för personen
        JSONArray jsonArray = JsonPath.read(response, "$..behandlingsinformation");
        System.out.println("\nAntal recept: " + jsonArray.size() + " : Tid: " + transaction.getElapsedTime("anrop"));
    }

}



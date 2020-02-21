package NLL_migration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.minidev.json.JSONObject;
import se.nhpj.LoadRunner.*;

import org.junit.Test;
import se.nhpj.rest.AccessTokenHandler;
import se.nhpj.rest.BaseRest;
import se.nhpj.rest.RestFormatter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//import static org.junit.Assert.*;

public class Test_Stores {

    String endpoint = "test28.systest.receptpartner.se:19443";

    // ARKO //
    @Test
    public void test_get_arkostore_kontrolleraArbetsplatsGiltighet() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/arkostore-mgr/rest/arkostore/kontrolleraArbetsplatsGiltighet?arbetsplatskod=4000000000000");

        String response = br.get();

        System.out.println( "Response: " + response );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            System.out.println(node.get("giltig").asText());
        }
    }
    @Test
    public void test_post_arkostore_kontrolleraArbetsplatsGiltighet() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/arkostore-mgr/rest/arkostore/kontrolleraArbetsplatsGiltighet");

        String s  = "{'arbetsplatskod' : '4000000000000','datum' : '" + se.nhpj.soap.utils.CurrentDateTime.getTExtraLong() + "'}";
        s         = s.replace("'","\"");

        br.setPostParam(s);

        String response = br.post();

        System.out.println( "Response: " + response );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            System.out.println(node.get("giltig").asText());
        }
    }

    // EXPO //
    @Test
    public void test_post_expostore_expoHamtaService() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/expostore-mgr/rest/expostore/expoHamtaService");

        String s  = "{'id' : {'glnKod' : '7350045511119' }}";  // 7310915374165 // 7350045511119 //
        s = s.replace("'","\"");

        br.setPostParam(s);
        String response = br.post();

        System.out.println( "Response: " + response );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            System.out.println(node.findPath("status").asText());
        }
    }
    @Test
    public void test_post_expostore_hamtaGlnLank() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/expostore-mgr/rest/expostore/hamtaGlnLank");

        String s  = lr.eval_string("{'glnKod' : '7350045511119' }");
        s = s.replace("'","\"");

        br.setPostParam(s);
        lr.start_transaction("hamtaGlnLank");
            String response = br.post();
        lr.end_transaction("hamtaGlnLank",lr.AUTO);

        lr.output_message( "Response:\n" + RestFormatter.prettyPrint(response) );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            lr.output_message(node.findPath("glnLankId").asText());
        }
    }

    // FOLK //
    @Test /* Denna returnerar inte personnumret jag skickar in utan jag får null tillbaka ?*/
    public void test_post_folkstore_hamtaPatientInformation() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/folkstore-mgr/rest/folkstore/hamtaPatientInformation");

        String s  = "{ 'personnummer' : '194412279129' }";

        s = s.replace("'","\"");

        br.setPostParam(s);

        System.out.println(br.getUrl());

        String response = br.post();

        System.out.println( "Response: " + se.nhpj.rest.RestFormatter.prettyPrint(response) );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            System.out.println("personnummer: " + node.findPath("personnummer").asText());
        }
    }

    @Test /* Denna returnerar inte personnumret jag skickar in utan jag får null tillbaka ?*/
    public void test_post_folkstore_hamtaFolkbokInformation() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/folkstore-mgr/rest/folkstore/hamtaFolkbokInformation");

        String s  = "{ 'personnummer' : '201203052392' }";

        s = s.replace("'","\"");

        br.setPostParam(s);
        String response = br.post();

        System.out.println( "raw-Response: " + response );
        System.out.println( "pretty-Response: " + se.nhpj.rest.RestFormatter.prettyPrint(response) );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            System.out.println("personnummer: " + node.findPath("personnummer").asText());
        }
    }
    @Test
    public void test_post_folkstore_hamtaFullmaktPrivatpersonInternal() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/folkstore-mgr/rest/folkstore/hamtaFullmaktPrivatpersonInternal");

        String s  = lr.eval_string("{'apotekId' : '7350045511119', 'persNr' : '199401182382', 'vhavPersNr' : '', 'fullmaktval' : 'A', 'fullmaktstatus': '1'}");

        s = s.replace("'","\"");

        br.setPostParam(s);
        lr.start_transaction("hamtaFullmaktPrivatpersonInternal");
        String response = br.post();
        lr.end_transaction("hamtaFullmaktPrivatpersonInternal",lr.AUTO);

        lr.output_message( "Response: " + response );

        if (!response.toLowerCase().contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            lr.output_message("fullmakter: " + node.findPath("fullmakter").asText());
        }
    }

    @Test
    public void test_post_folkstore_hamtaFullmaktPrivatperson() throws IOException {
        BaseRest br = new BaseRest();
        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();

        br.setUrl("https://"+endpoint+"/folkstore-mgr/rest/folkstore/hamtaFullmaktPrivatperson");
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getInternalAccessToken(AccessTokenHandler.PRIVATPERSON));

        String s  = lr.eval_string("{'apotekId' : '7350045511119', 'persNr' : '199102102382', 'vhavPersNr' : '', 'fullmaktval' : 'A', 'fullmaktstatus': '1'}");

        s = s.replace("'","\"");

        br.setPostParam(s);
        lr.start_transaction("hamtaFullmaktPrivatperson");
            String response = br.post();
        lr.end_transaction("hamtaFullmaktPrivatperson",lr.AUTO);

        lr.output_message( "Response: " + response );

        if (!response.toLowerCase().contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            lr.output_message("fullmakter: " + node.findPath("fullmakter").asText());
        }
    }



    // hamtaFolkbokInformationBasService, hamtaFolkbokInformationUtokadService, uppdateraFolkSamtyckenService //


    // FORS //
    @Test
    public void test_post_forsstore_kontrolleraForskrivare() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/forsstore-mgr/rest/forsstore/kontrolleraForskrivare");

        String s  = "{'forskrivarkod' : '1111202' }";
        s = s.replace("'","\"");

        br.setPostParam(s);
        String response = br.post();

        System.out.println( "Response: " + response );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            System.out.println(node.get("forskrivarkod").asText());
        }
    }
    @Test
    public void test_get_forsstore_kontrolleraForskrivare() throws IOException {
        BaseRest br = new BaseRest();

        br.setUrl("https://"+endpoint+"/forsstore-mgr/rest/forsstore/kontrolleraForskrivare?forskrivarkod=123456"); // 1111202

        String response = br.get();

        System.out.println( "Response: " + response );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            System.out.println(node.get("forskrivarkod").asText());
        }
    }
    @Test
    public void test_post_forsstore_hamtaLegitimationsInformationInternal() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/forsstore-mgr/rest/forsstore/hamtaLegitimationsInformationInternal");

        String s  = lr.eval_string("{ 'forskrivarIdLista': [ {'personnummer' : '190609029814' }, { 'forskrivarkod' : '1111202' } , { 'legitimationskod' : '600000' } ], 'administrativSokning' : 'true' }");

        s = s.replace("'","\"");

        br.setPostParam(s);
        lr.start_transaction("hamtaLegitimationsInformationInternal");
            String response = br.post();
        lr.end_transaction("hamtaLegitimationsInformationInternal",lr.AUTO);

        lr.output_message( "Response: " + RestFormatter.prettyPrint( response ) );

        if (!response.toLowerCase().contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            Integer rc = node.findPath("legitimationsinformationInternalLista").size();
            lr.output_message(rc.toString());
        }


    }
    @Test
    public void test_post_forsstore_kontrolleraLegitimationInternal() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/forsstore-mgr/rest/forsstore/kontrolleraLegitimationInternal");

//        String s  = "{ 'personnummer' : '190608189809' }";    // OK - Tandläkare Sanna
        String s  = "{ 'legitimationskod' : '123456' }";      // 900138 deslegitimerad
//        String s  = "{ 'forskrivarkod' : '1111517' }";        // OK - Barnmorskan Lina

        s = s.replace("'","\"");

        br.setPostParam(s);
        lr.start_transaction("kontrolleraLegitimationInternal");
            String response = br.post();
        lr.end_transaction("kontrolleraLegitimationInternal",lr.AUTO);

        lr.output_message( "Response: " + response );

        if (!response.toLowerCase().contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            lr.output_message(node.get("namn").asText());
        }
    }
//    @Test // ToDo
//    public void test_post_forsstore_hamtaGruppbehorighetskoderInternal() throws IOException {
//        BaseRest br = new BaseRest();
//        br.setUrl("https://"+endpoint+"/forsstore-mgr/rest/forsstore/hamtaGruppbehorighetskoderInternal");
//
//        String s  = "{ 'yrkeskod' : 'AP' }";
//
//        s = s.replace("'","\"");
//
//        br.setPostParam(s);
//        lr.start_transaction("hamtaGruppbehorighetskoderInternal");
//            String response = br.post();
//        lr.end_transaction("hamtaGruppbehorighetskoderInternal",lr.AUTO);
//
//        lr.output_message( "Response: " + response );
//
//        if (!response.toLowerCase().contains("error")) {
//            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
//            lr.output_message(node.get("namn").asText());
//        }
//    }


    // VARA //
    @Test
    public void test_post_vara() throws IOException {
        BaseRest br = new BaseRest();
        br.setUrl("https://"+endpoint+"/varastore-mgr/rest/varastore/hamtaArtikel");

        String s  = "{ 'artikelIdentiteter' : [{ 'nplPackageId' : '20080407101023' , 'varunr' : '' , 'gtin' : '' }]}";
        s = s.replace("'","\"");

        br.setPostParam(s);
        String response = br.post();

        System.out.println( "Response: " + response );

        if (!response.contains("error")) {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(response);
            System.out.println(node.findPath("nplPackageId").asText());
        }
    }


/* TODO */
    // PRIS //
    @Test
    public void test_get_prisstore() {}
    @Test
    public void test_post_prisstore() {}

    // ADR //
    @Test
    public void test_get_adrstore() {}
    @Test
    public void test_post_adrstore() {}

    // GT //
    @Test
    public void test_get_gtstore() {}
    @Test
    public void test_post_gtstore() {}

    // NKOO //
    @Test
    public void test_get_nkoostore_hamtaBehandlingsorsak() {}
    @Test
    public void test_post_nkoostore_hamtaBehandlingsorsak() {}


}
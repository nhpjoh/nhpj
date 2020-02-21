package NLL_migration;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import se.nhpj.database.DB_Access;
import se.nhpj.rest.BaseRest;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class remove_recept {

    private String endpoint = "https://nll-fhir-server-s16-test1-deploy2.test.ecp.systest.receptpartner.se";
    private String NLL_SERVICENAME = "NLLTEST1DEPLOY2S16";
    private String STP_SERVICENAME = "TEST28";

    public void remove_all_STP(String servicename, String personnummer) {
    // call RR_PROD.CLEANUP.REMOVEALLPNR(191010189809); i STP miljön
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/"+servicename, "ETCDBA", "ETCDBA");
        DB_Access.getResultSet(con,"call RR_PROD.CLEANUP.REMOVEALLPNR(" + personnummer +")");
        DB_Access.closeConnection(con);
    }

    public void remove_all_NLL(String servicename, String raw_personid) {
    // exec NLL_DATA.TESTSUPPORT.radera_forskr_patient_med_personid('4885C63413284F1A94231DCF2132D3BF'); i NLL(SCP) miljön
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/"+servicename, "ETCDBA", "ETCDBA");
        DB_Access.getResultSet(con,"call NLL_DATA.TESTSUPPORT.radera_forskr_patient_med_personid('" + raw_personid +"')");
        DB_Access.closeConnection(con);
    }

    public String getPatitentUUID(String personnummer) {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint + "/fhir-server/fhir/Patient/?_format=json&identifier=" + personnummer );

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(response);
        JSONArray a = JsonPath.read(document, "$..resource.id");
        return (String) a.get(0);
    }

    @Test
    public void test_remove_all_NLL() {
        Skatteverket skatten = new Skatteverket();
        ArrayList<Person> listan = (ArrayList)skatten.getPersons();
        Iterator iter = listan.iterator();

        while(iter.hasNext()) {
            Person person = (Person) iter.next();
            System.out.print("Tar bort recept för ... " + person.getPersonnummer() + " ... ");

            String personUUID = getPatitentUUID(person.getPersonnummer());
            personUUID = personUUID.replace("-","").toUpperCase();

            System.out.print(personUUID);
            remove_all_NLL(NLL_SERVICENAME,personUUID);
            System.out.println(" ... borttaget i " + NLL_SERVICENAME);
        }
    }

    @Test
    public void test_remove_all_reverse_NLL() {
        Skatteverket skatten = new Skatteverket();
        ArrayList<Person> listan = (ArrayList)skatten.getPersons();

        for (int i = listan.size(); i > 0; i--) {
            Person person = (Person) listan.get(i-1);
            System.out.print("Tar bort recept för ... " + person.getPersonnummer() + " ... ");
            String personUUID = test_FHIR_anrop.getPatientUUID(endpoint,person.getPersonnummer()).replace("-","").toUpperCase();
            System.out.print(personUUID);
            remove_all_NLL(NLL_SERVICENAME,personUUID);
            System.out.println(" ... borttaget i " + NLL_SERVICENAME);
        }
    }

    @Test
    public void test_remove_all_STP() {
        Skatteverket skatten = new Skatteverket();
        List listan = skatten.getPersons();
        Iterator iter = listan.iterator();

        while(iter.hasNext()) {
            Person person = (Person) iter.next();
            System.out.print("Tar bort recept för ... " + person.getPersonnummer() + " ... ");
            remove_all_STP(STP_SERVICENAME, person.getPersonnummer());
            System.out.println(" borttaget!");
        }
    }

    @Test
    public void test_remove_all_reverse_STP() {
        Skatteverket skatten = new Skatteverket();
        List listan = skatten.getPersons();

        for (int i = listan.size(); i > 0; i--) {
            Person person = (Person) listan.get(i-1);
            System.out.print("Tar bort recept för ... " + person.getPersonnummer() + " ... ");
            remove_all_STP(STP_SERVICENAME, person.getPersonnummer());
            System.out.println(" borttaget!");
        }
    }

    @Test
    public void test_getPatitentUUID() {
        Skatteverket skatten = new Skatteverket();
        ArrayList<Person> listan = (ArrayList)skatten.getPersons();
        Iterator iter = listan.iterator();

        while(iter.hasNext()) {
            Person person = (Person) iter.next();

            String pnr =  person.getPersonnummer();

            System.out.println(pnr);

            String uuid =  getPatitentUUID(pnr);

            System.out.println(uuid);
        }
    }

}
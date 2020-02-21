package NLL_migration;

import org.junit.Test;
import se.nhpj.rest.AccessTokenHandler;
import se.nhpj.rest.BaseRest;

public class test_web_persistence_client_anrop {

    private String endpoint_business = "https://nll-web-persistence-testadmin-s17-test1-deploy1.test.ecp.systest.receptpartner.se";
//    private String endpoint_business = "https://nll-web-persistence-webbingang-s17-test1-deploy1.test.ecp.systest.receptpartner.se";

    @Test
    public void test_get_apotek() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-web-backend/api/apotek");
        System.out.println(response);
    }

    @Test
    public void test_get_apotek_glnkod() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-web-backend/api/apotek/7350045511065");
        System.out.println(response);
    }

    @Test
    public void test_get_lakemedel_nplpackid() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-web-backend/api/lakemedel/20010123100017");
        System.out.println(response);
    }

    // ToDo
    @Test
    public void test_get_lakemedel() {
        String response;
        BaseRest br = new BaseRest();

        //https://nll-web-testadmin-s17-test1-deploy1.test.ecp.systest.receptpartner.se/testadmin/api/lakemedel?type=godkanda&query=enalapri&criteria=namn
        //https://nll-web-testadmin-s17-test1-deploy1.test.ecp.systest.receptpartner.se/testadmin/api/lakemedel?type=godkanda&query=alvedon&criteria=namn
        response = br.getSSL(endpoint_business + "/nll-web-backend/api/lakemedel/?type=godkanda&query=enalapri&criteria=namn");
        System.out.println(response);
    }


    @Test
    public void test_get_ordinationsorsakandamal_nplId() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-web-backend/api/ordinationsorsakandamal/20080730000024");
        System.out.println(response);
    }

    // ToDo
    @Test
    public void test_get_acconunt() {}
    @Test
    public void test_get_acconunt_accountid_patient() {}
    @Test
    public void test_get_acconunt_accontid_forskrivare() {}


    @Test
    public void test_ping() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_business + "/nll-web-backend/api/ping");
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(response));
    }


}

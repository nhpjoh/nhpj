package NLL_migration;

import org.junit.Test;
import se.nhpj.rest.BaseRest;

public class test_Kodverk_anrop {

    private String endpoint_kodverk = "https://nll-kodverk-logic-s17-test1-deploy2.test.ecp.systest.receptpartner.se";

    @Test
    public void get_ValueSet() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/ValueSet");
        System.out.println(response);
    }
    @Test
    public void get_ValueSet_nll_valueset_administreringsvag() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/ValueSet/nll-valueset-administreringsvag");
        System.out.println(response);
    }
    @Test
    public void get_ValueSet_nll_valueset_behandlingsorsak() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/ValueSet/nll-valueset-behandlingsorsak");
        System.out.println(response);
    }
    @Test // Funkar inte i LoadRunner
    public void get_ValueSet_nll_valueset_edward_history() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/ValueSet/nll-valueset-edward/_history/");
        System.out.println(response);
    }
    @Test
    public void get_ValueSet_nll_valueset_behandlingsorsak_validate() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/ValueSet/nll-valueset-behandlingsorsak/$validate-code/?code=54891000052104");
        System.out.println(response);
    }
    @Test
    public void get_ValueSet_ValueSet_nll_valueset_administreringsvag() {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/ValueSet/nll-valueset-administreringsvag/$validate-code/?code=372450004");
        System.out.println(response);
    }



    // ---------------------------------OLD-------------------------------OLD-----------------------------OLD-----------------
//    @Test
//    public void get_CodeSystem_id() {
//        String response;
//        BaseRest br = new BaseRest();
//
//        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/CodeSystem/nll-behandlingsorsak");
//        System.out.println(response);
//    }
//
//    @Test
//    public void get_CodeSystem_name() {
//        String response;
//        BaseRest br = new BaseRest();
//
//        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/CodeSystem/?name=NKOO%20Behandlingsorsak");
//        System.out.println(response);
//    }
//
//    @Test
//    public void get_ValueSet_id() {
//        String response;
//        BaseRest br = new BaseRest();
//
//        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/ValueSet/62014003");
//        System.out.println(response);
//    }
//
//    @Test
//    public void get_ValueSet() {
//        String response;
//        BaseRest br = new BaseRest();
//
//        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/ValueSet");
//        System.out.println(response);
//    }
//
//    @Test
//    public void get_OperationDefinition_id() {
//        String response;
//        BaseRest br = new BaseRest();
//
//        response = br.getSSL(endpoint_kodverk + "/kodverk/fhir/OperationDefinition/1");
//        System.out.println(response);
//    }
}

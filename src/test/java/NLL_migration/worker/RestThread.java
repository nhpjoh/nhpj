package NLL_migration.worker;

import se.nhpj.rest.BaseRest;
import se.nhpj.testdata.Transaction;

public class RestThread extends Thread {

    private static Integer  iter = 0;
    private static String   latest;
    private static String   response = null;
    private Transaction transaction = new Transaction();

    public void     addIterations() { iter = iter + 1; }
    public Integer  getIterations() { return iter; }
    public String   getLatest() { return latest; }
    public String   getLatestResponse() { return response; }


    // Startar tråden //
    public void run(){
        BaseRest br = new BaseRest();

        for( int i=0 ; i < 100 ; i++) {
            transaction.start("Transaction");

            response = br.getSSL("https://nll-kodverk-logic-s14-test1-deploy2.test.ecp.systest.receptpartner.se/kodverk/fhir/CodeSystem/nll-behandlingsorsak");

            transaction.stop("Transaction");
            addIterations();
            latest = transaction.getElapsedTime("Transaction");
            Paceing.pace(250);
        }
    }
}

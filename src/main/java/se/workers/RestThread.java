package se.workers;

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

            response = br.getSSL("https://petstore.swagger.io/v2/store/inventory");

            transaction.stop("Transaction");
            addIterations();
            latest = transaction.getElapsedTime("Transaction");
            Paceing.pace(250);
        }
    }
}

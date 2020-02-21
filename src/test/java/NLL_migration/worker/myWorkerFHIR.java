package NLL_migration.worker;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import se.nhpj.rest.AccessTokenHandler;
import se.nhpj.rest.BaseRest;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;
import se.nhpj.testdata.Transaction;

public class myWorkerFHIR extends Worker {
//    private static Integer allWorkerIterations = 0;
//    private static String allWorkerLatestResponseTime;
//    private static Integer workerIterations = 10;
//    private static String allWorkerLatestResponse = null;
//    private static Integer workerPaceingTime = 1000;
//    private static String transactionName = "Worker";       // Transaktionsnamnet
//    private String  transFileName = worker.out;             // Filnamn var transaktionerna ska sparas


    @Override
    public void run() {
        String response;
        BaseRest br = new BaseRest();
        Transaction transaction = new Transaction();
        Skatteverket skatteverket = new Skatteverket();
        for (int i = 0; i < this.getWorkerIterations(); i++) {
            Person person = skatteverket.getSlumpadPerson();

            AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
            br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

            transaction.start(this.getTransactionName());
            // Add your fantastic code here //
                response = br.getSSL("https://nll-fhir-server-s14-test1-deploy2.test.ecp.systest.receptpartner.se/fhir-server/fhir/MedicationRequest/?status=active&patient.identifier=" + person.getPersonnummer());
            transaction.stop(this.getTransactionName());

            JSONArray jsonArray = JsonPath.read(response, "$..total");

            if (getTransactionLogging() == true) {
                transaction.logElapsedTime(this.getTransactionName(),this.getTransactionLogFile()) ;
            }
            else {
                System.out.println("Workers Working! : Person: " + person.getPersonnummer() + " har " + jsonArray.get(0) + " recept : Svartsid: " + transaction.getElapsedTime(this.getTransactionName()));
            }
            addAllWorkerIteration();
            setAllWorkerLatestResponseTime(transaction.getElapsedTime(this.getTransactionName()));
            setAllWorkerLatestResponse("Still working!");
            Paceing.pace(this.getWorkerPaceingTime());
        }
        setAllWorkerLatestResponse("Done working!");
    }
}

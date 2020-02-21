package NLL_migration.worker;

import se.nhpj.rest.BaseRest;
import se.nhpj.testdata.Transaction;

public class myWorker extends Worker {
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
        this.setTransactionName("Worker1");
        for (int i = 0; i < this.getWorkerIterations(); i++) {

            transaction.start(this.getTransactionName());
            // Add your fantastic code here //
                Integer antal = se.nhpj.testdata.RndData.getNumber(1,50);
//                response = br.get("http://192.168.37.129:8080/nhpj/formular_testnummer.jsp?antal=" + antal + "&knapp=H%C3%A4mta");
                Paceing.pace(antal);
            transaction.stop(this.getTransactionName());

            if (getTransactionLogging() == true) {
                transaction.logElapsedTime(this.getTransactionName(),this.getTransactionLogFile()) ;
            }
            else {
                System.out.println("Workers Working! : Antal TestNummer: " + antal + " Svartsid: " + transaction.getElapsedTime(this.getTransactionName()));
            }
            addAllWorkerIteration();
            setAllWorkerLatestResponseTime(transaction.getElapsedTime(this.getTransactionName()));
            setAllWorkerLatestResponse("Still working!");
            Paceing.pace(this.getWorkerPaceingTime());
        }
        setAllWorkerLatestResponse("Done working!");
    }
}

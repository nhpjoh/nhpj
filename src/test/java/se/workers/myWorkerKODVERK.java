package se.workers;


import se.nhpj.rest.BaseRest;
import se.nhpj.testdata.Transaction;
import se.workers.transaktionlogger.Row;
import se.workers.transaktionlogger.RowHandler;

public class myWorkerKODVERK extends Worker {

    @Override
    public void run() {
        String response;
        BaseRest br = new BaseRest();
        Transaction transaction = new Transaction();
        RowHandler rowHandler = new RowHandler();
        this.setTransactionName(this.getClass().getName());

        for (int i = 0; i < this.getWorkerIterations(); i++) {

            transaction.start(this.getTransactionName());
                // Add your fantastic code here //
                response = br.getSSL("https://nll-kodverk-logic-s18-test1-deploy2.test.ecp.systest.receptpartner.se/kodverk/fhir/ValueSet/nll-valueset-administreringsvag");

            transaction.stop(this.getTransactionName());

            if ( getTransactionLogging() ) {
                rowHandler.appendRow(this.getTransactionLogFile(),new Row(this.getTransactionName(), transaction.getElapsedTime(this.getTransactionName())));
            }
            else {
                System.out.println("Workers Working! : Svartsid: " + transaction.getElapsedTime(this.getTransactionName()));
            }
            addAllWorkerIteration();
            setAllWorkerLatestResponseTime(transaction.getElapsedTime(this.getTransactionName()));
            setAllWorkerLatestResponse("Still working!");
            Paceing.pace(this.getWorkerPaceingTime());
        }
        setAllWorkerLatestResponse("Done working!");
    }
}

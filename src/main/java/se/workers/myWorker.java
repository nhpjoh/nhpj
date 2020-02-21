package se.workers;


import se.workers.transaction.Transaction;
import se.workers.transaktionlogger.Row;
import se.workers.transaktionlogger.RowHandler;

public class myWorker extends Worker {

    @Override
    public void run() {
        RowHandler rowHandler = new RowHandler();
        Transaction transaction = new Transaction();
        setTransactionName(this.getClass().getName());

        for (int i = 0; i < getWorkerIterations(); i++) {

            transaction.start( getTransactionName() );
                // Add your fantastic code here //

                Integer antal = se.nhpj.testdata.RndData.getNumber(1,100);
                Paceing.pace( antal );

            transaction.stop( getTransactionName() );

            if ( getTransactionLogging()) {
                rowHandler.appendRow( getTransactionLogFile() ,new Row( getTransactionName() , transaction.getElapsedTime( getTransactionName() )));
            }
            else {
                System.out.println("Workers Working! : Svartsid: " + transaction.getElapsedTime( getTransactionName() ));
            }
            addAllWorkerIteration();
            setAllWorkerLatestResponseTime(transaction.getElapsedTime( getTransactionName() ));
            setAllWorkerLatestResponse("Still working!");
            Paceing.pace( getWorkerPaceingTime() );
        }
        setAllWorkerLatestResponse("Done working!");
    }
}

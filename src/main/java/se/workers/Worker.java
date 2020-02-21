package se.workers;

import se.workers.transaction.Transaction;
import se.workers.transaktionlogger.Row;
import se.workers.transaktionlogger.RowHandler;

abstract class Worker extends Thread {

    private static Integer     allWorkerIterations = 0;
    private static String      allWorkerLatestResponseTime;
    private static Integer     workerIterations = 10;
    private static String      allWorkerLatestResponse = null;
    private static Integer     workerPaceingTime = 1000;
    private static String      transactionName = "Worker";                  // Transaktionsnamnet
    private        String      transactionLogFile = "c:/temp/worker.out";   // Filnamn var transaktionerna ska sparas
    private        Transaction transaction = new Transaction();
    private        Boolean     transLogging = false;

    public void     addAllWorkerIteration() { allWorkerIterations = allWorkerIterations + 1; }
    public Integer  getAllWorkerIteration() { return allWorkerIterations; }

    public void     setWorkerIterations(Integer WorkerIterations) { workerIterations = WorkerIterations; }
    public Integer  getWorkerIterations() { return workerIterations; }

    public void     setTransactionName( String name ) { transactionName = name; }
    public String   getTransactionName() { return transactionName; }

    public void     setAllWorkerLatestResponse(String time) { allWorkerLatestResponse = time; }
    public String   getAllWorkerLatestResponse() { return allWorkerLatestResponse; }

    public void     setAllWorkerLatestResponseTime(String time) { allWorkerLatestResponseTime = time; }
    public String   getAllWorkerLatestResponseTime() { return allWorkerLatestResponseTime; }

    public void     setWorkerPaceingTime( Integer millis) { workerPaceingTime = millis; }
    public Integer  getWorkerPaceingTime() { return workerPaceingTime; }

    public void     setTransactionLogFile(String name) {transactionLogFile = name; }
    public String   getTransactionLogFile() {return transactionLogFile; }

    public void     setTransactionLogging( Boolean value) { this.transLogging = value;}
    public Boolean  getTransactionLogging() { return this.transLogging;}

    @Override
    public void run() {
//        implemet your version of this method and the WorkerHandler can start it :-)

        // Example
        RowHandler rowHandler = new RowHandler();
        for (int i = 0; i < getWorkerIterations(); i++) {
            transaction.start(this.getTransactionName());

                // Add your fantastic code here //
                System.out.println("Worker Working!");

            transaction.stop(this.getTransactionName());
            //transaction.logElapsedTime(this.getTransactionName(),this.getTransactionLogFile());
            rowHandler.appendRow(this.getTransactionLogFile(),new Row(this.getTransactionName(), transaction.getElapsedTime(this.getTransactionName())));
            addAllWorkerIteration();
            if ( getTransactionLogging() ) { setAllWorkerLatestResponseTime( transaction.getElapsedTime(this.getTransactionName()) ); }
            setAllWorkerLatestResponse("Still working!");
            Paceing.pace(getWorkerPaceingTime());
        }
        setAllWorkerLatestResponse("Done working!");
    }

}
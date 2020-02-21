package NLL_migration.worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerHandler {
    private Integer numberOfWorkers = null;                 // Antalet Workers
    private Integer workerIterations = null;                // Antalet anrop per worker
    private Integer workerThinkTime = null;                 // Hur lång tid mellan anropen
    private Integer startPace = null;                       // Hur långt mellanrum mellan varje worker som ska startas i millisekunder
    private String  workerClass = null;                     // Worker klassen som ska köras
    private String  transactionLogFile = null;              // Filnamn var transaktionerna ska sparas
    private Boolean transactionLogging = false;             // slå på och av transactions loggning till fil

//    private String  transactionName = "Worker";           // Transaktionsnamnet

    public void startWorker(){}
    public void startWorker( String aWorkerClass )
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, Exception {

        if(this.numberOfWorkers == null) { throw new Exception("NumberOfWorkers not set"); }
        if(this.startPace == null) { throw new Exception("StartPace not set"); }

        // Skapar listan som håller i alla trådar
        List<Worker> workers = new ArrayList<>();

        Class theWorker = Class.forName(aWorkerClass);

        for ( int i = 0 ; i < this.numberOfWorkers ; i++) {
            Worker w = (Worker) theWorker.newInstance();
            if(this.transactionLogFile != null) { w.setTransactionLogFile(this.transactionLogFile); }
            if(this.workerIterations != null) { w.setWorkerIterations(this.workerIterations); }
            if(this.workerThinkTime != null) { w.setWorkerPaceingTime(this.workerThinkTime); }
            w.setTransactionLogging(this.transactionLogging);
            w.start();
            System.out.println("Worker " +(i+1)+ " Startad!");
            workers.add(w);
            Paceing.pace(this.startPace);
        }
        System.out.println("All started!!!");
        int doneWorkers = 0;
        for (Worker working : workers) {
            try {
                working.join();
                System.out.println("Worker " + (doneWorkers++) + " - " + working.getWorkerIterations()+ " terations done!");
            } catch (InterruptedException e) {
                System.out.println("inteupted");
            }
        }
        Worker wCheck =(Worker) theWorker.newInstance();
        System.out.println( "Totalt körda iterationer: " + wCheck.getAllWorkerIteration() + " : Sista svarstiden: " + wCheck.getAllWorkerLatestResponseTime() + " : Sista svaret: " + wCheck.getAllWorkerLatestResponse());
    }

    public void     setWorkerIterations(Integer iterations) { this.workerIterations = iterations; }
    public void     setWorkerThinkTime(Integer millis) { this.workerThinkTime = millis; }
    public void     setStartPace(Integer millis) { this.startPace = millis; }
    public void     setNumberOfWorkers(Integer antal) {this.numberOfWorkers = antal;}
    public void     setTransactionLogFile(String fullFileName) { this.transactionLogFile = fullFileName; }
    public String   getTransactionLogFile() { return this.transactionLogFile; }
    public void     setTransactionLogging( Boolean value) { this.transactionLogging = value;}
    public Boolean  getTransactionLogging() { return this.transactionLogging;}

}

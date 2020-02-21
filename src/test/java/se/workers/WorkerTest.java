package se.workers;

import org.junit.Test;

public class WorkerTest {
    @Test
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException, Exception{
        WorkerHandler workers = new WorkerHandler();
        workers.setNumberOfWorkers(10);
        workers.setWorkerIterations(25);
        workers.setWorkerThinkTime(50);
        workers.setStartPace(700);
        workers.setTransactionLogFile("myWorkers1.json");
        workers.setTransactionLogging(true);
        workers.startWorker("se.workers.myWorker");
    }
}

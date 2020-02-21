package NLL_migration.worker;

import org.junit.Test;

public class WorkerTest {
    @Test
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException, Exception {
        WorkerHandler workers = new WorkerHandler();
        workers.setNumberOfWorkers(10);
        workers.setWorkerIterations(10);
        workers.setWorkerThinkTime(100);
        workers.setStartPace(700);
        workers.setTransactionLogFile("/Users/patrik.johansson/slask/worker1.log");
        workers.setTransactionLogging(true);
        workers.startWorker("NLL_migration.worker.myWorker");
    }
}

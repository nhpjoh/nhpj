package se.workers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Test_RestThread {

    // Start av 2 trådar
    @Test
    public void variant1() {
        RestThread tråd = new RestThread();
        tråd.start();

        for (int i = 0; i < 30; i++) {

            Paceing.pace(2500);
            System.out.println(tråd.getIterations() + " : "  + tråd.getLatest() );
        }
        System.out.println("\nKlar " + tråd.getIterations() + " : "  + tråd.getLatest()  + " \nSista svaret: " + tråd.getLatestResponse());
    }




















    // Startar 10 trådar //
    @Test
    public void variant2() {
        List<RestThread> tradar = new ArrayList<>();
        for ( int i = 0 ; i < 10 ; i++) {
            RestThread tråd = new RestThread();
            tråd.start();
            System.out.println("Tråd " +(i+1)+ " Startad!");
            tradar.add(tråd);
        }

        for (RestThread restThread : tradar) {
            try {
                restThread.join();
                System.out.println("Tråd klar");
            } catch (InterruptedException e) {
                System.out.println("inteupted");
            }
        }
        RestThread rtCheck = new RestThread();
        System.out.println( rtCheck.getIterations() + " : " + rtCheck.getLatest() + " : " + rtCheck.getLatestResponse());
    }

}

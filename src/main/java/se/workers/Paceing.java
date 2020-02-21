package se.workers;

public class Paceing {

    public static void pace(Integer millis) {
        try
        { Thread.sleep(millis); }
        catch(InterruptedException ex)
        { Thread.currentThread().interrupt(); }
    }
}

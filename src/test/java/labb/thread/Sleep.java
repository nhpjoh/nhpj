package labb.thread;

public class Sleep {

    public static void sleep(Integer millis) {
        try
        { Thread.sleep(millis); }
        catch(InterruptedException ex)
        { Thread.currentThread().interrupt(); }
    }
}

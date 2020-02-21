package se.nhpj.LoadRunner;

import se.nhpj.testdata.Transaction;

public class lr {
    public static Integer AUTO=0;
    public static void   output_message(String s) { System.out.println(s); }
    public static void   error_message(String s) { System.out.println(s); }
    public static void   start_transaction(String s) { System.out.println("starts_transaction: " + s); }
    public static void   end_transaction(String s, int i) { System.out.println("end_transaction: " + s); }
    public static String eval_string(String s) {return s;}
    public static void   think_time(Double tid) {
        try {
            tid=tid*1000;
            Thread.sleep(tid.intValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

}

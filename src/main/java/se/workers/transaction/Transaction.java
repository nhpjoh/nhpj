package se.workers.transaction;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Denna class används för att mäta/klocka svarstider
 * Ett exempel:
 * Transaction t = new Transaction();
 * t.start("transaktionsnamn");
 * ... kod som ska mätas
 * t.stop("transaktionsnamn");
 *
 * System.out.println("Svarstid: " + t.getElapsedTime("transaktionsnamn",Transaction.SECONDS));
 */
public class Transaction {

    private HashMap<String,Long[]> transar = new HashMap<>();

    public static final Integer MILLISECONDS = 0;
    public static final Integer SECONDS = 1;
    public static final Integer MINUTES = 2;

//    /**
//     * Konstruktor. Skapar en instans av Transaktions classen som har ett antal metoider för att klocka tider.
//     */
//    public void Transaction() {}


    /**
     * Denna metod startar klockan på "namn" transaktionen
     * @param namn Transaktionsnamn
     */
    public void start(String namn) {
        Long[] tid = new Long[]{System.currentTimeMillis(), (long) -1};
        transar.put(namn, tid);
    }

    /**
     * Denna metod stoppar klockan på "namn" transaktionen
     * @param namn Transaktionsnamn
     */
    public void stop(String namn) {
        try {
            Long[] tid = transar.get(namn);
            tid[1] = System.currentTimeMillis();
            transar.replace(namn, tid);
        } catch ( NullPointerException ex ) {
            System.err.println("Error: 6 - Hittar inte en transaktion med detta namn\n");
        }
    }

    /**
     * Denna metod returnerar skillnaden i millisekunder mellan start och stoptiden
     * @param namn Transaktionsnamnet
     * @param typ presentation av tiden (MILLISECONDS,SECONDS,MINUTES)
     * @return Long millisekunder
     */
    public Float getElapsedTime(String namn, Integer typ) {
        try {
            Long[] tid = transar.get(namn);
            Long retVal = tid[1] - tid[0];
            switch (typ) {
                case 0:
                    return retVal.floatValue();
                case 1:
                    return retVal.floatValue() / 1000;
                case 2:
                    return (retVal.floatValue() / 1000) / 60;
                default:
                    return retVal.floatValue();
            }
        } catch ( NullPointerException ex) {
            System.err.println("Error: 6 - Hittar inte en transaktion med detta namn\n");
            ex.printStackTrace();
        }
        return (float) -1;
    }

    /**
     * Denna metod returnerar svarstiden på transaktionen i sekunder
     * @param namn Transaktionsnamnet
     * @return String med svarstid
     */
    public String getElapsedTime(String namn) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(3);
        return numberFormat.format( getElapsedTime(namn,SECONDS) );
    }

    /**
     * Denna metod skriver skillnaden i millisekunder mellan start och stoptiden till en fil "filnamn".
     * I filen kommer det att stå: "transaktionsnamn";svarstid
     * @param namn  Transaktionsnamnet
//     * @param typ presentation av tiden (MILLISECONDS,SECUNDS,MINUTES)
     * @param filnamn namn på loggfilen
     */
    public void logElapsedTime(String namn, String filnamn) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        String datetime = sdf.format(new Date());

        String tid = getElapsedTime(namn);
        FileWriter fw = null;
        try {
            fw = new FileWriter(filnamn, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(datetime + ";" + namn + ";" + tid);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


package se.nhpj.testdata;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Denna klass hanterar personnummer
 *
 * @author nhpj
 */

public class Personnummer {
    private String nr;

    /**
     * Denna metod konverterar ett tecken i en String med siffror till en int
     * @param str String med siffror
     * @param i sifferteckenet att konvertera
     * @return returnerar sifferteknet som en int
     */
    public static int toInt(String str, int i) {
        if (Character.isDigit(str.charAt(i)))
            return Integer.parseInt(str.substring(i, i + 1));
        else
            throw new IllegalArgumentException();
    }

    /**
     * Konstruktor
     */
    public Personnummer() {
    }

    public String getPersonnummer() {
        return this.nr;
    }

    /**
     * Denna konstruktor tar ett personnummer och kontrollerar om det är ett riktigt. <br>
     * Skickar IllegalArgumentException om det inte är ett riktigt personnummer
     *
     * @param s - personnummer
     * @throws IllegalArgumentException - Genereras när personnummret är felaktigt
     */
    public void setPersonnummer(String s) {
        // Kontrollera att numret är korrekt
        String y = "";
        if (s.length() > 11) {
            y = s.substring(0, 2);
            s = s.substring(2, s.length());
        }
        int k = s.indexOf('-');
        if (k != 6 || s.length() != 11)
            throw new IllegalArgumentException();
        String t = s.substring(0, k) + s.substring(k + 1);   // ta bort tecknet '-'
        // beräkna kontrollsumman
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int tal = toInt(t, i);
            int j = tal * (2 - i % 2);   // multiplicera med 2 eller 1
            sum += j / 10 + j % 10;      // addera siffrorna i resultatet till summan
        }
        sum %= 10;
        if ((toInt(t, 9) + sum) % 10 != 0)
            throw new IllegalArgumentException();
        // Korrekt!
        if (y.length() > 0) {
            s = y + s;
        }
        nr = s;
    }

    /**
     * Denna method returnerar personnummret som en String
     * @return String personnummer
     */
    @Override
    public String toString() {
        return nr;
    }

    /**
     * Denna metod returnerar true om personnummret är en kvinna
     * @return boolean true/false
     */
    public boolean isKvinna() { return toInt(nr, 11) % 2 == 0; }
    public static boolean isKvinna(String pnr) { return toInt(pnr, 11) % 2 == 0; }

    /**
     * Denna metod returnerar true om personnummret är en man
     * @return boolean true/false
     */
    public boolean isMan() { return !isKvinna(); }
    public static boolean isMan(String pnr) { return !isKvinna(pnr); }

    private String getMonth() {
        String mm = "mm";
        Double i_mm = 0.0;
        while (i_mm < 1.0 || i_mm > 12.0) {
            i_mm = Math.random() * 100;
            mm = Integer.toString(i_mm.intValue());
        }
        if (i_mm.intValue() < 10) {
            mm = "0" + mm;
        }
        return mm;
    }

    private String getDay(String month) {
        Double days = 0.0;
        Integer m = Integer.parseInt(month);
        if (m == 2) {
            days = 28.0;
        }
        if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            days = 31.0;
        } else {
            days = 30.0;
        }
        String dd = "dd";
        Double i_dd = 0.0;
        while (i_dd < 1.0 || i_dd > days) {
            i_dd = Math.random() * 100;
            dd = Integer.toString(i_dd.intValue());
        }
        if (m == 2 && i_dd > 28) { // Dubbelkoll //
            dd = "28";
        }
        if (i_dd.intValue() < 10) {
            dd = "0" + dd;
        }
        return dd;
    }

    private String getYear(String min, String max) {
        String yyyy = "yyyy";
        Double i_yyyy = 0.0;
        while (i_yyyy < Double.parseDouble(min) || i_yyyy > Double.parseDouble(max)) {
            i_yyyy = Math.random() * 10000;
            yyyy = Integer.toString(i_yyyy.intValue());
        }
        return yyyy;
    }

    /**
     * Denna metod slumpar fram ett personnummer mellan två årtal (min/max)
     *
     * @param min - minsta årtalet
     * @param max - högsta årtalet
     * @return - Ett personnummer
     */
    public String generatePnr(Integer min, Integer max) {
        String mm = this.getMonth();
        String födelsedatum = this.getYear(min.toString(), max.toString()) + mm + this.getDay(mm) + "-" + RndData.getNumbers(3);
        Personnummer p = new Personnummer();
        boolean funkar = false;
        int i = 0;
        while (!funkar) {
            try {
                p.setPersonnummer(födelsedatum + i);
                funkar = true;
            } catch (IllegalArgumentException ex) {
                i++;
                funkar = false;
            }
        }
        return p.toString();
    }


    public static String generateOrgNr() {
        // Första två siffrorna för Aktiebolag är 55
        // Andra två siffrorona ska vara större 20
        // Om det ska vara 12 siffror så börjar ett organitiationsnummer med 16

        String orgNr = "55" + RndData.getNumber(2000,9999);
        String extra3 = RndData.getNumbers(3);
        String slutsiffra = Luhn.CalcNext(orgNr+extra3);

        return orgNr+extra3+slutsiffra;
    }

}


package se.nhpj.testdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  I denna klass finns ett antal olika hjälp metoder för att skapa testdata.
 *  @author nhpj
 *  @version 0.03
 */
public class RndData {
    
    /**
     * This is a constructor of this class
     */
    public RndData() {}
    
    /**
     * Denna metod returnera en slumpmässig rad från den fil som anges
     * @param filename - Namnet på filen
     * @return Rad från fil
     */
    public static String fromFile(String filename) {
        String result = null;
         try {
            File f = new File(filename);
            Random rand = new Random();
            int n = 0;

            for(Scanner sc = new Scanner(f); sc.hasNext(); )
            {
                ++n;
                String line = sc.nextLine();
                if(rand.nextInt(n) == 0)         
                    result = line;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RndData.class.getName()).log(Level.SEVERE, null, ex);
        }
      return result;      
    } 
    
    /**
     * Denna metod returnerar en stäng med "antal" versala bokstäver 
     * @param antal - Antal bokstäver
     * @return Sträng med "antal" versala bokstäver
     */
    public static String getChars(int antal) {
        int rnd=-1;
        int loop = -1;
        String chars="";
        
        ArrayList kod = new ArrayList();
        kod.add("A"); kod.add("B"); kod.add("C"); kod.add("D"); kod.add("E"); kod.add("F"); kod.add("G"); kod.add("H"); kod.add("I"); 
        kod.add("J"); kod.add("K"); kod.add("L"); kod.add("M"); kod.add("N"); kod.add("O"); kod.add("P"); kod.add("Q"); kod.add("R"); 
        kod.add("S"); kod.add("T"); kod.add("U"); kod.add("V"); kod.add("X"); kod.add("Y"); kod.add("Z"); kod.add("Å"); kod.add("Ä"); 
        kod.add("Ö");

        for ( int i = 0; i < antal; i++) {
	        while (loop != 0) {
	           double d_rnd = Math.random() * 100;
	           rnd = (int)d_rnd;
	           if (rnd < kod.size() && rnd >= 1 ) { loop = 0; }
	        } 
		   loop = -1;
	        chars = chars + kod.get(rnd);
        }
		return chars;
    }

     /**
     * Denna metod returnerar en stäng med "antal" Siffror 
     * @param antal - Antal siffror
     * @return Sträng med "antal" siffror
     */
    public static String getNumbers(int antal) {
        int rnd=-1;
        int loop = -1;
        String numbers="";
        
        ArrayList kod = new ArrayList();
        kod.add("0"); kod.add("1"); kod.add("2"); kod.add("3"); kod.add("4"); 
        kod.add("5"); kod.add("6"); kod.add("7"); kod.add("8"); kod.add("9");

        for ( int i = 0; i < antal; i++) {
	        while (loop != 0) {
	           double d_rnd = Math.random() * 100;
	           rnd = (int)d_rnd;
	           if (rnd < kod.size() && rnd >= 1 ) { loop = 0; }
	        } 
		   loop = -1;
	        numbers = numbers + kod.get(rnd);
        }
		return numbers;
    }

    /**
     * Denna metod returnerar ett slumpat tal mellan "min" och max"
     * @param min Lägst tal
     * @param max Högst tal
     * @return String innehållande det slumpade talet
     */
    public static String getANumber(int min, int max) {
        if (min == max) {return String.valueOf(min);}
        int i_rnd=-1;
        int loop = -1;

        while (loop != 0) {
           double d_rnd = Math.random() * max;
           i_rnd = (int)d_rnd;
           if (i_rnd <= max && i_rnd >= min ) { loop = 0; }
        } 
        return String.valueOf(i_rnd);
    }  
    
    /**
     * Denna metod returnerar ett slumpat tal mellan "min" och max"
     * @param min Lägst tal
     * @param max Högst tal
     * @return int innehållande det slumpade talet
     */
    public static int getNumber(int min, int max) {
        int i = -1;
        int loop = -1;
        Random rand = new Random();
        while ( loop != 0) {
            double d = rand.nextDouble()* max;
            i = (int)d;
            if ( i < max && i > min) { loop = 0; }
        }
        return i; 
    }

    /**
     * Denna metod returnerar en stäng med "antal" ASCII bokstäver (mellan ascii 20 och 126)
     * @param antal - ANtal tecken
     * @return Sträng med "antal" versala ASCII tecken
     */
    public static String getRandomASCIIString(int antal) {
        Integer siffra;
        String chars="";
        for (int i = 0; i < antal; i++) {
            siffra = getNumber(32, 126);
            char c = (char)siffra.intValue();
            chars = chars + c;
        }
        return chars;
    }

    /**
     * Denna metod returnerar en slupad sträng från en String[] array
     * @param lista String[] .. ex  {"A","B","C","D"}
     * @return String
     */
    public static String rndFrom(String[] lista) {
        Random rand = new Random();
        double d = rand.nextDouble()* lista.length;
        return lista[(int)d];
    }
    
}

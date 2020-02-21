/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nhpj.testdata;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Denna klass hanterar slumpade telefonnummer av olika slag...
 * @author nhpj
 */
public class Telefonnummer {
    
    /**
     * Denna metod returnerar ett slumpat telefonnummer med riktummer från den "kommun" som skickas med som parameter
     * @param kommun Kommun från vilket riktnummret ska tas
     * @return Slumpat telefonnummer innehållande ett riktnummer
     */
    public String getTelefonnummer(String kommun) {
        String prefix = getRiktnummer(kommun);
        if (kommun.compareToIgnoreCase("mobil") == 0) { return this.getMobilNummer(); }
        
        return prefix + "-" + RndData.getNumbers(8);
    }
    
    /**
     * Denna metod returnerar ett slumpat mobilnummer
     * @return Sträng med ett mobilnummer
     */
    public static String getMobilNummer() {
        int rnd=-1;
        int loop = -1;
        
        ArrayList kod = new ArrayList();
        kod.add("070"); kod.add("072"); kod.add("073"); kod.add("076"); kod.add("079"); 
        
        while (loop != 0) {
           double d_rnd = Math.random() * 100;
           rnd = (int)d_rnd;
           if (rnd < kod.size() && rnd >= 0 ) { loop = 0; }
        } 
        return kod.get(rnd).toString() +"-"+ RndData.getNumbers(8);
    }

    /**
     * Denna metod returnerar ett riktnummer från en viss ort. Om orten är Testorten svaret blir svaret '0999' 
     * @param ort - Orten i adressen
     * @return Sträng med ett riktnummer
     */
    public String getRiktnummer(String ort) {
        if (ort.contains("Testorten")) { return "0999"; }
        HashMap hm = new HashMap();
        String retVal = "-1";
        Scanner s;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/riktnummer.dat");
        s = new Scanner(inStream);
        while (s.hasNext()){
            String s1 = s.next();
            String[] s2 = s1.split(";");
            hm.put(s2[1],s2[0]);
        }
        s.close();
        try {
            retVal = hm.get(ort).toString();
        } catch (NullPointerException ex) { /* Do nothing */ }
        return retVal;
    }

}

package se.nhpj.testdata;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * Denna klass innehåller ett antal metoder för att skapa för- och efter- namnstestdata
 * @author nhpj
 */
public class Namn {
    
    /**
     * Denna metod returnerar ett manligt förnamn
     * @return Manligt förnamn som en Sträng
     */
    public String getFirstNameMale() {
        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/herr.dat");
        
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
      return result;      
    }
    /**
     * Denna metod returnerar ett kvinligt förnamn
     * @return Kvinligt förnamn som en Sträng
     */
    public String getFirstNameFemale() {
        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/dam.dat");
        
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
      return result;      
    }
    /**
     * Denna metod returnerar ett Test-efternamn
     * @return Testefternamn som en sträng
     */
    public String getLastNameTest() {
        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/test_efternamn.dat");
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
      return result;      
    }
    /**
     * Denna metod returnerar ett Svenskt efternamn
     * @return Efternamn som en sträng
     */
    public String getLastNameSwedish() {
        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/svenska_efternamn.dat");
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
      return result;      
    }

}

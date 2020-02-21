package se.nhpj.xml;

/**
 *
 * Java program to compare two XML files using XMLUnit example 
 * @author nhpj
 */

import java.io.IOException; 
import java.io.Reader; 
import java.io.StringReader;
import java.util.Iterator;
import java.util.List; 
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;
import se.nhpj.xml.xmlunit.Diff;
import se.nhpj.xml.xmlunit.DetailedDiff;
import se.nhpj.xml.xmlunit.Difference;


public class XMLComparator {
    /**
     * Denna metod är en intern metod
     * @param s
     * @return Reader
     */
    private static Reader ReadXMLString( String s) {
        Reader r = new StringReader(s);
        return r;
    }

    /**
     * Denna metod jämför två XML strängar
     * @param source XML sträng
     * @param target XML sträng
     * @return List Returnerar en lista med skillnader
     */
    public static List compareXML(String source, String target) {
        return compareXML(ReadXMLString(source), ReadXMLString(target));
    }

    /**
     * Denna metod jämför två XML strängar
     * @param source Reader
     * @param target Reader
     * @return List Returnerar en lista med skillnader
     */
    public static List compareXML(Reader source, Reader target){
        DetailedDiff detailXmlDiff = null;
        try {
        //creating Diff instance to compare two XML files
        Diff xmlDiff = new Diff(source, target);
        
        //for getting detailed differences between two xml files
        detailXmlDiff = new DetailedDiff(xmlDiff);
         
        } catch (SAXException | IOException ex) {
            Logger.getLogger(XMLComparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detailXmlDiff.getAllDifferences();
    }

    /**
     * Denna metod skriver ut innehållet i listan till standard output
     * @param differences List från compareXML()
     */
    public static void printDifferences(List differences){ 
        int totalDifferences = differences.size(); 
        System.out.println("=========================================="); 
        System.out.println("Total differences : " + totalDifferences); 
        System.out.println("=========================================="); 
        Iterator it = differences.iterator();
        while ( it.hasNext() ) {
            try {
                Difference diff = (Difference) it.next();
                System.out.print("Tag: " + diff.getControlNodeDetail().getNode().getParentNode().getNodeName());
                System.out.print(" \t: Diff xml1: " + diff.getControlNodeDetail().getValue());
                System.out.print(" xml2: " + diff.getTestNodeDetail().getValue());
                System.out.println(" \t: xPath:" + diff.getControlNodeDetail().getXpathLocation());
            } catch ( NullPointerException ex ) { System.out.println("..."); break;}
        }
    }    
    
    /**
     * Denna metod returnerar antalet skillnader som finns i List från compareXML() som bara är datum
     * @param differences Listan med differanser som man får från compareXML()
     * @return antal
     */
    public static int checkDateDifferences(List differences){
        int cnt = 0;
        Iterator it = differences.iterator();
        while (it.hasNext()) {
            Difference diff = (Difference)it.next();
            
            String txt1 = diff.getControlNodeDetail().getValue();
            String txt2 = diff.getTestNodeDetail().getValue();
            if (DateValidate.isValidEHMDate(txt1) && DateValidate.isValidEHMDate(txt2)) {
//                System.out.print(txt1);
//                System.out.println(" Valid EHM: " + DateValidate.isValidEHMDate(txt1));
//                System.out.print(txt2);
//                System.out.println(" Valid EHM: " + DateValidate.isValidEHMDate(txt2));
                cnt++;
            }
        }
        return cnt;
    }
}

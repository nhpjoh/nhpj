package NLL_migration;

import java.util.List;
import static org.junit.Assert.assertTrue;
import se.nhpj.soap.services.SoapResponseXML;
import static se.nhpj.xml.XMLComparator.checkDateDifferences;
import static se.nhpj.xml.XMLComparator.compareXML;
import static se.nhpj.xml.XMLComparator.printDifferences;

/**
 *
 * @author kundtest1
 */
public class checkDifferences {
    public static void checkDifferences(SoapResponseXML rsp1, SoapResponseXML rsp2,boolean ignorDate, boolean debug ){
        int rc = 0;
        // Jämför två XML:er
        List differences = compareXML(rsp1.getXML(), rsp2.getXML());
        
        // Check if there is any differences
        if (!differences.isEmpty()) { // Om det finns skilnader
            if(debug) {System.out.println("Olikheter funna");}  DebugMsg.DebugMsg(debug,rsp1,rsp2);
            
            // Check om det bara är datum som skiljer ...
            rc = checkDateDifferences(differences);     // get number of Datedifferences- returns only tags where there is dates and they not match
            if (differences.size()-checkDateDifferences(differences) != 0) {
                //showing differences found in the two xml files 
                printDifferences(differences); 
                assertTrue("Inte bara datum",false);
            } else if (!ignorDate) {
                //showing differences found in the two xml files 
                printDifferences(differences); 
                assertTrue("Det finns olikheter ",false);
            }
        }
        
    }
    
}

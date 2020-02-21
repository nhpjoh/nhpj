package labb;

import java.util.Iterator;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nhpj
 */
public class Test_xml_Utils {
    
    public Test_xml_Utils() {
    }
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}

    public String xml1 = "<?xml version='1.0' encoding='UTF-8'?>"+
                   "<student>"+
                   "<age>11</age>"+
                   "<id>12</id>"+
                   "<stad>Stockholm</stad>" +
                   "<name>JavaInterviewPoint</name>"+
                   "<tel>"+
                       "<hem>08-123457</hem>"+
                       "<mob>08-123456</mob>"+
                   "</tel>"+
                   "<A>TestVärde</A>"+
                   "</student>";

    public String xml2 = "<?xml version='1.0' encoding='UTF-8'?>"+
                   "<student>"+
                   "<age>11</age>"+
                   "<id>13</id>"+
                   "<stad>Stockholm</stad>" +
                   "<name>JavaInterviewPoint</name>"+
                   "<tel>"+
                       "<hem>08-123456</hem>"+
                       "<mob>08-123457</mob>"+
                   "</tel>"+
                   "</student>";    

     @Test
     public void XMLtoMap() {
        se.nhpj.xml.Utils u = new se.nhpj.xml.Utils();
        Map m = u.XMLtoMap(xml1);
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String key = (String)it.next();
            System.out.println(key + ":" +m.get(key) );
        }
     }

     @Test
     public void compareXML() {
        se.nhpj.xml.Utils u = new se.nhpj.xml.Utils();
        
        boolean rc = u.compareXML(xml1,xml2);
        System.out.println( "compareXML returns: " + rc + "\n");
        
        //System.out.println( u.getDiffList());
        
        if (!rc) {
            Iterator it = u.getDiffList().iterator();
            while (it.hasNext()) {
                System.out.println((String)it.next());
            }
        }

     }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import static se.nhpj.xml.XMLComparator.compareXML;
import static se.nhpj.xml.XMLComparator.printDifferences;
import static se.nhpj.xml.XMLComparator.checkDateDifferences;
import static org.junit.Assert.*;


/**
 *
 * @author kundtest1
 */
public class Test_XMLComparator {
    
    public Test_XMLComparator() {    }
    
    @BeforeClass
    public static void setUpClass() {    }
    
    @AfterClass
    public static void tearDownClass() {    }
    
    @Before
    public void setUp() {    }
    
    @After
    public void tearDown() {    }
    
    public String xml1 = "<?xml version='1.0' encoding='UTF-8'?>"+
                   "<student>"+
                        "<age>11</age>"+
                        "<id>13</id>"+
                        "<stad>Stockholm</stad>" +
                        "<name>JavaInterviewPoint</name>"+
                        "<tel>"+
                            "<hem>08-123456</hem>"+
                            "<mob>08-123457</mob>"+
                        "</tel>"+
                        "<datum>2009-01-01T00:00:00+01:00</datum>"+
                        "<datum>2009-01-01T00:00:00+01:00</datum>"+
                   "</student>";

    public String xml2 = "<?xml version='1.0' encoding='UTF-8'?>"+
                   "<student>"+
                        "<age>99</age>"+
                        "<id>13</id>"+
                        "<stad>Stockholm</stad>" +
                        "<name>JavaInterviewPoint</name>"+
                        "<tel>"+
                            "<hem>08-123456</hem>"+
                            "<mob>08-123457</mob>"+
                        "</tel>"+
                        "<datum>2009-01-01T00:00:00+01:00</datum>"+
                        "<datum>2018-12-13 10:11:12:000</datum>"+
                   "</student>"; 

     @Test
     public void testXMLComparator() {
        int rc;
        // Jämför två XML:er
        List differences = compareXML(xml1, xml2);
        
        // Check if there is any differences
        System.out.println("Är de lika: " + differences.isEmpty());
        
        //Check number of Datedifferences- returns only tags where there is dates and they not match
        rc = checkDateDifferences(differences);     
        System.out.println("Antal olika datum: " + rc);
        
        //showing differences found in the two xml files 
        printDifferences(differences); 
        
        // Check om det bara är datum som skiljer ...
        assertEquals("Check if other than dates that mismatch", 1,differences.size()-checkDateDifferences(differences));
     }
}

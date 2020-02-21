/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLL_migration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import labb.Test_properties;
import org.junit.Test;

/**
 *
 * @author kundtest1
 */
public class ReadProps {

    @Test
    public void test1() {
        System.out.println(getProp("debug"));
    }
    @Test
    public void test2() {
        System.out.println(getProp("personnummer", "/NLL_migration/inData.properties"));
    }

    public String getProp(String property) {
        return getProp(property, "/NLL_migration/inData.properties");
    }

    public String getProp(String property, String fileName) {
        InputStream inStream = null;
        String retVal="-1";
        try {
            inStream = this.getClass().getResourceAsStream( fileName );
            Properties props = new Properties();
            props.load(inStream);

            retVal = props.getProperty(property);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return retVal;
    }

}

package labb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 * @author nhpj
 */
public class Test_properties {

    @Test
    public void test() {
        
        InputStream inStream = null;
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            System.out.println("rootPath: " + rootPath);
            
            Properties props = new Properties();
            inStream = this.getClass().getResourceAsStream("/soap/services/SkapaOrdinationApotek.properties");
            props.load(inStream);

            Enumeration prop = props.propertyNames();
            while (prop.hasMoreElements()) {
                String pName = prop.nextElement().toString();
                System.out.println(pName + " : " + props.getProperty(pName));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test_properties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test_properties.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Test_properties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

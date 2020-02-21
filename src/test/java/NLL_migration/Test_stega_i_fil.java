package NLL_migration;

import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test_stega_i_fil {

    @Test
    public void test_stega_i_fil() {

        try {
            File f = new File("c:/temp/PATIENT_ID2.dat");
            for(Scanner sc = new Scanner(f); sc.hasNext(); )
            {
                String line = sc.nextLine();
                Integer patient_id = Integer.parseInt(line);
                System.out.println(patient_id);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test_stega_i_fil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

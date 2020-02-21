package NLL_migration;

import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test_connect_NLLdb {

    @Test
    public void test_connect_NLLdb(){
        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/int28","ETCDBA","ETCDBA");

        /*  Hur man gör ett prepare / execute statement  */
        String sql = "Select count(*) as antal from FOLK_PROD.SPAR_PERSON WHERE MELLANNAMN = ?";
        String retValue = "<0>";

        PreparedStatement statement = null;

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);

            statement.setString(1, "TEST");

            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                retValue = result.getString("antal");
                statement.close();
                se.nhpj.database.DB_Access.closeConnection(con);
            }
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }

        System.out.println(retValue);
    }


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
            Logger.getLogger(Test_connect_NLLdb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

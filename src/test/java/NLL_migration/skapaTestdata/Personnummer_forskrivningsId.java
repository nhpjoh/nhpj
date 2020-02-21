package NLL_migration.skapaTestdata;

import org.junit.Test;
import se.nhpj.database.DB_Access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Personnummer_forskrivningsId {

    @Test
    public void skapa(){
        // ta fram listan med FORSKRIVNING_ID n
        // Spring igenom listan och ta fram personnummret för id:t
        // skriv ut till std output
        getForskrivningIdLista();
    }

    public String getPersonnummer(String patient_ref) {
        PreparedStatement statement = null;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/INT28", "ETCDBA", "ETCDBA");
        String retVal=null;
        String sql = "select i.IDENTITET " +
                    " from folk_prod.person p, FOLK_PROD.PERSONIDENTITET i, FOLK_PROD.SPAR_PERSON s " +
                    " where p.PERSON_ID = i.PERSON_ID " +
                    " and s.PERSONNR = i.IDENTITET " +
                    " and p.LOGISKT_ID =  upper(?) ";

        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, patient_ref);

            statement.execute();
            ResultSet result = statement.getResultSet();

            if (result.next()) {
                retVal = result.getString("IDENTITET");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB_Access.closeConnection(con);
        return retVal;
    }

    public void getForskrivningIdLista()  {
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/NLLTEST1DEPLOY2S15", "ETCDBA", "ETCDBA");
        String sql = "select f.FORSKRIVNING_ID,l.PATIENT_REF " +
                    " from nll_data.lakemedelslista l, nll_data.forskrivning f " +
                    " where l.LOGISKT_ID = f.LAKEMEDELSLISTA_REF" +
                    " --and f.FORSKRIVNING_ID like '5611%' ";

        try {
            ResultSet result = DB_Access.getResultSet(con,sql);
            while (result.next()) {
                String forskrivning_id = result.getString("FORSKRIVNING_ID");
                String PATIENT_REF = result.getString("PATIENT_REF");
                System.out.println(getPersonnummer(PATIENT_REF) + "\t" + forskrivning_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB_Access.closeConnection(con);
    }
}

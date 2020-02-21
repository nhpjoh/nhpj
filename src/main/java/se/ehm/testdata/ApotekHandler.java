package se.ehm.testdata;

import se.nhpj.database.DB_Access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApotekHandler {

    public static Apotek getRndApotek(String ServiceName) {

        Apotek apotek = null;

        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceName, "ETCDBA", "ETCDBA");

        String sql = "select * from (" +
                " select E.BUTIKSKOD as GLN, S.EXPONAMN NAMN, S.KORTNAMN, S.ORGID " +
                " from EXPO_PROD.EXPOSTALLE S, EXPO_PROD.EXPO_EXTERNID E " +
                " where S.EXPOID = E.EXPOID " +
                " and E.EXPOID not in (7002,7004,7006,7007,7009, 12, 13, 1) " +
                " and s.TOM_DATUM is null " +
                " and s.TILLSTAND = 'GODKANT' " +
                " and s.HUVUD_ORGTYP = 'AP' " +
                " ORDER BY sys.dbms_random.value desc " +
                ") where rownum < 2";

        ResultSet result = se.nhpj.database.DB_Access.getResultSet(con,sql);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                apotek = new Apotek();
                apotek.setGLN(result.getString("GLN"));
                apotek.setNamn(result.getString("NAMN"));
                apotek.setKortNamn(result.getString("KORTNAMN"));
                apotek.setOrgId(result.getString("ORGID"));
            }
            // Stänger databaskopplingen
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }

        return apotek;
    }

}

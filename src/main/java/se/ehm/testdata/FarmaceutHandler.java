package se.ehm.testdata;

import se.nhpj.database.DB_Access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutHandler {

    public static List<Farmaceut> getAllFarmaceut(String ServiceName) {
        ArrayList<Farmaceut> lista = new ArrayList<Farmaceut>();

        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceName, "ETCDBA", "ETCDBA");

        String sql = "  select F.KOD legitimationskod, F.personnr, F.NAMN as FORS_namn, P.FORNAMN as folk_fornamn, P.EFTERNAMN as folk_efternamn " +
                "  from fors_prod.fors F, FORS_PROD.FORS_KATEGORI K, FOLK_PROD.SPAR_PERSON P " +
                "  where F.kod = K.kod " +
                "  and K.KATEGORI = 'AP' " +
                "  and F.KOD_CHECK is null " +
                "  and F.PERSONNR = P.PERSONNR ";

            ResultSet result = DB_Access.getResultSet(con,sql);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                Farmaceut farmaceut = new Farmaceut();
                farmaceut.setLegitimationskod(result.getString("legitimationskod"));
                farmaceut.setFornamn(result.getString("folk_fornamn"));
                farmaceut.setEfternamn(result.getString("folk_efternamn"));
                lista.add(farmaceut);
            }
            // Stänger databaskopplingen
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }

        return lista;
    }

    public static Farmaceut getRndFarmaceut(String ServiceName) {

        Farmaceut farmaceut = null;

        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceName, "ETCDBA", "ETCDBA");

        String sql = "select * from (" +
                "  select F.KOD legitimationskod, F.personnr, F.NAMN as FORS_namn, P.FORNAMN as folk_fornamn, P.EFTERNAMN as folk_efternamn  " +
                "  from fors_prod.fors F, FORS_PROD.FORS_KATEGORI K, FOLK_PROD.SPAR_PERSON P " +
                "  where F.kod = K.kod " +
                "  and K.KATEGORI = 'AP' " +
                "  and F.KOD_CHECK is null " +
                "  and F.PERSONNR = P.PERSONNR " +
                "  ORDER BY sys.dbms_random.value desc " +
                ") where rownum < 2";

        ResultSet result = DB_Access.getResultSet(con,sql);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                farmaceut = new Farmaceut();
                farmaceut.setLegitimationskod(result.getString("legitimationskod"));
                farmaceut.setFornamn(result.getString("folk_fornamn"));
                farmaceut.setEfternamn(result.getString("folk_efternamn"));
            }
            // Stänger databaskopplingen
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }

        return farmaceut;
    }

}

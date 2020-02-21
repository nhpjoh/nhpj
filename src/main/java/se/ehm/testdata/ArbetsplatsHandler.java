package se.ehm.testdata;

import se.nhpj.database.DB_Access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArbetsplatsHandler {

    public static Arbetsplats getRndArbetsplats(String ServiceName) {

        Arbetsplats arbetsplats = null;

        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceName, "ETCDBA", "ETCDBA");

        String sql = 
                "Select * from (" +
                " select ARKO_PROD.ARBETSPLATS.ARBETSPLATSKOD as kod, " +
                "  ARKO_PROD.ARBETSPLATS.ARBETSPLATSNAMN as namn, " +
                "  ARKO_PROD.ARBETSPLATS.POSTADRESS as postadress, " +
                "  ARKO_PROD.ARBETSPLATS.POSTORT as postort, " +
                "  ARKO_PROD.ARBETSPLATS.POSTNUMMER as postnummer, " +
                "  ARKO_PROD.ARBETSPLATS.TELEFONNUMMER1 as telefon1, " +
                "  ARKO_PROD.ARBETSPLATS.TEXT as text," +
                "  ARKO_PROD.ARBETSPLATS.VERKSAMHETSOMRADEKOD as omrodesKod" +
                "  from ARKO_PROD.ARBETSPLATS" +
                " where ARKO_PROD.ARBETSPLATS.TOM_DATUM > SYSDATE" +
                " ORDER BY sys.dbms_random.value desc" +
                ") where rownum < 2";

        ResultSet result = se.nhpj.database.DB_Access.getResultSet(con,sql);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                arbetsplats = new Arbetsplats();
                arbetsplats.setKod(result.getString("kod"));
                arbetsplats.setNamn(result.getString("namn"));
                arbetsplats.setPostAdress(result.getString("postadress"));
                arbetsplats.setPostOrt(result.getString("postort"));
                arbetsplats.setPostNummer(result.getString("postnummer"));
                arbetsplats.setTelefon1(result.getString("telefon1"));
                arbetsplats.setText(result.getString("text"));
                arbetsplats.setOmrodesKod(result.getString("omrodesKod"));
            }
            // Stänger databaskopplingen
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }
        return arbetsplats;
    }

    public static Arbetsplats getPreSetArbetsplats() {
        Arbetsplats arbetsplats = new Arbetsplats();

        arbetsplats.setKod("4000000000000");
        arbetsplats.setNamn("Sjukhuset ett");
        arbetsplats.setPostAdress("Gatan 1");
        arbetsplats.setPostOrt("Staden 1");
        arbetsplats.setPostNummer("11111");
        arbetsplats.setTelefon1("011-123456");
        arbetsplats.setText("Sjukhuset ett");
        arbetsplats.setOmrodesKod(null);

        return arbetsplats;
    }

}

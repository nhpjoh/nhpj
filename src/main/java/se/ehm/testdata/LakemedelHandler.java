package se.ehm.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.nhpj.database.DB_Access;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LakemedelHandler {


    public static Lakemedel getRndLakemedel(String ServiceName) {

        Lakemedel lakemedel = null;

        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceName, "ETCDBA", "ETCDBA");

        String sql = "select * from (" +
                "select nplpackageid, nplid, varunr, antal_numerisk, antal_klartext, produktnamn, benamning, aip pris " +
                "from vara_prod2.vara_online " +
                "where artikel_id is not null " +
                "and utgangen = 'N' " +
                "and tillhandahalls = 'Y' " +
                "and forsaljningsstopp = 'N' " +
                "and lakemedel = 'Y' " +
                "and narkotika = 'N' " +
                "and sarskiltlakemedel = 'N' " +
                "and antal_multipel_1 is null " +
                "and antal_multipel_2 is null " +
                "and alt_enhet_strl_forp_mult1 is null " +
                "and alt_enhet_strl_forp_mult2 is null " +
                "and aip is not null " +
                "and aup is not null " +
                "  ORDER BY sys.dbms_random.value desc " +
                ") where rownum < 2";

        ResultSet result = se.nhpj.database.DB_Access.getResultSet(con,sql);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                lakemedel = new Lakemedel();
                lakemedel.setNPLID(result.getString("nplid"));
                lakemedel.setNPLPACKID(result.getString("nplpackageid"));
                lakemedel.setVARUNR(result.getString("varunr"));
                lakemedel.setANTAL_NUMERISKT(Float.parseFloat(result.getString("antal_numerisk")));
                lakemedel.setANTAL_KLARTEXT(result.getString("antal_klartext"));
                lakemedel.setPRODUKTNAMN(result.getString("produktnamn"));
                lakemedel.setBENEMNING(result.getString("benamning"));
                lakemedel.setPRIS(result.getString("pris"));
                lakemedel.setAntalUttag("0");
                lakemedel.setNumberOfPackages("0");
            }
            // Stänger databaskopplingen
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }

        return lakemedel;
    }

    public Lakemedel getRndLakemedel() {
        Lakemedel lakemedel = new Lakemedel();

        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/Lakemedel.dat");
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
//        System.out.println(result);
        try {
            ObjectMapper mapper = new ObjectMapper();
            lakemedel = mapper.readValue(result, Lakemedel.class);
        }catch (IOException ex) {
            Logger.getLogger(Lakemedel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lakemedel;
    }


}

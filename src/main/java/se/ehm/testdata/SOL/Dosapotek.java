package se.ehm.testdata.SOL;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.nhpj.database.DB_Access;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Denna class skapar upp dosapotek i tabellen SOL_PROD.SOL_DOSAPOTEK.
 */

public class Dosapotek extends SetEnv {

    private static String apotek = "[{\"ID\":\"10\", \"DOSAKTOR\":\"Aktor 10\",\"DOSAPOTEK\":\"Apotek 10\",\"DOSAPOTEKID\":\"7310000000010\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"11\", \"DOSAKTOR\":\"Aktor 11\",\"DOSAPOTEK\":\"Apotek 11\",\"DOSAPOTEKID\":\"7310000000011\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"12\", \"DOSAKTOR\":\"Aktor 12\",\"DOSAPOTEK\":\"Apotek 12\",\"DOSAPOTEKID\":\"7310000000012\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"13\", \"DOSAKTOR\":\"Aktor 13\",\"DOSAPOTEK\":\"Apotek 13\",\"DOSAPOTEKID\":\"7310000000013\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"14\", \"DOSAKTOR\":\"Aktor 14\",\"DOSAPOTEK\":\"Apotek 14\",\"DOSAPOTEKID\":\"7310000000014\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"15\", \"DOSAKTOR\":\"Aktor 15\",\"DOSAPOTEK\":\"Apotek 15\",\"DOSAPOTEKID\":\"7310000000015\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"16\", \"DOSAKTOR\":\"Aktor 16\",\"DOSAPOTEK\":\"Apotek 16\",\"DOSAPOTEKID\":\"7310000000016\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"17\", \"DOSAKTOR\":\"Aktor 17\",\"DOSAPOTEK\":\"Apotek 17\",\"DOSAPOTEKID\":\"7310000000017\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"18\", \"DOSAKTOR\":\"Aktor 18\",\"DOSAPOTEK\":\"Apotek 18\",\"DOSAPOTEKID\":\"7310000000018\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"19\", \"DOSAKTOR\":\"Aktor 19\",\"DOSAPOTEK\":\"Apotek 19\",\"DOSAPOTEKID\":\"7310000000019\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}," +
                                    "{\"ID\":\"20\", \"DOSAKTOR\":\"Aktor 10\",\"DOSAPOTEK\":\"Apotek 20\",\"DOSAPOTEKID\":\"7310000000020\",\"AKTORID\":\"5567634778\",\"MAXANTALHAMTABESTALLNINGAR\":\"99999\"}]";


    public Dosapotek() {}

    public static Dosapotek_token rndDosapotek() {
        Dosapotek dosapotek = new Dosapotek();
        List<Dosapotek_token> list = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(apotek, new TypeReference<List<Dosapotek_token>>(){});
        } catch (IOException ex) {
            Logger.getLogger(Dosapotek_token.class.getName()).log(Level.SEVERE, null, ex);
        }

        int irad = se.nhpj.testdata.RndData.getNumber(0, list.size());
        return list.get(irad);
    }

    public static Dosapotek_token getDosapotek(String gln) {
        Dosapotek dosapotek = new Dosapotek();
        Dosapotek_token dt = null;
        List<Dosapotek_token> list = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(apotek, new TypeReference<List<Dosapotek_token>>(){});
        } catch (IOException ex) {
            Logger.getLogger(Dosapotek_token.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Leta rätt på rätt gln
        for ( int i = 0; i < list.size() ; i++ ){
            if ( list.get(i).getDOSAPOTEKID().equals(gln))
                dt =  list.get(i);
        }
        return dt;
    }

    /**
     * Denna metod skapar upp 11 dosapoteken i sol databasen
     * @param args
     */
    public static void main(String args[]) {
        Dosapotek dosapotek = new Dosapotek();
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/INT13", "ETCDBA", "ETCDBA");

        System.out.println("Skapar  dosapotek i miljö " + dosapotek.getSUT() );

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Dosapotek_token> list;
            list = mapper.readValue(apotek, new TypeReference<List<Dosapotek_token>>(){});

            for ( Dosapotek_token item : list ) {
                System.out.println("Adding row: " + item.toString());
                String sql = "INSERT INTO SOL_PROD.SOL_DOSAPOTEK VALUES (" + Integer.valueOf(item.getID())+",'"+item.getDOSAKTOR()+"','"+item.getDOSAPOTEK()+"','"+item.getDOSAPOTEKID()+"',"+Long.valueOf(item.getAKTORID())+","+Integer.valueOf(item.getMAXANTALHAMTABESTALLNINGAR())+")";
                Integer rc = DB_Access.doInsert(con,sql);
            }

            DB_Access.closeConnection(con);

        } catch (IOException ex) {
            Logger.getLogger(Dosapotek_token.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



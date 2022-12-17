package se.nhpj.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class DB_AccessTest {

//    @Test
//    public void test() {
//        String retVal="-1";
//        List lista = new ArrayList();
//        Connection connection;
//
//        // Skapar kontakt med databasen
//        connection = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/INT7", "ETCDBA", "ETCDBA");
//
//        String sql = "select PERSONNR, FORNAMN, EFTERNAMN from folk_prod.spar_person where FORNAMN like 'Lars%'";
//
//        // Kör din SQL
//        ResultSet result = DB_Access.getResultSet(connection,sql);
//
//        // Hämtar upp resultatet av din SQL
//        try {
//            while (result.next()) {
//                // lägger till en rad i taget till listan
//                lista.add(result.getString("PERSONNR") + "," +
//                         result.getString("FORNAMN")  + "," +
//                         result.getString("EFTERNAMN"));
//            }
//            // Stänger databaskopplingen
//            DB_Access.closeConnection(connection);
//
//        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
//            e.printStackTrace();
//        }
//
//        // Skriver ut resultat listan
//        Iterator itr = lista.iterator();
//        while (itr.hasNext()) {
//            System.out.println(itr.next());
//        }
//
//    }

    @Test
    public void test_HSQLDB() {
        Connection con;
        con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select * from person";
        List lista = new ArrayList();
        // Kör din SQL
        ResultSet result = DB_Access.getResultSet(con,sql);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                lista.add(result.getString("PERSONNUMMER") + "," +
                          result.getString("FORNAMN")  + "," +
                          result.getString("EFTERNAMN"));
            }
            // Stänger databaskopplingen
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }

        // Skriver ut resultat listan
        Iterator itr = lista.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

    }
}
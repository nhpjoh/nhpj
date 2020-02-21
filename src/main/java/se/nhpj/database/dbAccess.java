package se.nhpj.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Denna klass innehåller bara en metod för att göra SELECT mot ehm:s testdatabaser
 * @author nhpj
 */

public class dbAccess {
    private static ArrayList _DataArray = new ArrayList();

//    public void dbAccess() {
//    }
/**
 * Denna metod returnerar en lista med innehållet från en sql select fråga.
 * @param SQLStmt - SQL SELECT frågan
 * @param testmiljo - vilken testmiljö ex. INT2
 * @param databas - vilken databas/användare
 * @param passord - passord
 * @return - En lista med innehållet av SQL SELECT frågan
 */
    public static ArrayList dbAccess(String SQLStmt, String testmiljo, String databas, String passord) {

        System.out.println("-------- Oracle JDBC Connection Testning ------");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Var är din Oracle JDBC Driver?");
            e.printStackTrace();
        }

        System.out.println("Oracle JDBC Driver Registrerad!");
        Connection connection = null;

        try {
            //connection = DriverManager.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/int10", "RR_PROD", "RR_PROD");
            //connection = DriverManager.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/int2", "ORDINATION_DATA", "ORDINATION_DATA");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + testmiljo, databas, passord );
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("Det fungerar, ta kontroll över databasen nu!\n");

            ArrayList al = new ArrayList();

            try {
                //creating PreparedStatement object to execute query
                PreparedStatement preStatement = connection.prepareStatement(SQLStmt);
                ResultSet result = preStatement.executeQuery();

                // Skriver ut columnnamnen
                int i=1;
                ArrayList cols = new ArrayList();
                while (i <= result.getMetaData().getColumnCount()) {
                    String c = result.getMetaData().getColumnName(i);
                    cols.add(c);
                    i++;
                }
                //System.out.println(cols);
                _DataArray.add(cols);

                // Laddar datarader in i en ArrayList:a
                while(result.next()){
                    int j=1;
                    ArrayList _s = new ArrayList();
                    while ( j <= result.getMetaData().getColumnCount() ) {
                        String s = result.getString( j );
                        _s.add(s);
                        j++;
                    }
                    _DataArray.add(_s);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Misslyckades med att få till en 'connection'!");
        }
        return _DataArray;
    }

    private ArrayList getInDataArray() {
        return _DataArray;
    }

    private void setInDataArray(ArrayList listan) {
        _DataArray = listan;
    }

    private void addToDataArray(Object data) {
        _DataArray.add(data);
    }

//    public static void main(String[] args) {
//        dbAccess a = new dbAccess();
//
//        ArrayList testdata = a.getInDataArray();
//        java.util.Iterator itr = testdata.iterator();
//
////        ArrayList aa = dbAccess("select * from pnr_scenario_map order by 6,5", "int2", "ORDINATION_DATA", "ORDINATION_DATA");
//        ArrayList aa = dbAccess("select * from RR_PROD.AFF_FEL", "int2", "ETCDBA", "ETCDBA");
//
//        System.out.println("Rader + kolumnnamn: " + aa.size());
//
//        itr = aa.iterator();
//        while ( itr.hasNext() ) {
//            ArrayList s = (ArrayList) itr.next();
//            System.out.println(s);
//        }
//    }

}




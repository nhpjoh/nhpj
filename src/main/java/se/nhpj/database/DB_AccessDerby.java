package se.nhpj.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Denna klass innehåller ett antal metoder för att prata med en derby databas
 * @author nhpj
 */
public class DB_AccessDerby {
    private static Connection connection = null;
    public static ArrayList data = new ArrayList();

    /**
     * En tom konstruktor.
     */
    public void DB_AccessDerby() { }

    /**
     * Denna funktion returnerar en instans av ett Connection objekt som sedan används i t.ex getValue()
     * @param	connectionString	En Connection sträng exempelvis: "jdbc:derby://localhost:1527/sample","app","app"" (Java DB)
     * @param	user			databasanvändare
     * @param	password		passordet för databasanvändaren
     * @return	Connection		En Connection instans
     */
    public static Connection getConnection(String connectionString, String user, String password) {

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Var är din JDBC Driver?");
            e.printStackTrace();
        }
        try {
            //  connection = DriverManager.getConnection("jdbc:derby://localhost:1527/sample","app","app");
            connection = DriverManager.getConnection(connectionString, user, password);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Denna funktion stänger ett Connection objekt.
     *
     * @param	con	Ett Connection objekt
     */
    public static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Close connection Failed! Check output console");
            e.printStackTrace();
        }
    }

    /**
     * Denna funktion returnerar resultatet av en SQL count(*) fråga i form av en Sträng.
     * @param	con		Ett Connection objekt. från t.ex. getConnection().
     * @param	sqlQuery	En SQL count(*) fråga. Typ SELECT count(*) from "tabellnamn" eller liknande som returnerar ett Sträng-värde
     * @param	colName()       Namnet på kolumnen som returneras i SQL frågan
     * @return  String          Strängvärdet returnerat av SQL frågan
     */
    public static String getStringValue(Connection con, String sqlQuery, String colName) {
        Statement statement = null;
        String retValue = "";
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            System.out.println("Create statement failed!");
            e.printStackTrace();
        }
        try {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println("Execute statement failed!");
            e.printStackTrace();
        }
        try {
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                retValue = result.getString(colName);
                statement.close();
                //con.close();
            }
        } catch (SQLException e) {
            System.out.println("Create statement failed!");
            e.printStackTrace();
        }
        //System.out.println(retValue);
        return retValue;
    }

    /**
     * Denna funktion returnerar resultatet av en SQL count(*) fråga i form av en Integer.
     * @param	con		Ett Connection objekt. från t.ex. getConnection().
     * @param	sqlQuery	En SQL count(*) fråga. Typ SELECT count(*) from "tabellnamn" eller annan fråga som returnerar en Integer(siffra)
     * @param	colName()       Namnet på kolumnen som returneras i SQL frågan
     * @return  Integer         Strängvärdet returnerat av SQL frågan
     */
    public static Integer getIntegerValue(Connection con, String sqlQuery, String colName) {
        Statement statement = null;
        Integer retValue = 0;
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            System.out.println("Create statement failed!");
            e.printStackTrace();
        }
        try {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println("Execute statement failed!");
            e.printStackTrace();
        }
        try {
            ResultSet result = statement.getResultSet();                
            if (result.next()) {
                if (result.getMetaData().getCatalogName(1).toString() == "") {
                    retValue = result.getInt(1);
                } else {
                    retValue = result.getInt(colName);
                }
                statement.close();
                //con.close();
            }
        } catch (SQLException e) {
            System.out.println("Create statement failed!");
            e.printStackTrace();
        }
        //System.out.println(retValue);
        return retValue;
    }

    /**
     * Denna funktion används för INSERT eller UPDATE.
     * @param	con		Ett Connection objekt. från t.ex. getConnection().
     * @param	sqlQuery	En SQL count(*) fråga. Typ SELECT count(*) from "tabellnamn" eller annan fråga som returnerar en Integer(siffra)
     * @return  Integer         Värdet returnerat av SQL frågan
     */
    public static Integer doInsert(Connection con, String sqlQuery) {
        Statement statement = null;
        Integer retValue = 0;
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            System.out.println("Create statement failed!");
            e.printStackTrace();
        }
        try {
            retValue = statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            System.out.println("Execute statement failed!");
            e.printStackTrace();
        }

        //System.out.println(retValue);
        return retValue;
    }
    /**
     * Denna funktion används för INSERT eller UPDATE.
     * @param	con		Ett Connection objekt. från t.ex. getConnection().
     * @param	sqlQuery	En SQL count(*) fråga. Typ SELECT count(*) from "tabellnamn" eller annan fråga som returnerar en Integer(siffra)
     * @return  Integer         Värdet returnerat av SQL frågan
     */
    public static Integer doUptade(Connection con, String sqlQuery) {
      return doInsert(con, sqlQuery);
    }
    
    /**
     * Denna funktion returnerar resultatet av en SQL fråga.
     * <DIV id="getData"> </DIV>
     * @param	con			Ett Connection objekt. från t.ex. <a href="#getConnection">getConnection()</A>.
     * @param	anSQLCountStmt		En SQL count(*) fråga. Typ SELECT count(*) from "tabellnamn"
     * @return  Anatlrader returnerade som ligger i  ArrayList testdata.
     * @see	#getConnection(String, String, String)
     */
    public static Integer getData( Connection con , String anSQLCountStmt ){
        Statement stmt = null;
        Integer retValue = null;
        ArrayList totalAL = new ArrayList();
        
        if (con != null) {
            System.out.println("Det fungerar, tar kontroll över databasen nu!");
            try {
                String sql = anSQLCountStmt;

                //creating PreparedStatement object to execute query
                PreparedStatement preStatement = con.prepareStatement(sql);

                ResultSet result = preStatement.executeQuery();

                // Skriver ut columnnamnen till standard output.
                int i=1;
                String cols = new String();
                ArrayList headerAL = new ArrayList();
                while (i <= result.getMetaData().getColumnCount()) {
                    String c = result.getMetaData().getColumnName(i);
                    if(cols.length() > 0) {
                        cols = cols + ';' + c;
                        headerAL.add(c);
                    } else { 
                        cols = c; 
                        headerAL.add(c);
                    }
                    i++;
                }
//                System.out.println(cols);
                System.out.println(headerAL.toString());
                
                totalAL.add(headerAL);

                // Skriver ut datarader till standard output
                while(result.next()){
                    int j=1;
                    String _s = new String();
                    ArrayList rowAL = new ArrayList();
                    while (j <= result.getMetaData().getColumnCount()) {
                        String s = result.getString(j);
                        if(_s.length() > 0) {
                            _s = _s + ';' + s;
                            rowAL.add(s);
                        } else {
                            _s = s; 
                            rowAL.add(s);
                        }
                        j++;
                    }
//                    System.out.println(_s);
                    System.out.println(rowAL.toString());
//                    retValue = Integer.parseInt(_s);
                    totalAL.add(rowAL);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Misslyckades. Fick inget fungerande 'Connection' objekt ! ");
        }
        data = totalAL;
        return totalAL.size();
    }

    
//    /**
//     * Denna main är bara i klassen för felsökningsändamål
//     * @param args Inparameter för main
//     */
//    public static void main(String[] args) {
//        Connection con = DB_AccessDerby.getConnection("jdbc:derby://localhost:1527/sample","app","app");
//
//        System.out.println("Antal Rader: " + DB_AccessDerby.getData(con, "SELECT * FROM CUSTOMER") );
//        System.out.println("testdata: \n" + DB_AccessDerby.testdata.toString() );
////        System.out.println("AntalRader: "   + DB_AccessDerby.getIntegerValue(con, "select count(*) from customer", ""));
////        System.out.println("doInsert: "   + DB_Access.doInsert(con, "INSERT INTO CUSTOMER  VALUES (100,..........)"));
//
//        DB_AccessDerby.closeConnection(con);
//    }
}

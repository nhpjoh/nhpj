package se.nhpj.database;

import java.sql.*;

/**
 * Denna klass innehåller ett antal metoder för att prata med ehm:s test databaser
 * @author nhpj
 */

public class DB_AccessHSQLdb {
    private static Connection connection = null;

//    /**
//     * En tom konstruktor.
//     */
//    public void DB_Access() { }

    /**
     * Denna funktion returnerar en instans av ett Connection objekt som sedan används i t.ex getValue()
     * @param	connectionString	En Connection sträng exempelvis: "jdbc:hsqldb:hsql:<server>:3306/<databas>"
     * @param	user			databasanvändare
     * @param	password		passordet för databasanvändaren
     * @return	Connection		En Connection instans
     */
    public static Connection getConnection(String connectionString, String user, String password) {

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Var är din HSQLdb JDBC Driver?");
            e.printStackTrace();
        }
        try {
            //  connection = DriverManager.getConnection("jdbc:hsqldb:hsql:localhost:3306/person", "SA", "");
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
        //Class.forName("org.hsqldb.jdbc.JDBCDriver");
        //DriverManager.registerDriver(driver);
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
        //Class.forName("org.hsqldb.jdbc.JDBCDriver");
        //DriverManager.registerDriver(driver);
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
                retValue = result.getInt(colName);
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
        //Class.forName("org.hsqldb.jdbc.JDBCDriver");
        //DriverManager.registerDriver(driver);
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
     * Denna funktion returnerar resultatet av en SQL fråga i form av ett java.SQL.ResultSet.
     * @param	con		Ett Connection objekt. från t.ex. getConnection().
     * @param	sqlQuery	En SQL count(*) fråga. Typ SELECT count(*) from "tabellnamn" eller liknande som returnerar ett Sträng-värde
     * @return  String          Strängvärdet returnerat av SQL frågan
     */
    public static ResultSet getResultSet(Connection con, String sqlQuery) {
        //Class.forName("org.hsqldb.jdbc.JDBCDriver");
        //DriverManager.registerDriver(driver);
        Statement statement = null;
        ResultSet resultSet = null;
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
            resultSet = statement.getResultSet();
        } catch (SQLException e) {
            System.out.println("Create statement failed!");
            e.printStackTrace();
        }
        //System.out.println(retValue);
        return resultSet;
    }




//    /**
//     * Denna main är bara i klassen för felsökningsändamål
//     * @param args Inparameter för main
//     */
//    public static void main(String[] args) {
//        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/person", "SA", "");
//
//        /*
//         *  Hur man gör ett prepare / execute statement
//         */
//
//        String sql = "Select count(*) as antal from person where id = ?";
//        String retValue = "<0>";
//
//        PreparedStatement statement = null;
//
//        try {
//            con.setAutoCommit(false);
//            statement = con.prepareStatement(sql);
//
//                statement.setString(1, "2");
//                statement.execute();
//                ResultSet result = statement.getResultSet();
//                if (result.next()) {
//                    retValue = result.getString("antal");
//                    statement.close();
//                    DB_Access.closeConnection(con);
//                }
//        } catch (SQLException e ) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(retValue);
//  }
}

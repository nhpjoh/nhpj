package se.nhpj.database;

import java.sql.*;

/**
 * <h1>OracleAccess</h1>
 * Denna 'class' innehåller ett antal funktioner för att på ett
 * enkelt sätt skriva SQL frågor mot en Oracle databas
 * <p>
 * För att classen ska fungera måste en oracle:s ´java drivrutiner (ojdbc6.jar)
 * finnas med i classpath:en. För att får det att fungera i ett LoadRunner script
 * måste både denna class och oracles jar fil finnas i classpathe:en
 *
 *
 * Ett sätt att avnvända funktionerna i LoadRunner's java vu user är att i init delen
 * skapa en connection (getConnection) och i action delen köra anropen (getValue)
 * samt koppla ned (closeConnection) i end delen.
 * Glöm inte att deklarera ett Connection object i klassen som används av alla funktionerna.
 *
 * För att skapa Javadoc skriv "javadoc -d C:\"path"\OracleAccess.javaDoc OracleAccess.java"
 *
 * @author  Patrik Johansson
 * @version 1.0
 * @since   2015-05-08
 */
public class OracleAccess {

    /**
     * En tom konstruktor.
     */
    public void OracleAccess() {}

    /**
     * Denna funktion returnerar en instans av ett Connection objekt som sedan används i t.ex <a href="#getValue">getValue()</a>
     * <DIV id="getConnection"> </DIV>
     * @param	ConnectionString	En Connection sträng exempelvis: "jdbc:oracle:thin:@td01-scan.systest.receptpartner.se:1521/int6" (Test5)
     * @param	user			databasanvändare
     * @param	password		passordet för databasanvändaren
     * @return	Connection		En Connection instans
     * @see	#getValue(Connection, String)
     * @throws  Throwable Exeption
     */
    public static Connection getConnection(String ConnectionString, String user, String password) throws Throwable {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Var är din Oracle JDBC Driver?");
            e.printStackTrace();
        }
        try {
//            connection = DriverManager.getConnection("jdbc:oracle:thin:@td01-scan.systest.receptpartner.se:1521/int6", "ETCDBA", "ETCDBA");
            connection = DriverManager.getConnection(ConnectionString, user, password);

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
     * Denna funktion returnerar resultatet av en SQL count(*) fråga i form av en Integer.
     * <DIV id="getValue"> </DIV>
     * @param	con			Ett Connection objekt. från t.ex. <a href="#getConnection">getConnection()</A>.
     * @param	anSQLCountStmt		En SQL count(*) fråga. Typ SELECT count(*) from "tabellnamn"
     * @return  Integer Ett tal
     * @see	#getConnection(String, String, String)
     */
    public static Integer getValue( Connection con , String anSQLCountStmt ){
        Statement stmt = null;
        Integer retValue = null;

        if (con != null) {
            System.out.println("Det fungerar, tar kontroll över databasen nu!");
            try {
//              String sql ="select count(*) from RR_PROD.FORSKRORIG";
                String sql = anSQLCountStmt;

                //creating PreparedStatement object to execute query
                PreparedStatement preStatement = con.prepareStatement(sql);

                ResultSet result = preStatement.executeQuery();

                // Skriver ut columnnamnen till standard output.
                int i=1;
                String cols = new String();
                while (i <= result.getMetaData().getColumnCount()) {
                    String c = result.getMetaData().getColumnName(i);
                    if(cols.length() > 0) {
                        cols = cols + ';' + c;
                    } else { cols = c; }
                    i++;
                }
                System.out.println(cols);

                // Skriver ut datarader till standard output
                while(result.next()){
                    int j=1;
                    String _s = new String();
                    while (j <= result.getMetaData().getColumnCount()) {
                        String s = result.getString(j);
                        if(_s.length() > 0) {
                            _s = _s + ';' + s;
                        } else {_s = s; }
                        j++;
                    }
                    System.out.println(_s);
                    retValue = Integer.parseInt(_s);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Misslyckades. Fick inget fungerande 'Connection' objekt ! ");
        }

        return retValue;

    }
/*
    public static void main(String[] args) throws Throwable {
        //init
        Connection con = getConnection("jdbc:oracle:thin:@td01-scan.systest.receptpartner.se:1521/int6", "ETCDBA", "ETCDBA");

        // Action
        Integer retValue = getValue(con, "select count(*) from RR_PROD.FORSKRORIG") ;
        System.out.println("Detta fick jag  tillbaka: " + retValue);

        //End
        closeConnection(con);
    }
*/


    /**
     * Denna funktion returnerar resultatet av en SQL count(*) fråga i form av en Integer.
     * @param	con			Ett Connection objekt. från t.ex. <a href="#getConnection">getConnection()</A>.
     * @param	anSQLCountStmt		En SQL count(*) fråga. Typ SELECT count(*) from "tabellnamn"
     * @return  Integer Ett tal
     * @see	#getConnection(String, String, String)
     */

//    public static String getAValue() throws SQLException, ClassNotFoundException {
//        //Class.forName("oracle.jdbc.driver.OracleDriver");
//        //DriverManager.registerDriver(driver);
//
//        final String URL      = "jdbc:oracle:thin:@td01-scan.systest.receptpartner.se:1521/int6";
//        final String USERNAME = "ETCDBA";
//        final String PASSWORD = "ETCDBA";
//
//        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//
//        Statement statement = connection.createStatement();
//
//        statement.execute("select count(*) from VARA_PROD2.produkt");   // hämtar ett 'id'.
//
//        ResultSet result = statement.getResultSet();
//        int retVal = 0;
//        if(result.next()) {
//            retVal = result.getInt("count(*)");   // plockar ut ett 'id' från resultat sättet
//            statement.close();                    // Stänger ned efter mig
//            connection.close();                   // Stänger ned efter mig
//        }
//        System.out.println(retVal);               // Skriver ut värdet.
//        return String.valueOf(retVal);            // Returnerar värdet.
//    }
//    
//    public static void main(String[] args) throws Throwable {
//        getAValue();
//    }

}

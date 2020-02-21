package labb;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import se.nhpj.testdata.Adress;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;
import se.nhpj.database.Utils;


public class Test_DB_AccessHSQLdb {
    @Test
    public void select() {
        Connection con = null;
        Statement stmt = null;
        ResultSet result = null;

        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
            if (con!= null){
                System.out.println("Connection created successfully");
            } else {
                System.out.println("Problem with creating connection");
            }
            stmt = con.createStatement();
            String sql = "SELECT f.regplat as Regplåt, f.marke as Märke,f.modellar as årsmodell, p.fornamn || ' ' || p.efternamn as Ägare " +
                    "from person p, fordon f where p.id = f.agare";

            result = stmt.executeQuery( sql );

            while(result.next()){
                System.out.println(" | "+
                  Utils.padd(result.getString("Regplåt"),6) +" | "+
                  Utils.padd(result.getString("Märke"),15)+" | "+
                  Utils.padd(result.getString("årsmodell"),4)+" | "+
                  Utils.padd(result.getString("Ägare"),18)+" | "
                );
            }


        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Test
    public void register_search() {
        String reg = "";
        String brand = "VOLVO";
        String year = "";
        String owner = "";

        reg = reg.concat("%");
        brand = brand.concat("%");
        year = year.concat("%");
        owner = owner.concat("%");

        Connection con = null;
        ResultSet result = null;
        PreparedStatement statement = null;
        String retValue = "<0>";
        StringBuilder sb = new StringBuilder();

        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
            if (con!= null){
                System.out.println("Connection created successfully");
            } else {
                System.out.println("Problem with creating connection");
            }

            String sql = "SELECT f.regplat as Regplåt, f.marke as Märke,f.modellar as Årsmodell, p.fornamn || ' ' || p.efternamn as Ägare " +
                    "from person p, fordon f where p.id = f.agare " +
                    "and f.regplat like ?" +
                    " and f.marke like ? and f.modellar like ? and p.efternamn like ?"
                    ;

            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);

            statement.setString(1, reg);
            statement.setString(2, brand);
            statement.setString(3, year);
            statement.setString(4, owner);
            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"Regplåt\" : \"" + result.getString("Regplåt") +
                          "\" , \"Märke\" : \"" + result.getString("Märke") +
                          "\" , \"Årsmodell\" : \"" + result.getString("Årsmodell") +
                          "\" , \"Ägare\" : \"" + result.getString("Ägare") +
                          "\" } ," );
            }
            statement.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e ) {
            System.out.println(e.getMessage());
        }

        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-1)) {
            sb.replace(kommat,len,"");
        }
        sb.append("] }");
        String json = sb.toString();
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        List a = JsonPath.read(document, "$.lista");

        Iterator itr =  a.iterator();


        while (itr.hasNext()) {
            Object oJson = itr.next();
            System.out.println(JsonPath.read(oJson, "$.Regplåt").toString());
            System.out.println(JsonPath.read(oJson, "$.Märke").toString());
            System.out.println(JsonPath.read(oJson, "$.Årsmodell").toString());
            System.out.println(JsonPath.read(oJson, "$.Ägare").toString());
        }

    }

    @Test
    public void addSkatteverketsPersoner() {
        int i = 100001;
        Skatteverket skatteverket = new Skatteverket();
        List<Person> listan = skatteverket.getPersons();
        Connection con = se.nhpj.database.DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");

        Iterator<Person> itr = listan.iterator();
        while(itr.hasNext()) {
            Person person = itr.next();
            String SQL = "INSERT INTO person VALUES (" + i++ + ", '"+
                    person.getPersonnummer() +"', '"+
                    person.getFornamn() + "', 'TEST', '" +
                    person.getEfternamn() + "')";
            System.out.println(SQL);
            se.nhpj.database.DB_AccessHSQLdb.doInsert(con,SQL);

        }
        se.nhpj.database.DB_AccessHSQLdb.closeConnection(con);
    }

    @Test
    public void addSkatteverketsAdresser() {

        Connection con = se.nhpj.database.DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");

        String postadress;
        String postnummer;
        String postort;
        Adress adress;
        for (int i = 100001 ; i < 120088 ; i++) {
            adress = new Adress().getTestAdress();
            postadress = adress.getGatuAdress();
            postnummer = adress.getPostnummer().replace(" ","");
            postort = adress.getOrt();
            String SQL = "INSERT INTO adress VALUES (" + i + ", '" + postadress + "', '" + postnummer + "', '" + postort + "', 'Sverige')";
            System.out.println(SQL);
            se.nhpj.database.DB_AccessHSQLdb.doInsert(con,SQL);
        }
        se.nhpj.database.DB_AccessHSQLdb.closeConnection(con);
    }
}

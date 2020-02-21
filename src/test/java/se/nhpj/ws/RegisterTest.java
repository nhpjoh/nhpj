package se.nhpj.ws;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.internal.JsonContext;
import org.junit.Test;
import se.nhpj.database.DB_AccessHSQLdb;
import se.nhpj.rest.BaseRest;
import se.nhpj.testdata.Transaction;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class RegisterTest {

    @Test
    public void getMessage() {
        Transaction transaction = new Transaction();
        transaction.start("trans");

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("http://localhost:8080/nhpj/rest/register?reg=&brand=&year=&typ=MOTORCYKEL");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");


            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader( new InputStreamReader( (conn.getInputStream()) ) );

            String output;

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        transaction.stop("trans");
        System.out.println("Trans: " + transaction.getElapsedTime("trans",Transaction.MILLISECONDS));
        System.out.println(sb.toString());

        // Plocka ut delar och bygga ny

        // Konverterar svaret till UFT-8
        byte pText[] = sb.toString().getBytes();
        String json = null;
        try {
            json = new String(pText,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Object oJson = null;
        Object document = null;
        if( json.length()>1) {
            document = Configuration.defaultConfiguration().jsonProvider().parse(json);

            List a = JsonPath.read(document, "$.lista");

            Iterator itr =  a.iterator();

            while (itr.hasNext()) {
                oJson = itr.next();
            }
        }


        String id = JsonPath.read(oJson, "$.id");
        System.out.println(id);

        // Uppdatera nod i en jSon sträng
        Object updatedJson = JsonPath.parse(oJson).set(JsonPath.compile("$.id"), "22222").json();

        id = JsonPath.read(updatedJson, "$.id");
        System.out.println(id);

    }

    @Test
    public void postMessage() {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("http://localhost:8080/nhpj/rest/register");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            String param = "{ \"id\" : \"10001\" , \"regplat\" : \"ABC123\" , \"typ\" : \"PERSONBIL\" , \"marke\" : \"BMW 325i\" , " +
                    "\"arsmodell\" : \"1981\" , \"drivmedel\" : \"BENSIN\" , \"vaxellada\" : \"MANUELL\" , \"hastkrafter\" : \"95\" , " +
                    "\"farg\" : \"RÖD\" , \"kilometer\" : \"324500\" , \"pris\" : \"11200\" , \"bildurl\" : \" \" , \"agarid\" : \"100001\" , " +
                    "\"agare\" : \"Izabella Smäll\" }";
            os.write(param.getBytes());

            os.flush();
            os.close();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader( new InputStreamReader( (conn.getInputStream()) ) );

            String output;

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
    }

    @Test
    public void regiserPostCode() {
        String message = "{ \"id\" : \"10001\" , \"Regplåt\" : \"ABC123\" , \"Typ\" : \"PERSONBIL\" , " +
                "\"Märke\" : \"BMW 325i\" , \"Årsmodell\" : \"1981\" , \"Drivmedel\" : \"BENSIN\" , " +
                "\"Växellåda\" : \"MANUELL\" , \"Hästkrafter\" : \"95\" , \"Färg\" : \"RÖD\" , " +
                "\"Kilometer\" : \"324500\" , \"Pris\" : \"11222\" , \"BildUrl\" : \" \" , \"ÄgarId\" : \"100001\" , " +
                "\"Ägare\" : \"Izabella Smäll\" }";

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(message);

        String id = JsonPath.read(document, "$.id");
        String Regplåt = JsonPath.read(document, "$.Regplåt");
        String Typ = JsonPath.read(document, "$.Typ");
        String Märke = JsonPath.read(document, "$.Märke");
        String Årsmodell = JsonPath.read(document, "$.Årsmodell");
        String Drivmedel = JsonPath.read(document, "$.Drivmedel");
        String Växellåda = JsonPath.read(document, "$.Växellåda");
        String Hästkrafter = JsonPath.read(document, "$.Hästkrafter");
        String Färg = JsonPath.read(document, "$.Färg");
        String Kilometer = JsonPath.read(document, "$.Kilometer");
        String Pris = JsonPath.read(document, "$.Pris");
        String BildUrl = JsonPath.read(document, "$.BildUrl");
        String ÄgarId = JsonPath.read(document, "$.ÄgarId");

        //Connection con = null;
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        String sql = "UPDATE FORDON SET REGPLAT='" + Regplåt + "', TYP='" + Typ + "', MARKE='" + Märke +
                "', MODELLAR=" + Årsmodell + ", DRIVMEDEL='" + Drivmedel + "', VAXELLADA='" + Växellåda +
                "', HASTKRAFTER='" + Hästkrafter +"', FARG='" + Färg + "', KILOMETER=" + Kilometer +
                " , PRIS=" + Pris + ", BILD='" + BildUrl + "', AGARE=" + ÄgarId +
                "     WHERE ID = " + id;

        System.out.println("Update fordon: " + sql);

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        Integer rc = DB_AccessHSQLdb.doUptade(con,sql);

        // Loggar SQL:en till liggen
        System.out.println("rc: " + rc);

    }

    @Test
    public void getMessageSQLtest() {
        // Startar en timer
        se.nhpj.testdata.Transaction transaction = new Transaction();
        transaction.start("register");

        String reg   = "%";
        String brand = "%";
        String year  = "%";
        String typ = "PERSONBIL";

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

            String sql = "SELECT f.id as ID, f.regplat as Regplåt, f.Typ as Typ, f.marke as Märke, " +
                    "f.modellar as Årsmodell, f.drivmedel as Drivmedel, vaxellada as Växellåda, " +
                    "hastkrafter as Hästkrafter, farg as Färg, kilometer as Kilometer, pris as Pris, bild as BildUrl, agare as Ägarid, " +
                    "p.fornamn || ' ' || p.efternamn as Ägare " +
                    "from person p, fordon f " +
                    "where p.id = f.agare " +
                    "and f.typ = ? " +
                    "and f.regplat like ? " +
                    "and f.marke like ? " +
                    "and f.modellar like ? "
                    ;

            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);

            statement.setString(1, typ);
            statement.setString(2, reg);
            statement.setString(3, brand);
            statement.setString(4, year);
            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"id\" : \"" + result.getString("ID") +
                        "\" , \"regplat\" : \"" + result.getString("Regplåt") +
                        "\" , \"typ\" : \"" + result.getString("Typ") +
                        "\" , \"marke\" : \"" + result.getString("Märke") +
                        "\" , \"arsmodell\" : \"" + result.getString("Årsmodell") +
                        "\" , \"drivmedel\" : \"" + result.getString("Drivmedel") +
                        "\" , \"vaxellada\" : \"" + result.getString("Växellåda") +
                        "\" , \"hastkrafter\" : \"" + result.getString("Hästkrafter") +
                        "\" , \"farg\" : \"" + result.getString("Färg") +
                        "\" , \"kilometer\" : \"" + result.getString("Kilometer") +
                        "\" , \"pris\" : \"" + result.getString("Pris") +
                        "\" , \"bildurl\" : \"" + result.getString("BildUrl") +
                        "\" , \"agarid\" : \"" + result.getString("Ägarid") +
                        "\" , \"agare\" : \"" + result.getString("Ägare") +
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
        transaction.stop("register");
        sb.append("] , \"responseTime\" : \"" + transaction.getElapsedTime("register",Transaction.MILLISECONDS).intValue() + "\" }");
        String retVal = sb.toString();
        System.out.println("register: responseTime:" + transaction.getElapsedTime("register",Transaction.MILLISECONDS).intValue() + "ms data: " + retVal);

    }

}
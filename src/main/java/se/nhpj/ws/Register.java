package se.nhpj.ws;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import se.nhpj.database.DB_AccessHSQLdb;
import se.nhpj.testdata.Transaction;

import javax.ws.rs.*;
import java.sql.*;

@Path("register")
public class Register {
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getMessage( @QueryParam("reg") String reg,
                              @QueryParam("brand") String brand,
                              @QueryParam("year") String year,
                              @QueryParam("typ") String typ ) {
        // Startar en timer
        se.nhpj.testdata.Transaction transaction = new Transaction();
        transaction.start("register");

        reg   = reg.concat("%");
        brand = brand.concat("%");
        year  = year.concat("%");

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
                    "and f.modellar like ?"
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
        return retVal;
    }


    @POST
    @Consumes("application/json")
    public String postMessage( String message) {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(message);

        String id = JsonPath.read(document, "$.id");
        String Regplåt = JsonPath.read(document, "$.regplat");
        String Typ = JsonPath.read(document, "$.typ");
        String Märke = JsonPath.read(document, "$.marke");
        String Årsmodell = JsonPath.read(document, "$.arsmodell");
        String Drivmedel = JsonPath.read(document, "$.drivmedel");
        String Växellåda = JsonPath.read(document, "$.vaxellada");
        String Hästkrafter = JsonPath.read(document, "$.hastkrafter");
        String Färg = JsonPath.read(document, "$.farg");
        String Kilometer = JsonPath.read(document, "$.kilometer");
        String Pris = JsonPath.read(document, "$.pris");
        String BildUrl = JsonPath.read(document, "$.bildurl");
        String ÄgarId = JsonPath.read(document, "$.agarid");

        DB_AccessHSQLdb db = new DB_AccessHSQLdb();

        String sql = "UPDATE FORDON SET REGPLAT='" + Regplåt + "', TYP='" + Typ + "', MARKE='" + Märke +
                "', MODELLAR=" + Årsmodell + ", DRIVMEDEL='" + Drivmedel + "', VAXELLADA='" + Växellåda +
                "', HASTKRAFTER='" + Hästkrafter +"', FARG='" + Färg + "', KILOMETER=" + Kilometer +
                " , PRIS=" + Pris + ", BILD='" + BildUrl + "', AGARE=" + ÄgarId +
                "     WHERE ID = " + id;

        Connection con = db.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        Integer rc = db.doUptade(con,sql);

        // Loggar SQL:en till liggen
        System.out.println("Update fordon: " + sql);

        return "{ \"Uppdaterat\" : \""+id+"\", \"SQL-Status\" : \""+ rc +"\" }";
    }

}

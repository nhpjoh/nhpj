package se.nhpj.ws.vinhyllan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import se.nhpj.database.DB_AccessHSQLdb;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Vinhyllan {

    /**
     * Denna metod returnerar en json sträng.
     * @param typ typ fältet i vinhyllan_kodverk
     * @return json String
     */
    public String getKodverk(String typ) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select typ, text, varde from vinhyllan_kodverk where typ = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setString(1, typ);

            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"typ\" : \"" + result.getString("typ") +
                        "\" , \"text\" : \"" + result.getString("text") +
                        "\" , \"varde\" : \"" + result.getString("varde") +
                        "\" } ,");
            }
            con.commit();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        // Ta bort sista kommat i stängen+
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-1)) {
            sb.replace(kommat,len,"");
        }
        sb.append("] }");
        String json = sb.toString();

        return json;
    }
    public String getKodverk(String typ, Integer varde) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select typ, text, varde from vinhyllan_kodverk where typ = ? and varde = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setString(1, typ);
            statement.setInt(2, varde);

            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"typ\" : \"" + result.getString("typ") +
                        "\" , \"text\" : \"" + result.getString("text") +
                        "\" , \"varde\" : \"" + result.getString("varde") +
                        "\" } ,");
            }
            con.commit();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        // Ta bort sista kommat i stängen+
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-1)) {
            sb.replace(kommat,len,"");
        }
        sb.append("] }");
        String json = sb.toString();

        return json;
    }
    public void   addKodverk(String typ, String text, Integer varde) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "INSERT INTO vinhyllan_kodverk VALUES(?,?,?,?)";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, getSequenseValue());
            statement.setString(2, typ);
            statement.setString(3, text);
            statement.setInt(4, varde);

            statement.execute();
            con.commit();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }
    public void   delKodverk(String typ, String text, Integer varde) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "DELETE FROM vinhyllan_kodverk WHERE typ = ? AND text = ? AND varde = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setString(1, typ);
            statement.setString(2, text);
            statement.setInt(3, varde);

            statement.execute();
            statement.close();
            con.commit();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Denna metod returnerar de druvor som är kopplade till detta dryck_id:t
     * @param dryck_id id på ett vin/dryck
     * @return json String
     */
    public String getDruvor(Integer dryck_id) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select dryck_id, namn from vinhyllan_druvor where dryck_id = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);

            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"dryck_id\" : \"" + result.getString("dryck_id") +
                        "\" , \"namn\" : \"" + result.getString("namn") +
                        "\" } ,");
            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        // Ta bort sista kommat i stängen+
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-1)) {
            sb.replace(kommat,len,"");
        }
        sb.append("] }");
        String json = sb.toString();

        return json;
    }
    /**
     * Denna metod lägger till en druva till ett dryck_id
     * @param dryck_id Drycken som ska ha kopplingen till detta namn
     * @param namn Namnet på det som ska kopplas till drycken
     */
    public void   addDruva(Integer dryck_id, String namn) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "INSERT INTO vinhyllan_druvor VALUES(?,?)";

        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);
            statement.setString(2, namn);

            statement.execute();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }
    public void   addDruvor(Integer dryck_id, List druvor ) {
        Iterator itr = druvor.iterator();
        while (itr.hasNext()) {
            addDruva(dryck_id, itr.next().toString());
        }
    }

    /**
     * Denna metod lägger till en druva till ett dryck_id
     * @param dryck_id Drycken som ska ha kopplingen till detta namn
     * @param namn Namnet på det som ska kopplas till drycken
     */
    public void   delDruva(Integer dryck_id, String namn) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "DELETE FROM vinhyllan_druvor WHERE dryck_id = ? and namn = ?";

        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);
            statement.setString(2, namn);

            statement.execute();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Denna metod returnerar de maträtter denna dryck passat till
     * @param dryck_id id på ett vin/dryck
     * @return json String
     */
    public String getPassarMatratt(Integer dryck_id) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select dryck_id, namn, kalla, recept from vinhyllan_passar_matratt where dryck_id = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);

            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"dryck_id\" : \"" + result.getString("dryck_id") +
                        "\" , \"namn\" : \"" + result.getString("namn") +
                        "\" , \"kalla\" : \"" + result.getString("kalla") +
                        "\" , \"recept\" : \"" + result.getString("recept") +
                        "\" } ,");
            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        // Ta bort sista kommat i stängen+
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-1)) {
            sb.replace(kommat,len,"");
        }
        sb.append("] }");
        String json = sb.toString();

        return json;
    }
    public void   addPassarMatratt(Integer dryck_id, String namn, String kalla, String recept) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "INSERT INTO vinhyllan_passar_matratt VALUES(?,?,?,?)";

        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);
            statement.setString(2, namn);
            statement.setString(3, kalla);
            statement.setString(4, recept);

            statement.execute();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    public void   addPassarMatratt(Integer dryck_id, List matratter) {
        Iterator<Matratt> itr = matratter.iterator();
        while(itr.hasNext()) {
            Matratt matratt = itr.next();
            addPassarMatratt(dryck_id,matratt.getNamn(),matratt.getKalla(),matratt.getRecept());
        }
    }

    public void   delPassarMatratt(Integer dryck_id, String namn) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "DELETE FROM vinhyllan_passar_matratt WHERE dryck_id = ? and namn = ?";

        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);
            statement.setString(2, namn);

            statement.execute();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Denna metod returnerar de typer av mat enlingd systemet som denna dryck passat till
     * @param dryck_id id på ett vin/dryck
     * @return json String
     */
    public String getPassarMattyp(Integer dryck_id) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select dryck_id, namn from vinhyllan_passar_mattyp where dryck_id = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);

            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"dryck_id\" : \"" + result.getString("dryck_id") +
                        "\" , \"namn\" : \"" + result.getString("namn") +
                        "\" } ,");
            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        // Ta bort sista kommat i stängen+
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-1)) {
            sb.replace(kommat,len,"");
        }
        sb.append("] }");
        String json = sb.toString();

        return json;
    }
    public String getPassarMattypUsed() {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select distinct namn from vinhyllan_passar_mattyp order by namn";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);

            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"namn\" : \"" + result.getString("namn") + "\" } ,");
            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        // Ta bort sista kommat i stängen+
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-1)) {
            sb.replace(kommat,len,"");
        }
        sb.append("] }");
        String json = sb.toString();

        return json;
    }
    public String getPassarMattypAll() {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select distinct text from vinhyllan_kodverk where TYP='PASSAR_TILL_MATTYP' order by text";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);

            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"lista\" : [ ");
            while (result.next()) {
                sb.append("{ \"namn\" : \"" + result.getString("text") + "\" } ,");
            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        // Ta bort sista kommat i stängen+
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-1)) {
            sb.replace(kommat,len,"");
        }
        sb.append("] }");
        String json = sb.toString();

        return json;
    }
    public void   addPassarMattyp(Integer dryck_id, String namn) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "INSERT INTO vinhyllan_passar_mattyp VALUES(?,?)";

        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);
            statement.setString(2, namn);

            statement.execute();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    public void   addPassarMattyp(Integer dryck_id, List mattyper) {
        Iterator itr = mattyper.iterator();
        while (itr.hasNext()) {
            addPassarMattyp(dryck_id,itr.next().toString());
        }
    }

    public void   delPassarMattyp(Integer dryck_id, String namn) {
        ResultSet result = null;
        PreparedStatement statement = null;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "DELETE FROM vinhyllan_passar_mattyp WHERE dryck_id = ? and namn = ?";

        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);
            statement.setString(2, namn);

            statement.execute();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Swnna metod returnerar viken possition som en dryck finns (dryck_id)
     * @param dryck_id
     * @return possition i vinhyllan
     */
    public Integer getPosition(Integer dryck_id) {
        ResultSet result = null;
        PreparedStatement statement = null;
        Integer pos = -1;

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select position from vinhyllan_position where dryck_id = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);

            statement.execute();
            result = statement.getResultSet();
            while (result.next()) {
                pos = result.getInt("position");
            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return pos;
    }       // returns possition
    public Integer getPositionInfo(Integer position) {
        ResultSet result = null;
        PreparedStatement statement = null;
        Integer pos = -1;

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select dryck_id from vinhyllan_position where position = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, position);

            statement.execute();
            result = statement.getResultSet();
            while (result.next()) {
                pos = result.getInt("dryck_id");
            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return pos;
    }   // returns dryck_id
    public String  getAllPositionsInfo() {
        ResultSet result = null;
        PreparedStatement statement = null;
        Integer pos = -1;
        StringBuilder sb = new StringBuilder();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select * from vinhyllan_position order by position";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);

            statement.execute();
            result = statement.getResultSet();
            sb.append("{ \"listan\" : [ ");
            while (result.next()) {
                sb.append("{ \"position\" : \"");
                sb.append(result.getInt("position"));
                sb.append("\" , \"dryck_id\" : \"");
                sb.append(result.getInt("dryck_id"));
                sb.append("\" }, ");
            }
            sb.append(" ] }");
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        String json = sb.toString();
        json = json.replace(",  ", " ");

        return json;

    }               // returns a json list of all position, dryck_id
    public void    addPosition(Integer position, Integer dryck_id) {
        ResultSet result = null;
        PreparedStatement statement = null;

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "INSERT INTO vinhyllan_position VALUES (?,?)";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, position);
            statement.setInt(2, dryck_id);

            statement.execute();
            con.commit();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }
    public void    delPosition(Integer position) {
        ResultSet result = null;
        PreparedStatement statement = null;

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "DELETE FROM vinhyllan_position WHERE position = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, position);

            statement.execute();
            con.commit();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Denna method returnerar dryck_id för possition.
     * @param possition
     * @return dryck_id;
     */
    public Integer getDryckAtPossition(Integer possition){
        ResultSet result = null;
        PreparedStatement statement = null;
        Integer pos = -1;

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "select dryck_id from vinhyllan_position where position = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, possition);

            statement.execute();
            result = statement.getResultSet();
            while (result.next()) {
                pos = result.getInt("dryck_id");
            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return pos;
    }

    /**
     * Denna metod returnerar att unikt värde att använda som id i vinhyllans tabeller
     * @return Integer med unikt värde
     */
    public Integer getSequenseValue() {
        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "call NEXT VALUE FOR vinhyllan_seq";
        Integer val = DB_AccessHSQLdb.getIntegerValue(con,sql,"@p0");
        return val;
    }

    /**
     * Denna metod returnerar vad som ska visas i position 'nn' i vinhyllan
     * @param position Integer, position i vinhyllan
     * @return String
     */
    public String getHyllFacksTyp(Integer position) {
        Vinhyllan v = new Vinhyllan();
        Dryck dryck = v.getDryck(v.getDryckAtPossition(position));

        String dryck_typ = dryck.getDryck_typ();
        String retVal = null;

        switch (dryck_typ) {
            case "RÖTT" :       retVal = "red"; break;
            case "VITT" :       retVal = "yellow"; break;
            case "MOSSERANDE" : retVal = "yellow"; break;
            case "ROSÉ" :       retVal = "pink"; break;
            case "ÖL" :         retVal = "lightgreen"; break;
            case "WHISKEY" :    retVal = "brown"; break;
            default :           retVal = "white"; break;
        }
        return  retVal;
    }

    /**
     * Denna metod returnera en Dryck beroände på Dryck_id
     * @param dryck_id id i databasen
     * @return Dryck - av klassen Dryck.
     */
    public Dryck getDryck(Integer dryck_id) {
        Dryck dryck = new Dryck();

        ResultSet result = null;
        PreparedStatement statement = null;
        // Connection till databasen
        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "SELECT * from vinhyllan where dryck_id = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);
            // Skickar SQL till databasen
            statement.execute();
            result = statement.getResultSet();
            // Stegar igenom resultatet
            while (result.next()) {
                dryck.setDryck_id(result.getInt("dryck_id"));
                dryck.setDryck_typ(result.getString("dryck_typ"));
                dryck.setNamn(result.getString("namn"));
                dryck.setDryck_kvalitet(result.getString("dryck_kvalitet"));
                dryck.setBeskrivning(result.getString("beskrivning"));
                dryck.setSotma(result.getInt("sotma"));
                dryck.setFyllighet(result.getInt("fyllighet"));
                dryck.setStravhet(result.getInt("stravhet"));
                dryck.setFruktstyrka(result.getInt("fruktstyrka"));
                dryck.setProducent(result.getString("producent"));
                dryck.setArtal(result.getString("artal"));
                dryck.setSista_dag(result.getString("sista_dag"));
                dryck.setLand(result.getString("land"));
                dryck.setDestrikt(result.getString("destrikt"));
                dryck.setAlkoholhalt(result.getFloat("alkoholhalt"));
                dryck.setDoft(result.getString("doft"));
                dryck.setFarg(result.getString("farg"));
                dryck.setPris(result.getFloat("pris"));
                dryck.setBild(result.getString("bild"));
                dryck.setBetyg(result.getInt("betyg"));
                dryck.setOmdomme(result.getString("omdomme"));
                dryck.setSystemet_id(result.getString("systemet_id"));
                dryck.setSystemet_link(result.getString("systemet_link"));

                // Fyller listor passar_mattyp, passar_matratt, druvor via dryck_id
                List passar_mattyp  = new ArrayList();
                List passar_matratt = new ArrayList();
                List druvor         = new ArrayList();
                // Hämtar upp data från databasen i form av en json strän
                String passar_mattyp_json   = this.getPassarMattyp(dryck_id);
                String passar_matratt_json  = this.getPassarMatratt(dryck_id);
                String druvor_json          = this.getDruvor(dryck_id);
                // Konverterar json sträng till ett JsonPath object
                Object document_passar_mattyp   = Configuration.defaultConfiguration().jsonProvider().parse(passar_mattyp_json);
                Object document_passar_matratt  = Configuration.defaultConfiguration().jsonProvider().parse(passar_matratt_json);
                Object document_druvor          = Configuration.defaultConfiguration().jsonProvider().parse(druvor_json);
                List a   = JsonPath.read(document_passar_mattyp, "$.lista");
                List b    = JsonPath.read(document_passar_matratt, "$.lista");
                List c    = JsonPath.read(document_druvor, "$.lista");
                // Skapar List object från JsonPath objektet
                Iterator itr1 =  a.iterator();
                while (itr1.hasNext()) { passar_mattyp.add(JsonPath.read(itr1.next(),"$.namn").toString()); }
                Iterator itr2 =  b.iterator();
                while (itr2.hasNext()) {
                    Object o = itr2.next();
                    passar_matratt.add(new Matratt(JsonPath.read(o,"$.namn").toString(),JsonPath.read(o,"$.kalla").toString(),JsonPath.read(o,"$.recept").toString()));
                }
                Iterator itr3 =  c.iterator();
                while (itr3.hasNext()) { druvor.add(JsonPath.read(itr3.next(),"$.namn").toString()); }

                dryck.setPassar_mattyp(passar_mattyp);
                dryck.setPassar_matratt(passar_matratt);
                dryck.setDruvor(druvor);

            }
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }

        return dryck;
    }

    public String getDryck(String namn, String matratt, String mattyp, String druva) {
        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        StringBuilder sb = new StringBuilder();
        PreparedStatement stmt;
        ResultSet result;

        switch (mattyp) {
            case "NOT"              : mattyp = "NÖT"; break;
            case "FLASK"            : mattyp = "FLÄSK"; break;
            case "FAGEL"            : mattyp = "FÅGEL"; break;
            case "SALLSKAPSDRYCK"   : mattyp = "SÄLLSKAPSDRYCK"; break;
            case "BUFFEMATBUFFEMAT" : mattyp = "BUFFEMATBUFFÉMAT"; break;
        }


        namn = "%"+namn+"%";
        String passar_matratt = "%"+matratt+"%";
        String passar_mattyp = "%"+mattyp+"%";
        druva = "%"+druva+"%";
        String dryck_kvalitet = "%%";

        String sql = "select DISTINCT (a.position), (b.dryck_id), (e.namn), (e.dryck_kvalitet) " +
                "from vinhyllan_position a, vinhyllan_passar_matratt b, vinhyllan_passar_mattyp c, vinhyllan_druvor d, vinhyllan e " +
                "where a.dryck_id = b.dryck_id and a.dryck_id = c.dryck_id and a.dryck_id = d.dryck_id and a.dryck_id = e.dryck_id " +
                "and b.namn like ? " +          // maträtt
                "and c.namn like ? " +          // mattyp
                "and d.namn like ? " +          // druva
                "and e.namn like ? " +          // drycknamn
                "and e.dryck_kvalitet like ? "+ // dryck_kvalitet
                "order by a.position";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1,passar_matratt);
            stmt.setString(2,passar_mattyp);
            stmt.setString(3,druva);
            stmt.setString(4,namn);
            stmt.setString(5,dryck_kvalitet);
            result = stmt.executeQuery();
            sb.append("{ \"listan\" : [ ");
            while (result.next()) {
                Integer retVal_position = result.getInt("position");
                Integer retVal_dryck_id = result.getInt("dryck_id");
                String retVal_namn = result.getString("namn");
                String retVal_dryck_kvalitet = result.getString("dryck_kvalitet");
                sb.append("{ \"position\" : \"");
                sb.append(retVal_position);
                sb.append("\" , \"dryck_id\" : \"");
                sb.append(retVal_dryck_id);
                sb.append("\" , \"namn\" : \"");
                sb.append(retVal_namn);
                sb.append("\" , \"dryck_kvalitet\" : \"");
                sb.append(retVal_dryck_kvalitet);
                sb.append("\" }, ");
            }
            sb.append(" ] }");
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String json = sb.toString();
        json = json.replace(",  ", " ");

        return json;
    }

    private java.sql.Date parseArtal(String artal) {
        DateFormat format = new SimpleDateFormat("yyyy");
        java.util.Date datum=null;
        try {
            datum = format.parse(artal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(datum.getTime());
    }
    private java.sql.Date parseSista_dag(String datum) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d=null;
        try {
            d = format.parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(d.getTime());
    }


    public Integer addDryck(Dryck dryck) {
        ResultSet result = null;
        PreparedStatement statement = null;
        Integer dryck_id = this.getSequenseValue();

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "INSERT INTO vinhyllan VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt   (1,  dryck_id);
            statement.setString(2,  dryck.getDryck_typ());
            statement.setString(3,  dryck.getNamn());
            statement.setString(4,  dryck.getDryck_kvalitet());
            statement.setString(5,  dryck.getBeskrivning());
            statement.setInt   (6,  dryck.getSotma());
            statement.setInt   (7,  dryck.getFyllighet());
            statement.setInt   (8,  dryck.getStravhet());
            statement.setInt   (9,  dryck.getFruktstyrka());
            statement.setString(10, dryck.getProducent());
            statement.setDate  (11, (java.sql.Date) parseArtal(dryck.getArtal()));
            statement.setDate  (12, (java.sql.Date) parseSista_dag(dryck.getSista_dag()));
            statement.setString(13, dryck.getLand());
            statement.setString(14, dryck.getDestrikt());
            statement.setFloat (15, dryck.getAlkoholhalt());
            statement.setString(16, dryck.getDoft());
            statement.setString(17, dryck.getFarg());
            statement.setFloat (18, dryck.getPris());
            statement.setString(19, dryck.getBild());
            statement.setInt   (20, dryck.getBetyg());
            statement.setString(21, dryck.getOmdomme());
            statement.setString(22, dryck.getSystemet_id());
            statement.setString(23, dryck.getSystemet_link());

            statement.execute();
            con.commit();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
            return -1;
        }
        this.addPassarMattyp(dryck_id, dryck.getPassar_mattyp());
        this.addPassarMatratt(dryck_id, dryck.getPassar_matratt());
        this.addDruvor(dryck_id, dryck.getDruvor());
        // Returnerar dryck_id;
        return dryck_id;
    }

    public void delDryck(Integer dryck_id) {
        ResultSet result = null;
        PreparedStatement statement = null;

        Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
        String sql = "DELETE FROM vinhyllan WHERE dryck_id = ?";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);
            statement.setInt(1, dryck_id);

            statement.execute();
            con.commit();
            statement.close();
            con.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }

    }

    public String getAllDryckVinhylla() {
        Vinhyllan v = new Vinhyllan();
        String jsonPositions = v.getAllPositionsInfo();
        StringBuilder sb = new StringBuilder();
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonPositions);
        List a = JsonPath.read(document, "$.listan");
        Iterator itr =  a.iterator();
        sb.append("{ \"listan\" : [ ");
        while (itr.hasNext()) {
            Object o = itr.next();
            Integer position = Integer.parseInt( JsonPath.read(o,"$.position") );
            Integer dryck_id = Integer.parseInt( JsonPath.read(o,"$.dryck_id") );

            sb.append("{ \"position\" : " + position + " , \"dryck\" : ");
            Dryck dryck = v.getDryck(dryck_id);
            try {
                // Konvertera till json string //
                ObjectMapper mapper = new ObjectMapper();
                String s =  mapper.writer().writeValueAsString(dryck);
                sb.append(s);
                sb.append(" }, ");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        sb.append(" ] }");
        String json = sb.toString();
        json = json.replace(",  ", " ");
        return json;
    }

    /* ToDo

    public String getDryck(String json) {     return "";  }
    public List   getDryck(Dryck dryck) {      return new ArrayList();  }

    public void addDryck(String json) { }

    */

}

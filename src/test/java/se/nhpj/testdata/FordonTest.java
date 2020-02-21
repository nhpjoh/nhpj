package se.nhpj.testdata;

import org.junit.Test;
import se.nhpj.database.DB_AccessHSQLdb;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;

import static java.lang.System.err;
import static java.lang.System.exit;
import static org.junit.Assert.*;

public class FordonTest {

    @Test
    public void test_getRndRegNummer() {
        System.out.println(se.nhpj.testdata.Fordon.getRndRegNummer());
    }

    @Test
    public void test_getRndColor() {
        System.out.println(se.nhpj.testdata.Fordon.getRndColor());
    }

    @Test
    public void test_getRndBrand() {
        System.out.println(se.nhpj.testdata.Fordon.getRndBrand(Fordon.BIL));

        System.out.println(se.nhpj.testdata.Fordon.getRndBrand(Fordon.MOTORCYCKEL));
    }

    @Test
    public void test() throws UnsupportedEncodingException {
        int id = 12500;
        for (int i=0 ; i < 5 ; i++ ) {
            String sql = skapaBilInadata(id++);
            System.out.println(sql);

            // Konverterar svaret till UFT-8
            byte pText[] = sql.getBytes();
            String json = new String(pText,"ISO_8859_1"); //ISO_8859_1 // UTF-8

            Connection con = DB_AccessHSQLdb.getConnection("jdbc:hsqldb:hsql://localhost/register", "SA", "");
            Integer rc = DB_AccessHSQLdb.doInsert(con,sql);
        }
    }

    public String skapaMCInadata(int id) {
        String plåt = se.nhpj.testdata.Fordon.getRndRegNummer();
        String färg = se.nhpj.testdata.Fordon.getRndColor();
        String owner = se.nhpj.testdata.RndData.getANumber(100000,120087);
        String s = se.nhpj.testdata.Fordon.getRndBrand(Fordon.MOTORCYCKEL);
        String[] split = s.split(":");

        return "INSERT INTO fordon VALUES ("+id+",'"+plåt+"','MOTORCYKEL','" + split[0] +"',"+ split[1] +",'"+ split[2] +"','"+ split[3] +"','"+ split[4] +"','"+färg.toUpperCase()+"',,,'',"+owner+");";
    }

    public String skapaBilInadata(int id) {
        String retVal="";
        String plåt = se.nhpj.testdata.Fordon.getRndRegNummer();
        String färg = se.nhpj.testdata.Fordon.getRndColor();
        String owner = se.nhpj.testdata.RndData.getANumber(100000,120087);
        String s = se.nhpj.testdata.Fordon.getRndBrand(Fordon.BIL);
        try {
            String[] split = s.split(":");
            retVal = "INSERT INTO fordon VALUES ("+id+",'"+plåt+"','PERSONBIL','" + split[0] +"',"+ split[1] +",'"+ split[2] +"','"+ split[3] +"','"+ split[4] +"','"+färg.toUpperCase()+"',0,0,'',"+owner+")";
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("java.lang.ArrayIndexOutOfBoundsException: " + s);
            exit(-1);
        }
        return retVal;
    }
}
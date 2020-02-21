package se.nhpj.soap.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.database.*;

/**
 * Denna klass innehåller ett antal metoder för att ändra på datum i olika databaser så som recept, forsorig, fota etc...
 * @author nhpj
 */
public class ManipuleraReceptDatum {

    
    // Flytta i pris oxå ....
    // PRIS_PROD.HANDELSE
    // PRIS_PROD.KONTO
    // PRIS_PROD.PERIOD
    // PRIS_PROD.TRANSAKTION
    
//    public static Integer changeDateHKDBtransaktion( Integer dagar, String personnummer, String OracleServiceName ) {
//        Integer retVal=null;
//        String sql;
//        PreparedStatement statement = null;
//        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
//         
//        sql = "";
//
//        try {
//            statement = con.prepareStatement(sql);
//            statement.setInt(1, dagar);
//            statement.setInt(2, dagar);
//            statement.setInt(3, dagar);
//            statement.setInt(4, dagar);
//                
//            retVal = statement.executeUpdate();
//            DB_Access.closeConnection(con);
//        } catch (SQLException e ) {
//            System.out.println(e.getMessage());
//        }
//        DB_Access.closeConnection(con);
//
//        if (retVal==1) { System.out.println("changeDateHKDBtransaktion: OK : " + dagar + " dagar bakåt i tiden"); }
//        else { System.out.println("changeDateHKDBtransaktion: Failed: " + retVal); }
//        return retVal;
//    }
    
        
    /**
     * Denna metod uppdaterar FORSTRANS tabellen i FOTA_PROD med dagar +/- 
     *    --- Förbätrad och anpassad för EXAData med prepare / execute istället för en vanilg execute ---
     * 
     * För att detta ska fungera måste partitioneringsinställningarna ändras
     * 
     * @param dagar - Antal dagar +/-
     * @param expeditionsId - Ett expeditionsId
     * @param OracleServiceName - ex. INT5 eller PTRR
     * @return - Returnerar 1 om uppdateringen gick bra
     */
    public static Integer changeDateFotaPrepared( Integer dagar, String expeditionsId, String OracleServiceName ) {
        Integer retVal=null;
        String sql;
        PreparedStatement statement = null;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        
        sql =   " update FOTA_PROD.FORSTRANS set " +
                " PERSTARTDATUM = PERSTARTDATUM + ? " +
                ",UTFDATUM = UTFDATUM + ? "  +
                ",AVHDATUM = AVHDATUM + ? " +
                ",SPARAD = SPARAD + ? " +
                " where expeditionsId = ? ";
        try {
            statement = con.prepareStatement(sql);
//            System.out.println(sql);
                statement.setInt(1, dagar);
                statement.setInt(2, dagar);
                statement.setInt(3, dagar);
                statement.setInt(4, dagar);
                statement.setString(5, expeditionsId);
                
                retVal = statement.executeUpdate();
                statement.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        DB_Access.closeConnection(con);
        if (retVal==1) { System.out.println("changeDateFotaPrepared: OK : " + dagar + " dagar bakåt i tiden"); }
        else { System.out.println("changeDateFotaPrepared: Failed: " + retVal); }
        return retVal;
    }
     
    /**
     * Denna metod uppdaterar datum i tabellen uttag med dagar +/-
     *    --- Förbätrad och anpassad för EXAData med prepare / execute istället för en vanilg execute ---
     * @param dagar - Antal dagar +/-
     * @param ordinationsId - Ett ordinationsId
     * @param expeditionsId - Är samma som receptId i databastabellen
     * @param OracleServiceName - ex. INT5 eller PTRR
     * @return - Returnerar 1 om uppdateringen gick bra
     */
    public static Integer changeDateGodkannUttagPrepared( Integer dagar, String ordinationsId, String expeditionsId, String OracleServiceName ) {
        Integer retVal=null;
        PreparedStatement statement = null;
        String sql;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        
        sql =   "update RR_PROD.uttag set " +
                " rrstatusdate = rrstatusdate + ? " +
                ",rrcreationdate = rrcreationdate + ? " +
                ",expdatum = expdatum + ? " +
                ",fmkdate = fmkdate + ? " +
                " where receptid = ? " + 
                " and exphuvid = ? ";
            try {
                statement = con.prepareStatement(sql);
//            System.out.println(sql);
                statement.setInt(1, dagar);
                statement.setInt(2, dagar);
                statement.setInt(3, dagar);
                statement.setInt(4, dagar);
                statement.setString(5, ordinationsId);
                statement.setString(6, expeditionsId);
                
                retVal = statement.executeUpdate();
                DB_Access.closeConnection(con);
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        DB_Access.closeConnection(con);
        if (retVal==1) { System.out.println("changeDateGodkannUttagPrepared: OK: " + dagar + " dagar bakåt i tiden"); }
        else { System.out.println("changeDateGodkannUttagPrepared: Failed: " + retVal + " rader uppdaterade"); }
        return retVal;
    }
    
    /** Hämta eRecreceptId med hjälp av ordinationsId från FORSKRORIG
     * 
     * @param ordinationsId - Ett ordinationsId
     * @param OracleServiceName - ex. INT5 eller PTRR
     * @return eRecreceptId som en String
     */
    public static String getEReceptidPrepared(String ordinationsId, String OracleServiceName) {
        String retVal=null;
        PreparedStatement statement = null;
        
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        String sql = "select f.ERECRECEPTID erec from RR_PROD.recept r, RR_PROD.FORSKRORIG f where r.RRFORSKRORIGINAL = f.id and r.ID = ?";

        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, ordinationsId);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                retVal = result.getString("erec");
                statement.close();
                //con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        String eRecreceptId = DB_Access.getStringValue(con, "select f.ERECRECEPTID erec from RR_PROD.recept r, RR_PROD.FORSKRORIG f where r.RRFORSKRORIGINAL = f.id and r.ID = " + ordinationsId,"erec");
        DB_Access.closeConnection(con);
        
        return retVal;
    }
    
    /**
     * Denna metod ändrar datum i tabellen TBLLAKFOR med dagar + eller minus. Har du inget receptId skriv tom sträng ""
     * 
     * Om uttag gjorts så är normalfallet att  man också gjort rapportering till LF
     * Regel: Om uttag är gjort på läkemedel sker rapportering annars EJ
     * 
     * @param dagar - Antal dagar +/-
     * @param receptId - Ett receptId         
     * @param expeditionsId - Ett expeditionsId    
     * @param OracleServiceName     ex. INT5 eller PTRR
     * @return - Returnerar 1 om uppdateringen gick bra
     */
    public static Integer changeDateExpeditionLFPrepared( Integer dagar, String receptId, String expeditionsId, String OracleServiceName ) {
        Integer retVal=null;
        PreparedStatement statement = null;
        String sql;
        
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        
        try {
            if (receptId.isEmpty()) {
                sql =   "update lakfor_prod.tbllakfor set " + 
                        " Exp_Datum = Exp_Datum + ? " +
                        ",Lag_Datum = to_char( to_date(Lag_Datum,'YYYY-MM-DD')+ ? , 'YYYY-MM-DD')" +
                        ",Lag_Tid = Lag_Tid + ? " +
                        " where (expeditions_id = ? or urspr_expeditions_id = ?)";
                statement = con.prepareStatement(sql);
                statement.setInt(1, dagar);
                statement.setInt(2, dagar);
                statement.setInt(3, dagar);
                statement.setString(4, expeditionsId);
                statement.setString(5, expeditionsId);

            } else {
                sql =   "update lakfor_prod.tbllakfor set " + 
                        " Exp_Datum = Exp_Datum + ? " +
                        ",Lag_Datum = to_char( to_date(Lag_Datum,'YYYY-MM-DD')+ ?, 'YYYY-MM-DD')" +
                        ",Lag_Tid = Lag_Tid + ? " +
                        " where (expeditions_id = ? or urspr_expeditions_id = ? " +
                        " and  receptid = ?)";
                statement = con.prepareStatement(sql);
                statement.setInt(1, dagar);
                statement.setInt(2, dagar);
                statement.setInt(3, dagar);
                statement.setString(4, expeditionsId);
                statement.setString(5, expeditionsId);
                statement.setString(6, receptId);
            }
            retVal = statement.executeUpdate();
            statement.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        DB_Access.closeConnection(con);

        if (retVal==1) { System.out.println("changeDateExpeditionLFPrepared: OK: " + dagar + " dagar bakåt i tiden"); }
        else { System.out.println("changeDateExpeditionLFPrepared: Failed: " + retVal); }
        return retVal;
    }
    /**
     * Denna metod ändrar datum i tabellen TBLLAKFOR med dagar + eller minus. Har du inget receptId skriv tom sträng ""
     * (Uppdaterad changeDateExpeditionLFPrepared för stt gå snabbare)
     * Om uttag gjorts så är normalfallet att  man också gjort rapportering till LF
     * Regel: Om uttag är gjort på läkemedel sker rapportering annars EJ
     * 
     * @param dagar - Antal dagar +/-
     * @param receptId - Ett receptId          
     * @param expeditionsId - expeditionsId   
     * @param OracleServiceName - ex. INT5 eller PTRR
     * @return - Reurnerar 1 om uppdateringen gick bra
     */
    public static Integer changeDateExpeditionLFPreparedLFID( Integer dagar, String receptId, String expeditionsId, String OracleServiceName ) {
        Integer retVal=null;
        PreparedStatement statement = null;
        String sql;
        ResultSet rs;
        String lfid;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        
        try {
            sql = "SELECT LFID FROM lakfor_prod.tbllakfor WHERE (expeditions_id = ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, expeditionsId);
            rs = statement.executeQuery();
            rs.next();
            System.out.println( rs.getString("LFID") );
            lfid = rs.getString("LFID");
            

            if (receptId.isEmpty()) {
                sql =   "update lakfor_prod.tbllakfor set " + 
                        " Exp_Datum = Exp_Datum + ? " +
                        ",Lag_Datum = to_char( to_date(Lag_Datum,'YYYY-MM-DD')+ ? , 'YYYY-MM-DD')" +
                        ",Lag_Tid = Lag_Tid + ? " +
                        " where lfid = ?";
                statement = con.prepareStatement(sql);
                statement.setInt(1, dagar);
                statement.setInt(2, dagar);
                statement.setInt(3, dagar);
                statement.setString(4, lfid);

            } else {
                sql =   "update lakfor_prod.tbllakfor set " + 
                        " Exp_Datum = Exp_Datum + ? " +
                        ",Lag_Datum = to_char( to_date(Lag_Datum,'YYYY-MM-DD')+ ?, 'YYYY-MM-DD')" +
                        ",Lag_Tid = Lag_Tid + ? " +
                        " where ( lfid = ? " +
                        " and  receptid = ?)";
                statement = con.prepareStatement(sql);
                statement.setInt(1, dagar);
                statement.setInt(2, dagar);
                statement.setInt(3, dagar);
                statement.setString(4, lfid);
                statement.setString(5, receptId);
            }
            retVal = statement.executeUpdate();
            statement.close();
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        DB_Access.closeConnection(con);

        if (retVal==1) { System.out.println("changeDateExpeditionLFPrepared: OK: " + dagar + " dagar bakåt i tiden"); }
        else { System.out.println("changeDateExpeditionLFPrepared: Failed: " + retVal); }
        return retVal;
    }



    /**
     * Denna metod ändrar datum i forskorig, recept, ordinationslista antal dagar +/-
     * @param dagar - Antal dagar +/-
     * @param personnummer - Ett personnummer
     * @param eRecreceptId - Ett eRecreceptId
     * @param OracleServiceName - ex. INT5 eller PTRR
     * @return - Returnerar 1 om uppdateringen gick bra
     */
    public static Integer changeDateNyttRecept( Integer dagar, String personnummer, String eRecreceptId, String OracleServiceName ) {
        Integer retVal;
        String sql;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        
        sql =" update RR_PROD.forskrorig set " +
             " forskrdatumtid=forskrdatumtid + " + dagar + 
             ",rrcreationdate=rrcreationdate + " + dagar + 
             ",FMKDATE = FMKDATE + " + dagar +
             ",INTERCHANGEDATETIME = INTERCHANGEDATETIME + " + dagar +
             ",ISSUEDATEANDTIMEOFMESSAGE = ISSUEDATEANDTIMEOFMESSAGE + "+ dagar +
             " where erecreceptid= '" + eRecreceptId +"'";
        
        retVal = DB_Access.doUptade(con, sql);
        if (retVal==1) { System.out.println("changeDateNyttRecept: forskrorig : OK: " + dagar + " dagar bakåt i tiden"); }
        else { System.out.println("changeDateNyttRecept: forskrorig: Failed: " + retVal); }

        sql = " update RR_PROD.recept set " +
              " uttagforstafore=uttagforstafore +" + dagar +
              ",uttaggiltigtom=uttaggiltigtom +" + dagar + 
              ",rrcreationdate=rrcreationdate +" + dagar +
              ",rrstatusdate= rrstatusdate +" + dagar +             //to_date(rrstatusdate +"+ dagar + ",'YYYY-MM-DD HH24:MI:SS')" +
              ",rrfmkdate=rrfmkdate +" + dagar +
  //            ",insattningsdatum=insattningsdatum +" + dagar_insattning + 
              " Where rrforskroriginal=( select id from rr_prod.forskrorig where erecreceptid='"+eRecreceptId+"')";
        
        retVal = DB_Access.doUptade(con, sql);
        if (retVal>0) { System.out.println("changeDateNyttRecept: recept : OK: " + dagar + " dagar bakåt i tiden : " + retVal + " rader uppdaterade"); }
        else { System.out.println("changeDateNyttRecept: recept: Failed: " + retVal); }
        
        sql =   " update rr_prod.ordinationslista set " + 
//                " ordtidpunkt = ordtidpunkt +" + dagar +
                " ordtidpunkt = sysdate " + dagar +
                ",statusdate = sysdate +" + dagar +
                " where personnummer = " + personnummer;
        
        retVal = DB_Access.doUptade(con, sql);
        DB_Access.closeConnection(con);
        if (retVal==1) { System.out.println("changeDateNyttRecept: ordinationslista : OK: " + dagar + " dagar bakåt i tiden"); }
        else { System.out.println("changeDateNyttRecept: ordinationslista: Failed: " + retVal); }

        return retVal;
    }
        
    /** Kollar om det är ett PirrHandler recept, Blir true om receptet är ett pirrrecept
     * 
     * @param eRecreceptId - Ett eRecreceptId
     * @param OracleServiceName - OracleServiceName ex. INT3
     * @return - Returnerar true eller false
     */
    public static Boolean isPirrRecept(String eRecreceptId, String OracleServiceName) {
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        Integer antal = DB_Access.getIntegerValue(con, "select count(*) antal from RR_PROD.forskrorig where ERECRECEIVEDATE is not null and erecreceptid = '"+eRecreceptId+"'","antal");
        DB_Access.closeConnection(con);
        return (antal > 0) ? true : false;
    }

    
    
    
    //////////////////////
    //    deprecated    //
    //////////////////////
    /**
     * Denna metod uppdaterar FORSTRANS tabellen i FOTA_PROD med dagar +/- 
     * 
     * För att detta ska fungera måste partitioneringsinställningarna ändras
     * @deprecated 
     * @param dagar - Antal dagar +/-
     * @param expeditionsId - Ett expeditionsId
     * @param OracleServiceName - ex. INT5 eller PTRR
     * @return - Returnerar 1 om uppdateringen gick bra
     */
    public static Integer changeDateFota( Integer dagar, String expeditionsId, String OracleServiceName ) {
        Integer retVal;
        String sql;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        
        sql =   " update FOTA_PROD.FORSTRANS set " +
                " PERSTARTDATUM = PERSTARTDATUM + " + dagar +
                ",UTFDATUM = UTFDATUM + " + dagar +
                ",AVHDATUM = AVHDATUM + " + dagar +
                ",SPARAD = SPARAD + " + dagar +
                " where expeditionsId = '" + expeditionsId + "'";

        retVal = DB_Access.doUptade(con, sql);
        DB_Access.closeConnection(con);
        if (retVal==1) { System.out.println("changeDateFota: OK : " + dagar + " dagar bakåt i tiden"); }
        return retVal;
    }
    /**
     * Denna metod uppdaterar datum i tabellen uttag med dagar +/-
     * @deprecated 
     * @param dagar - Antal dagar +/-
     * @param ordinationsId - Ett ordinationsId
     * @param expeditionsId - Är samma som receptId i databastabellen
     * @param OracleServiceName - ex. INT5 eller PTRR
     * @return - Returnerar 1 om uppdateringen gick bra
     */
    public static Integer changeDateGodkannUttag( Integer dagar, String ordinationsId, String expeditionsId, String OracleServiceName ) {
        Integer retVal;
        String sql;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        
        sql =   "update RR_PROD.uttag set " +
                " rrstatusdate = rrstatusdate + " + dagar +
                ",rrcreationdate = rrcreationdate + " + dagar +
                ",expdatum = expdatum + " + dagar + 
                ",fmkdate = fmkdate + " + dagar + 
                " where receptid = " + ordinationsId +
                " and exphuvid = '" + expeditionsId +"'";

        retVal = DB_Access.doUptade(con, sql);
        DB_Access.closeConnection(con);
        if (retVal==1) { System.out.println("changeDateGodkannUttag: OK: " + dagar + " dagar bakåt i tiden"); }
        return retVal;
    }
    /** Hämta eRecreceptId med hjälp av ordinationsId från FORSKRORIG
     * 
     * @deprecated 
     * @param ordinationsId - Ett expeditionsId 
     * @param OracleServiceName     ex. INT5 eller PTRR
     * @return eRecreceptId - Ett eRecreceptId
     */
    public static String getEReceptid(String ordinationsId, String OracleServiceName) {
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        String eRecreceptId = DB_Access.getStringValue(con, "select f.ERECRECEPTID erec from RR_PROD.recept r, RR_PROD.FORSKRORIG f where r.RRFORSKRORIGINAL = f.id and r.ID = " + ordinationsId,"erec");
        DB_Access.closeConnection(con);
        
        return eRecreceptId;
    }
    /**
     * Denna metod ändrar datum i tabellen TBLLAKFOR med dagar + eller minus. Har du inget receptId skriv tom sträng ""
     * 
     * Om uttag gjorts så är normalfallet att  man också gjort rapportering till LF
     * Regel: Om uttag är gjort på läkemedel sker rapportering annars EJ
     * 
     * @deprecated 
     * @param dagar     - Antal dagar +/-
     * @param receptId  - Ett receptId        
     * @param expeditionsId - Ett expeditionsId    
     * @param OracleServiceName - ex. INT5 eller PTRR
     * @return - Returnerar 1 om uppdateringen gick bra
     */
    public static Integer changeDateExpeditionLF( Integer dagar, String receptId, String expeditionsId, String OracleServiceName ) {
        Integer retVal;
        String sql;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        
        if (receptId.isEmpty()) {
            sql =   "update lakfor_prod.tbllakfor set " + 
                    " Exp_Datum = Exp_Datum + " + dagar +
                    ",Lag_Datum = to_char( to_date(Lag_Datum,'YYYY-MM-DD')+ " + -180 + ", 'YYYY-MM-DD')" +
                    ",Lag_Tid = Lag_Tid + " + dagar +
                    " where (expeditions_id = " + expeditionsId +" or urspr_expeditions_id = " + expeditionsId +")";
        } else {
            sql =   "update lakfor_prod.tbllakfor set " + 
                    " Exp_Datum = Exp_Datum + " + dagar +
                    ",Lag_Datum = to_char( to_date(Lag_Datum,'YYYY-MM-DD')+ " + -180 + ", 'YYYY-MM-DD')" +
                    ",Lag_Tid = Lag_Tid + " + dagar +
                    " where (expeditions_id = " + expeditionsId +" or urspr_expeditions_id = " + expeditionsId +")" + 
                    " and  receptid = " + receptId;
        }
        
        retVal = DB_Access.doUptade(con, sql);
        DB_Access.closeConnection(con);
        if (retVal==1) { System.out.println("changeDateExpeditionLF: OK: " + dagar + " dagar bakåt i tiden"); }
        return retVal;
    }
    

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {

//        String eRec;
//        int antalDagar = -10;
//        String patient_pnr="189001019802";
//        String ordiantionsId = "2788853331";
//        String eReceptId;
//        String eReceptId2;
//        String expeditionsId = "10202118559";
//        String receptId = "";
        
        // Kolla om det är ett PirrHandler recept eller inte
        // Hämta eReceptid med hjälp av ordinationsId
//        eReceptId = getEReceptid(ordiantionsId, "PTRR");
        
//        System.out.println(eReceptId);
//        eReceptId2 = getEReceptidPrepared(ordiantionsId, "PTRR");
        // Kollar om det är ett PirrHandler recept, Blir true om receptet är ett pirrrecept
        // Visar resultat om pitt eller ej
//        System.out.println(eReceptId);
//        System.out.println(eReceptId2);
//        System.out.println(isPirrRecept(eReceptId, "PTRR"));
        
//        changeDateNyttRecept(antalDagar, patient_pnr, eReceptId, "PTRR" );
//        changeDateGodkannUttagPrepared(antalDagar, ordiantionsId, expeditionsId, "PTRR");
//        changeDateExpeditionLFPrepared(antalDagar, eReceptId, expeditionsId, "PTRR");
//        changeDateFotaPrepared(-1, expeditionsId,"PTFOTA");

 //changeDateExpeditionLFPreparedLFID(antalDagar, receptId, expeditionsId, "PTRR");

        
//    }
    
}

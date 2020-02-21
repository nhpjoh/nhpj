/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nhpj.soap.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.database.DB_Access;

/**
 * Denna klass har metoder för att ta ränsa bort ordinationer/recept
 * @author nhpj
 */
public class removeAllRecepies {

    // Stödfunktioner //

    /**
     * Denna metod tar bort allt för en person/patient
     * @param personnummer - personnumer
     * @param OracleServiceName - ex. INT7
     */
    
    public static void removeAllPnr(String personnummer, String OracleServiceName) {
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        String sql =   "call RR_PROD.CLEANUP.RemoveAllPnr ("+personnummer+")";
        
        try {
            CallableStatement cStmt = con.prepareCall(sql);
            cStmt.execute();
            DB_Access.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ManipuleraReceptDatum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Denna metod tar bort allt för en person/patient
     * @param personnummerLista - lista med personnummer
     * @param OracleServiceName - ex. INT2
     */
    public static void removeAllPnr(ArrayList personnummerLista, String OracleServiceName) {
        if(personnummerLista.isEmpty()) { System.out.println("Finns inga personnummer i listan"); }
        else { System.out.println(personnummerLista.size() + " personnummer i listan att ta bort");}
        Iterator i = personnummerLista.iterator();
        while (i.hasNext()) { 
            String s = i.next().toString();
            System.out.println("Tar bort recept för: " + s ); 
            removeAllPnr(s,OracleServiceName);
        }

    }    

////    /**
////     * @param args the command line arguments
////     */
////    public static void main(String[] args) {
////        String sqlQuery = "SELECT UNIQUE(kundpersonnr) as personnummer FROM rr_prod.forskrorig ORDER BY 1";
////        ArrayList listan = new ArrayList();
////        
////        try {
////            Class.forName("oracle.jdbc.driver.OracleDriver");
////            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/PTRR", "ETCDBA", "ETCDBA");
////                Statement statement = con.createStatement()) {
////                statement.execute(sqlQuery);
////                ResultSet result = statement.getResultSet();
////                
////                while (result.next()) {
////                    listan.add(result.getString("personnummer"));
////                }
////            }
////        } catch (SQLException | ClassNotFoundException ex) {
////            Logger.getLogger(removeAllRecepies.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        
////        System.out.println(listan.size());
//////        removeAllPnr(listan,"PTRR");
////
////    }
    
    
}

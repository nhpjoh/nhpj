package labb;

import se.nhpj.soap.services.RapporteraExpeditionLF;
import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.UppdateraForsaljningOppenvardForskrivning;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;
import se.nhpj.database.DB_Access;
import se.nhpj.soap.utils.CurrentDateTime;
import se.nhpj.testdata.Transaction;

/**
 * @author nhpj
 */
public class SkapaFotaLFdata {

    Boolean debug = false;

    @Test
    public void runTestSkapaFotaLFdata(){
        for (int i = 0; i < 1 ; i++ ) {
            debug = false;
            System.out.println("\n\nIteration: " + (i+1) + "\n\n");
            testSkapaFotaLFdata();
        }
    }

    @Test
    public void testSkapaFotaLFdata() {
        SoapResponseXML response;
        Tickets ticket          = new Tickets();
        String  GLN             = "7350045511119";   
        String  OrgNr            = "5567634778";
        Integer antalDagar      = -100;
        String  receptid         = null; //"2796083657";  // hårdkodad just nu
        String  AP_yrkeskod      = null;
        String  AP_kod           = null;
        String  expeditionsId    = null;
        String  avhamtadDatum    = null;
        String  utfardarDatum    = null;
        String  patient_pnr      = null;
        String  patient_fnamn    = null;
        String  patient_enamn    = null;
        String  nplPackId        = null;
        String  varuNr           = null;
        String  pris_aup         = null;
        Transaction t           = new Transaction();
        
        
        String OracleServiceNameRR = "INT2";
        String OracleServiceNameGD = "INT2";
        String SERVICEENDPOINT = "https://prestanda/apisp";

        String sqlQuery;
        Statement statement = null;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceNameRR, "ETCDBA", "ETCDBA");
        
        sqlQuery = "select receptid,AP_yrkeskod,AP_kod,expeditionsId,avhamtadDatum,utfardarDatum from ( select receptid, CREATIONYRKESKOD AP_yrkeskod, CREATIONLEGITIMATIONSKOD AP_kod, EXPHUVID expeditionsId, EXPDATUM avhamtadDatum, RRCREATIONDATE utfardarDatum from RR_PROD.UTTAG where RRSTATUS=200 ORDER BY sys.dbms_random.value desc) where rownum <2";
//        sqlQuery = "select receptid,AP_yrkeskod,AP_kod,expeditionsId,avhamtadDatum,utfardarDatum from ( select receptid, CREATIONYRKESKOD AP_yrkeskod, CREATIONLEGITIMATIONSKOD AP_kod, EXPHUVID expeditionsId, EXPDATUM avhamtadDatum, RRCREATIONDATE utfardarDatum from RR_PROD.UTTAG where RRSTATUS=200 and receptid='"+ receptid + "' ORDER BY sys.dbms_random.value desc) where rownum <2";

        t.start("Transaktion"); // Startar transaktion t

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
        try { // Slumpa fram ett recept
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                receptid = result.getString("receptid");
                AP_yrkeskod = result.getString("AP_yrkeskod");
                AP_kod = result.getString("AP_kod");
                expeditionsId = result.getString("expeditionsId");
                avhamtadDatum = result.getString("avhamtadDatum");
                utfardarDatum = result.getString("utfardarDatum");
//                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Create statement failed!");
            e.printStackTrace();
        }
        t.stop("Transaktion"); // Stoppar transaktion t

        System.out.println("Svartsid SlumpaFramRecept: " + t.getElapsedTime("Transaktion",Transaction.SECONDS) + "\n");

        if (debug) {
            System.out.println(receptid);
            System.out.println(AP_yrkeskod);
            System.out.println(AP_kod);
            System.out.println(expeditionsId);
            System.out.println(avhamtadDatum);
            System.out.println(utfardarDatum);
        }

        avhamtadDatum = avhamtadDatum.replace(' ', 'T');
        avhamtadDatum = avhamtadDatum.replace(".0",".000");
        utfardarDatum = utfardarDatum.replace(' ', 'T');
        utfardarDatum = utfardarDatum.replace(".0",".000");

        if (debug) {
            System.out.println(avhamtadDatum);
            System.out.println(utfardarDatum);
        }

        sqlQuery = "select f.KUNDPERSONNR patient_pnr, f.KUNDFNAMN patient_fnamn, f.KUNDNAMN patient_enamn, r.VARANPLPACK nplPackid, VARANR varuNr " +
                   "from RR_PROD.RECEPT r, RR_PROD.FORSKRORIG f " +
                   "where r.RRFORSKRORIGINAL = f.id " +
                   "and r.ID =" + receptid;

        try {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println("Execute statement failed!");
            e.printStackTrace();
        }
        try { // Hämta info från RR_prod.recept och forskorig via receptId
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                patient_pnr = result.getString("patient_pnr");
                patient_fnamn = result.getString("patient_fnamn");
                patient_enamn = result.getString("patient_enamn");
                nplPackId = result.getString("nplPackid");
                varuNr = result.getString("varuNr");
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Create statement failed!");
            e.printStackTrace();
        }

        DB_Access.closeConnection(con);

        if (debug) {
            System.out.println(patient_pnr);
            System.out.println(patient_fnamn);
            System.out.println(patient_enamn);
            System.out.println(nplPackId);
            System.out.println(varuNr);
        }

        con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceNameGD, "ETCDBA", "ETCDBA");

        sqlQuery = "select AUP pris_aup from VARA_PROD2.VARA_ONLINE where NPLPACKAGEID = '" + nplPackId + "'";
                
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
        try { // Slumpa fram ett recept
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                pris_aup = result.getString("pris_aup");
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("get result statement failed!");
            e.printStackTrace();
        }
        
        DB_Access.closeConnection(con);

        if (debug) {
            System.out.println(pris_aup);
        }
        
        
        
//  SQL:er för att få tag i indata till detta script
//  
//  select receptid, CREATIONYRKESKOD AP_yrkeskod, CREATIONLEGITIMATIONSKOD AP_kod, EXPHUVID expeditionsId, EXPDATUM avhamtadDatum, RRCREATIONDATE utfardarDatum  from RR_PROD.UTTAG where RRSTATUS=200;
//  select f.KUNDPERSONNR, f.KUNDFNAMN patient_fnamn, f.KUNDNAMN patient_enamn, r.VARANPLPACK nplPackid, VARANR varuNr from RR_PROD.RECEPT r, RR_PROD.FORSKRORIG f where r.RRFORSKRORIGINAL = f.id and r.ID = 2788996315
//  select AUP from VARA_PROD2.VARA_ONLINE where NPLPACKAGEID = '20120201100096';

        for (int i=0; i < 1; i++) {
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// UppdateraForsaljningEgenvard ------------------------------------------------------------------------------------------------------------------------------
// UppdateraForsaljningSlutenvard ----------------------------------------------------------------------------------------------------------------------------
// UppdateraForsaljningOppenvardForskrivning -----------------------------------------------------------------------------------------------------------------
// FOTA trans ------------------------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
            String rnd = se.nhpj.testdata.RndData.getChars(3) + se.nhpj.testdata.RndData.getNumbers(6);

            ticket.setHealthcareProfessionalLicenseIdentityNumber(AP_kod);
            ticket.setPharmacyIdentifier(GLN);
            ticket.setHealthcareProfessionalLicense(AP_yrkeskod);

            UppdateraForsaljningOppenvardForskrivning ufof = new UppdateraForsaljningOppenvardForskrivning();
            ufof.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
            ufof.setStandardDefaultValues();

            ufof.setSoapEndpointUrl( SERVICEENDPOINT + "/UppdateraForsaljningOppenvardForskrivningResponder/V6");
            System.out.println(ufof.getSoapEndpointUrl());

            ufof.setPersonnummer(patient_pnr);
            ufof.setTagValue("*//klientinformation/system", GLN);
            ufof.setTagValue("*//expoOrgNr", OrgNr);
            ufof.setTagValue("*//aktorExpeditionsId", expeditionsId);
            ufof.setTagValue("*//expeditionsId", expeditionsId);
            ufof.setTagValue("*//expoButiksId", expeditionsId);
     //       ufof.setTagValue("*//periodStartDatum", periodStart);
            ufof.setTagValue("*//periodStartDatum", utfardarDatum);
            ufof.setTagValue("*//avhamtadDatum", avhamtadDatum);
            ufof.setTagValue("*//aktorTransId", expeditionsId + "_" + rnd + i);
            ufof.setTagValue("*//aktorReceptId", "1");
            ufof.setTagValue("*//aupExMomsAktor", pris_aup);
            ufof.setTagValue("*//formanExMoms", pris_aup);
            ufof.setTagValue("*//merkostnadExMoms", "0");
            ufof.setTagValue("*//nplPackid", nplPackId);
            ufof.setTagValue("*//receptId", receptid);
            ufof.setTagValue("*//utfardarDatum", utfardarDatum);
            ufof.setTagValue("*//varuNr", varuNr);

            response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( ufof.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( ufof.getXML() ))));

            ufof.checkResponse(response);
        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// RapporteraExpeditionLF ------ Ej om det är handelsvara ----------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

            ticket.setHealthcareProfessionalLicenseIdentityNumber(AP_kod);
            ticket.setPharmacyIdentifier(GLN);
            ticket.setHealthcareProfessionalLicense(AP_yrkeskod);

            RapporteraExpeditionLF relf = new RapporteraExpeditionLF();
            relf.insertXmlAfterTag("<soapenv:Header>", ticket.getTicket());
            relf.setStandardDefaultValues();

            relf.setSoapEndpointUrl( SERVICEENDPOINT + "/RapporteraExpeditionLFResponder/V5");
            System.out.println(relf.getSoapEndpointUrl());

            relf.setPersonnummer(patient_pnr);
            relf.setNamn(patient_fnamn,patient_enamn);
            relf.setTagValue("*//aktorsExpeditionsid", expeditionsId + "_" + rnd + i);
            relf.setTagValue("*//expeditionsid", expeditionsId);
            relf.setTagValue("*//expeditionsdatum", avhamtadDatum);
            relf.setTagValue("*//nplPackid", nplPackId);

            response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( relf.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( relf.getXML() ))));

            relf.checkResponse(response);
  
        }        
    }
}

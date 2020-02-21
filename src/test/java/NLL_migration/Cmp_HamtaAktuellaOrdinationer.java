package NLL_migration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.HamtaAktuellaOrdinationer;
import se.nhpj.soap.services.SoapResponseXML;

import javax.xml.soap.SOAPMessage;

/**
 * @author nhpj
 */
public class Cmp_HamtaAktuellaOrdinationer {
    
    public SoapResponseXML response_1;
    public SoapResponseXML response_2;
    public String  GLN                  = "7350045511119";        
    
    public String patient_pnr;
    
    String SERVICEENDPOINT_STP      = "http://test28:10080/apisp";
    String SERVICEENDPOINT_NLL      = "https://nll-transformator-logic-s13-test1-deploy2.test.ecp.systest.receptpartner.se/nll-apisp";

    public String AP_kod                = "920007";                     // FORS_PROD.APOTEKSPERSONAL.INTR_ID
    public String AP_yrkeskod           = "AP";                         // FORS_PROD.FORS_KATEGORI.KATEGORI
    
    public boolean ignorDate            = true;
    private boolean debug               = true;

    public ReadProps readProps = new ReadProps();

    public String getPnr() { return this.patient_pnr; }
    public void setPnr(String pnr) { this.patient_pnr = pnr; }
    public Boolean getDebug() {return this.debug;}
    public void setDebug(boolean debug) { this.debug = debug; }
    public void setDebug(String debug) {
        if (debug.toUpperCase().contains("TRUE")) {this.setDebug(true);}
        else this.setDebug(false);
    }
    public Boolean getIgnoreDate() {return this.ignorDate; }
    public void setIgnorDate(boolean value) { this.ignorDate = value;}
    public void setIgnorDate(String value) {
        if (value.toUpperCase().contains("TRUE")) {this.setIgnorDate(true);}
        else this.setIgnorDate(false);
    }

    public Cmp_HamtaAktuellaOrdinationer() {}
    
    @Before
    public void setUp() { 
        setPnr(readProps.getProp("personnummer"));
//        setDebug(readProps.getProp("debug"));
    }

    @Test
    public void Cmp_HamtaAktuellaOrdinationer() {
        
        // Skapar en SAMBI ticket
        Tickets t = new Tickets();
        t.setHealthcareProfessionalLicense(AP_yrkeskod);
        t.setHealthcareProfessionalLicenseIdentityNumber(AP_kod);
        t.setPharmacyIdentifier(GLN);

// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaAktuellaOrdinationer 1 -------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------

        HamtaAktuellaOrdinationer hao1 = new HamtaAktuellaOrdinationer();
        hao1.insertXmlAfterTag("<soapenv:Header>", t.getTicket());
        hao1.setStandardDefaultValues();
        hao1.setPersonnummer(patient_pnr);
        hao1.removeTag("*//ordinationsId");
        hao1.setSoapEndpointUrl(SERVICEENDPOINT_STP + "/HamtaAktuellaOrdinationerResponder/V5");
        response_1 = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hao1.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hao1.getXML() ))));
        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HamtaAktuellaOrdinationer 2 -------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
        HamtaAktuellaOrdinationer hao2 = new HamtaAktuellaOrdinationer();
        hao2.insertXmlAfterTag("<soapenv:Header>", t.getTicket());
        hao2.setStandardDefaultValues();
        hao2.setPersonnummer(patient_pnr);
        hao2.setSoapEndpointUrl(SERVICEENDPOINT_NLL + "/HamtaAktuellaOrdinationerResponder/V5");
        hao2.removeTag("*//ordinationsId");
        
        response_2 = new SoapResponseXML( BaseXML.SoapResponseMsgToString( BaseXML.sendSoapRequest( hao2.getSoapEndpointUrl(), BaseXML.getSoapMessageFromString( hao2.getXML() ))));

// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Compare responses
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
        if(debug) System.out.println("DEBUG: HamtaAktuellaOrdinationer: diffCheck");
        checkDifferences.checkDifferences( response_1, response_2, this.getIgnoreDate(), this.getDebug() );
    }
    
    


    @BeforeClass
    public static void setUpClass() {}
    @AfterClass
    public static void tearDownClass() {}
    @After
    public void tearDown() {}
}

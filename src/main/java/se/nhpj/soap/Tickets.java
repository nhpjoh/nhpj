package se.nhpj.soap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;




/**
 * Denna klass har metoder för att skapa SAMBI biljetter och oAuth biljetter
 * @author nhpj
 */
public class Tickets {
    /**
     * Typ av ticket APOTEKARE
     */
    public static final int APOTEKARE = 1;
    /**
     * Typ av ticket LAKARE
     */
    public static final int LAKARE = 2;
    /**
     * Typ av ticket PRIVATPERSON - inte implementerad ännu
     */
    public static final int PRIVATPERSON = 3;
    
    /** Privat variabel innehållande listan på atribut för ticket genereringen */
    private List<NameValuePair> params = new ArrayList<NameValuePair>();
    /**
     * Variable innehållande URL till ticketgeneratorn
     */
    public String soapEndpointUrl = "http://TICGEN:17777/sambi/SambiTicketServlet";

    /**
     * Default construktor
     */
    public Tickets() {
        params = new ArrayList<NameValuePair>();
    }

    /**
     * Denna metod returnerar vilken ENDPOINT som är satt
     * @return - ENDPOINT URL
     */
    public String getSoapEndpointUrl() { return  soapEndpointUrl ; }
    
    /**
     * Denna metod sätter ENDPOINT URL
     * @param endpoint - en sträng med en URL
     */
    public void setSoapEndpointUrl( String endpoint ) { soapEndpointUrl = endpoint; }
    
    /**
     * Denna metod sätter vilken ticket som ska genereras 
     * @param ticketTyp - ex. APOTEKARE eller LAKARE
     */
    public void setTicket(int ticketTyp) {
        // Default värden
        setLevelOfAssurance("http://id.sambi.se/loa/loa3");
        setAudience("http://www.ehalsomyndigheten.se");
        setIssuer("http://www.ehalsomyndigheten.se/idp");
        setNameId("NameID_12345");
        setNameIdQualifier("http://www.ehalsomyndigheten.se");
        setAudienceRestrictions("eHälsomyndigheten");

        switch (ticketTyp) {
            case APOTEKARE: 
                params.add(new BasicNameValuePair("healthcareProfessionalLicense", "AP"));
                params.add(new BasicNameValuePair("healthcareProfessionalLicenseIdentityNumber", "920007"));  // Legitimationskod
                params.add(new BasicNameValuePair("pharmacyIdentifier", "7350045511119"));  // 7350045511997
                break;
            case LAKARE: 
                params.add(new BasicNameValuePair("healthcareProfessionalLicense", "LK"));
                params.add(new BasicNameValuePair("personalPrescriptionCode","9000027")); // Förskrivarkod
                break;
            default: try {
                        throw new Exception("Felaktig ticketTyp angiven");
                     } catch (Exception ex) {
                        Logger.getLogger(Tickets.class.getName()).log(Level.SEVERE, "Ticket kontroll", ex);
                     }
                     break;
            }
    }

    /**
     * Denna metod retyrnerar en lista med värden på vad denna ticket är satt att generera.
     * @return List - lista med parametarvärden
     */
    public List getTicketValues() { 
        // Sätter defaultvärden som måste finnas om de inte är satta
        if (this.getParamIndex("levelOfAssurance") == -1 )      { setLevelOfAssurance("http://id.sambi.se/loa/loa3"); }
        if (this.getParamIndex("audience") == -1 )              { setAudience("http://www.ehalsomyndigheten.se");}
        if (this.getParamIndex("issuer") == -1 )                { setIssuer("http://www.ehalsomyndigheten.se/idp");}
        if (this.getParamIndex("nameId") == -1 )                { setNameId("NameID_12345");}
        if (this.getParamIndex("nameIdQualifier") == -1 )       { setNameIdQualifier("http://www.ehalsomyndigheten.se");}
        if (this.getParamIndex("audienceRestrictions") == -1 )  { setAudienceRestrictions("eHälsomyndigheten");}
        
        return params; 
    }
    /**
     * Denna metod sätter om ett antal värden till sina standard värden (default) så som
     * LevelOfAssurance, Audience, Issuer, NameId, NameIdQualifier, AudienceRestrictions
     */
    public void resetTicketValues() { 
        params.clear();
        // Sätter defaultvärden som måste finnas
        setLevelOfAssurance("http://id.sambi.se/loa/loa3");
        setAudience("http://www.ehalsomyndigheten.se");
        setIssuer("http://www.ehalsomyndigheten.se/idp");
        setNameId("NameID_12345");
        setNameIdQualifier("http://www.ehalsomyndigheten.se");
        setAudienceRestrictions("eHälsomyndigheten");
    }
    /**
     * Denna metod returnerar ett index till den parameter du söker
     * @param NameValuePairName - parameter namn
     * @return - index till parametern i listan
     */
    private Integer getParamIndex( String NameValuePairName ) {
        int index = -1;
        for ( int i = 0; i < params.size() ; i++  ) {
            NameValuePair nvp = (NameValuePair) params.get(i);
            if (nvp.getName().equalsIgnoreCase( NameValuePairName )) { index = i; }
        }
        return index;
    }
    /**
     * Denna metod sätter GLN
     * @param gln - GLN nummer
     */
    public void setGLN(String gln) {             // pharmacyIdentifier                           // GLN-kod
        int index = getParamIndex("pharmacyIdentifier");
        BasicNameValuePair bnvp = new BasicNameValuePair("pharmacyIdentifier",gln);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }
    }
    /**
     * Denna metod sätter apoteket (GLN)
     * @param pharmacyIdentifier - apoteksnummer (GLN)
     */
    public void setPharmacyIdentifier( String pharmacyIdentifier ) {             // pharmacyIdentifier                           // GLN-kod
        int index = getParamIndex("pharmacyIdentifier");
        BasicNameValuePair bnvp = new BasicNameValuePair("pharmacyIdentifier",pharmacyIdentifier);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }
    }
    /**
     * Denna metod sätter yrkeskoden
     * @param yrkeskod - yrkeskoden 
     */
    public void setOccupationalCode( String yrkeskod ) {          // occupationalCode  // utökad yrkeskod enligt ny lista
        int index = getParamIndex("occupationalCode");
        BasicNameValuePair bnvp = new BasicNameValuePair("occupationalCode",yrkeskod);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }
    }
    /**
     * Denna metod sätter legitimationskoden 
     * @param legitimationskod - healthcareProfessionalLicenseIdentityNumber
     */
    public void setLegitimationskod( String legitimationskod ) {          // healthcareProfessionalLicenseIdentityNumber  // legitimationskod
        int index = getParamIndex("healthcareProfessionalLicenseIdentityNumber");
        BasicNameValuePair bnvp = new BasicNameValuePair("healthcareProfessionalLicenseIdentityNumber",legitimationskod);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }
    }
    /**
     * Denna metod sätter legitimationskoden 
     * @param legitimationskod - healthcareProfessionalLicenseIdentityNumber
     */
    public void setHealthcareProfessionalLicenseIdentityNumber( String legitimationskod ) {          // healthcareProfessionalLicenseIdentityNumber  // legitimationskod
        int index = getParamIndex("healthcareProfessionalLicenseIdentityNumber");
        BasicNameValuePair bnvp = new BasicNameValuePair("healthcareProfessionalLicenseIdentityNumber",legitimationskod);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }
    }
    /**
     * Denna metod sätter healthcareProfessionalLicense
     * @param professionalLicense - healthcareProfessionalLicense
     */
    public void setHealthcareProfessionalLicense( String professionalLicense ) {          // healthcareProfessionalLicense  // License
        int index = getParamIndex("healthcareProfessionalLicense");
        BasicNameValuePair bnvp = new BasicNameValuePair("healthcareProfessionalLicense",professionalLicense);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }
    }
    /**
     * Denna metod sätter personalPrescriptionCode
     * @param personalprescriptioncode - personalPrescriptionCode
     */
    public void setPersonalPrescriptionCode( String personalprescriptioncode ) {  // personalPrescriptionCode                     // Förskrivarkod
        int index = getParamIndex("personalPrescriptionCode");
        BasicNameValuePair bnvp = new BasicNameValuePair("personalPrescriptionCode",personalprescriptioncode);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }
    }
    /**
     * Denna metod sätter groupPrescriptionCode
     * @param groupprescriptioncode - groupPrescriptionCode
     */
    public void setGroupPrescriptionCode( String groupprescriptioncode ) {     // groupPrescriptionCode
        int index = getParamIndex("groupPrescriptionCode");
        BasicNameValuePair bnvp = new BasicNameValuePair("groupPrescriptionCode",groupprescriptioncode);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /** 
     * Denna metod sätter personnummret
     * @param personnummer - Ett personnummer -personalIdentityNumber
     */
    public void setPersonnummer( String personnummer ) {               // personalIdentityNumber // Personnummer
        int index = getParamIndex("personalIdentityNumber");
        BasicNameValuePair bnvp = new BasicNameValuePair("personalIdentityNumber",personnummer);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * Denna metod sätter personnummret
     * @param personalidentitynumber - personnummer - personalIdentityNumber
     */
    public void setPersonalIdentityNumber( String personalidentitynumber ) {               // personalIdentityNumber // Personnummer
        int index = getParamIndex("personalIdentityNumber");
        BasicNameValuePair bnvp = new BasicNameValuePair("personalIdentityNumber",personalidentitynumber);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
//    /**
//     * Denna metod sätter professionalLicence
//     * @param professionallicence - professionalLicence
//     * @deprecated
//     */
//    @Deprecated
//    public void setProfessionalLicence( String professionallicence ) {       // professionalLicence  // LK, AP ...
//        int index = getParamIndex("professionalLicence");
//        BasicNameValuePair bnvp = new BasicNameValuePair("professionalLicence",professionallicence);
//        if (index != -1) { params.set(index, bnvp); }
//        else if ( index == -1 ){ params.add(bnvp); }
//    }
    /**
     * Denna metod sätter healthcareProviderId<br>
     * kan innehålla t.ex. ett org-num eller "sjukhus/avd 2" eller något liknande
     * @param healthcareproviderid - healthcareProviderId
     */
    public void setHealthcareProviderId( String healthcareproviderid ) {      // healthcareProviderId // kan innehålla t.ex. ett org-num eller "sjukhus/avd 2" eller något liknande
        int index = getParamIndex("healthcareProviderId");
        BasicNameValuePair bnvp = new BasicNameValuePair("healthcareProviderId",healthcareproviderid);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * Denna metod sätter levelOfAssurance
     * @param levelofassurance - levelOfAssurance
     */
    public void setLevelOfAssurance( String levelofassurance ) {         // levelOfAssurance     // http://id.sambi.se/loa/loa3
        int index = getParamIndex("levelOfAssurance");
        BasicNameValuePair bnvp = new BasicNameValuePair("levelOfAssurance",levelofassurance);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * denna metod sätter audience
     * @param audience - audience
     */
    public void setAudience( String audience) {                  // audience             // http://www.ehalsomyndigheten.se
        int index = getParamIndex("audience");
        BasicNameValuePair bnvp = new BasicNameValuePair("audience",audience);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * Denna metod sätter issuer
     * @param issuer - issuer
     */
    public void setIssuer( String issuer ) {                    // issuer               // http://www.ehalsomyndigheten.se/idp
        int index = getParamIndex("issuer");
        BasicNameValuePair bnvp = new BasicNameValuePair("issuer",issuer);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * Denna metos sätter nameId
     * @param nameid - nameId
     */
    public void setNameId( String nameid ) {                    // nameId               // random nummer typ ....
        int index = getParamIndex("nameId");
        BasicNameValuePair bnvp = new BasicNameValuePair("nameId",nameid);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * Denna metod sätter nameIdQualifier
     * @param nameidqualifier - nameIdQualifier
     */
    public void setNameIdQualifier( String nameidqualifier) {                   // nameIdQualifier      // http://www.ehalsomyndigheten.se
        int index = getParamIndex("nameIdQualifier");
        BasicNameValuePair bnvp = new BasicNameValuePair("nameIdQualifier",nameidqualifier);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * Denna metod sätter audienceRestrictions
     * @param audiencerestrictions - audienceRestrictions
     */
    public void setAudienceRestrictions( String audiencerestrictions ) {        // audienceRestrictions // eHälsomyndigheten
        int index = getParamIndex("audienceRestrictions");
        BasicNameValuePair bnvp = new BasicNameValuePair("audienceRestrictions",audiencerestrictions);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * Denna metod sätter issueInstant
     * @param issueInstant - issueInstant
     */
    public void setIssueInstant(String issueInstant) {                           // issueInstant         // Timestamp now()
        int index = getParamIndex("issueInstant");
        BasicNameValuePair bnvp = new BasicNameValuePair("issueInstant",issueInstant);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }
    /**
     * Denna metod sätter notBefore
     * @param notBefore - notBefore
     */
    public void setNotBefore(String notBefore) {                                // notBefore            // TimeStamp now.minusMinuteS(5)
        int index = getParamIndex("notBefore");
        BasicNameValuePair bnvp = new BasicNameValuePair("notBefore",notBefore);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }        
    }                
    /**
     * Denna metod sätter notOnOrAfter
     * @param notOnOrAfter - notOnOrAfter
     */
    public void setNotOnOrAfter(String notOnOrAfter) {                          // notOnOrAfter         // TimeStamp now.plusMinuter(45)
        int index = getParamIndex("notOnOrAfter");
        BasicNameValuePair bnvp = new BasicNameValuePair("notOnOrAfter",notOnOrAfter);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }                
    }

    public void setSecurityImplamentation(String SecurityImplamentation) {
        int index = getParamIndex("securityImplementation");
        BasicNameValuePair bnvp = new BasicNameValuePair("securityImplementation",SecurityImplamentation);
        if (index != -1) { params.set(index, bnvp); }
        else if ( index == -1 ){ params.add(bnvp); }
    }

    /**
     * Denna metod returnerar en ticket av ticketTyp (LAKARE, APOTEKARE)
     * @param ticketTyp - ex. LAKARE, APOTEKARE
     * @return - ett Ticket objekt
     */
    public static String getTicket(int ticketTyp) {
        Tickets t = new Tickets();
        t.setTicket(ticketTyp);
        return t.getTicket();
    }
    /**
     * Denna metod returnerar en Ticket med de förutsättningar som är satta 
     * @return - en Ticket
     */
    public String getTicket() {
        String body = "";
        
        if (this.getParamIndex("levelOfAssurance") == -1 )      { setLevelOfAssurance("http://id.sambi.se/loa/loa3"); }
        if (this.getParamIndex("audience") == -1 )              { setAudience("http://www.ehalsomyndigheten.se");}
        if (this.getParamIndex("issuer") == -1 )                { setIssuer("http://www.ehalsomyndigheten.se/idp");}
        if (this.getParamIndex("nameId") == -1 )                { setNameId("NameID_12345");}
        if (this.getParamIndex("nameIdQualifier") == -1 )       { setNameIdQualifier("http://www.ehalsomyndigheten.se");}
        if (this.getParamIndex("audienceRestrictions") == -1 )  { setAudienceRestrictions("eHälsomyndigheten");}
        
        if (params.size() > 6) {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapEndpointUrl);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                CloseableHttpResponse response = client.execute(httpPost);
                ResponseHandler<String> handler = new BasicResponseHandler();
                body = handler.handleResponse(response);
//                System.out.println("Ticket - StatusCode: " + response.getStatusLine().getStatusCode() + "\n");
                body = body.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>","");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                throw new Exception("Ingen ticket genererad. Ticket parametar inte satta");
            } catch (Exception ex) {
                Logger.getLogger(Tickets.class.getName()).log(Level.SEVERE, "Ticket kontroll", ex);
            }
            
        }
     return body;
    }
    /**
     * Denna metod returnerar en Apotekare ticket
     * @return - en Ticket av typen Apotekare
     * @deprecated 
     */
    public static String getAPTicket() {
        
        String soapEndpointUrl = "http://TICGEN:17777/sambi/SambiTicketServlet";
        String body = "";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(soapEndpointUrl);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pharmacyIdentifier", "7350045511119"));
            params.add(new BasicNameValuePair("healthcareProfessionalLicenseIdentityNumber", "920007"));
            params.add(new BasicNameValuePair("professionalLicence", "AP"));
            params.add(new BasicNameValuePair("levelOfAssurance", "http://id.sambi.se/loa/loa3"));
            params.add(new BasicNameValuePair("audience", "http://www.ehalsomyndigheten.se"));
            params.add(new BasicNameValuePair("issuer", "http://www.ehalsomyndigheten.se/idp"));
            params.add(new BasicNameValuePair("nameId", "NameID_12345"));
            params.add(new BasicNameValuePair("nameIdQualifier", "http://www.ehalsomyndigheten.se"));
            params.add(new BasicNameValuePair("audienceRestrictions", "eHälsomyndigheten"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(httpPost);
            ResponseHandler<String> handler = new BasicResponseHandler();
            body = handler.handleResponse(response);
            System.out.println("Ticket - StatusCode: " + response.getStatusLine().getStatusCode() + "\n");
            body = body.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>","");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        }
     return body;
    }
}



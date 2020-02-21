package se.nhpj.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Denna klass är framtagen för att kunna testa och använda sig av oAuth lösningen.<br>
 * För att använda detta så skapar man först en instans med konstruktorn som man sedan förser med en URL och sätter en Assertion<br>
 * URL är adressen till mijön som ska anropas.<br>
 * Assertion kan vara ett REFRESH_TOKEN eller en ASSERTION som är en sambi ticket ränsad från wsse taggen. <br>
 * ex. på vad som ska bort<br>
 * "&lt;wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"&gt;<br>
 * "&lt;/wsse:Security&gt;"<br>
 * och sedan BASE64 encodad.
 * <br>
 * <pre>
 * Exempel kod 
 *      
        import se.nhpj.rest.*;
        import se.nhpj.soap.*;
        
        // Skapar en SAMBI ticket
        Tickets t = new Tickets();
        t.setHealthcareProfessionalLicense("AP");
        t.setHealthcareProfessionalLicenseIdentityNumber("920007");
        t.setPharmacyIdentifier("7350045511119");
        
        // Hämtar sambiticket
        String ticket = t.getTicket(); 
        
        // Rensar bort taggar från svar ...
        ticket = ticket.replaceAll("&lt;wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"&gt;", "");
        ticket = ticket.replaceAll("&lt;/wsse:Security&gt;", "");
        
        // Base64 krypterar sambiticket
        String encodedTicket = Base64Converter.encodeString(ticket);
        
        // Skapar ett RestCall object 
        RestCall restcall = new RestCall();
        
        // Sätter testmiljön
        restcall.setUrl("https://test6.systest.receptpartner.se:19443/oauth2/api/oauth/token"); 
 
        // Sätter Assertion objektet 
        restcall.setAssertion(encodedTicket); 

        try {
            // Gör anropet
            restcall.runRestCall(restcall.ASSERTION);
       
            System.out.println(restcall.getStatusCode());  // skriver ut status på anropet
            System.out.println(restcall.getResponse());    // skriver ut response
            
            // Svaret på restanropet kommer tillbaka i ett OauthToken.
            // Om stausen är 200 så gicg anropet bra så kommer dessa värden tillbaka
            if (restcall.getStatusCode() == 200 ) {     
                System.out.println("access_token: " +  restcall.getOauthToken().getAccess_token() );
                System.out.println("expires_in: " +    restcall.getOauthToken().getExpires_in() );
                System.out.println("token_type: " +    restcall.getOauthToken().getToken_type() );
                System.out.println("refresh_token: " + restcall.getOauthToken().getRefresh_token() );
            // Om status koden är något annat så gick något fel och då kommer dessa värden tillbaka
            } else {
                System.out.println("Något gick fel");
                System.out.println(restcall.getOauthToken().toString());
            }

            // Skickar in det refresh token som finns i svaret på det tidigare anropet
            restcall.setAssertion(restcall.getOauthToken().getRefresh_token()); // Sätter assertion med ett refreshToken
            restcall.runRestCall(restcall.REFRESH_TOKEN);                       // Gör ett refresh token rest anrop
            System.out.println("\n\n" + restcall.getOauthToken().toString());   // skriver ut svaret 
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
            
 * </pre>
 * @author kundtest1
 */

public class RestCall {

    public final Integer  ASSERTION = 1;
    public final Integer  REFRESH_TOKEN = 2;
    String client_id = "EHM-USER";
    String client_secret = "EHM-PSW";
//    String url = "https://test9.systest.receptpartner.se:19443/oauth2/api/oauth/token";
    String url = null;
    String assertion = null;
    String response = null;
    Integer statusCode = null;

    OauthTokens oauthtoken = null;
    
    public void setAssertion( String str ) { assertion = str; } 
    public String getAssertion() { return assertion; }
    public void setUrl( String url ) { this.url = url; } 
    public String getUrl() { return this.url; }
    public String getResponse() { return this.response; }
    public Integer getStatusCode() { return this.statusCode; }
    public OauthTokens getOauthToken() { return oauthtoken; }
    public void setOauthToken(OauthTokens oauthtoken) { this.oauthtoken = oauthtoken; }

    public String getClient_id() { return client_id; }
    public void setClient_id(String client_id) { this.client_id = client_id; }
    public String getClient_secret() { return client_secret; }
    public void setClient_secret(String client_secret) { this.client_secret = client_secret; }

    /**
     * Denna metod konerterar en timestamp som en Long till en datum String ('yyyy-MM-dd hh:mm:ss.SSS')
     * Uasge: System.out.println(timeStampToDate(1509562452123L));
     * @param timestamp
     * @return String
     */
    public static String timeStampToDate(Long timestamp) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return df.format( cal.getTime() );
    }
    /**
     * Denna metod konerterar en timestamp som en String till en datum sträng ('yyyy-MM-dd hh:mm:ss.SSS')
     * Usage: System.out.println(timeStampToDate("1509562452123"));
     * @param timestamp
     * @return String
     */
    public static String timeStampToDate(String timestamp) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(timestamp));
        return df.format( cal.getTime() );
    }

            
    /**
     * Denna metod skickar ett rest anrop som returnerar ett oAuth token.<br>
     * För att det ska fungera måste en URL vara satt. (<b>setUrl()</b>)<br>
     * och ett Assertion objektet måste sättas (<b>setAssertion()</b>)antingen en assertion(BASE64 encodad sambiticket) token eller ett refrech_token<br>
     * @param grantType - Vilken typ av oAuth Token du vill ha tillbaka (ASSERTION, REFRESH_TOKEN)
     * @throws Exception Den kan skicka ett antal olika excepions
     */
    public void runRestCall(Integer grantType) throws Exception {

        String urlEncodedAssertion = URLEncoder.encode(assertion, StandardCharsets.UTF_8.toString());
        String encodedCredentials = Base64.getEncoder().encodeToString((client_id+":"+client_secret).getBytes(StandardCharsets.UTF_8));
        String POST_PARAMS = null;
        if (grantType == ASSERTION) {
            POST_PARAMS = "grant_type=urn:ietf:params:oauth:grant-type:saml2-bearer&assertion="+urlEncodedAssertion;
        } else if (grantType == REFRESH_TOKEN) {
            POST_PARAMS = "grant_type=refresh_token&refresh_token="+urlEncodedAssertion;
        }

        URL urlCon = new URL(getUrl());
        HttpsURLConnection con = null;
        int responseCode = 0;
        StringBuilder response = new StringBuilder();

        try {
            con = (HttpsURLConnection) urlCon.openConnection();
            con.setSSLSocketFactory(createSSLContext().getSocketFactory());
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Authorization", "Basic " + encodedCredentials);

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            
            os.flush();
            os.close();

            responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream() ));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            responseCode = con.getResponseCode();
            System.out.println("responseCode: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader( con.getErrorStream() ));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }

        // Spara undan svaret
        this.statusCode = responseCode;
        this.response = response.toString();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.setOauthToken(mapper.readValue(this.response, OauthTokens.class));
        } catch (IOException ex) {
            Logger.getLogger(RestCall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private SSLContext createSSLContext() throws Exception{
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null , new TrustManager[] {new TrustAnyTrustManager()}, new SecureRandom());
        return sslContext;
    }

    public class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
    
}


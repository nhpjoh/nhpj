package se.nhpj.rest;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.rest.Base64Converter;
import se.nhpj.rest.BaseRest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessTokenHandler {

    private String serviceEndpoint = "https://test28.systest.receptpartner.se:19443"; // "https://ext18-1-lb.exttest.receptpartner.se" // för externmiljön

    public void setServiceEndpoint(String endpoint) {serviceEndpoint=endpoint;}

    public static final int FARMACEUT = 0;
    public static final int FORSKRIVARE = 1;
    public static final int PRIVATPERSON = 2;

    public String getAccessToken( int type) {
        // Skapar en SAMBI ticket
        Tickets t = new Tickets();

        t.setLevelOfAssurance("http://id.sambi.se/loa/loa3");
        t.setAudience("http://www.ehalsomyndigheten.se");
        t.setIssuer("http://www.ehalsomyndigheten.se/idp");
        t.setNameId(UUID.randomUUID().toString());
        t.setNameIdQualifier("http://www.ehalsomyndigheten.se");

        // Sätter piletten
        switch (type) {
            case 0 : {
                        t.setPharmacyIdentifier("7350045511119");
                        t.setHealthcareProfessionalLicenseIdentityNumber("920007");
                        t.setHealthcareProfessionalLicense("AP");
            }
                break;
            case 1 : {
                        t.setHealthcareProfessionalLicense("LK");
                        t.setPersonalPrescriptionCode("8880999");
            }
                break;
            default: {}
        }

        // Hämtar sambiticket
        String ticket = t.getTicket();

//        System.out.println(t.getTicket());

        // Ränsar bort taggar från svar som ska bort...
        ticket = ticket.replaceAll("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">", "");
        ticket = ticket.replaceAll("</wsse:Security>", "");

        // Base64 krypterar sambiticket
        String encodedTicket = Base64Converter.encodeString(ticket);

        // Skapar ett RestCall object
        RestCall restcall = new RestCall();

        // Sätter testmiljön
        restcall.setUrl(serviceEndpoint + "/oauth2/api/oauth/token");

        // Skriver vad URL:en är satt till
         System.out.println(restcall.getUrl());

        // Sätter Assertion objektet .. antingen en assertion(sambiticket) token eller ett refrech_token
        restcall.setAssertion(encodedTicket); // encodedTicket

        try {
            // Gör restanropet
            restcall.runRestCall(restcall.ASSERTION);

            // skriver ut status på anropet
            if (restcall.getStatusCode() != 200)
            System.out.println(restcall.getStatusCode());

        } catch (Exception ex) {
            //Logger.getLogger(Oauth_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        return restcall.getOauthToken().getAccess_token();
    }


    public String getAccessToken(Tickets tickets) {
//        String serviceEndpoint = "https://test28.systest.receptpartner.se:19443";

        // Hämtar sambiticket
        String ticket = tickets.getTicket();

        // Ränsar bort taggar från svar som ska bort...
        ticket = ticket.replaceAll("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">", "");
        ticket = ticket.replaceAll("</wsse:Security>", "");

        // Base64 krypterar sambiticket
        String encodedTicket = Base64Converter.encodeString(ticket);

        // Skapar ett RestCall object
        RestCall restcall = new RestCall();

        // Sätter testmiljön
        restcall.setUrl( serviceEndpoint + "/oauth2/api/oauth/token");

        // Kollar att URL:en är satt
        System.out.println(restcall.getUrl());

        // Sätter Assertion objektet .. antingen en assertion(sambiticket) token eller ett refrech_token
        restcall.setAssertion(encodedTicket);  // encodedTicket

        try {
            // Gör restanropet
            restcall.runRestCall(restcall.ASSERTION);

            // skriver ut status på anropet
            System.out.println(restcall.getStatusCode());

        } catch (Exception ex) {
            //Logger.getLogger(Oauth_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        return restcall.getOauthToken().getAccess_token();
    }


    public String getInternalAccessToken(int type) {
        String soapEndpointUrl = "http://TICGEN:17777/internalsecurityoauth2/InternalSecurityOauth2AccessTokenServlet";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        BasicNameValuePair bnvp;
        String body = "";

        int index = 0;

        switch (type) {
            case 0 : {
                bnvp = new BasicNameValuePair("healthcareProfessionalLicense","AP");
                params.add(index++, bnvp);
                bnvp = new BasicNameValuePair("healthcareProfessionalLicenseIdentityNumber","920007");
                params.add(index++, bnvp);
                bnvp = new BasicNameValuePair("pharmacyIdentifier","7350045511119");
                params.add(index++, bnvp);
            }
            break;
            case 1 : {
                bnvp = new BasicNameValuePair("healthcareProfessionalLicense","LK");
                params.add(index++, bnvp);
                bnvp = new BasicNameValuePair("personalPrescriptionCode","8880999");
                params.add(index++, bnvp);
            }
            break;
            case 2 : {
//                bnvp = new BasicNameValuePair("personalIdentityNumber","199401182382");
                bnvp = new BasicNameValuePair("personnummer","199102102382");
                params.add(index++, bnvp);
                bnvp = new BasicNameValuePair("biljettformat","CGI");
                params.add(index++, bnvp);
                bnvp = new BasicNameValuePair("signAssertion","false");
                params.add(index++, bnvp);
                bnvp = new BasicNameValuePair("signResponse","true");
                params.add(index++, bnvp);
                bnvp = new BasicNameValuePair("securityImplementation","BANKID");
                params.add(index++, bnvp);

//                bnvp = new BasicNameValuePair("levelOfAssurance","3");
//                params.add(index++, bnvp);
//                bnvp = new BasicNameValuePair("audience","http://www.ehalsomyndigheten.se");
//                params.add(index++, bnvp);
//                bnvp = new BasicNameValuePair("issuer","http://www.ehalsomyndigheten.se/idp");
//                params.add(index++, bnvp);
//                bnvp = new BasicNameValuePair("nameId",UUID.randomUUID().toString());
//                params.add(index++, bnvp);
//                bnvp = new BasicNameValuePair("nameIdQualifier","http://www.ehalsomyndigheten.se");
//                params.add(index++, bnvp);
            }
            break;
            default: {}
        }

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(soapEndpointUrl);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(httpPost);
             System.out.println("Ticket - StatusCode: " + response.getStatusLine().getStatusCode() + "\n");
            ResponseHandler<String> handler = new BasicResponseHandler();
            body = handler.handleResponse(response);
            body = body.replaceAll("\n","");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return body;
    }

}

package labb;

import javax.xml.soap.SOAPMessage;
import org.junit.Test;
import se.nhpj.rest.Base64Converter;
import se.nhpj.rest.BaseRest;
import se.nhpj.rest.RestCall;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.Tickets;
import se.nhpj.soap.services.HamtaGallandeUnderlagsversion;
import se.nhpj.soap.services.SoapResponseXML;
/**
 * @author nhpj
 */
public class Test_oAuth {

    @Test
    public void test() {
        String serviceEndpoint = "https://test3.systest.receptpartner.se:19443";

        // Gammal accesstoken sparad för test
        String oldTicket = "CgoKPHNhbWwyOkFzc2VydGlvbiBJRD0iXzJiODlkZThkODI1YzQzYjkyMzc5NjY0NGE3NzMzNjVkIiBJc3N1ZUluc3RhbnQ9IjIwMTgtMDctMDNUMDc6NTY6MDkuOTA5WiIgVmVyc2lvbj0iMi4wIiB4bWxuczpzYW1sMj0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFzc2VydGlvbiIgeG1sbnM6eHM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hIj48c2FtbDI6SXNzdWVyPmh0dHA6Ly93d3cuZWhhbHNvbXluZGlnaGV0ZW4uc2UvaWRwPC9zYW1sMjpJc3N1ZXI+PGRzOlNpZ25hdHVyZSB4bWxuczpkcz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+PGRzOlNpZ25lZEluZm8+PGRzOkNhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiLz48ZHM6U2lnbmF0dXJlTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3JzYS1zaGExIi8+PGRzOlJlZmVyZW5jZSBVUkk9IiNfMmI4OWRlOGQ4MjVjNDNiOTIzNzk2NjQ0YTc3MzM2NWQiPjxkczpUcmFuc2Zvcm1zPjxkczpUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIvPjxkczpUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiPjxlYzpJbmNsdXNpdmVOYW1lc3BhY2VzIFByZWZpeExpc3Q9InhzIiB4bWxuczplYz0iaHR0cDovL3d3dy53My5vcmcvMjAwMS8xMC94bWwtZXhjLWMxNG4jIi8+PC9kczpUcmFuc2Zvcm0+PC9kczpUcmFuc2Zvcm1zPjxkczpEaWdlc3RNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjc2hhMSIvPjxkczpEaWdlc3RWYWx1ZT5XaDRzNUFhZTdacFJqVHpXS3BVb1hyWFZ3NHc9PC9kczpEaWdlc3RWYWx1ZT48L2RzOlJlZmVyZW5jZT48L2RzOlNpZ25lZEluZm8+PGRzOlNpZ25hdHVyZVZhbHVlPkdCcnhBNVYwbzdiVWVUaGk0dnl4aVpxUUF2emtWMWNiNkowYTJUMk44QUN3QmI0YnA5L1oxQWk5dVNJeTM5TmptZHNlN25UQTdpWW10ZFNDWllXc0FQZGdEdUxlRlBNWUc1ZHdFU000TlduRitjWUljNGpBZlRJVVY1Znp3cFd1eDQ4REVob0hxZFBNVnBwMjdCemhjMndxU1FpR0Z5QzVmWkdyOTFuNE1MZm9GcUR1VDkwYlpXNGVzYTlVdUFzSTl2STZrRFBjb3JMTjZud00zMk03bEJXOGdmZCtBdkI3V0JnTEdvRUNoZHR0VjlyeTBIRitoc2FjcUlyZEtTb1hEQUt3anFkYUliYmNYQlZMb1p2dzBVUEJ3dkduTzFOT0xKZkpibElKOGdBWXdQanNhTDZEVklmc3RpZTBydW5zeWJ3NUh6bjNuZC9EcnU4SVQ5V3lndz09PC9kczpTaWduYXR1cmVWYWx1ZT48ZHM6S2V5SW5mbz48ZHM6S2V5VmFsdWU+PGRzOlJTQUtleVZhbHVlPjxkczpNb2R1bHVzPnM1cXVpVnl3Z215Y1h2QUtZNEtOeFVqU2dRZXdrbE9iN2R6azVvY2NEQmZ1YmVrWVJmM3BXeUJ3OHVZNFExcnltdXZQWHRWV2FtN00KSGhoTjRNTkFMQkIwZTJsajl5TGRKNXAwSHhGUHVEbDNnWXlmUmxaTnljSzNSVFFjNHpWcE4yb0xWaEM2eHp1RW03eC82MXRXeWFqbApCa2NaVGUvaSs4dm5PbVVWSTdRL2JUbENnMXZoc0xTWkkzbVFuSkRyaFE4UVkySk9iaEQ1UVE1dStQMnhqMnhqZnNGbGI0UDFVd01QCmxVQjE4K0RUWHN6NnJGVUlzRGFwV1pkdU1IZU9Ubm1PMExZL1BnRnp5bjYwSHQ5K09TQ0RubXpmTmNzZXh4eTVZdndoNVFrWWR0TWkKaXhjSnFVR05wY3pGcXB1ZGhpV1B2U01YMHkrS01COGN6N0MvRnc9PTwvZHM6TW9kdWx1cz48ZHM6RXhwb25lbnQ+QVFBQjwvZHM6RXhwb25lbnQ+PC9kczpSU0FLZXlWYWx1ZT48L2RzOktleVZhbHVlPjxkczpYNTA5RGF0YT48ZHM6WDUwOUNlcnRpZmljYXRlPk1JSUQxakNDQXI2Z0F3SUJBZ0lCRmpBTkJna3Foa2lHOXcwQkFRVUZBREJjTVFzd0NRWURWUVFHRXdKVFJURU9NQXdHQTFVRUNBd0YKYzNSb2JHMHhEekFOQmdOVkJBb01CbFJsYzNRZ1R6RVFNQTRHQTFVRUN3d0hWR1Z6ZENCUFZURWFNQmdHQTFVRUF3d1JaV2hoYkhOdgpiWGx1WkdsbmFHVjBaVzR3SGhjTk1UWXdOakF4TVRRd05ERTRXaGNOTVRnd05qQXhNVFF3TkRFNFdqQlFNUXN3Q1FZRFZRUUdFd0pUClJURU9NQXdHQTFVRUNBd0ZjM1JvYkcweER6QU5CZ05WQkFvTUJsUmxjM1FnVHpFUU1BNEdBMVVFQ3d3SFZHVnpkQ0JQVlRFT01Bd0cKQTFVRUF3d0ZZWEJwYzNBd2dnRWlNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0SUJEd0F3Z2dFS0FvSUJBUUN6bXE2SlhMQ0NiSnhlOEFwagpnbzNGU05LQkI3Q1NVNXZ0M09UbWh4d01GKzV0NlJoRi9lbGJJSER5NWpoRFd2S2E2ODllMVZacWJzd2VHRTNndzBBc0VIUjdhV1AzCkl0MG5tblFmRVUrNE9YZUJqSjlHVmszSndyZEZOQnpqTldrM2FndFdFTHJITzRTYnZIL3JXMWJKcU9VR1J4bE43K0w3eStjNlpSVWoKdEQ5dE9VS0RXK0d3dEpramVaQ2NrT3VGRHhCallrNXVFUGxCRG03NC9iR1BiR04rd1dWdmcvVlRBdytWUUhYejROTmV6UHFzVlFpdwpOcWxabDI0d2Q0NU9lWTdRdGo4K0FYUEtmclFlMzM0NUlJT2ViTjgxeXg3SEhMbGkvQ0hsQ1JoMjB5S0xGd21wUVkybHpNV3FtNTJHCkpZKzlJeGZUTDRvd0h4elBzTDhYQWdNQkFBR2pnYTR3Z2Fzd0NRWURWUjBUQkFJd0FEQVJCZ2xnaGtnQmh2aENBUUVFQkFNQ0JhQXcKTEFZSllJWklBWWI0UWdFTkJCOFdIVTl3Wlc1VFUwd2dSMlZ1WlhKaGRHVmtJRU5sY25ScFptbGpZWFJsTUIwR0ExVWREZ1FXQkJSRApmVG9JTU82SHM2eWU5V1VVRmNwWFVMTmFnakFmQmdOVkhTTUVHREFXZ0JTQXp1amNZelJsSFdhN0pWUjZoYmtWbWxXd1pUQWRCZ05WCkhTVUVGakFVQmdnckJnRUZCUWNEQWdZSUt3WUJCUVVIQXdRd0RRWUpLb1pJaHZjTkFRRUZCUUFEZ2dFQkFLUldJZ1psdjMwOXdTYUUKTCt5RGJ3NHVIeEVhY2hvWmRHZSsxQ0JhbjJkTEhsTHVPWDV3UzdXc1FUUm8wY1BWTVNQbUNLaVJUbGs3SWJiU2gzcjlIejZPdkJoSwpFcjQydTJaamVkczlsR0VneWNzQnQ3M1E1Z2RaSzd6MlFCS0o1bEZMSTNkbUV6U0JNQjMxTlVJdWNzZ3BDd05KVTZJL3Y4WHU0T2FsCnJCSXpKUUU1cVJKVjBLZHFvcUUyeC8vcnMrUStWUkQwL2FEbGc4UnAwRDhxMkg1dnhKYXJNc1I4Z1UzQjVpNkFlUHJmUjc5R002eloKV0tvSVZIeGlGMTJDQ0dsWVFwa1A3Z0RGODdVYUl4eEIyYUl0S1pQVGF5VldhWC9YdVhmemRmMFU1M1FuK2lvWlFwa1FxUWk3Tm1EQwpUcVlaNW9jblVMVy9iUXpGT2FpR2JoOD08L2RzOlg1MDlDZXJ0aWZpY2F0ZT48L2RzOlg1MDlEYXRhPjwvZHM6S2V5SW5mbz48L2RzOlNpZ25hdHVyZT48c2FtbDI6U3ViamVjdD48c2FtbDI6TmFtZUlEIEZvcm1hdD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOm5hbWVpZC1mb3JtYXQ6dHJhbnNpZW50IiBOYW1lUXVhbGlmaWVyPSJodHRwOi8vd3d3LmVoYWxzb215bmRpZ2hldGVuLnNlIj42ZWM1NjY5MS05ZDE4LTQyMDctOTU1MC1iNzU5ZDBlOGYzOWE8L3NhbWwyOk5hbWVJRD48L3NhbWwyOlN1YmplY3Q+PHNhbWwyOkNvbmRpdGlvbnMgTm90QmVmb3JlPSIyMDE4LTA3LTAzVDA3OjUxOjEwLjYxOFoiIE5vdE9uT3JBZnRlcj0iMjAxOC0wNy0wM1QwODo0MToxMC42MThaIj48c2FtbDI6QXVkaWVuY2VSZXN0cmljdGlvbj48c2FtbDI6QXVkaWVuY2U+aHR0cDovL3d3dy5laGFsc29teW5kaWdoZXRlbi5zZTwvc2FtbDI6QXVkaWVuY2U+PC9zYW1sMjpBdWRpZW5jZVJlc3RyaWN0aW9uPjwvc2FtbDI6Q29uZGl0aW9ucz48c2FtbDI6QXV0aG5TdGF0ZW1lbnQgQXV0aG5JbnN0YW50PSIyMDE4LTA3LTAzVDA3OjU2OjEwLjYzNVoiPjxzYW1sMjpBdXRobkNvbnRleHQ+PHNhbWwyOkF1dGhuQ29udGV4dENsYXNzUmVmPmh0dHA6Ly9pZC5zYW1iaS5zZS9sb2EvbG9hMzwvc2FtbDI6QXV0aG5Db250ZXh0Q2xhc3NSZWY+PC9zYW1sMjpBdXRobkNvbnRleHQ+PC9zYW1sMjpBdXRoblN0YXRlbWVudD48c2FtbDI6QXR0cmlidXRlU3RhdGVtZW50PjxzYW1sMjpBdHRyaWJ1dGUgRnJpZW5kbHlOYW1lPSJwaGFybWFjeUlkZW50aWZpZXIiIE5hbWU9Imh0dHA6Ly9zYW1iaS5zZS9hdHRyaWJ1dGVzLzEvcGhhcm1hY3lJZGVudGlmaWVyIiBOYW1lRm9ybWF0PSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6YXR0cm5hbWUtZm9ybWF0OnVyaSI+PHNhbWwyOkF0dHJpYnV0ZVZhbHVlIHhtbG5zOnhzaT0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEtaW5zdGFuY2UiIHhzaTp0eXBlPSJ4czpzdHJpbmciPjczNTAwNDY2MTE5NjY8L3NhbWwyOkF0dHJpYnV0ZVZhbHVlPjwvc2FtbDI6QXR0cmlidXRlPjxzYW1sMjpBdHRyaWJ1dGUgRnJpZW5kbHlOYW1lPSJoZWFsdGhjYXJlUHJvZmVzc2lvbmFsTGljZW5zZSIgTmFtZT0iaHR0cDovL3NhbWJpLnNlL2F0dHJpYnV0ZXMvMS9oZWFsdGhjYXJlUHJvZmVzc2lvbmFsTGljZW5zZSIgTmFtZUZvcm1hdD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmF0dHJuYW1lLWZvcm1hdDp1cmkiPjxzYW1sMjpBdHRyaWJ1dGVWYWx1ZSB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIiB4c2k6dHlwZT0ieHM6c3RyaW5nIj5BUDwvc2FtbDI6QXR0cmlidXRlVmFsdWU+PC9zYW1sMjpBdHRyaWJ1dGU+PHNhbWwyOkF0dHJpYnV0ZSBGcmllbmRseU5hbWU9ImhlYWx0aGNhcmVQcm9mZXNzaW9uYWxMaWNlbnNlSWRlbnRpdHlOdW1iZXIiIE5hbWU9Imh0dHA6Ly9zYW1iaS5zZS9hdHRyaWJ1dGVzLzEvaGVhbHRoY2FyZVByb2Zlc3Npb25hbExpY2Vuc2VJZGVudGl0eU51bWJlciIgTmFtZUZvcm1hdD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmF0dHJuYW1lLWZvcm1hdDp1cmkiPjxzYW1sMjpBdHRyaWJ1dGVWYWx1ZSB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIiB4c2k6dHlwZT0ieHM6c3RyaW5nIj45MjAwMDc8L3NhbWwyOkF0dHJpYnV0ZVZhbHVlPjwvc2FtbDI6QXR0cmlidXRlPjwvc2FtbDI6QXR0cmlidXRlU3RhdGVtZW50Pjwvc2FtbDI6QXNzZXJ0aW9uPgoKDQo=";
        
        // Skapar en SAMBI ticket
        Tickets t = new Tickets();
        t.setHealthcareProfessionalLicense("AP");
        t.setHealthcareProfessionalLicenseIdentityNumber("920007");
        t.setPharmacyIdentifier("7350045511119");
        
        // Hämtar sambiticket
        String ticket = t.getTicket();
        
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
        restcall.setAssertion(encodedTicket); // oldTicket // encodedTicket
        
        try {
            // Gör restanropet
            restcall.runRestCall(restcall.ASSERTION);
       
            // skriver ut status på anropet
            System.out.println(restcall.getStatusCode());

            // skriver ut response
            System.out.println(restcall.getResponse());    
            
            // Svaret på restanropet kommer tillbaka i ett OauthToken eller ett OauthErrorTOken objekt beroände på statuskoden.
            if (restcall.getStatusCode() == 200 ) {
                System.out.println("access_token: " +  restcall.getOauthToken().getAccess_token() );
                System.out.println("expires_in: " +    restcall.getOauthToken().getExpires_in() );
                System.out.println("token_type: " +    restcall.getOauthToken().getToken_type() );
                System.out.println("refresh_token: " + restcall.getOauthToken().getRefresh_token() );
            } else {
                System.out.println("Något gick fel");
                System.out.println(restcall.getOauthToken().toString());
            }
            
            // Skickar in ett refresh token för att se att det fungerar
            if (restcall.getStatusCode() == 200 ) {
                restcall.setAssertion(restcall.getOauthToken().getRefresh_token()); // Sätter assertion med ett refreshToken
                restcall.runRestCall(restcall.REFRESH_TOKEN);                       // Gör ett refresh token rest anrop
                System.out.println("\n\n" + restcall.getOauthToken().toString());   // skriver ut svaret 
            }            
        } catch (Exception ex) {
            //Logger.getLogger(Oauth_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// HämtaGallandeUnderlagsversion using Access_token ----------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
        // Kollar att tidigare anrop gått bra annars avsluta innan tjensteanropet.
        if (restcall.getStatusCode() != 200) {  System.exit(restcall.getStatusCode()); }

        // Skapar en instans av enjänsteanrops class
        HamtaGallandeUnderlagsversion hgu = new HamtaGallandeUnderlagsversion(); 

        // Sätter standard inställningar för anropet
        hgu.setStandardDefaultValues(); 

        // Sätter testmiljö
        hgu.setSoapEndpointUrl( serviceEndpoint + "/apisp/HamtaGallandeUnderlagsversionResponder/V5");

        // Sätter personnummer på soapAnropet
        hgu.setTagValue("*//personnummer", "189001019802"); 
        
        // Skapar soapMeddelandet från XML:en
        SOAPMessage soapmessage = BaseXML.getSoapMessageFromString( hgu.getXML() ); 

        // Lägger till token i meddelandets header i detta fall svaret från ett refreshToken
        soapmessage.getMimeHeaders().addHeader("Authorization", "Bearer " + restcall.getOauthToken().getAccess_token() );

        // skickar soap meddelandet
       	SOAPMessage responsemessage = BaseXML.sendSoapRequest( hgu.getSoapEndpointUrl(), soapmessage ); 
        
        // hämtar upp svaret 
        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( responsemessage )); 
        
        // Kollar svaret med standardkontrollen
        hgu.checkResponse(response);    
        
        // Loggar ut svaret
        //response.logXML();
        
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
        
    }

    @Test // NLL-rest
    public void nll_rest_oAuth() {
        String serviceEndpoint = "https://test28.systest.receptpartner.se:19443";

        // Skapar en SAMBI ticket
        Tickets t = new Tickets();
//        t.setHealthcareProfessionalLicense("AP");
//        t.setHealthcareProfessionalLicenseIdentityNumber("920007");
//        t.setPharmacyIdentifier("7350045511119");

        t.setPersonalPrescriptionCode("9000027");
        t.setHealthcareProfessionalLicense("LK");

        // Hämtar sambiticket
        String ticket = t.getTicket();

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
        restcall.setAssertion(encodedTicket); // oldTicket // encodedTicket

        try {
            // Gör restanropet
            restcall.runRestCall(restcall.ASSERTION);

            // skriver ut status på anropet
            System.out.println(restcall.getStatusCode());

//            // skriver ut response
//            System.out.println(restcall.getResponse());

//            // Svaret på restanropet kommer tillbaka i ett OauthToken eller ett OauthErrorTOken objekt beroände på statuskoden.
//            if (restcall.getStatusCode() == 200 ) {
//                System.out.println("access_token: " +  restcall.getOauthToken().getAccess_token() );
//                System.out.println("expires_in: " +    restcall.getOauthToken().getExpires_in() );
//                System.out.println("token_type: " +    restcall.getOauthToken().getToken_type() );
//                System.out.println("refresh_token: " + restcall.getOauthToken().getRefresh_token() );
//            } else {
//                System.out.println("Något gick fel");
//                System.out.println(restcall.getOauthToken().toString());
//            }

//            // Skickar in ett refresh token för att se att det fungerar
//            if (restcall.getStatusCode() == 200 ) {
//                restcall.setAssertion(restcall.getOauthToken().getRefresh_token()); // Sätter assertion med ett refreshToken
//                restcall.runRestCall(restcall.REFRESH_TOKEN);                       // Gör ett refresh token rest anrop
//                System.out.println("\n\n" + restcall.getOauthToken().toString());   // skriver ut svaret
//            }
        } catch (Exception ex) {
            //Logger.getLogger(Oauth_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        String response;
        BaseRest br = new BaseRest();

        br.addHeader("Authorization", "Bearer " + restcall.getOauthToken().getAccess_token());

        response = br.getSSL("https://nll-fhir-server-s11-test1-deploy2.test.ecp.systest.receptpartner.se/fhir-server/fhir/MedicationRequest/1cff573e-2550-4df4-84d4-4e7896ceb7b5");
        System.out.println(response);


    }
}

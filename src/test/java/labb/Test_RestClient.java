package labb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import se.nhpj.rest.OauthTokens;
import se.nhpj.rest.RestCall;

public class Test_RestClient {

    @Test
    public void NetClientGetLabb() {
        try {
            URL url = new URL("https://petstore.swagger.io/v2/store/inventory");
//            URL url = new URL("https://nll-businesslogic-test1-deploy1-s7.test.ecp.systest.receptpartner.se/nll-businesslogic-0.0.7-29/rest/businesslogic/forskrivning"); // Funkar

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void NetClientGet() {
        try {
            URL url = new URL("https://petstore.swagger.io/v2/store/inventory");
//            URL url = new URL("https://test28.systest.receptpartner.se:19443/arkostore-mgr/rest/arkostore/" +
//                    "kontrolleraArbetsplatsGiltighet?arbetsplatskod=4000000000000"); // Funkar

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    String client_id = "EHM-USER";
    String client_secret = "EHM-PSW";

    @Test
    public void ClientPost() throws Exception {

        URL urlCon = new URL("https://test6.systest.receptpartner.se:19443/arkostore-mgr/rest/arkostore/kontrolleraArbetsplatsGiltighet");

//        String encodedCredentials = Base64.getEncoder().encodeToString((client_id+":"+client_secret).getBytes(StandardCharsets.UTF_8));

        String POST_PARAMS  = "{'arbetsplatskod' : '4000000000000','datum' : '" + se.nhpj.soap.utils.CurrentDateTime.getTExtraLong() + "'}";
        POST_PARAMS         = POST_PARAMS.replace("'","\"");

        HttpsURLConnection con = null;
        int responseCode = 0;
        StringBuilder response = new StringBuilder();

        try {
            con = (HttpsURLConnection) urlCon.openConnection();
//            con.setSSLSocketFactory(createSSLContext().getSocketFactory());
            con.setRequestMethod("POST");

//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            con.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(POST_PARAMS.getBytes());

            os.flush();
            os.close();

            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + con.getResponseCode());
            }

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
        System.out.println(response.toString());

    }

    private SSLContext createSSLContext() throws Exception{
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null , new TrustManager[] {new Test_RestClient.TrustAnyTrustManager()}, new SecureRandom());
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



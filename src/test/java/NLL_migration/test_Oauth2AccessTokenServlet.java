package NLL_migration;

import org.junit.Test;
import se.nhpj.rest.BaseRest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.net.URL;
import java.util.Map;

public class test_Oauth2AccessTokenServlet {

    // Special test ...
    String header_type = null;
    String header_value = null;
    public void addHeader( String type, String value) {
        header_type = type;
        header_value = value;
    }

    //
    // måste få till det med Mutal TLS
    //

    @Test
    public void test() throws Exception  {

//        System.setProperty("javax.net.ssl.trustStore", "/path/to/tmnt-truststore.jks");

        Map headers = null;
        String post_params = "";

        URL url = new URL("https://webticketserver-nll-ext18-prj1.ext.ecp.receptpartner.se/oauth2/Oauth2AccessTokenServlet");
//        URL url = new URL("https://test28.systest.receptpartner.se:19443/oauth2/Oauth2AccessTokenServlet");
        HttpsURLConnection con = null;
        int responseCode = 0;
        StringBuilder response = new StringBuilder();
        String encodedCredentials = Base64.getEncoder().encodeToString(("EHM-USER:EHM-PSW").getBytes(StandardCharsets.UTF_8));

        try {
            con = (HttpsURLConnection) url.openConnection();
            con.setSSLSocketFactory(createSSLContext().getSocketFactory());

            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            if (header_type != null)
                con.setRequestProperty(header_type, header_value);

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(post_params.getBytes());

            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream() ));
            String inputLine;

            headers = con.getHeaderFields();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            try {
                responseCode = con.getResponseCode();
                System.out.print("responseCode: " + responseCode + " : ");
                BufferedReader in = new BufferedReader(new InputStreamReader( con.getErrorStream() ));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("Exeption: " + e.getMessage() );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println(response.toString());
    }

    private SSLContext createSSLContext() throws Exception{
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null , new TrustManager[] {new test_Oauth2AccessTokenServlet.TrustAnyTrustManager()}, new SecureRandom());
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

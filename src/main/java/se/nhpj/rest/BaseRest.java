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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;

/**
 * Denna Class föränklar anrop till restresurser
 * att initiera ett objekt av klassen går det på ett änkelt sätt att anropa
 * en rest resurs med url och parameter beroände på om det är POST eller GET
 */

public class BaseRest {

    private URL url = null;
    private String post_params = "";
    private String put_params = "";
    private String client_id = "EHM-USER";
    private String client_secret = "EHM-PSW";
    private Map headers = null;

    /**
     * Konstruktor
     */
    public void BaseRest(){}

    /**
     * Denna metod returnerar java.util.Map med headers från senaste anropet
     * @return Map
     */
    public Map getHeaders() { return this.headers; }

    /**
     * Denna metod sätter den URL som pekar ut resursen
     * @param url String ex. "http://localhost:8080/nhpj/rest/register?reg=xxxxx"
     */
    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Denna metod sätter den URL som pekar ut resursen
     * @param url URL ex. new URL("http://localhost:8080/nhpj/rest/register?reg=xxxxx")
     */
    public void setUrl(URL url) { this.url = url; }

    public URL getUrl() { return this.url; }

    public void setPostParam( String param) { this.post_params = param; }

    public String getPostParam() { return this.post_params; }

    public void setPutParam( String param) { this.put_params = param; }

    public String getPutParam() { return this.put_params; }

    public String get(String url) {
        this.setUrl(url);
        return get();
    }

    public String get() {
        StringBuffer retVal = new StringBuffer();
        HttpURLConnection conn = null;
        int responseCode = 0;
        try {
            conn = (HttpURLConnection) getUrl().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader( conn.getInputStream() ));
            String output;
            while ((output = br.readLine()) != null) {
                    retVal.append(output);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                responseCode = conn.getResponseCode();
                System.out.println("responseCode: " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader( conn.getErrorStream() ));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    retVal.append(inputLine);
                }
                in.close();
                System.out.println("Exeption:\n" + e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return retVal.toString();
    }

    public String post(String url,String param) {
        this.setPostParam(param);
        this.setUrl(url);
        return post();
    }

    public String post() {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(getPostParam().getBytes());
            os.flush();
            os.close();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader( new InputStreamReader( (conn.getInputStream()) ) );
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String putSSL(String url,String param) {
        this.setPutParam(param);
        this.setUrl(url);
        try {
            return putSSL();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String putSSL()  throws Exception  {
        HttpsURLConnection con = null;
        int responseCode = 0;
        StringBuilder response = new StringBuilder();
        String encodedCredentials = Base64.getEncoder().encodeToString((client_id+":"+client_secret).getBytes(StandardCharsets.UTF_8));

        try {
            con = (HttpsURLConnection) getUrl().openConnection();
            con.setSSLSocketFactory(createSSLContext().getSocketFactory());

            con.setRequestMethod("PUT");
            con.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            if (header_type != null)
                con.setRequestProperty(header_type, header_value);

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(getPutParam().getBytes());

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
                System.out.println("responseCode: " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader( con.getErrorStream() ));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("Exeption:\n" + e.getMessage() );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
//        System.out.println(con.getHeaderFields().toString());
        return response.toString();
    }

    public String postSSL(String url,String param) {
        this.setPostParam(param);
        this.setUrl(url);
        try {
            return postSSL();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    // Bytt namn eftersom den arbetar med SSL/https
    public String postSSL()  throws Exception  {
        HttpsURLConnection con = null;
        int responseCode = 0;
        StringBuilder response = new StringBuilder();
        String encodedCredentials = Base64.getEncoder().encodeToString((client_id+":"+client_secret).getBytes(StandardCharsets.UTF_8));

        try {
            con = (HttpsURLConnection) getUrl().openConnection();
            con.setSSLSocketFactory(createSSLContext().getSocketFactory());

            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            if (header_type != null)
                con.setRequestProperty(header_type, header_value);

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(getPostParam().getBytes());

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
//        System.out.println(con.getHeaderFields().toString());
        return response.toString();
    }

    // Special test ...
    String header_type = null;
    String header_value = null;
    public void addHeader( String type, String value) {
        header_type = type;
        header_value = value;
    }

    public String getSSL(String url) {
        this.setUrl(url);
        try {
            return getSSL();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String getSSL() throws Exception {
        StringBuffer retVal = new StringBuffer();
        String encodedCredentials = Base64.getEncoder().encodeToString((client_id+":"+client_secret).getBytes(StandardCharsets.UTF_8));
        HttpsURLConnection con = null;
        int responseCode = 0;
        try {
            con = (HttpsURLConnection) getUrl().openConnection();
            con.setSSLSocketFactory(createSSLContext().getSocketFactory());
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Basic " + encodedCredentials );
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            if (header_type != null)
                con.setRequestProperty(header_type, header_value);

            BufferedReader br = new BufferedReader(new InputStreamReader( con.getInputStream() ));

            headers = con.getHeaderFields();

            String output;
            while ( (output = br.readLine())  != null) {
                retVal.append(output);
            }
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                responseCode = con.getResponseCode();
                System.out.println("BaseRest getSSL ResponseCode: " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader( con.getErrorStream() ));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    retVal.append(inputLine);
                }
                in.close();
                System.out.println("Exeption BaseRest getSSL:\n" + e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return retVal.toString();
    }

    private SSLContext createSSLContext() throws Exception{
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null , new TrustManager[] {new BaseRest.TrustAnyTrustManager()}, new SecureRandom());
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

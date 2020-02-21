package se.nhpj.ws;

import com.jayway.jsonpath.Configuration;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestPersonTest {

    @Test
    public void getTestPerson() {
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL("http://localhost:8080/nhpj/rest/testperson");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader( new InputStreamReader( (conn.getInputStream()) ) );

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String resp = sb.toString();
        System.out.println(resp);

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(resp);
    }
}
package se.nhpj.ws;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class TestNummerTest {

    @Test
    public void getMessage() {
        StringBuilder sb = new StringBuilder();
        se.nhpj.testdata.Namn namn = new se.nhpj.testdata.Namn();

        try {
            URL url = new URL("http://localhost:8080/nhpj/rest/testnummer?antal=4");
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
        System.out.println(sb.toString());

        if( sb.length()>1) {

            Object document = Configuration.defaultConfiguration().jsonProvider().parse(sb.toString());

            List a = JsonPath.read(document, "$.lista");

            Iterator itr = a.iterator();

            while (itr.hasNext()) {
                String testNr = JsonPath.read(itr.next(), "$.testnummer").toString().replace("-","");
                String fNamn = "";
                if (se.nhpj.testdata.Personnummer.isKvinna(testNr)) { fNamn = namn.getFirstNameFemale(); }
                if (se.nhpj.testdata.Personnummer.isMan(testNr)) { fNamn = namn.getFirstNameMale(); }
                String eNamn = namn.getLastNameTest();

                System.out.println(testNr +"\t"+ fNamn +"\t"+ eNamn);
            }
        }
    }
}
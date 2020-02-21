package se.nhpj.ws;

import org.junit.Test;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import static org.junit.Assert.*;

public class RetvalTest {

    Client client = Client.create();
    String getUrl  = "http://localhost:8080/nhpj/rest/val?val=APA";
    String postUrl = "http://localhost:8080/nhpj/rest/val";

    @Test
    public void getMessage() {
            WebResource webResource = client.resource(getUrl);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if(response.getStatus()!=200){
                throw new RuntimeException("HTTP Error: "+ response.getStatus());
            }
            String result = response.getEntity(String.class);
            System.out.println("Response from the Server: ");
            System.out.println(result);
    }

    @Test
    public void postMessage() {
        WebResource webResource = client.resource(postUrl);
        String inputData = "{\"firstName\":\"Alice\",\"lastName\":\"Brown\",\"school\":\"Bright Stars\",\"standard\":\"Three\",\"rollNumber\":1212}";
        ClientResponse response = webResource.type("text/plain").post(ClientResponse.class,inputData);
        if( response.getStatus()!=200 && response.getStatus()!=201){
            throw new RuntimeException("HTTP Error: "+ response.getStatus());
        }

        String result = response.getEntity(String.class);
        System.out.println("Response from the Server: ");
        System.out.println(result);
    }
}
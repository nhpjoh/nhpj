package labb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import se.nhpj.json.TimeToken;

/**
 * @author nhpj
 */

public class Test_json {

    @Test
    public void json_mapper() {  // till och från en object klass

        String json = "{'year':'+1','month':'-2','day':'-3','hour':'+4','minute':'-5','second':'+6','millisecond':'+7'}";
        json = json.replace("'","\"");
        System.out.println("json: " + json);
        System.out.println("Som ett TimeToken objekt:");
        try {
            ObjectMapper mapper = new ObjectMapper();
            TimeToken tt;
            tt = mapper.readValue(json, TimeToken.class);
            System.out.println(tt.toString());

            System.out.println();
            System.out.println("---------------------------------------------------------------------");
            System.out.println("------------------------ json sting again ---------------------------");
            System.out.println("---------------------------------------------------------------------");

            tt.setDay("7");

            String s =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tt);
            System.out.println(s);

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(s);

            node.replace("year",mapper.readTree("\"-99\""));
            System.out.println(node);

            node.set("second",mapper.readTree("\"66\""));
            System.out.println(node);

        } catch (IOException ex) {
            Logger.getLogger(Test_json.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Test
    public void JsonPath() {

        String json = "{ 'listan' : [{ 'rad' : '1', 'värde' : '11' },{ 'rad' : '2', 'värde' : '12' , 'extra' : '13' }]}";
        json = json.replace("'","\"");

        System.out.println("Start strängen:\n" + json);

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        List a = JsonPath.read(document, "$.listan");

        System.out.println("\nSkriver ut 'listan'");
        Iterator itr =  a.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println("\nSkrver ut ett visst värde:");
        System.out.println("extra: " + JsonPath.read(document, "$.listan.[1].extra").toString());

        System.out.println("\nSkrver ut ett visst värde ur 'listan'");
        Iterator itr2 =  a.iterator();
        while (itr2.hasNext()) {
            Object o = itr2.next();
            System.out.println("Värdet: " + JsonPath.read(o,"$.värde").toString());
        }
    }

    
}

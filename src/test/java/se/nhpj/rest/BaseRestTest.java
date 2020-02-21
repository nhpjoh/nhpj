package se.nhpj.rest;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseRestTest {

    @Test
    public void get() {
        BaseRest br = new BaseRest();
        String retVal = br.get("http://localhost:8080/nhpj/rest/register?reg=A&brand=&year=&owner=");
        System.out.println(retVal);
    }

    @Test
    public void post() {
        BaseRest br = new BaseRest();

        String param = "{ \"id\" : \"10001\" , \"regplat\" : \"ABC123\" , \"typ\" : \"PERSONBIL\" , \"marke\" : \"BMW 325i\" , " +
                "\"arsmodell\" : \"1981\" , \"drivmedel\" : \"BENSIN\" , \"vaxellada\" : \"MANUELL\" , \"hastkrafter\" : \"95\" , " +
                "\"farg\" : \"RÖD\" , \"kilometer\" : \"324500\" , \"pris\" : \"11000\" , \"bildurl\" : \" \" , " +
                "\"agarId\" : \"100001\" , \"agare\" : \"Izabella Smäll\" }";

        // Uppdaterar noder i en jSon sträng
        String updatedJson = param;
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.regplat"),     "ABC123").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.typ"),         "PERSONBIL").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.marke"),       "BMW 330i").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.arsmodell"),   "1980").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.drivmedel"),   "DISEL").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.vaxellåda"),   "AUTOMAT").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.hastkrafter"), "100").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.farg"),        "BLÅ").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.kilometer"),   "324511").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.pris"),        "22222").jsonString();
        updatedJson = JsonPath.parse(updatedJson).set(JsonPath.compile("$.bildurl"),     "").jsonString();
        System.out.println(updatedJson);

        //Uppdatera i databas via POST till RESTfull register ...
//        String retVal = br.post("http://localhost:8080/nhpj/rest/register",updatedJson);
//        System.out.println(retVal);
    }
}
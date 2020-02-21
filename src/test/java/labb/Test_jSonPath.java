package labb;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

public class Test_jSonPath {

    @Test
    public void test() {
        String json = "{ \"id\" : \"10001\" , \"Regplåt\" : \"ABC123\" , \"Typ\" : \"PERSONBIL\" ," +
                " \"Märke\" : \"BMW 325i\" , \"Årsmodell\" : \"1981\" , \"Drivmedel\" : \"BENSIN\" ," +
                " \"Växellåda\" : \"MANUELL\" , \"Hästkrafter\" : \"95\" , \"Färg\" : \"RÖD\" ," +
                " \"Kilometer\" : \"324500\" , \"Pris\" : \"11000\" , \"BildUrl\" : \" \" ," +
                " \"ÄgarId\" : \"100001\" , \"Ägare\" : \"Izabella Smäll\" }";

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        String ID = JsonPath.read(document, "$.id");
        String Regplåt = JsonPath.read(document, "$.Regplåt");
        String Typ = JsonPath.read(document, "$.Typ");
        String Märke = JsonPath.read(document, "$.Märke");
        String Årsmodell = JsonPath.read(document, "$.Årsmodell");
        String Drivmedel = JsonPath.read(document, "$.Drivmedel");
        String Växellåda = JsonPath.read(document, "$.Växellåda");
        String Hästkrafter = JsonPath.read(document, "$.Hästkrafter");
        String Färg = JsonPath.read(document, "$.Färg");
        String Kilometer = JsonPath.read(document, "$.Kilometer");
        String Pris = JsonPath.read(document, "$.Pris");
        String BildUrl = JsonPath.read(document, "$.BildUrl");
        String ÄgarId = JsonPath.read(document, "$.ÄgarId");



        System.out.println(ID);
        System.out.println(Regplåt);
        System.out.println(Typ);
        System.out.println(Märke);
        System.out.println(Årsmodell);
        System.out.println(Drivmedel);
        System.out.println(Växellåda);
        System.out.println(Hästkrafter);
        System.out.println(Färg);
        System.out.println(Kilometer);
        System.out.println(Pris);
        System.out.println(BildUrl);
        System.out.println(ÄgarId);

        String SQL = "UPDATE FORDON SET REGPLAT='" + Regplåt + "', TYP='" + Typ + "', MARKE='" + Märke + "', MODELLAR=" + Årsmodell + ", DRIVMEDEL='" + Drivmedel + "', VAXELLADA='" + Växellåda +
                     "', HASTKRAFTER='" + Hästkrafter +"', FARG='" + Färg + "', KILOMETER=" + Kilometer +", PRIS=" + Pris + ", BILD='" + BildUrl + "', AGARE=" + ÄgarId +
                     "     WHERE ID = " + ID;

        System.out.println(SQL);
    }

}

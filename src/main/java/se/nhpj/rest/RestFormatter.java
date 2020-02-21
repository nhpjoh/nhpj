package se.nhpj.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Denna class innehåller metoder för att arbeta med rest anrop på eHälsomyndigheten
 */
public class RestFormatter {

    /**
     * Denna metod skapar en formaterad streäng av en json sträng
     * @param jsonString
     * @return String formatted pretty
     */
    public static String prettyPrint( String jsonString ) {
        String retVal=null;
        ObjectMapper mapper = new ObjectMapper();
        Object jsonObject = null;
        try {
            jsonObject = mapper.readValue(jsonString, Object.class);
            retVal = (mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * Denna metod används för att konvertera UUID oracle RAW till en UUID string
     * @param raw
     * @return String
     */
    public static String rawUUID2String(String raw) {
        return raw.replaceAll( "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5").toLowerCase();
    }

    /**
     * Denna metod används för att konvertera en UUID string till UUID oracle RAW
     * @param string
     * @return String
     */
    public static String stringUUID2Raw(String string) {
        return string.replaceAll("-","").toUpperCase();
    }


}

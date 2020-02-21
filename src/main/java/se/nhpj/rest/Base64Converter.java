package se.nhpj.rest;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Denna klass har ett par metoder för att konvertera en sträng till en BASE64 sträng och också konvertera tillbaka från BASE64
 * @author nhpj
 */
public class Base64Converter {

    /**
     * Denna metod Base64 codar en sträng
     * @param text - String to encode
     * @return - Encoded string
     */
    public static String encodeString(String text) {
        java.util.Base64.Encoder enc = java.util.Base64.getEncoder();
        byte[] bytes = null;
        try {
            bytes = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Base64Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enc.encodeToString(bytes);
    }

    /**
     * Denna metod Base64 avcodar en sträng
     * @param text - String to decode
     * @return - Decoded string
//     * @throws java.io.UnsupportedEncodingException - For your info
     */
    public static String decodeString( String text )  {
        java.util.Base64.Decoder dec = java.util.Base64.getDecoder();
        byte[] decoded = dec.decode(text);
        
        StringBuilder sb = new StringBuilder();
        for (int i=0 ; i<decoded.length ; i++ ) {
            sb.append((char)decoded[i]);
        }
        return sb.toString();
    }
    
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String code = "Hejsan";
//        
//        System.out.println(code);
//        String coded = encodeString(code);
//        System.out.println(coded);
//        System.out.println(decodeString(coded));
//        
//    }
}

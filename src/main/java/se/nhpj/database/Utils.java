package se.nhpj.database;

public class Utils {

    public static String padd(String string, Integer length) {
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        for( int i=string.length() ; i<length ; i++ ) {
           sb.append(" ");
        }
        return sb.toString();
    }
}

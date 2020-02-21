
package labb;


import org.junit.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author nhpj
 */
public class Slask {
    
    public static void main(String[] args) {
        System.out.println("Hepp");
    }

    @Test
    public void test() {
        Float f = new Float(2.07023);
        String strFloat = String.format("%.3f", f);
        System.out.println(strFloat); // print 2.00


        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(3);
        System.out.println(nf.format(f));

    }
}

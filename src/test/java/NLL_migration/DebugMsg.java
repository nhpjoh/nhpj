package NLL_migration;

import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.utils.XmlFormatter;

/**
 * @author nhpj
 */
public class DebugMsg {
    public static void DebugMsg(boolean debug, SoapResponseXML rsp1, SoapResponseXML rsp2) {
        XmlFormatter xmlformatter = new XmlFormatter();

        if (debug) {
            System.out.println("\nDEBUG:\nResponse_1:\n" + xmlformatter.format( rsp1.getXML()) );
            System.out.println("\n\nResponse_2:\n" + xmlformatter.format( rsp2.getXML() ) +"\n\n");
        }

    }
}

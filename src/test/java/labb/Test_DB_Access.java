package labb;

/**
 * @author nhpj
 */

import java.sql.Connection;
import org.junit.Test;
import se.nhpj.database.DB_Access;

public class Test_DB_Access {
    @Test
    public void test() {
        int retVal=-1;
        Connection connection;
        connection = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/INT3", "ETCDBA", "ETCDBA");
        
//        retVal = DB_Access.doUptade(connection, "CALL RR_PROD.STANDARD_RFC.UPPDATERA_FORSKRIVARKOD ('8880023','8888888','9999')");
        System.out.println(retVal);
        
//        retVal = DB_Access.doUptade(connection, "CALL RR_PROD.STANDARD_RFC.UPPDATERA_FORSKRIVARKOD ('8888888','8880023','9999')");
        System.out.println(retVal);
        
        DB_Access.closeConnection(connection);
        
    }
    
}

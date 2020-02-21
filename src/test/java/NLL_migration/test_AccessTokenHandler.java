package NLL_migration;

import org.junit.Test;
import se.nhpj.rest.AccessTokenHandler;
import se.nhpj.soap.Tickets;

import java.util.UUID;

public class test_AccessTokenHandler {

    @Test // FARMACEUT, FORSKRIVARE //
    public void test_getAccessToken_type() {
        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();

        accessTokenHandler.setServiceEndpoint("https://test28.systest.receptpartner.se:19443");

        System.out.println("Apotekare");
        System.out.println(accessTokenHandler.getAccessToken( AccessTokenHandler.FARMACEUT ));

        accessTokenHandler = new AccessTokenHandler();

        System.out.println("Läkare");
        System.out.println(accessTokenHandler.getAccessToken( AccessTokenHandler.FORSKRIVARE ));

    }

    @Test  // parameter is a ticket
    public void test_getAccessToken_ticket(){
        Tickets t = new Tickets();
        t.setPharmacyIdentifier("7350045511119");
        t.setHealthcareProfessionalLicenseIdentityNumber("920007");
        t.setHealthcareProfessionalLicense("AP");
        t.setLevelOfAssurance("http://id.sambi.se/loa/loa3");
        t.setAudience("http://www.ehalsomyndigheten.se");
        t.setIssuer("http://www.ehalsomyndigheten.se/idp");
        t.setNameId(UUID.randomUUID().toString());
        t.setNameIdQualifier("http://www.ehalsomyndigheten.se");

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        System.out.println(accessTokenHandler.getAccessToken(t));

    }

    @Test // FARMACEUT, FORSKRIVARE //
    public void test_InternalAccessToken() {
        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();

        System.out.println(accessTokenHandler.getInternalAccessToken( AccessTokenHandler.FARMACEUT ));

    }
}

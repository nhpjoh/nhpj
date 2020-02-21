package se.nhpj.ws;

import se.nhpj.testdata.Personnummer;
import se.nhpj.testdata.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("testnummer")
public class TestNummer {
    @GET
    @Produces("application/json")
    public String getMessage( @QueryParam("antal") String antal ) {
        // Startar en timer
        se.nhpj.testdata.Transaction transaction = new Transaction();
        transaction.start("testnummer");

        Personnummer pnr = new Personnummer();
        StringBuilder sb = new StringBuilder();
        if (antal.isEmpty()) { return ""; }
        sb.append("{ \"lista\" : [ ");
        for( int i=0 ; i < Integer.parseInt(antal) ; i++ ) {
            String p = pnr.generatePnr(1900, 2018);
            sb.append("{ \"testnummer\" : \""+ p +"\" }");
            if (i+1 < Integer.parseInt(antal)) {
                sb.append(", ");
            }
            try {
                Thread.sleep(se.nhpj.testdata.RndData.getNumber(0,5));
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        sb.append(" ] }");

        transaction.stop("testnummer");
        sb.append("] , \"responseTime\" : \"" + transaction.getElapsedTime("testnummer",Transaction.MILLISECONDS).intValue() + "ms\" }");

        String retVal = sb.toString();

        System.out.println("testnummer: " + retVal);
	    return retVal;
    }
}

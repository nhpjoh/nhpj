package se.nhpj.ws;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("val")
public class Retval {

    @GET
    @Produces("application/json; charset=UTF-8")
    public String getMessage( @QueryParam("val") String val ) {
    	String retVal = "{ \"parameter\" : \""+val+"\" }";
        System.out.println("val:");
        System.out.println("in-param: message: " + val);
        System.out.println("response: " + retVal);
    	return retVal;
    }

    @POST
    @Consumes("text/plain")
    @Produces("application/json")
    public Response postMessage(String message) {

        String result = "Record entered: "+ message;

        System.out.println("val:");
        System.out.println("in-param: message: " + message);
        System.out.println("response: " + result);
        return Response.status(201).entity(result).build();
    }
}

package se.nhpj.ws;

import javax.ws.rs.*;

@Path("helloworld")
public class HelloWorldResource {
    @GET
    @Produces("application/json")
    public String getMessage() {
        String retVal="{ \"parameter\" : \"värde\" }";
        System.out.println("helloworld" + retVal);
        return retVal;
    }
}

package se.nhpj.ws.vinhyllan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import java.nio.charset.StandardCharsets;


@Path("vinhyllan")
public class VinhyllanResource {

    @Path("/alladrycker")
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getAllDryckVinhylla() {
        Vinhyllan v = new Vinhyllan();
        return v.getAllDryckVinhylla();
    }

    @Path("/positioninfo")
    @GET
    @Produces("application/json; charset=UTF-8")
    public String positionInfo( @QueryParam("position") Integer position) {
        StringBuilder sb = new StringBuilder();
        Vinhyllan v = new Vinhyllan();
        Dryck dryck = new Dryck();
        String typ = "";
        Integer dryck_id = v.getPositionInfo(position);

        if (dryck_id > 0) {
            dryck = v.getDryck(dryck_id);
            typ = v.getHyllFacksTyp(position);
        }

        sb.append("{ \"position\" : " + position + " , ");
        sb.append("\"dryck_id\" : " + dryck_id + " , ");
        sb.append("\"hyllfackstyp\" : \"" + typ + "\" , ");
        sb.append("\"namn\" : \"" + dryck.getNamn() + "\" }");

        System.out.println("positionsinfo: " + sb.toString());

        return sb.toString();
    }

    @Path("/dryck")
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getDryck(@QueryParam("dryck_id") Integer dryck_id) {
        Vinhyllan v = new Vinhyllan();
        Dryck dryck = v.getDryck(dryck_id);
        String json = "";
        try {
            // Konvertera till json string //
            ObjectMapper mapper = new ObjectMapper();
            json =  mapper.writer().writeValueAsString(dryck);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Path("/sokdryck")
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getDryck(@QueryParam("namn") String namn, @QueryParam("matratt") String matratt, @QueryParam("mattyp") String mattyp, @QueryParam("druva") String druva) {
        Vinhyllan v = new Vinhyllan();
        String json = v.getDryck(namn, matratt, mattyp, druva);
        return json;
    }

    @Path("/koder")
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getKod(@QueryParam("typ") String typ) {
        Vinhyllan v = new Vinhyllan();
        String json = v.getKodverk(typ);
        return json;
    }

    @Path("/kod")
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getKod(@QueryParam("typ") String typ, @QueryParam("varde") Integer varde) {
        Vinhyllan v = new Vinhyllan();
        String json = v.getKodverk(typ,varde);
        return json;
    }

}



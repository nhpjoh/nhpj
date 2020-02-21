package se.nhpj.ws.vinhyllan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DryckTest {

    @Test
    public void setDryck() {

        Dryck dryck = new Dryck();

        dryck.setDryck_id(1);
        dryck.setDryck_typ("RÖTT");
        dryck.setNamn("EttRöttVin");
        dryck.setDryck_kvalitet("Reserva");
        dryck.setBeskrivning("Billigt rötjut");
        dryck.setSotma(6);
        dryck.setFyllighet(7);
        dryck.setStravhet(8);
        dryck.setFruktstyrka(9);
        dryck.setProducent("Vinfabriken");
        dryck.setArtal("2010");
        dryck.setSista_dag("2020-07-01");
        dryck.setLand("Sverige");
        dryck.setDestrikt("Skåne");
        dryck.setAlkoholhalt((float)14.50);
        dryck.setDoft("Tjära");
        dryck.setFarg("Ljusröd");
        dryck.setPris((float) 79.50);
        dryck.setBild("urlTillBild");
        dryck.setBetyg(5);
        dryck.setOmdomme("Inget vidare men prisvärt");
        dryck.setSystemet_id("0");
        dryck.setSystemet_link("00");

        String[] m = {"NÖT","FLÄSK","LAX"};
        ArrayList<String> mattyper  = new ArrayList<String>(Arrays.asList(m));
        dryck.setPassar_mattyp(mattyper);

        List<Matratt> matratter = new ArrayList<Matratt>();
        matratter.add(new Matratt("Spagetti och köttfärssås",null,"500g Köttfärs, 1 gullök, 2 msk tomatpure"));
        matratter.add(new Matratt("Pasta med laxsås",null,null));
        dryck.setPassar_matratt(matratter);

        String[] d = {"Tempranillo","Cabernet"};
        ArrayList<String> druvor   = new ArrayList<String>(Arrays.asList(d));
        dryck.setDruvor(druvor);

        System.out.println("toString: " + dryck);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = null;
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dryck);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("getArtal: " + dryck.getArtal());

    }
}


package se.nhpj.ws.vinhyllan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import labb.Test_json;
import org.junit.Test;
import se.nhpj.database.DB_AccessHSQLdb;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import java.util.Date;

public class VinhyllanTest {

    @Test
    public void set_get_del_Position() {
        Vinhyllan v = new Vinhyllan();
        v.addPosition(1,3000);
        System.out.println("getPosition: " + v.getPosition(3000));
        System.out.println("getDryckAtPossition: " + v.getDryckAtPossition(1));
        v.delPosition(v.getPosition(3000));
        System.out.println("getPosition: " + v.getPosition(3000));
    }

    @Test
    public void set_get_del_Kodverk() {
        Vinhyllan v = new Vinhyllan();
        v.addKodverk("TestTyp","TestText", 99);
        System.out.println(v.getKodverk("TestTyp"));
        v.delKodverk("TestTyp","TestText",99);
        System.out.println(v.getKodverk("TestTyp"));
    }

    @Test
    public void getHyllFacksTyp() {
        Vinhyllan v = new Vinhyllan();
        System.out.println(v.getHyllFacksTyp(3));
    }

    @Test
    public void getKodverk() throws IOException{
        Vinhyllan v = new Vinhyllan();
        String json = v.getKodverk("PASSAR_TILL_MATTYP");

        System.out.println(json);

        json = v.getKodverk("PASSAR_TILL_MATTYP",6);

        System.out.println("\n\n" + json);


    }

    @Test
    public void getDruvor() {
        Vinhyllan v = new Vinhyllan();
        String json = v.getDruvor(1);

        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }
    @Test
    public void setDruva() {
        Vinhyllan v = new Vinhyllan();
        v.addDruva(3000,"TestDruvan");

        String json = v.getDruvor(3000);
        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }
    @Test
    public void delDruva() {
        Vinhyllan v = new Vinhyllan();
        v.delDruva(3000,"TestDruvan");

        String json = v.getDruvor(3000);
        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }

    @Test
    public void getPassarMatratt() {
        Vinhyllan v = new Vinhyllan();
        String json = v.getPassarMatratt(1);

        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }
    @Test
    public void setPassarMatratt() {
        Vinhyllan v = new Vinhyllan();
        Integer dryck_id = 3000;
        v.addPassarMatratt(dryck_id, "TestMatRätt", "Buffe","EjSparat");

        String json = v.getPassarMatratt(dryck_id);

        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }
    @Test
    public void delPassarMatratt() {
        Integer dryck_id = 3000;
        Vinhyllan v = new Vinhyllan();
        v.delPassarMatratt(dryck_id, "TestMatRätt");

        String json = v.getPassarMatratt(dryck_id);
        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }

    @Test
    public void getPassarMattyp() {
        Vinhyllan v = new Vinhyllan();
        String json = v.getPassarMattyp(3000);

        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }
    @Test
    public void setPassarMattyp() {
        Vinhyllan v = new Vinhyllan();
        v.addPassarMattyp(3000, "TestMatTyp");

        String json = v.getPassarMattyp(3000);
        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }
    @Test
    public void delPassarMattyp() {
        Vinhyllan v = new Vinhyllan();
        v.delPassarMattyp(3000, "TestMatTyp");

        String json = v.getPassarMattyp(3000);
        System.out.println(json);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));
    }
    @Test
    public void getPassarMattypUsed() {
        Vinhyllan v = new Vinhyllan();
        String json = v.getPassarMattypUsed();
        System.out.println(json);

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        List a = JsonPath.read(document, "$.lista");
        Iterator itr =  a.iterator();
        while (itr.hasNext()) {
            Object o = itr.next();
            System.out.println(JsonPath.read(o,"$.namn").toString());
        }

    }
    @Test
    public void getPassarMattypAll() {
        Vinhyllan v = new Vinhyllan();
        String json = v.getPassarMattypAll();
        System.out.println(json);
        System.out.println("\n");

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        List a = JsonPath.read(document, "$.lista");
        Iterator itr =  a.iterator();
        while (itr.hasNext()) {
            Object o = itr.next();
            System.out.println(JsonPath.read(o,"$.namn").toString());
        }

    }
    @Test
    public void getSequenseValue() {
        Vinhyllan v = new Vinhyllan();
        System.out.println(v.getSequenseValue());
    }

    @Test
    public void getDryck_dryck_id() {
        Vinhyllan v = new Vinhyllan();
        System.out.println(v.getDryck(1007).toString());
        System.out.println();
        System.out.println(v.getDryck(1008).toString());
        System.out.println();
        System.out.println(v.getDryck(1011).toString());
    }

    @Test
    public void getDryck_namn_matratt_mattyp_druva() {
        Vinhyllan v = new Vinhyllan();
        String json = v.getDryck("","","","");
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint( json ));
    }

    @Test
    public void addDryck_Dryck() {
        Vinhyllan vinhyllan = new Vinhyllan();
        Dryck dryck = new Dryck();
        Integer position = 8;

        dryck.setDryck_typ("RÖTT");
        dryck.setNamn("Faustino I");
        dryck.setDryck_kvalitet("Gran Reserva");
        dryck.setBeskrivning("Nyanserad, utvecklad, kryddig smak med fatkaraktär, inslag av torkade körsbär, choklad, katrinplommon, dill, nötter och vanilj. Serveras vid 16-18°C till rätter av lamm- eller nötkött.");
        dryck.setSotma(0);
        dryck.setFyllighet(8);
        dryck.setStravhet(8);
        dryck.setFruktstyrka(9);
        dryck.setProducent("Faustino Martínez");
        dryck.setArtal("2006");
        dryck.setSista_dag("2020-07-01");
        dryck.setLand("Spanien");
        dryck.setDestrikt("Rioja");
        dryck.setAlkoholhalt((float)13.50);
        dryck.setDoft("Nyanserad, utvecklad, kryddig doft med fatkaraktär, inslag av torkade körsbär, choklad, kanel, dill, katrinplommon och vanilj");
        dryck.setFarg("Mörk, röd färg");
        dryck.setPris((float) 189.00);
        dryck.setBild("urlTillBild");
        dryck.setBetyg(8);
        dryck.setOmdomme("Gott vin");
        dryck.setSystemet_id("267801");
        dryck.setSystemet_link("https://www.systembolaget.se/dryck/roda-viner/faustino-i-267801");

        String[] m = {"NÖT","LAMM"};
        ArrayList<String> mattyper  = new ArrayList<String>(Arrays.asList(m));
        dryck.setPassar_mattyp(mattyper);

        List<Matratt> matratter = new ArrayList<Matratt>();
        matratter.add(new Matratt("Spagetti och köttfärssås",null,"500g Köttfärs, 1 gullök, 2 msk tomatpure"));
        matratter.add(new Matratt("Grillad Entrecot","Rutiga sid 186",null));
        dryck.setPassar_matratt(matratter);

        String[] d = {"Tempranillo"};
        ArrayList<String> druvor   = new ArrayList<String>(Arrays.asList(d));
        dryck.setDruvor(druvor);

        System.out.println("toString: " + dryck);

        // Skriver ut som en json string ...
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = null;
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dryck);
            System.out.println(json);
        } catch (JsonProcessingException e) { e.printStackTrace(); }


        Integer dryck_id = vinhyllan.addDryck(dryck);
        System.out.println("dryck_id: " + dryck_id);

        // Lägg till dryck på position ?
        vinhyllan.addPosition(position,dryck_id);

    }

    @Test
    public void getAllDryckVinhylla() {
        Vinhyllan v = new Vinhyllan();
        String json = v.getAllDryckVinhylla();

//        System.out.println(json);

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        List a = JsonPath.read(document, "$.listan");

        System.out.println("\nSkriver ut 'listan'");
        Iterator itr =  a.iterator();
        while (itr.hasNext()) {
            Object o = itr.next();
            System.out.println(JsonPath.read(o,"$.dryck.dryck_id").toString());
            System.out.println();
            System.out.println(itr.next());
        }
    }


    @Test  //
    public void labb2() {

        Vinhyllan vinhyllan = new Vinhyllan();
        Dryck dryck = new Dryck();
        Integer position = 7;

        dryck.setDryck_typ("RÖTT");
        dryck.setNamn("Faustino I");
        dryck.setDryck_kvalitet("Gran Reserva");
        dryck.setBeskrivning("Nyanserad, utvecklad, kryddig smak med fatkaraktär, inslag av torkade körsbär, choklad, katrinplommon, dill, nötter och vanilj. Serveras vid 16-18°C till rätter av lamm- eller nötkött.");
        dryck.setSotma(0);
        dryck.setFyllighet(8);
        dryck.setStravhet(8);
        dryck.setFruktstyrka(9);
        dryck.setProducent("Faustino Martínez");
        dryck.setArtal("2006");
        dryck.setSista_dag("2020-07-01");
        dryck.setLand("Spanien");
        dryck.setDestrikt("Rioja");
        dryck.setAlkoholhalt((float)13.50);
        dryck.setDoft("Nyanserad, utvecklad, kryddig doft med fatkaraktär, inslag av torkade körsbär, choklad, kanel, dill, katrinplommon och vanilj");
        dryck.setFarg("Mörk, röd färg");
        dryck.setPris((float) 189.00);
        dryck.setBild("urlTillBild");
        dryck.setBetyg(8);
        dryck.setOmdomme("Gott vin");
        dryck.setSystemet_id("267801");
        dryck.setSystemet_link("https://www.systembolaget.se/dryck/roda-viner/faustino-i-267801");

        String[] m = {"NÖT","LAMM"};
        ArrayList<String> mattyper  = new ArrayList<String>(Arrays.asList(m));
        dryck.setPassar_mattyp(mattyper);

        List<Matratt> matratter = new ArrayList<Matratt>();
        matratter.add(new Matratt("Spagetti och köttfärssås",null,"500g Köttfärs, 1 gullök, 2 msk tomatpure"));
        matratter.add(new Matratt("Grillad Entrecot","Rutiga sid 186",null));
        dryck.setPassar_matratt(matratter);

        String[] d = {"Tempranillo"};
        ArrayList<String> druvor   = new ArrayList<String>(Arrays.asList(d));
        dryck.setDruvor(druvor);


        //System.out.println("ÅrtalSträng: " + dryck.getArtalString());

        // Skriver ut som en json string ...
        String json = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dryck);

            System.out.println("Dryck som json: \n" + json);
        } catch (JsonProcessingException e) { e.printStackTrace(); }


        // bygger tillbaka en Dryck från en jsonSträng
        ObjectMapper mapper = new ObjectMapper();
        Dryck json_dryck = null;
        try {
            json_dryck = mapper.readValue(json, Dryck.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(json_dryck.toString());


    }

    @Test
    public void labb_bygga_json_dryck() {
        String XXXX = se.nhpj.testdata.RndData.getChars(1);
        String YYYY = se.nhpj.testdata.RndData.getNumbers(1);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("'dryck_id' : null, ");
        sb.append("'dryck_typ' : '" + se.nhpj.testdata.RndData.getChars(1) + "' ," );
        sb.append("'namn' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'dryck_kvalitet' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'beskrivning' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'sotma' : '"+ YYYY +"', ");
        sb.append("'fyllighet' : '"+ YYYY +"', ");
        sb.append("'stravhet' : '"+ YYYY +"', ");
        sb.append("'fruktstyrka' : '"+ YYYY +"', ");
        sb.append("'producent' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'artal' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'sista_dag' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'land' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'destrikt' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'alkoholhalt' : '"+ YYYY +"', ");
        sb.append("'doft' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'farg' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'pris' : '"+ YYYY +"', ");
        sb.append("'bild' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'betyg' : '"+ YYYY +"', ");
        sb.append("'omdomme' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'systemet_link' : '"+ se.nhpj.testdata.RndData.getChars(1) +"', ");
        sb.append("'passar_mattyp' : [], ");
//        sb.append("'passar_matratt' : [], ");
//        sb.append("'druvor' : [] " );

        // Ta bort sista kommat i stängen
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-2)) {
            sb.replace(kommat,len,"");
        }

        sb.append(" }");


        String json = sb.toString();
        json = json.replace("'","\"");

        // bygger tillbaka en Dryck från en jsonSträng
        ObjectMapper mapper = new ObjectMapper();
        Dryck json_dryck = null;
        try {
            json_dryck = mapper.readValue(json, Dryck.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(json_dryck.toString());

    }

    @Test
    public void labb_bygga_json_mattyp() {
        String not 				= "true";
        String flask			= "true";
        String lamm				= "true";
        String asiatiskt		= "true";
        String vilt				= "true";
        String kryddstarkt		= "true";
        String ost				= "true";
        String fagel			= "true";
        String dessert			= "true";
        String sallskapsdryck	= "true";
        String skaldjur			= "true";
        String fisk				= "true";
        String apertif			= "true";
        String buffemat			= "true";

        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        if (not.contains("true")) { sb.append("'NÖT', "); }
        if (flask.contains("true")) { sb.append("'FLÄSK', "); }
        if (lamm.contains("true")) { sb.append("'LAMM', "); }
        if (asiatiskt.contains("true")) { sb.append("'ASIATISKT', "); }
        if (vilt.contains("true")) { sb.append("'VILT', "); }
        if (kryddstarkt.contains("true")) { sb.append("'KRYDDSTARKT', "); }
        if (ost.contains("true")) { sb.append("'OST', "); }
        if (fagel.contains("true")) { sb.append("'FÅGEL', "); }
        if (dessert.contains("true")) { sb.append("'DESERT', "); }
        if (sallskapsdryck.contains("true")) { sb.append("'SÄLLSKAPSDRYCK', "); }
        if (skaldjur.contains("true")) { sb.append("'SKALDJUR', "); }
        if (fisk.contains("true")) { sb.append("'FISK', "); }
        if (apertif.contains("true")) { sb.append("'APERTIF', "); }
        if (buffemat.contains("true")) { sb.append("'BUFFEMAT', "); }

        // Ta bort sista kommat i stängen
        int kommat = sb.lastIndexOf(",");
        int len = sb.length();
        if (kommat == (len-2)) {
            sb.replace(kommat,len,"");
        }

        sb.append(" ]");

        System.out.println(sb.toString());
    }

 }
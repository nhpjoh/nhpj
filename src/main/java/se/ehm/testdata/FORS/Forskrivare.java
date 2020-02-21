package se.ehm.testdata.FORS;

import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
    insert into FORS_PROD.FORS values (888001,'888001',X,'190001019801',' Ortoped, Olof','N');
    insert into FORS_PROD.FORS_KATEGORI values (888001,	'LK', '6600', '00', '2599', null, null, null, null, null, null, null, null, null, null,'J', null, null, null, null, null, null);

    Hämta personnummer från skatteverkslistan
    Kolla om förskrivaren finns, (personnummer, kod)
    Skapa kod + kontrollsiffra

    stopp in i FORS databasen
 */

public class Forskrivare {

    private String FORS_ID          = null;
    private String FORS_KOD         = null;
    private String FORS_KOD_CHECK   = null;
    private String FORS_PERSONNR    = null;
    private String FORS_NAMN        = null;
    private String FORS_SKYDDAD_ID  = "N";

    private String FORS_KATEGORI_KOD = FORS_ID;
    private String FORS_KATEGORI_KATEGORI = null;
    private String FORS_KATEGORI_UTBILDNINGSKOD = null;
    private String FORS_KATEGORI_SPECIALKODn = null;
    private String FORS_KATEGORI_FORSKRIVNINGSRATT = "J";

    public String getKodCheck( String kod) { return se.nhpj.testdata.Luhn.CalcNext(kod); }

    public boolean checkIfKodExists( String personnummer, String kod, String ServiceName ) {
        boolean retVal;
        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/"  + ServiceName  ,"ETCDBA","ETCDBA");
        Integer iPers = se.nhpj.database.DB_Access.getIntegerValue(con,"SELECT count(*) as cnt from FORS_PROD.FORS where PERSONNR = " + personnummer ,"cnt");
        Integer iKod  = se.nhpj.database.DB_Access.getIntegerValue(con,"SELECT count(*) as cnt from FORS_PROD.FORS where KOD = "      + kod          ,"cnt");
        se.nhpj.database.DB_Access.closeConnection(con);
        if (iKod+iPers > 0) retVal=true;
        else retVal = false;
        return retVal;
    }

    public void addForskrivare( Forskrivare forskrivare, String ServiceName ) {

            Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceName,"ETCDBA","ETCDBA");
            String sqlFors          = "insert into FORS_PROD.FORS values (" + forskrivare.getFORS_KOD() + "," + forskrivare.getFORS_KOD() + "," + forskrivare.getFORS_KOD_CHECK() + "," + forskrivare.getFORS_PERSONNR() + ",'" + forskrivare.getFORS_NAMN() + "','N')";
            String sqlForsKategori  = "insert into FORS_PROD.FORS_KATEGORI values ("+ forskrivare.getFORS_KOD() +",'LK', '6600', '00', '2599', null, null, null, null, null, null, null, null, null, null,'J', null, null, null, null, null, null,null)";
            // insert into FORS ....
            se.nhpj.database.DB_Access.doInsert(con,sqlFors);
            // insert into FORS_KATEGORI ....
            se.nhpj.database.DB_Access.doInsert(con,sqlForsKategori);
            se.nhpj.database.DB_Access.closeConnection(con);
    }


    public String getFORS_ID() { return FORS_ID; }
    public void setFORS_ID(String FORS_ID) { this.FORS_ID = FORS_ID; }
    public String getFORS_KOD() { return FORS_KOD; }
    public void setFORS_KOD(String FORS_KOD) { this.FORS_KOD = FORS_KOD; }
    public String getFORS_KOD_CHECK() { return FORS_KOD_CHECK; }
    public void setFORS_KOD_CHECK(String FORS_KOD_CHECK) { this.FORS_KOD_CHECK = FORS_KOD_CHECK; }
    public String getFORS_PERSONNR() { return FORS_PERSONNR; }
    public void setFORS_PERSONNR(String FORS_PERSONNR) { this.FORS_PERSONNR = FORS_PERSONNR; }
    public String getFORS_NAMN() { return FORS_NAMN; }
    public void setFORS_NAMN(String FORS_NAMN) { this.FORS_NAMN = FORS_NAMN; }
    public String getFORS_SKYDDAD_ID() { return FORS_SKYDDAD_ID; }
    public void setFORS_SKYDDAD_ID(String FORS_SKYDDAD_ID) { this.FORS_SKYDDAD_ID = FORS_SKYDDAD_ID; }
//    public String getFORS_KATEGORI_KOD() { return FORS_KATEGORI_KOD; }
//    public void setFORS_KATEGORI_KOD(String FORS_KATEGORI_KOD) { this.FORS_KATEGORI_KOD = FORS_KATEGORI_KOD; }
//    public String getFORS_KATEGORI_KATEGORI() { return FORS_KATEGORI_KATEGORI; }
//    public void setFORS_KATEGORI_KATEGORI(String FORS_KATEGORI_KATEGORI) { this.FORS_KATEGORI_KATEGORI = FORS_KATEGORI_KATEGORI; }
//    public String getFORS_KATEGORI_UTBILDNINGSKOD() { return FORS_KATEGORI_UTBILDNINGSKOD; }
//    public void setFORS_KATEGORI_UTBILDNINGSKOD(String FORS_KATEGORI_UTBILDNINGSKOD) { this.FORS_KATEGORI_UTBILDNINGSKOD = FORS_KATEGORI_UTBILDNINGSKOD; }
//    public String getFORS_KATEGORI_SPECIALKODn() { return FORS_KATEGORI_SPECIALKODn; }
//    public void setFORS_KATEGORI_SPECIALKODn(String FORS_KATEGORI_SPECIALKODn) { this.FORS_KATEGORI_SPECIALKODn = FORS_KATEGORI_SPECIALKODn; }
//    public String getFORS_KATEGORI_FORSKRIVNINGSRATT() { return FORS_KATEGORI_FORSKRIVNINGSRATT; }
//    public void setFORS_KATEGORI_FORSKRIVNINGSRATT(String FORS_KATEGORI_FORSKRIVNINGSRATT) { this.FORS_KATEGORI_FORSKRIVNINGSRATT = FORS_KATEGORI_FORSKRIVNINGSRATT; }



    public static void main (String args[]) {
        // Databas att stoppa in förskrivare i
        String ServiceName = "INT28";
        int cnt = 0;

        for (int i = 0; i < 1 ; i++ ) {
            Forskrivare forskrivare = new Forskrivare();

            // Slumpa fram en person från skatteverket:s lista
            Skatteverket skatten = new Skatteverket();

            List<Person> allaPersonner = skatten.getPersons();

            Iterator itr = allaPersonner.iterator();

            while ( itr.hasNext()) {
                Person p = (Person)itr.next();

                // Skapa en förskrivare som har en förskrivarkod som startar på en '7:a'
                forskrivare.setFORS_KOD(se.nhpj.testdata.RndData.getANumber(700000, 799999));
                forskrivare.setFORS_ID(forskrivare.getFORS_KOD());
                forskrivare.setFORS_KOD_CHECK(forskrivare.getKodCheck(forskrivare.getFORS_KOD()));
                forskrivare.setFORS_PERSONNR(p.getPersonnummer());
                forskrivare.setFORS_NAMN(p.getFornamn() + ", " + p.getEfternamn());
                forskrivare.setFORS_SKYDDAD_ID("N");


                // Om förskrivaren inte finns lägg till :-)
                if (!forskrivare.checkIfKodExists(forskrivare.getFORS_PERSONNR(), forskrivare.getFORS_KOD(), ServiceName)) {
                    forskrivare.addForskrivare(forskrivare, ServiceName);
                    System.out.println((i + 1) + "\t\tFörskrivare " + forskrivare.getFORS_KOD() + forskrivare.getFORS_KOD_CHECK() + ", personnummer: " + forskrivare.getFORS_PERSONNR());
                } else {
                    System.out.println((i + 1) + "\t\tFörskrivare " + forskrivare.getFORS_KOD() + forskrivare.getFORS_KOD_CHECK() + ", Finns redan " + ++cnt);
                }
//                forskrivare = null;
            }
        }
    }
}

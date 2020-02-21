package se.ehm.testdata.FOLK;

import se.nhpj.testdata.*;
import java.sql.Connection;

public class SparPerson {

    // SPAR_PERSON
    private String personnr;
    private String rednamn;
    private String rednamnmrk;
    private String fornamn;
    private String mellannamn = "TEST";
    private String efternamn;
    private String namnkod = "10";
    private String avlidendat;
    private String andringsdat;
    private String folkbokforddat;
    private String lkfkod;
    private String senastslagning;
    private String skapaddat;
    private String andratdat;
    private String traffkod = "50";
    private String kundinfo;
    private String spaadr_kod;
    private String adresskod;
    private String hpnr;
    private String sekerhetsmarkering ="N";
    private String skyddadfolkbokforing = "N";
    //SPAR_ADRESS
    private String adresstyp = "FOLK";
    private String coadress;
    private String gatuadress;
    private String foadress;
    private String postnr;
    private String postort;
    private String landkod;
    private String postkod;
    private String land;


    public SparPerson addRNDPerson(Integer MinYear, Integer MaxYear, String ServiceName) {
        Person person = new Person();
        Namn namn = new Namn();
        Personnummer pnr = new Personnummer();

        // Generera upp ett nytt testnummer.
        person.setPersonnummer(pnr.generatePnr(MinYear,MaxYear));
        this.setPersonnr(person.getPersonnummer().replace("-",""));

        // Generera förnamn
        if (person.isMan()) { this.setFornamn(namn.getFirstNameMale()); }
        else { this.setFornamn(namn.getFirstNameFemale()); }

        // Här skapar vi ett testEfternamn
        this.setEfternamn(namn.getLastNameTest());
        this.setRednamn(this.efternamn+", "+this.mellannamn+" " + this.fornamn);

        // Generera adress
        Adress adress = new Adress().getTestAdress();
        this.setGatuadress(adress.getGatuAdress());
        this.setLkfkod(adress.getLKF_kod());
        this.setPostnr(adress.getPostnummer().replace(" ",""));
        this.setPostort(adress.getOrt());

        String SQL_spar_person = "CALL FOLK_PROD.INS_SPAR_PERSON( '"+this.personnr+"', '"+this.rednamn+"',NULL,'"+this.fornamn+"','"+this.mellannamn+"','"+this.efternamn+"', '"+this.namnkod+"',NULL,NULL,NULL,'"+this.lkfkod+"',NULL, NULL, SYSDATE,'"+this.traffkod+"',NULL,NULL,NULL,NULL,'N','N','PRESTANDA' )";
        String SQL_spar_adress = "CALL FOLK_PROD.INS_SPAR_ADRESS( '"+this.personnr+"', 'FOLK', 'TEST', '"+this.gatuadress+"', 'TEST', '"+this.postnr+"', '"+this.postort+"', NULL, NULL, NULL, NULL, NULL, 'PRESTANDA' )";


        // kontrollera att personnummret ej finns
        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/"  + ServiceName  ,"ETCDBA","ETCDBA");
        Integer iPers  = se.nhpj.database.DB_Access.getIntegerValue(con,"SELECT count(*) as cnt from FOLK_PROD.SPAR_PERSON where PERSONNR = " + this.personnr ,"cnt");

        // Stoppa in i databasen
        if (iPers == 0) {
            int retValp = se.nhpj.database.DB_Access.doInsert(con, SQL_spar_person);
            int retVala = se.nhpj.database.DB_Access.doInsert(con, SQL_spar_adress);
        } else {
            System.out.println("Person: " + this.personnr + " finns redan i SPAR_PERSON");
        }
        se.nhpj.database.DB_Access.closeConnection(con);
        return this;
    }

    public SparPerson addRNDPerson(String ServiceName) {
        return this.addRNDPerson(1890,2018,ServiceName);
    }

    public SparPerson addPerson(Person person, String ServiceName) {

        this.setPersonnr(person.getPersonnummer().replace("-",""));
        this.setRednamn(person.getEfternamn() + ", " + person.getFornamn());
        this.setFornamn(person.getFornamn());
        this.setEfternamn(person.getEfternamn());
        Adress adress = person.getAdress();
        this.setGatuadress(adress.getGatuAdress());
        this.setPostnr(adress.getPostnummer());
        this.setPostort(adress.getOrt());

        String SQL_spar_person = "CALL FOLK_PROD.INS_SPAR_PERSON( '"+this.personnr+"', '"+this.rednamn+"',NULL,'"+this.fornamn+"','"+this.mellannamn+"','"+this.efternamn+"', '"+this.namnkod+"',NULL,NULL,NULL,'"+this.lkfkod+"',NULL, NULL, SYSDATE,'"+this.traffkod+"',NULL,NULL,NULL,NULL,'N','N','PRESTANDA' )";
        String SQL_spar_adress = "CALL FOLK_PROD.INS_SPAR_ADRESS( '"+this.personnr+"', 'FOLK', 'TEST', '"+this.gatuadress+"', 'TEST', '"+this.postnr+"', '"+this.postort+"', NULL, NULL, NULL, NULL, NULL, 'PRESTANDA' )";

        // kontrollera att personnummret ej finns
        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/"  + ServiceName  ,"ETCDBA","ETCDBA");
        Integer iPers  = se.nhpj.database.DB_Access.getIntegerValue(con,"SELECT count(*) as cnt from FOLK_PROD.SPAR_PERSON where PERSONNR = " + this.personnr ,"cnt");

        // Stoppa in i databasen
        if (iPers == 0) {
            int retValp = se.nhpj.database.DB_Access.doInsert(con, SQL_spar_person);
            int retVala = se.nhpj.database.DB_Access.doInsert(con, SQL_spar_adress);
        } else {
            System.out.println("Person: " + this.personnr + " finns redan i SPAR_PERSON");
        }
        se.nhpj.database.DB_Access.closeConnection(con);
        return this;
    }

    @Override
    public String toString() {
        return this.personnr +"|"+ this.fornamn +"|"+ this.mellannamn +"|"+ this.efternamn +"|"+ this.lkfkod +"|"+ this.gatuadress +"|"+ this.postnr +"|"+ this.postort;
    }

    public void setPersonnr(String personnummer) { this.personnr = personnummer; }
    public String getPersonnr() { return this.personnr; }
    public void setFornamn(String namn) { this.fornamn = namn; }
    public void setEfternamn(String namn) {this.efternamn = namn; }
    public void setLkfkod(String kod) { this.lkfkod = kod; }
    public void setRednamn(String rednamn) { this.rednamn = rednamn; }
    public void setGatuadress( String adress) { this.gatuadress = adress; }
    public void setPostnr(String postnummer) { this.postnr = postnummer; }
    public void setPostort(String ort) { this.postort = ort; }


}

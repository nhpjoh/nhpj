package se.nhpj.ws.vinhyllan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Dryck {
    private Integer dryck_id;       // Ett unikt id från databasen
    private String  dryck_typ;      // RÖTT_VIN,VITT_VIN, ROSE_VIN, ÖL, WISKEY, ROM ..etc..
    private String  namn;           // Dryckens Namn
    private String  dryck_kvalitet; // Reserva, Grand Reserva, VSOP, 18år etc... Dessa ändras beroände på dryck_typ
    private String  beskrivning;    // Beskriving av dryck
    private Integer sotma;          // 1-12(Systemets-klockan)
    private Integer fyllighet;      // 1-12(Systemets-klockan)
    private Integer stravhet;       // 1-12(Systemets-klockan)
    private Integer fruktstyrka;    // 1-12(Systemets-klockan)
    private String  producent;      // Tillverkare/producent
    private String  artal;          // Tillverkningsår
    private String  sista_dag;      // När blir drycken gammal 'typ när vin riskerar att bli vinäger'
    private String  land;           // Tillverkningsland
    private String  destrikt;       // Destrikt
    private Float   alkoholhalt;    // Alkohålhalt i %
    private String  doft;           // Doften
    private String  farg;           // Färgen
    private Float   pris;           // Inköpspris
    private String  bild;           // länk till bild
    private Integer betyg;          // Betyg
    private String  omdomme;                // Omdömme av drycken
    private String  systemet_id;       // beställningsnummer hos systemet
    private String  systemet_link;     // Länk till till exempel systemets eller liknande

    private List<String>  passar_mattyp;    // Från systemet, Lamm, Nöt, Vilt, Fisk, Fläsk, Fågel etc.
    private List<Matratt> passar_matratt;   // Egen lista på mat <Matratt> som man tycker att drycken passar till
    private List<String>  druvor;           // Lista med de druvor som drycken innehåller :-)

    public Integer getDryck_id() {
        return dryck_id;
    }

    public void setDryck_id(Integer dryck_id) {
        this.dryck_id = dryck_id;
    }

    public String getDryck_typ() {
        return dryck_typ;
    }

    public void setDryck_typ(String dryck_typ) {
        this.dryck_typ = dryck_typ;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getDryck_kvalitet() {
        return dryck_kvalitet;
    }

    public void setDryck_kvalitet(String dryck_kvalitet) {
        this.dryck_kvalitet = dryck_kvalitet;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public void setBeskrivning(String beskrivning) {
        this.beskrivning = beskrivning;
    }

    public Integer getSotma() {
        return sotma;
    }

    public void setSotma(Integer sotma) {
        this.sotma = sotma;
    }

    public Integer getFyllighet() {
        return fyllighet;
    }

    public void setFyllighet(Integer fyllighet) {
        this.fyllighet = fyllighet;
    }

    public Integer getStravhet() {
        return stravhet;
    }

    public void setStravhet(Integer stravhet) {
        this.stravhet = stravhet;
    }

    public Integer getFruktstyrka() {
        return fruktstyrka;
    }

    public void setFruktstyrka(Integer fruktstyrka) {
        this.fruktstyrka = fruktstyrka;
    }

    public String getProducent() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    public String getArtal(){ return artal; }

//    public String getArtalString() {
//        Calendar theDate = new GregorianCalendar();
//        theDate.setTime(artal);
//
//        return String.valueOf(theDate.get(Calendar.YEAR));
//    }

      public void setArtal(String datum) {
        this.artal = datum;
      }
//    public void setArtal(String artal) {
//        DateFormat format = new SimpleDateFormat("yyyy");
//        try {
//            this.artal = format.parse(artal);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    public String getSista_dag() { return sista_dag; }

//    public String getSista_dagString() {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar theDate = new GregorianCalendar();
//        theDate.setTime(sista_dag);
//
//        return format.format(theDate.getTime());
//    }

    public void setSista_dag(String datum) {
        this.sista_dag = datum;
    }

//    public void setSista_dag(String sista_dag) {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            this.sista_dag = format.parse(sista_dag);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getDestrikt() {
        return destrikt;
    }

    public void setDestrikt(String destrikt) {
        this.destrikt = destrikt;
    }

    public Float getAlkoholhalt() {
        return alkoholhalt;
    }

    public void setAlkoholhalt(Float alkoholhalt) {
        this.alkoholhalt = alkoholhalt;
    }

    public String getDoft() {
        return doft;
    }

    public void setDoft(String doft) {
        this.doft = doft;
    }

    public String getFarg() {
        return farg;
    }

    public void setFarg(String farg) {
        this.farg = farg;
    }

    public Float getPris() {
        return pris;
    }

    public void setPris(Float pris) {
        this.pris = pris;
    }

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }

    public Integer getBetyg() {
        return betyg;
    }

    public void setBetyg(Integer betyg) {
        this.betyg = betyg;
    }

    public String getOmdomme() {
        return omdomme;
    }

    public void setOmdomme(String omdomme) {
        this.omdomme = omdomme;
    }

    public String getSystemet_id() {
        return systemet_id;
    }

    public void setSystemet_id(String systemet_id) {
        if (systemet_id != null)
            this.systemet_id = systemet_id;
        else this.systemet_id = "";
    }

    public String getSystemet_link() {
        return systemet_link;
    }

    public void setSystemet_link(String systemet_link) {
        if(systemet_link != null)
            this.systemet_link = systemet_link;
        else this.systemet_link = "";
    }

    public List getPassar_mattyp() {
        return passar_mattyp;
    }

    public void setPassar_mattyp(List<String> passar_mattyp) {
        this.passar_mattyp = passar_mattyp;
    }

    public List getPassar_matratt() {
        return passar_matratt;
    }

    public void setPassar_matratt(List<Matratt> passar_matratt) {
        this.passar_matratt = passar_matratt;
    }

    public List getDruvor() {
        return druvor;
    }

    public void setDruvor(List<String> druvor) {
        this.druvor = druvor;
    }

    @Override
    public String toString() {
        return "Dryck{" +
                "dryck_id=" + dryck_id +
                ", dryck_typ='" + dryck_typ + '\'' +
                ", namn='" + namn + '\'' +
                ", dryck_kvalitet='" + dryck_kvalitet + '\'' +
                ", beskrivning='" + beskrivning + '\'' +
                ", sotma=" + sotma +
                ", fyllighet=" + fyllighet +
                ", stravhet=" + stravhet +
                ", fruktstyrka=" + fruktstyrka +
                ", producent='" + producent + '\'' +
                ", artal=" + this.getArtal() +
                ", sista_dag=" + this.getSista_dag() +
                ", land='" + land + '\'' +
                ", destrikt='" + destrikt + '\'' +
                ", alkoholhalt=" + alkoholhalt +
                ", doft='" + doft + '\'' +
                ", farg='" + farg + '\'' +
                ", pris=" + pris +
                ", bild='" + bild + '\'' +
                ", betyg=" + betyg +
                ", omdomme='" + omdomme + '\'' +
                ", systemet_id='" + systemet_id + '\'' +
                ", systemet_link='" + systemet_link + '\'' +
                ", passar_mattyp=" + passar_mattyp +
                ", passar_matratt=" + passar_matratt +
                ", druvor=" + druvor +
                '}';
    }
}

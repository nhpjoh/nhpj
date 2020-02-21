package se.ehm.testdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lakemedel {

    @JsonProperty("NPLID")
    private String  NPLID;
    @JsonProperty("NPLPACKAGEID")
    private String  NPLPACKAGEID;
    @JsonProperty("VARUNR")
    private String  VARUNR;
    @JsonProperty("ANTAL_NUMERISKT")
    private Float ANTAL_NUMERISKT;
    @JsonProperty("ANTAL_KLARTEXT")
    private String  ANTAL_KLARTEXT;
    @JsonProperty("PRODUKTNAMN")
    private String  PRODUKTNAMN;
    @JsonProperty("BENEMNING")
    private String  BENEMNING;
    @JsonProperty("PRIS")
    private String  PRIS;

    private String antalUttag = null;
    private String NumberOfPackages = null;

    public String getNPLID() { return NPLID; }

    public void setNPLID(String NPLID) { this.NPLID = NPLID; }

    public String getNPLPACKID() { return NPLPACKAGEID; }

    public void setNPLPACKID(String NPLPACKID) { this.NPLPACKAGEID = NPLPACKID; }

    public String getVARUNR() { return VARUNR; }

    public void setVARUNR(String VARUNR) { this.VARUNR = VARUNR; }

    public String getNPLPACKAGEID() { return NPLPACKAGEID; }

    public void setNPLPACKAGEID(String NPLPACKAGEID) { this.NPLPACKAGEID = NPLPACKAGEID; }

    public Float getANTAL_NUMERISKT() { return ANTAL_NUMERISKT; }

    public void setANTAL_NUMERISKT(Float ANTAL_NUMERISKT) { this.ANTAL_NUMERISKT = ANTAL_NUMERISKT; }

    public String getANTAL_KLARTEXT() { return ANTAL_KLARTEXT; }

    public void setANTAL_KLARTEXT(String ANTAL_KLARTEXT) { this.ANTAL_KLARTEXT = ANTAL_KLARTEXT; }

    public String getPRODUKTNAMN() { return PRODUKTNAMN; }

    public void setPRODUKTNAMN(String PRODUKTNAMN) { this.PRODUKTNAMN = PRODUKTNAMN; }

    public String getBENEMNING() { return BENEMNING; }

    public void setBENEMNING(String BENEMNING) { this.BENEMNING = BENEMNING; }

    public String getPRIS() { return PRIS; }

    public void setPRIS(String PRIS) { this.PRIS = PRIS; }

    @Override
    public String toString() {
        return  "{ \"Läkemedel\" : [ { \"NPLID\" : \"" + NPLID + "\" , \"NPLPACKAGEID\" : \"" + NPLPACKAGEID + "\" , \"VARUNR\" : \"" + VARUNR +
                "\" , \"ANTAL_NUMERISKT\" : " + ANTAL_NUMERISKT + ", \"ANTAL_KLARTEXT\" : \"" + ANTAL_KLARTEXT +
                "\" , \"PRODUKTNAMN\" : \"" + PRODUKTNAMN + "\", \"BENEMNING\" : \"" + BENEMNING +
                "\" , \"PRIS\" : \"" + PRIS +
                "\" , \"antalUttag\" : \"" + antalUttag + "\" , \"NumberOfPackages\" : \"" + NumberOfPackages + "\" } ] }";
    }

    public String getAntalUttag() { return antalUttag; }

    public void setAntalUttag(String antalUttag) { this.antalUttag = antalUttag; }

    public String getNumberOfPackages() { return NumberOfPackages; }

    public void setNumberOfPackages(String numberOfPackages) { NumberOfPackages = numberOfPackages; }

}

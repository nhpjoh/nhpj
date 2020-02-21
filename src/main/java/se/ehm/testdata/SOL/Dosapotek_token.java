package se.ehm.testdata.SOL;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Dosapotek_token {

    @JsonProperty("ID")
    private String ID;
    @JsonProperty("DOSAKTOR")
    private String DOSAKTOR;
    @JsonProperty("DOSAPOTEK")
    private String DOSAPOTEK;
    @JsonProperty("DOSAPOTEKID")
    private String DOSAPOTEKID;
    @JsonProperty("AKTORID")
    private String AKTORID;
    @JsonProperty("MAXANTALHAMTABESTALLNINGAR")
    private String MAXANTALHAMTABESTALLNINGAR;

    @Override
    public String toString() {
        return "ID: " + ID + ", DOSAKTOR: " + DOSAKTOR + ", DOSAPOTEK: " + DOSAPOTEK +  ", DOSAPOTEKID: " + DOSAPOTEKID + ", AKTORID: " + AKTORID + ", MAXANTALHAMTABESTALLNINGAR: " + MAXANTALHAMTABESTALLNINGAR;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDOSAKTOR() {
        return DOSAKTOR;
    }

    public void setDOSAKTOR(String DOSAKTOR) {
        this.DOSAKTOR = DOSAKTOR;
    }

    public String getDOSAPOTEK() {
        return DOSAPOTEK;
    }

    public void setDOSAPOTEK(String DOSAPOTEK) {
        this.DOSAPOTEK = DOSAPOTEK;
    }

    public String getDOSAPOTEKID() {
        return DOSAPOTEKID;
    }

    public void setDOSAPOTEKID(String DOSAPOTEKID) {
        this.DOSAPOTEKID = DOSAPOTEKID;
    }

    public String getAKTORID() {
        return AKTORID;
    }

    public void setAKTORID(String AKTORID) {
        this.AKTORID = AKTORID;
    }

    public String getMAXANTALHAMTABESTALLNINGAR() {
        return MAXANTALHAMTABESTALLNINGAR;
    }

    public void setMAXANTALHAMTABESTALLNINGAR(String MAXANTALHAMTABESTALLNINGAR) {
        this.MAXANTALHAMTABESTALLNINGAR = MAXANTALHAMTABESTALLNINGAR;
    }
}


package se.nhpj.testdata;

/**
 * Denna klass innehåller metoder för att arbeta med ett Bolag:s objekt
 * @author nhpj
 */

public class Bolag {

    private Integer id;
    private String orgNr;
    private String namn;
    private Adress adress;

    public Bolag() {};

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgNr() {
        return orgNr;
    }

    public void setOrgNr(String orgNr) {
        this.orgNr = orgNr;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Bolag{" +
                "id=" + id +
                ", orgNr='" + orgNr + '\'' +
                ", namn='" + namn + '\'' +
                '}';
    }


}

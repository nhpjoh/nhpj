package se.ehm.testdata;

public class Farmaceut {

    private String legitimationskod;
    private String apotekare_fornamn;
    private String apotekare_efternamn;


    public Farmaceut() {};
    public Farmaceut(String forskrivarkod, String fornamn, String efternamn) {
        setLegitimationskod(forskrivarkod);
        setFornamn(fornamn);
        setEfternamn(efternamn);
    }

    public String getLegitimationskod() {
        return legitimationskod;
    }

    public void setLegitimationskod(String legitimationskod) {
        this.legitimationskod = legitimationskod;
    }

    public String getFornamn() {
        return apotekare_fornamn;
    }

    public void setFornamn(String apotekare_fornamn) {
        this.apotekare_fornamn = apotekare_fornamn;
    }

    public String getEfternamn() {
        return apotekare_efternamn;
    }

    public void setEfternamn(String apotekare_efternamn) {
        this.apotekare_efternamn = apotekare_efternamn;
    }

    @Override
    public String toString() {
        return "{ \"legitimationskod\" : \"" + legitimationskod + "\" , \"apotekare_fornamn\" : \"" + apotekare_fornamn +
                "\" , \"fapotekare_efternamn\" : \"" + apotekare_efternamn + "\" }";
    }

}

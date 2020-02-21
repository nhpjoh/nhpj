package se.ehm.testdata;

/**
 * Denna klass kan
 *
 */
public class Forskrivare {

   private String forskrivarkod;
   private String forskrivare_fornamn;
   private String forskrivare_efternamn;

    public Forskrivare() {};
    public Forskrivare(String forskrivarkod, String fornamn, String efternamn) {
        setForskrivarkod(forskrivarkod);
        setFornamn(fornamn);
        setEfternamn(efternamn);
    }

    public String getForskrivarkod() {
        return forskrivarkod;
    }

    public void setForskrivarkod(String forskrivarkod) {
        this.forskrivarkod = forskrivarkod;
    }

    public String getFornamn() {
        return forskrivare_fornamn;
    }

    public void setFornamn(String forskrivare_fornamn) {
        this.forskrivare_fornamn = forskrivare_fornamn;
    }

    public String getEfternamn() {
        return forskrivare_efternamn;
    }

    public void setEfternamn(String forskrivare_efternamn) {
        this.forskrivare_efternamn = forskrivare_efternamn;
    }

    @Override
    public String toString() {
        return "{ \"forskrivarkod\" : \"" + forskrivarkod + "\" , \"forskrivare_fornamn\" : \"" + forskrivare_fornamn +
               "\" , \"forskrivare_efternamn\" : \"" + forskrivare_efternamn + "\" }";
    }
}

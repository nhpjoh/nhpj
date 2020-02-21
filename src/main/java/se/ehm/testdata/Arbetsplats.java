package se.ehm.testdata;

public class Arbetsplats {
    private String kod;
    private String namn;
    private String postAdress;
    private String postOrt;
    private String postNummer;
    private String telefon1;
    private String Text;        // Text från ARKO_PROD.ARBETSPLATS.text;
    private String omrodesKod;  // från ARKO_PROD.ARBETSPLATS.VERKSAMHETSOMRADEKOD

    public String getKod() { return kod; }

    public void setKod(String kod) { this.kod = kod; }

    public String getNamn() { return namn; }

    public void setNamn(String namn) { this.namn = namn; }

    public String getPostAdress() { return postAdress; }

    public void setPostAdress(String postAdress) { this.postAdress = postAdress; }

    public String getPostOrt() { return postOrt; }

    public void setPostOrt(String postOrt) { this.postOrt = postOrt; }

    public String getPostNummer() { return postNummer; }

    public void setPostNummer(String postNummer) { this.postNummer = postNummer; }

    public String getTelefon1() { return telefon1; }

    public void setTelefon1(String telefon1) { this.telefon1 = telefon1; }

    public String getText() { return Text; }

    public void setText(String text) { Text = text; }

    public String getOmrodesKod() { return omrodesKod; }

    public void setOmrodesKod(String omrodesKod) { this.omrodesKod = omrodesKod; }

    @Override
    public String toString() {
        return "{ \"arbetsplats\" : \"" + kod + "\" , \"namn\" : \"" + namn + "\" , \"postadress\" : \"" + postAdress + "\" , \"postort\" : \"" + postOrt +
               "\" , \"postnummer\" : \"" + postNummer + "\", \"telefon1\" : \"" + telefon1 + "\" , \"text\" : \"" + Text + "\" , \"omrodeskod\" : \"" + omrodesKod + "\" }";
    }

}

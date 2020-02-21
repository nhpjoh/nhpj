package se.nhpj.testdata;

public class TestPerson extends Person {
    String ePost;
    String tel;
    String mobil;

    public String getePost() {
        return ePost;
    }

    public void setePost(String ePost) {
        this.ePost = ePost;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    @Override
    public String toString() {
        return "TestPerson{" +
                super.toString() +
                " Tel='"  + tel   + "\' " +
                "Mobil='" + mobil + "\' " +
                "ePost='" + ePost + "\' " +
                '}';
    }

    public String toJson() {

        String jsonString = "{ \"testperson\" : { \"id\" : \"" + this.getId() + "\" , \"personnummer\" : \""+this.getPersonnummer()+"\" , " +
                            "\"fornamn\" : \"" + this.getFornamn() + "\" , \"mellannamn\" : \"" + this.getMellannamn() +
                            "\", \"efternamn\" : \"" + this.getEfternamn() + "\" , " +
                            "\"adress\" : { \"gata\" : \"" +this.getAdress().getGata()+ "\" , " +
                            "\"gatunummer\" : \"" +this.getAdress().getGatunummer()+ "\" , " +
                            "\"postnummer\" : \"" +this.getAdress().getPostnummer()+ "\" , " +
                            "\"ort\" : \""+this.getAdress().getOrt()+"\" , " +
                            "\"lkf-kod\" : \"" +this.getAdress().getLKF_kod()+ "\" , " +
                            "\"lan\" : \"" +this.getAdress().getLän()+ "\" , " +
                            "\"kommun\" : \"" +this.getAdress().getKommun()+ "\" , " +
                            "\"gatuadress\" : \""+this.getAdress().getGatuAdress()+"\" , " +
                            "\"land\" : \"" +this.getAdress().getLand() + "\" } , " +
                            "\"tel\" : \"" +this.getTel()+ "\" , \"mobil\" : \"" +this.getMobil()+ "\" , " +
                            "\"epost\" : \"" +this.getePost()+ "\" }}";

        return jsonString;
    }
}

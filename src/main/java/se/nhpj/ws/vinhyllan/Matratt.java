package se.nhpj.ws.vinhyllan;

public class Matratt {
    private String namn;
    private String kalla;
    private String recept;

    public Matratt(String namn, String kalla, String recept) {
        setNamn(namn);
        setKalla(kalla);
        setRecept(recept);
    }
    public Matratt(){}

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getKalla() {
        return kalla;
    }

    public void setKalla(String kalla) {
        this.kalla = kalla;
    }

    public String getRecept() {
        return recept;
    }

    public void setRecept(String recept) {
        this.recept = recept;
    }

    @Override
    public String toString() {
        return "Matratt{" +
                "namn='" + namn + '\'' +
                ", kalla='" + kalla + '\'' +
                ", recept='" + recept + '\'' +
                '}';
    }
}

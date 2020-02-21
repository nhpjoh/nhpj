package se.ehm.testdata;

public class Apotek {
    private String GLN;
    private String namn;
    private String kortNamn;
    private String orgId;

    public String getGLN() { return GLN; }

    public void setGLN(String GLN) { this.GLN = GLN; }

    public String getNamn() { return namn; }

    public void setNamn(String namn) { this.namn = namn; }

    public String getKortNamn() { return kortNamn; }

    public void setKortNamn(String kortNamn) { this.kortNamn = kortNamn; }

    public String getOrgId() { return orgId; }

    public void setOrgId(String orgId) { this.orgId = orgId; }

    @Override
    public String toString() {
        return " { Apotek-GLN : " + GLN + " , Namn : " + namn + " , Kortnamn : " + kortNamn + " , OrgId : " + orgId +" }";
    }

}

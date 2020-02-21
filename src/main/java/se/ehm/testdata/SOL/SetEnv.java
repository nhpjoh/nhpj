package se.ehm.testdata.SOL;

public class SetEnv {

    private String SUT                  = "test10";
    private String endpoint             = "https://" + SUT;
    private String db_ServiceName_FOTA  = "PTFOTA";
    private String db_ServiceName_EES   = "PTEES";
    private String db_ServiceName_GD    = "PTGD";
    private String db_ServiceName_LOG   = "PTLOG";
    private String db_ServiceName_RR    = "PTRR";
    private String db_hostname          = "td02-scan.systest.receptpartner.se:";
    private String db_port              = "1521";
    private String db_user              = "ETCDBA";
    private String db_password          = "ETCDBA";
    private String servicename;
    private String db_ConnectString     = "jdbc:oracle:thin:@" + this.db_hostname + this.db_port + "/";


    public SetEnv() {
    }

    public String getConnectString(String ServiceName) {
        this.servicename = ServiceName;
        return this.db_ConnectString + this.servicename;
    }
    public String getDb_user() {
        return this.db_user;
    }
    public String getDb_password() {
        return this.db_password;
    }
    public String getSUT() {
        return this.SUT;
    }

}

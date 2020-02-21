package labb.thread;

import se.nhpj.LoadRunner.lr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyThread extends Thread {
//    public void run(){
//        Data d = new Data();
//        for ( int i=0 ; i < 100 ; i++ ) {
//            d.setiData(1);
//            Sleep.sleep(se.nhpj.testdata.RndData.getNumber(1,200));
//        }
//    }

    public void run() {

            String text = "";
            Integer varde = 0;

            PreparedStatement statement = null;
            Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/PTRR", "ETCDBA", "ETCDBA");

//          String sql = "INSERT /* + APPEND */ INTO RR_PROD.SLASK VALUES('SLASK ' || RR_PROD.slask_seq.nextval ,RR_PROD.slask_seq.nextval)";
            String sql = "DECLARE\n" +
                        "BEGIN \n" +
                        "    <<loop>>\n" +
                        "    FOR i IN 1..5000 LOOP\n" +
                        "       INSERT /* + APPEND */ INTO RR_PROD.SLASK \n" +
                        "       VALUES('SLASK ' || RR_PROD.slask_seq.nextval ,RR_PROD.slask_seq.nextval); \n" +
                        "    END LOOP loop;\n" +
                        "    dbms_output.put_line('Klar!');\n" +
                        "END";

            // String sql = "INSERT INTO RR_PROD.SLASK VALUES(?,?)";
            // String sql = "INSERT /* + APPEND */ INTO RR_PROD.SLASK VALUES(?,?)";

            try {
                con.setAutoCommit(false);
                statement = con.prepareStatement(sql);

                for ( int i=0 ; i < 10 ; i++ ) {
//                    text = se.nhpj.testdata.RndData.getChars(50);
//                    varde = se.nhpj.testdata.RndData.getNumber(1, 999999999);
//                    statement.setString(1, text);
//                    statement.setInt(2, varde);

    //                lr.start_transaction("Insert");
                        statement.execute();
                        con.commit();
    //                lr.end_transaction("Insert",lr.AUTO);
                    Sleep.sleep(5);
                }
                statement.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }


}

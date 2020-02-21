package labb.thread;

import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test_MyThread {

    @Test
    public void test() {
        Thread thread1 = new Thread(){
            public void run(){
                System.out.println("Thread1 Running");
                Data d = new Data();
                d.setiData(7);
            }
        };

        Thread thread2 = new Thread(){
            public void run(){
                System.out.println("Thread2 Running");
                Data d = new Data();
                d.setiData(7);
            }
        };

        thread1.start();
        thread2.start();

        Sleep.sleep(1000);

        Data d1 = new Data();

        System.out.println(d1.getiData());
    }

    @Test
    public void test2() {

        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();


        t1.start();
        t2.start();

        Data d1 = new Data();

        for (int i = 0; i < 25; i++) {
            Sleep.sleep(500);
           System.out.println(d1.getiData());
        }

    }

    @Test
    public void test3() {
        List<MyThread> tradar = new ArrayList<>();
        for ( int i = 0 ; i < 10 ; i++) {
            MyThread myThread = new MyThread();
            myThread.start();
            System.out.println("Tråd " +(i+1)+ " Startad!");
            tradar.add(myThread);
        }

        for (MyThread myThread : tradar) {
            try {
                myThread.join();
                System.out.println("Tråd klar");
            } catch (InterruptedException e) {
                System.out.println("inteupted");
            }
        }
    }

    @Test
    public void test4() {
        String text = "";
        Integer varde = 0;

        PreparedStatement statement = null;
        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/PTRR", "ETCDBA", "ETCDBA");

//        String sql = "INSERT INTO RR_PROD.SLASK VALUES(?,?)";
//            String sql = "INSERT /* + APPEND */ INTO RR_PROD.SLASK VALUES(?,?)";
        String sql = "BEGIN FOR i IN 1..1000 LOOP INSERT INTO RR_PROD.SLASK VALUES('SLASK ' || RR_PROD.slask_seq.nextval ,RR_PROD.slask_seq.nextval); END LOOP; END;";

        try {
            con.setAutoCommit(false);
            statement = con.prepareStatement(sql);

            for ( int i=0 ; i < 1 ; i++ ) {
//                text = se.nhpj.testdata.RndData.getChars(50);
//                varde = se.nhpj.testdata.RndData.getNumber(1, 999999999);
//                statement.setString(1, text);
//                statement.setInt(2, varde);

                //                lr.start_transaction("Insert");
                statement.execute();
                con.commit();
                //                lr.end_transaction("Insert",lr.AUTO);
            }
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

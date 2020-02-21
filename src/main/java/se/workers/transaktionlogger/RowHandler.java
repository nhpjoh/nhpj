package se.workers.transaktionlogger;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RowHandler {

    // ToDo append row to Transactionlog File
    public void appendRow(String filnamn, Row row) {
        //Write JSON file
        ObjectMapper mapper = new ObjectMapper();

        try (FileWriter file = new FileWriter(filnamn,true)) {

            file.write(mapper.writer().writeValueAsString(row) + "\n");
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ToDo append row to Transactionlog
    // Database

    // List

    // etc.

    // Todo Read from transaactionlog file
    public List<Row> readLog(String filnamn)  {
        // Read JSON from file
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Row> list = new ArrayList<>();

        File file = new File(filnamn);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine())
            try {
                String line = sc.nextLine();
                Row row = mapper.readValue(line,Row.class);
                list.add(row);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return list;
    }

}

package se.workers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import se.workers.transaktionlogger.Row;
import se.workers.transaktionlogger.RowHandler;

import java.util.Iterator;
import java.util.List;

public class RowHandlerTest {

    @Test
    public void appendRow() {
        Row row = new Row("appendRow","0.343");
        RowHandler rowHandler = new RowHandler();
        rowHandler.appendRow("workerLogger.json",row);
    }

    @Test
    public void readLog() {
        RowHandler rowHandler = new RowHandler();
        ObjectMapper mapper = new ObjectMapper();

        List list = rowHandler.readLog("workerLogger.json");
        Iterator<Row> itr = list.iterator();

        while (itr.hasNext()) {
            Row row = itr.next();
            try {
                System.out.println(mapper.writer().writeValueAsString(row));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
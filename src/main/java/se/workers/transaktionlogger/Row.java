package se.workers.transaktionlogger;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Row {
    @JsonProperty("transactionName")
    private String transactionName;
    @JsonProperty("transactionTime")
    private String transactionTime;
    @JsonProperty("time")
    private String time;

    public Row(){
        // Add creation time to row
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.setTime(sdf.format(cal.getTime()));
    }

    public Row(String transactionName, String transactionTime) {
        this.setTransactionName(transactionName);
        this.setTransactionTime(transactionTime);
        // Add creation time to row
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.setTime(sdf.format(cal.getTime()));
    }

    public String getTransactionName() { return transactionName; }
    public void setTransactionName(String transactionName) {this.transactionName = transactionName; }
    public String getTransactionTime() { return transactionTime; }
    public void setTransactionTime(String transactionTime) { this.transactionTime = transactionTime; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}

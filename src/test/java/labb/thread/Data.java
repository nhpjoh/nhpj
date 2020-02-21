package labb.thread;

public class Data {

    private static String sData = "";
    private static Integer iData = 0;

    public String getsData() { return sData; }

    public void setsData(String data) {
        StringBuilder sb = new StringBuilder();
        sb.append(sData);
        sb.append(data);
        sData = sb.toString();
    }

    public static Integer getiData() { return iData; }

    public static void setiData(Integer data) {
        iData = iData + data;
    }
}

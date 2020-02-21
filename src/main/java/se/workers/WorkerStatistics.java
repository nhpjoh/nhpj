package se.workers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class WorkerStatistics {

    public String medel(List<Float> list) {
        AtomicReference<Float> summa= new AtomicReference<>(0.0f);
        list.forEach((Float f) -> summa.updateAndGet(v -> v + f));
        Float retVal = summa.get()/list.size();
        return String.format("%.3f",retVal).replace(',','.');
    }
    public String min(List<Float> list) {
        Float retVal = Collections.min(list);
        return String.format("%.3f",retVal).replace(',','.');
    }

    public String max(List<Float> list) {
        Float retVal = Collections.max(list);
        return String.format("%.3f",retVal).replace(',','.');
    }

    public String percentile(Integer percentil, List<Float> list) {
        list.sort(Comparator.naturalOrder());
        int antal = list.size();
        float pos = antal * percentil/100;
        Float retVal = Float.valueOf( list.get(Math.round(pos)).toString() );
        return String.format("%.3f",retVal).replace(',','.');
    }


//    @Test
//    public void run() {
//        ArrayList listan = new ArrayList<>(
//                Arrays.asList(5.4f, 2.3f, 1.7f, 8.4f, 9.3f, 14.9f,5.1f, 7.4f, 6.1f, 3.7f, 11.4f )
//        );
//
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.println("|\tTransaktionsnamn\t|\tMinimum\t|\tMedel\t|\tMaximum\t|\t90 % \t|");
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.println("|\tWorker          \t|\t" + min(listan) + "\t|\t" + medel(listan) + "\t|\t" + max(listan) + "\t|\t"+ percentile(90,listan) + "\t|");
//        System.out.println("-------------------------------------------------------------------------");
//    }

}

package se.ehm.testdata.FORS;

import se.nhpj.testdata.Skatteverket;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.RndData;

import java.util.Iterator;
import java.util.List;

public class GenerateCSV {
    /**
     * Denna metod genererar ett antal rader till en csv infil för att läsas in via batch
     * @param from start Förskrivar kod, Denna räknas upp med 1 för varje ny rad upp till 'antal' rader
     * @param antal antal som ska genereras
     * @param kategori (AP eller RC)
     */
    public void Farmaceut(Integer from, Integer antal, String kategori) {
        Skatteverket skatteverket = new Skatteverket();
        String[] listOfUtbkoder=null;
        if(kategori.contains("AP"))       { String[] AP = {"66400", "66401", "66488"}; listOfUtbkoder=AP;}		// det finns 3 stycken olika utbildningskoder för AP
        else if (kategori.contains("RC")) { String[] RC = {"655", "65500", "65588"};   listOfUtbkoder=RC;}		// det finns 3 stycken olika utbildningskoder för AP

        List personer = skatteverket.getPersons();
        Iterator<Person> itr = personer.iterator();
        Integer kod = from;

        while(itr.hasNext()) {
            Person person = itr.next();
            String pnr = person.getPersonnummer();
            String fodDatum = pnr.substring(0, 4) + "-" + pnr.substring(4, 6) + "-" + pnr.substring(6, 8);
            String eNamn = person.getEfternamn();
            String fNamn = person.getFornamn();
            String utbkod = RndData.rndFrom(listOfUtbkoder);


            // make new line in csv file
            String newCsvLine = (kod++) + ";" +
            pnr + ";" +
            fodDatum + ";" +
            eNamn + ";" +
            fNamn + ";" +
            kategori + ";" +
            utbkod + ";;;0";
            System.out.println(newCsvLine);
            if (antal-- == 0 ) {break;}
        }
    }
}

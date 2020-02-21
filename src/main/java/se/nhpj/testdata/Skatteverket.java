/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nhpj.testdata;

import java.io.InputStream;
import java.util.*;

/**
 * Denna klass innehåller ett antal hjälpmetoder för skatteverkets lista med tilllåtna personnummer
 * @author nhpj
 */
public class Skatteverket {

    /**
     *  Denna metod returnerar en slumpad person (Person) från skatteverkets lista med tillåtna personnummer.
     *  @return se.nhpj.testdata.Person
     */
    public Person getSlumpadPerson() {
        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/Skatteverket.dat");
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
        return parsaRad(result); // result;      
    }
    
    /**
     *  Denna metod returnerar en slumpad person (Person) från skatteverkets lista med tillåtna personnummer med fördelse år mellan <b>min</b> och <b>max</b>.
     *  @param minÅr - Från och med år
     *  @param maxÅr - Till och med år
     *  @return se.nhpj.testdata.Person
     */
    public Person getSlumpadPerson(Integer minÅr, Integer maxÅr) {
        Person person;
        Integer år;
        person = getSlumpadPerson();
        år = Integer.parseInt(person.getPersonnummer().substring(0, 4));
        while (år <= minÅr || år >= maxÅr) { 
            person = getSlumpadPerson();
            år = Integer.parseInt(person.getPersonnummer().substring(0, 4));
        }  
        return person; 
    }
    /**
     * Denna metod används bara lokalt för att parsa en rad från skatteverket.dat till en se.nhpj.testdata.Person
     * @param rad - en rad från filen 
     * @return se.nhpj.testdata.Person
     */
    private Person parsaRad( String rad ) {
        Person person = new Person();
        String[] s = rad.split("\t");
        person.setPersonnummer(s[0]);
        person.setFornamn(s[1]);
        person.setEfternamn(s[2]);
        Adress adress = new Adress();
        adress.setGatuAdress(s[3]);
        adress.setPostnummer(s[4]);
        adress.setOrt(s[5]);
        adress.setLand("Sverige");
        person.setAdress(adress);
        return person;
    }
    
    /**
     *  Denna metod returnerar en lista med alla personer (Person) från skatteverkets lista med tillåtna personnummer.
     *  @return List&lt;se.nhpj.testdata.Person&gt;
     */
    public List<Person> getPersons() {
        String result = null;
        ArrayList<Person> lista = new ArrayList<>();
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/Skatteverket.dat");
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            String line = sc.nextLine();
            lista.add(parsaRad(line));
        }
        return lista;
    }

    public Person getPerson(String personnummer) {
        Person person = null;
        ArrayList<Person> lista = (ArrayList) getPersons();

        Iterator itr = lista.iterator();
        while(itr.hasNext()) {
            Person p = (Person) itr.next();
            if (p.getPersonnummer().contains(personnummer))
                return p;
        }
        return person;
    }
}

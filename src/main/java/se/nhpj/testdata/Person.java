/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nhpj.testdata;

/**
 * Denna klass innehåller metoder för att arbeta med ett Person:s objekt
 * @author nhpj
 */
public class Person {

    private String id;
    private String personnummer;
    private String fornamn;
    private String mellannamn;
    private String efternamn;
    private Adress adress = new Adress();

    /**
     * Konstruktor
     */
    public Person() {}

    public String  getId() { return this.id; }
    public void    setId( String id) { this.id = id; }
    public String  getPersonnummer() { return personnummer; }
    public void    setPersonnummer(String personnummer) { this.personnummer = personnummer; }
    public String  getFornamn() { return fornamn; }
    public void    setFornamn(String fornamn) { this.fornamn = fornamn; }
    public String  getMellannamn() { return mellannamn; }
    public void    setMellannamn(String mellannamn) { this.mellannamn = mellannamn; }
    public String  getEfternamn() { return efternamn; }
    public void    setEfternamn(String efternamn) { this.efternamn = efternamn; }

    public boolean isKvinna() { return Personnummer.toInt(personnummer, 11) % 2 == 0; }
    public boolean isMan() { return !isKvinna(); }

    public static boolean isKvinna(String pnr) { return Personnummer.toInt(pnr, 11) % 2 == 0; }
    public static boolean isMan(String pnr) { return !isKvinna(pnr); }

    public void setAdress(Adress adr) { this.adress = adr; }
    public Adress getAdress() { return adress; }

    public String getFodelseDatum() { return personnummer.substring(0,8); }


    @Override
    public String toString() {
        return "{ \"person\" : {" +
                " \"id\" : \"" + id + '\"' +
                ", \"personnummer\" : \"" + personnummer + '\"' +
                ", \"fornamn\" : \"" + fornamn + '\"' +
                ", \"mellannamn\" : \"" + mellannamn + '\"' +
                ", \"efternamn\" : \"" + efternamn + '\"' +
                ", " + adress +
                " } }";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nhpj.testdata;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * Denna klass representerar en adress
 * @author nhpj
 */
public class Adress {
    private String Gata;
    private String Gatunummer;
    private String Postnummer;
    private String Ort;
    private String LKF_kod;
    private String Län;
    private String Kommun;
    private String frånNummer;
    private String tillNummer;
    private String GatuAdress;
    private String land;

    /**
     * Konstruktor
     */
    public Adress() {}
    /**
     * Denna Konstruktor tar en adressrad som inparamater
     * @param adressRad - Rad från filen svenska_adresser.dat
     */
    private Adress( String adressRad ) {
        String[] s = adressRad.split("\t");
        Gata = s[0];
        frånNummer = s[1];
        tillNummer = s[2];
        Postnummer = s[3];
        Ort = s[4];
        LKF_kod = s[5];
        Län = s[6];
        Kommun = s[7];
        if (s[1].equalsIgnoreCase(" ")) { s[1] = "1"; }
        if (s[2].equalsIgnoreCase(" ")) { s[2] = "1"; }
        Gatunummer = RndData.getANumber(Integer.parseInt(s[1]), Integer.parseInt(s[2]));
        GatuAdress =  Gata + " " + Gatunummer;

}
    public String getGata() { return Gata; }
    public void   setGata(String Gata) { this.Gata = Gata; }
    public String getGatunummer() { return Gatunummer; }
    public void   setGatunummer(String Gatunummer) { this.Gatunummer = Gatunummer; }
    public String getPostnummer() { return Postnummer; }
    public void   setPostnummer(String Postnummer) { this.Postnummer = Postnummer; }
    public String getOrt() { return Ort; }
    public void   setOrt(String Ort) { this.Ort = Ort; }
    public String getLKF_kod() { return LKF_kod; }
    public void   setLKF_kod(String LKF_kod) { this.LKF_kod = LKF_kod; }
    public String getLän() { return Län; }
    public void   setLän(String Län) { this.Län = Län; }
    public String getKommun() { return Kommun; }
    public void   setKommun(String Kommun) { this.Kommun = Kommun; }
    public Integer getFrånNummer() { return Integer.parseInt(frånNummer); }
    public void   setFrånNummer(String frånNummer) { this.frånNummer = frånNummer; }
    public Integer getTillNummer() { return Integer.parseInt(tillNummer); }
    public void   setTillNummer(String tillNummer) { this.tillNummer = tillNummer; }
    public String getGatuAdress() { return GatuAdress; }
    public void   setGatuAdress(String GatuAdress) { this.GatuAdress = GatuAdress; }
    public String getLand() { return land; }
    public void   setLand(String land) { this.land = land; }

    /**
     * Denna metod kommer att slumpa fram en adress från lista med riktiga adresser med postnummer och ort
     * @return Ett Adress objekt innehållande en slumpad adress
    */
    public Adress getSwedishAdress() {
        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/svenska_adresser.dat");
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
        Adress adr = new Adress(result); // result;      
        adr.setLand("Sverige");
        return adr;
    }

    /**
     * Denna metod kommer att slumpa fram en adress från lista med riktiga adresser med postnummer och ort
     * @return Ett Adress objekt innehållande en slumpad adress
     */
    public Adress getTestAdress() {
        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/test_adresser.dat");
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
        // Skakar fram data från svenska adresser och byter ut gatunamnet ....
        Adress adr = new Adress().getSwedishAdress(); // result;
        adr.setLand("Sverige");
        adr.setGatuAdress(result + " " + adr.getGatunummer());
        return adr;
    }

    /**
     * Denna metod formaterar en bra utskrift av innehållet i klassens objekt
     * @return - Formaterad text
     */
    @Override
    public String toString() {
        return "\"adress\" = {" +
                " \"gata\" : \"" + Gata + '\"' +
                ", \"gatunummer\" : \"" + Gatunummer + '\"' +
                ", \"postnummer\" : \"" + Postnummer + '\"' +
                ", \"ort\" : \"" + Ort + '\"' +
                ", \"LKF_kod\" : \"" + LKF_kod + '\"' +
                ", \"län\" : \"" + Län + '\"' +
                ", \"kommun\" : \"" + Kommun + '\"' +
//                ", frånNummer='" + frånNummer + '\'' +
//                ", tillNummer='" + tillNummer + '\'' +
                ", \"gatuadress\" : \"" + GatuAdress + '\"' +
                ", \"land\" : \"" + land + '\"' +
                '}';
    }
}

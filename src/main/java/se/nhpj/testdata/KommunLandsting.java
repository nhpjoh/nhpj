package se.nhpj.testdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KommunLandsting {

    @JsonProperty("LANSKOD")
    private String LANSKOD;
    @JsonProperty("LAN")
    private String LAN;
    @JsonProperty("LANDSTINGSKOD")
    private String LANDSTINGSKOD;
    @JsonProperty("LANDSTING")
    private String LANDSTING;
    @JsonProperty("KOMMUNKOD")
    private String KOMMUNKOD;
    @JsonProperty("KOMMUN")
    private String KOMMUN;


    /**
     *  Denna metod returnerar en slumpad KommunLandsting (KommunLandsting) från en lista med med många olika.
     *  @return se.nhpj.testdata.KommunLandsting
     */
    public KommunLandsting getSlumpad() {
        KommunLandsting kl = new KommunLandsting();
        String result = null;
        InputStream inStream = this.getClass().getResourceAsStream("/testdata/KommunLandsting.dat");
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(inStream); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
             kl = mapper.readValue(result, KommunLandsting.class);
        }catch (IOException ex) {
            Logger.getLogger(KommunLandsting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kl; // result;
    }

    public String asLKFKOD() { return this.LANSKOD+this.getKOMMUNKOD()+"00"; }

    @Override
    public String toString() {
        return "Länskod: " + getLANSKOD() + ", Län: " + getLAN() + ", Landstingskod: " + getLANDSTINGSKOD() + ", Landsting: " + getLANDSTING() + ", Kommunkod: " + getKOMMUNKOD() + ", Kommun: " + getKOMMUN();
    }


    public String getLANSKOD() { return LANSKOD; }
    public void setLANSKOD(String LANSKOD) { this.LANSKOD = LANSKOD; }
    public String getLAN() { return LAN; }
    public void setLAN(String LAN) { this.LAN = LAN; }
    public String getLANDSTINGSKOD() { return LANDSTINGSKOD; }
    public void setLANDSTINGSKOD(String LANDSTINGSKOD) { this.LANDSTINGSKOD = LANDSTINGSKOD; }
    public String getLANDSTING() { return LANDSTING; }
    public void setLANDSTING(String LANDSTING) { this.LANDSTING = LANDSTING; }
    public String getKOMMUNKOD() { return KOMMUNKOD; }
    public void setKOMMUNKOD(String KOMMUNKOD) { this.KOMMUNKOD = KOMMUNKOD; }
    public String getKOMMUN() { return KOMMUN; }
    public void setKOMMUN(String KOMMUN) { this.KOMMUN = KOMMUN; }
}

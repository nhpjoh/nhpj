package se.ehm.testdata;

import se.nhpj.database.DB_Access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForskrivareHandler {

    public static List<Forskrivare> getAllForskrivare(String ServiceName) {
        ArrayList<Forskrivare> lista = new ArrayList<Forskrivare>();

        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceName, "ETCDBA", "ETCDBA");

        String sql = "  select F.KOD || F.KOD_CHECK forskrivarkod, F.personnr, F.NAMN as FORS_namn, P.FORNAMN as folk_fornamn, P.EFTERNAMN as folk_efternamn " +
                "  from fors_prod.fors F, FORS_PROD.FORS_KATEGORI K, FOLK_PROD.SPAR_PERSON P " +
                "  where F.kod = K.kod " +
                "  and K.KATEGORI = 'LK' " +
                "  and K.FORSKR_RATT = 'J' " +
                "  and K.BEHORIG_BEGRANSNINGS_DATUM is null " +
                "  and F.SKYDDAD_ID = 'N' " +
                "  and F.KOD_CHECK is not null " +
                "  and F.PERSONNR = P.PERSONNR ";

            ResultSet result = se.nhpj.database.DB_Access.getResultSet(con,sql);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                Forskrivare forskrivare = new Forskrivare();
                forskrivare.setForskrivarkod(result.getString("forskrivarkod"));
                forskrivare.setFornamn(result.getString("folk_fornamn"));
                forskrivare.setEfternamn(result.getString("folk_efternamn"));
                lista.add(forskrivare);
            }
            // Stänger databaskopplingen
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }

        return lista;
    }

    public static Forskrivare getRndForskrivare(String ServiceName) {

        Forskrivare forskrivare = null;

        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceName, "ETCDBA", "ETCDBA");

        String sql = "select * from (" +
                "  select F.KOD || F.KOD_CHECK forskrivarkod, F.personnr, F.NAMN as FORS_namn, P.FORNAMN as folk_fornamn, P.EFTERNAMN as folk_efternamn " +
                "  from fors_prod.fors F, FORS_PROD.FORS_KATEGORI K, FOLK_PROD.SPAR_PERSON P " +
                "  where F.kod = K.kod " +
                "  and K.KATEGORI = 'LK' " +
                "  and K.FORSKR_RATT = 'J' " +
                "  and K.BEHORIG_BEGRANSNINGS_DATUM is null " +
                "  and F.SKYDDAD_ID = 'N' " +
                "  and F.KOD_CHECK is not null " +
                "  and F.KOD like '888%' " +
                "  and F.PERSONNR = P.PERSONNR " +
                "  ORDER BY sys.dbms_random.value desc " +
                ") where rownum < 2";

        ResultSet result = se.nhpj.database.DB_Access.getResultSet(con,sql);

        // Hämtar upp resultatet av din SQL
        try {
            while (result.next()) {
                // lägger till en rad i taget till listan
                forskrivare = new Forskrivare();
                forskrivare.setForskrivarkod(result.getString("forskrivarkod"));
                forskrivare.setFornamn(result.getString("folk_fornamn"));
                forskrivare.setEfternamn(result.getString("folk_efternamn"));
            }
            // Stänger databaskopplingen
            DB_Access.closeConnection(con);

        } catch (SQLException e) { // Krävs för att jobba med databaser :-)
            e.printStackTrace();
        }

        return forskrivare;
    }

    public static Forskrivare getSpecialSlumpadForskrivarKod() {
        ArrayList kod = new ArrayList();
        kod.add(new Forskrivare("8880007","Elizabeth","Fehrman"));
        kod.add(new Forskrivare("8880015","Per","Norman"));
        kod.add(new Forskrivare("8880023","Fatima","Gerhardsson"));
        kod.add(new Forskrivare("8880031","Viking","Lagerblad"));
        kod.add(new Forskrivare("8880049","Regina","Dahlberg"));
        kod.add(new Forskrivare("8880056","Sebastian","Person"));
        kod.add(new Forskrivare("8880064","Melinda","Friman"));
        kod.add(new Forskrivare("8880072","Ronny","Wellander"));
        kod.add(new Forskrivare("8880080","Ingrid","Malmsten"));
        kod.add(new Forskrivare("8880098","Peder","Jäderholm"));
        kod.add(new Forskrivare("8880106","Alyssa","Enqvist"));
        kod.add(new Forskrivare("8880114","Örjan","Lönnqvist"));
        kod.add(new Forskrivare("8880122","Maria","Farmarsson"));
        kod.add(new Forskrivare("8880130","Ture","Linderoth"));
        kod.add(new Forskrivare("8880148","Melissa","Amundson"));
        kod.add(new Forskrivare("8880155","Lorentz","Pontén"));
        kod.add(new Forskrivare("8880163","Teresia","Kumlien"));
        kod.add(new Forskrivare("8880171","Esaias","Lind"));
        kod.add(new Forskrivare("8880189","Nikita","Ekholm"));
        kod.add(new Forskrivare("8880197","Viking","Jarl"));
        kod.add(new Forskrivare("8880205","Veronika","Koch"));
        kod.add(new Forskrivare("8880213","Gotthard","Petterson"));
        kod.add(new Forskrivare("8880221","Dania","Bergson"));
        kod.add(new Forskrivare("8880239","Miguel","Wallander"));
        kod.add(new Forskrivare("8880247","Ophelia","Falk"));
        kod.add(new Forskrivare("8880254","Emil","Nordgren"));
        kod.add(new Forskrivare("8880262","Tindra","Granath"));
        kod.add(new Forskrivare("8880270","Leopold","Rönnegård"));
        kod.add(new Forskrivare("8880288","Veronika","Lundwall"));
        kod.add(new Forskrivare("8880296","Adolf","Abramsson"));
        kod.add(new Forskrivare("8880304","Gerda","Wester"));
        kod.add(new Forskrivare("8880312","Arne","Sundén"));
        kod.add(new Forskrivare("8880320","Elena","Berglin"));
        kod.add(new Forskrivare("8880338","Nikolaus","Mattsson"));
        kod.add(new Forskrivare("8880346","Evelin","Loberg"));
        kod.add(new Forskrivare("8880353","Maximus","Holm"));
        kod.add(new Forskrivare("8880361","Cassandra","Pedersen"));
        kod.add(new Forskrivare("8880379","Nicolai","Martinsson"));
        kod.add(new Forskrivare("8880387","Hilma","Adolfsson"));
        kod.add(new Forskrivare("8880395","Melker","Lundgren"));
        kod.add(new Forskrivare("8880403","Vendela","Nord"));
        kod.add(new Forskrivare("8880411","Hans","Klein"));
        kod.add(new Forskrivare("8880429","Yasmine","Söderbaum"));
        kod.add(new Forskrivare("8880437","Rudolf","Tholander"));
        kod.add(new Forskrivare("8880445","Noomi","Stockenberg"));
        kod.add(new Forskrivare("8880452","Gert","Henriques"));
        kod.add(new Forskrivare("8880460","Emma","Ekholm"));
        kod.add(new Forskrivare("8880478","Axel","Brandt"));
        kod.add(new Forskrivare("8880486","Tiffany","Hallberg"));
        kod.add(new Forskrivare("8880494","Jakob","Jonasson"));
        kod.add(new Forskrivare("8880502","Elinor","Jackson"));
        kod.add(new Forskrivare("8880510","Frans","Hagnell"));
        kod.add(new Forskrivare("8880528","Leonora","Fogelberg"));
        kod.add(new Forskrivare("8880536","Alf","Möller"));
        kod.add(new Forskrivare("8880544","Antonia","Holtz"));
        kod.add(new Forskrivare("8880551","Oscar","Ekbom"));
        kod.add(new Forskrivare("8880569","Bodil","Dunker"));
        kod.add(new Forskrivare("8880577","Baltsar","Koch"));
        kod.add(new Forskrivare("8880585","Ellinor","Gunne"));
        kod.add(new Forskrivare("8880593","Carl","Knutsson"));
        kod.add(new Forskrivare("8880601","Anastasia","Lundbom"));
        kod.add(new Forskrivare("8880619","Staffan","Ramstedt"));
        kod.add(new Forskrivare("8880627","Claudia","Von Gegerfelt"));
        kod.add(new Forskrivare("8880635","Rodmar","Sandegren"));
        kod.add(new Forskrivare("8880643","Angelika","Merkel"));
        kod.add(new Forskrivare("8880650","Alve","Salomonsson"));
        kod.add(new Forskrivare("8880668","Leija","Hult"));
        kod.add(new Forskrivare("8880676","Malcolm","Ibrahim"));
        kod.add(new Forskrivare("8880684","Daniella","Hylander"));
        kod.add(new Forskrivare("8880692","Troy","Kronberg"));
        kod.add(new Forskrivare("8880700","Millie","Malmborg"));
        kod.add(new Forskrivare("8880718","Malte","Blom"));
        kod.add(new Forskrivare("8880726","Albina","Ågerup"));
        kod.add(new Forskrivare("8880734","Esbjörn","Jacobsson"));
        kod.add(new Forskrivare("8880742","Christine","Ågren"));
        kod.add(new Forskrivare("8880759","Vince","Engeström"));
        kod.add(new Forskrivare("8880767","Vilja","Regnér"));
        kod.add(new Forskrivare("8880775","Ted","Bergland"));
        kod.add(new Forskrivare("8880783","Isolde","Moberg"));
        kod.add(new Forskrivare("8880791","Elvin","Brand"));
        kod.add(new Forskrivare("8880809","Ophelia","Lindblom"));
        kod.add(new Forskrivare("8880817","Kaspar","Frank"));
        kod.add(new Forskrivare("8880825","Nikita","Låftman"));
        kod.add(new Forskrivare("8880833","Eugen","Sund"));
        kod.add(new Forskrivare("8880841","Valentina","Appelberg"));
        kod.add(new Forskrivare("8880858","Miliam","Lundén"));
        kod.add(new Forskrivare("8880866","Angelika","Ask"));
        kod.add(new Forskrivare("8880874","Albin","Hanson"));
        kod.add(new Forskrivare("8880882","Annie","Korhonen"));
        kod.add(new Forskrivare("8880890","Lennox","Wallén"));
        kod.add(new Forskrivare("8880908","Lovelia","Lejon"));
        kod.add(new Forskrivare("8880916","Harry","Afzelius"));
        kod.add(new Forskrivare("8880924","Zoe","Dahlström"));
        kod.add(new Forskrivare("8880932","Edmund","Eggeling"));
        kod.add(new Forskrivare("8880940","Ninja","Forssmed"));
        kod.add(new Forskrivare("8880957","Raoul","Karlsson"));
        kod.add(new Forskrivare("8880965","Sabrina","Sederholm"));
        kod.add(new Forskrivare("8880973","Conrad","Jung"));
        kod.add(new Forskrivare("8880981","Lisa","Carlberg"));
        kod.add(new Forskrivare("8880999","Valter","Graaf"));

        int rnd=-1;
        int loop = -1;
        while (loop != 0) {
            double d_rnd = Math.random() * 100;
            rnd = (int)d_rnd;
            if (rnd < kod.size() && rnd >= 1 ) { loop = 0; }
        }
        return (Forskrivare) kod.get(rnd);
    }

}

package se.nhpj.testdata;

public class Fordon {


    public static String getRndRegNummer() {
        String[] bokstaver = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","R","S","T","U","W","X","Y","Z"};
        String[] siffror   = {"0","1","2","3","4","5","6","7","8","9"};
        StringBuilder sb = new StringBuilder();

        sb.append(se.nhpj.testdata.RndData.rndFrom(bokstaver));
        sb.append(se.nhpj.testdata.RndData.rndFrom(bokstaver));
        sb.append(se.nhpj.testdata.RndData.rndFrom(bokstaver));
        sb.append(se.nhpj.testdata.RndData.rndFrom(siffror));
        sb.append(se.nhpj.testdata.RndData.rndFrom(siffror));
        sb.append(se.nhpj.testdata.RndData.rndFrom(siffror));

        return sb.toString();
    }

    public static String getRndColor() {
        String[] color = {"akvamarin", "aprikos", "babyblå", "becksvart", "beige", "blodröd", "blodrött",
                "blå", "bronsfärgad", "brun", "brunt", "bärnstensgul", "cendré", "cerise", "citrongul",
                "cyan", "fuchsia", "grå", "gräddvit", "grön", "gul", "gyllne", "havsblå", "indigo", "isblå",
                "kaffebrun", "kaki", "kastanjefärgad", "koboltblå", "kornblå", "ljusblå", "ljusgrå", "ljusgul",
                "ljusröd", "mint", "mintgrön", "mörkblå", "mörkgrå", "mörkgrön", "mörkrosa", "olivgrön",
                "orange", "purpur", "rosa", "röd", "scharlakansröd", "smaragdgrön", "snövit", "solgul",
                "svart", "turkos", "vinröd", "viol", "violett", "vit", "äggul", "äggvit", "silver",
                "silvergrå", "guld", "pärlemor"};
        String[] metallic = {" metallic",""," metallic"};
        return se.nhpj.testdata.RndData.rndFrom(color) + se.nhpj.testdata.RndData.rndFrom(metallic);
    }

    public static final int BIL=0;
    public static final int MOTORCYCKEL=1;

    public static String getRndBrand() {
        return getRndBrand(BIL);
    }

    public static String getRndBrand(int typ) {

        String[] mc = {
                "Harley-Davidson STREET GLIDE:2019:BENSIN:MANUELL:107",
                "Harley-Davidson Sportster Superlow:2019:BENSIN:MANUELL:N/A",
                "Harley-Davidson IRON 883:2019:BENSIN:MANUELL:N/A",
                "Harley-Davidson 1200 CUSTOM:2019:BENSIN:MANUELL:N/A",
                "KTM 450 SX-F:2018:BENSIN:MANUELL:N/A",
                "KTM 250 SX-F:2018:BENSIN:MANUELL:N/A",
                "KTM 125 SX-F:2018:BENSIN:MANUELL:N/A",
                "Kawasaki KX450:2018:BENSIN:MANUELL:N/A",
                "Kawasaki KX250:2018:BENSIN:MANUELL:N/A",
                "Kawasaki KX125:2018:BENSIN:MANUELL:N/A",
                "Indian Chieftain:2018:BENSIN:MANUELL:N/A",
                "Indian FTR:2018:BENSIN:MANUELL:N/A",
                "Indian Scout:2018:BENSIN:MANUELL:N/A",
                "Indian Roadmaster:2018:BENSIN:MANUELL:N/A",
                "MotoGuzzi V7 III:2018:BENSIN:MANUELL:52",
                "MotoGuzzi MGX 21:2018:BENSIN:MANUELL:97",
                "MotoGuzzi V9 Bobber:2018:BENSIN:MANUELL:55",
                "MotoGuzzi Eldorado:2018:BENSIN:MANUELL:96",
                "Triumph Bonneville T120:1962:BENSIN:MANUELL:46",
                "Triumph TIGER 800:2018:BENSIN:MANUELL:94",
                "Triumph Street Twin:2018:BENSIN:MANUELL:55",
                "Triumph THRUXTON 1200:2018:BENSIN:MANUELL:97",
                "Husqvarna TC450:2008:BENSIN:MANUELL:63",
                "Husqvarna TC250:2008:BENSIN:MANUELL:63",
                "Husqvarna TC125:2008:BENSIN:MANUELL:63",
                "Suzuki RM-Z 450:2019:BENSIN:MANUELL:N/A",
                "Suzuki RM-Z 250:2019:BENSIN:MANUELL:N/A",
                "Suzuki RM-Z 125:2019:BENSIN:MANUELL:N/A",
                "Yamaha YZ450F:2018:BENSIN:MANUELL:N/A",
                "Yamaha YZ250F:2018:BENSIN:MANUELL:N/A",
                "Yamaha YZ125F:2018:BENSIN:MANUELL:N/A",
                "Ducati 999:2006:BENSIN:MANUELL:138",
                "Ducati Diavel 1260:2018:BENSIN:MANUELL:159",
                "Ducati Hypermotard 950:2018:BENSIN:MANUELL:84",
                "Ducati Monster 797:2018:BENSIN:MANUELL:73",
                "Honda CRF250L:2018:BENSIN:MANUELL:25",
                "Honda AFRICA TWIN ADVENTURE:2018:BENSIN:MANUELL:95",
                "Honda VFR1200X CROSSTOURER:2018:BENSIN:MANUELL:129",
                "Honda CBR650F:2018:BENSIN:MANUELL:91",
                "Honda GOLDWING TOUR :2018:BENSIN:MANUELL:126",
                "BMW R 1250:2018:BENSIN:MANUELL:136",
                "BMW F 850 GS:2018:BENSIN:MANUELL:95",
                "BMW C 400 GT:2018:BENSIN:MANUELL:34",
        };

        String[] brand = {
                "Buick Skylark:1984:BENSIN:AUTOMAT:115",
                "Cadillac Eldorado:1956:BENSIN:AUTOMAT:100",
                "Challenger:2005:BENSIN:MANUELL:375",
                "Chevrolet Camaro SS:2000:BENSIN:MANUELL:400",
                "Chrysler New Yorker:1977:BENSIN:AUTOMAT:233",
                "Daewoo Polonez:1978:BENSIN:MANUELL:64",
                "Datsun Bluebird 910:1983:BENSIN:MANUELL:85",
                "De Tomaso Pantera:1972:BENSIN:MANUELL:460",
                "DKW Munga:1960:BENSIN:MANUELL:x",
                "Aston Martin DB5:1963:BENSIN:MANUELL:282",
                "Autobianchi A112:1984:BENSIN:MANUELL:112",
                "Austin Mini:1980:BENSIN:MANUELL:34",
                "Austin-Healey Sprite:1958:BENSIN:MANUELL:46",
                "Bentley Continental GT:2018:BENSIN:MANUELL:x",
                "Ferrari 488GTB:2018:BENSIN:MANUELL:670",
                "Lada Niva:1977:BENSIN:MANUELL:80",
                "Lamborghini Countach:1983:BENSIN:MANUELL:375",
                "Lancia Flavia 2000:1972:BENSIN:MANUELL:114",
                "Morgan Plus:2019:BENSIN:MANUELL:154",
                "Morris Mini:1964:BENSIN:MANUELL:55",
                "Lincoln Navigator:2010:ETANOL:AUTOMAT:314",
                "Lotus Esprit:1980:BENSIN:MANUELL:210",
                "Maserati Kyalami:1977:BENSIN:MANUELL:255",
                "GMC Sierra 2500 HD:2017:DISEL:AUTOMAT:143",
                "Rolls-Royce Phantom:2003:BENSIN:AUTOMAT:460",
                "Rover 75:2000:BENSIN:AUTOMAT:177",
                "TVR 280i:1988:BENSIN:MANUELL:139",
                "Shelby Cobra:1962:BENSIN:MANUELL:335",
                "McLaren 650S Spider:2015:BENSIN:AUTOMAT:641",
                "MG MGB:1966:BENSIN:MANUELL:95",
                "Oldsmobile Cutlass:1970:BENSIN:AUTOMAT:400",
                "Hummer H1:1996:DISEL:AUTOMAT:190",
                "Plymouth Superbird:1970:BENSIN:MANUELL:425",
                "Pontiac Firebird Trans Am:1978:BENSIN:AUTOMAT:155",

                "Land Rover Discovery:1991:BENSIN:MANUELL:172",
                "Land Rover Defender 90 2.5 TD5 4x4:1999:DISEL:AUTOMAT:122",

                "Alfa Romeo 2000 Sportiva:1954:BENSIN:MANUELL:138",
                "Alfa Romeo Alfetta:1979:BENSIN:MANUELL:118",
                "Alfa Romeo 155:1993:BENSIN:MANUELL:141",
                "Alfa Romeo GT:2006:BENSIN:MANUELL:170",

                "Audi A1 TDI:2011:DISEL:AUTOMAT:90",
                "Audi A3:1998:BENSIN:MANUELL:150",
                "Audi A4:2001:BENSIN:MANUELL:125",
                "Audi A6:1994:BENSIN:AUTOMAT:140",
                "Audi A7:2013:DISEL:AUTOMAT:205",
                "Audi A8:1997:BENSIN:AUTOMAT:193",
                "Audi TT:2002:BENSIN:MANUELL:180",
                "Audi Q3:2016:BENSIN:AUTOMAT:150",
                "Audi Q5:2015:DISEL:AUTOMAT:170",

                "BMW 318i:2006:BENSIN:MANUELL:129",
                "BMW 320i:2008:BENSIN:AUTOMAT:170",
                "BMW 330i Touring:2019:BENSIN:AUTOMAT:252",
                "BMW 520i:1998:BENSIN:MANUELL:150",
                "BMW 525i:1992:BENSIN:AUTOMAT:190",
                "BMW 535d GT:2011:DISEL:AUTOMAT:299",
                "BMW X5:2007:BENSINI:AUTOMAT:272",
                "BMW X3:2006:DISEL:MANUELL:150",

                "Citroën CX:1982:BENSIN:MANUELL:98",
                "Citroën BX:1990:BENSIN:MANUELL:71",
                "Citroën 2CV:1961:BENSIN:MANUELL:9",
                "Citroën DS:1975:BENSIN:MANUELL:98",
                "Citroën H Van:1964:DISEL:MANUELL:xx",
                "Citroën XM:1992:DISEL:MANUELL:82",
                "Citroën C4:2004:BENSIN:MANUELL:89",

                "Dacia Nova:1995:BENSIN:MANUELL:63",
                "Dacia Duster 1.5 DCi:2011:DISEL:MANUELL:107",
                "Dacia Sandero:2007:BENSIN:MANUELL:72",

                "Fiat 500:1960:BENSIN:MANUELL:13",
                "Fiat Uno:1997:BENSIN:MANUELL:60",
                "Fiat Ritmo:1979:BENSIN:MANUELL:65",
                "Fiat Bravo:2009:DISEL:MANUELL:120",
                "Fiat Croma:1996:DISEL:AUTOMAT:115",
                "Fiat Sedici:2014:DISEL:MANUELL:135",
                "Fiat Linea:2012:DISEL:AUTOMAT:105",
                "Fiat 500 LOUNGE:2015:BENSIN:MANUELL:69",

                "Ford Fiesta:2010:DISEL:MANUELL:71",
                "Ford Mondeo:2007:DISEL:MANUELL:130",
                "Ford Focus:2015:DISEL:MANUELL:95",
                "Ford Kuga:2017:BENSIN:MANUELL:150",
                "Ford C-Max:2007:DISEL:MANUELL:109",
                "Ford S-Max:2008:DISEL:AUTOMAT:163",
                "Ford Sierra:1988:BENSIN:AUTOMAT:109",

                "Honda Civic:2000:BENSIN:MANUELL:182",
                "Honda Prelude:1993:BENSIN:MANUELL:160",
                "Honda Accord:1999:BENSIN:MANUELL:147",
                "Honda CR-V:1999:BENSIN:MANUELL:147",
                "Honda FR-V:2005:BENSIN:MANUELL:150",
                "Honda Jazz:2006:BENSIN:MANUELL:83",
                "Honda Insight:2010:EL-BENSIN:AUTOMAT:88",

                "Hyundai Atos:1999:BENSIN:MANUELL:54",
                "Hyundai Accent:2000:BENSIN:MANUELL:90",
                "Hyundai Santa Fe:2001:BENSIN:MANUELL:145",
                "Hyundai Sonata:2003:BENSIN:MANUELL:131",
                "Hyundai Elantra:2004:BENSIN:AUTOMAT:143",
                "Hyundai Matrix:2004:BENSIN:MANUELL:122",
                "Hyundai Getz:2005:BENSIN:MANUELL:63",
                "Hyundai TUCSON:2005:BENSIN:MANUELL:141",
                "Hyundai i30:2009:DISEL:MANUELL:90",
                "Hyundai i20:2009:BENSIN:MANUELL:77",
                "Hyundai i40:2013:DISEL:MANUELL:136",

                "Isuzu SWIFT:2011:DISEL:MANUELL:75",
                "Isuzu D-MAX:2017:DISEL:MANUELL:163",

                "Jaguar E-Type 4.2L:1070:BENSIN:MANUELL:202",
                "Jaguar XJR:1995:BENSIN:AUTOMAT:320",
                "Jaguar F-Type:2015:BENSIN:AUTOMAT:551",

                "Jeep Wrangler 4.2:1989:BENSIN:AUTOMT:121",
                "Jeep Grand Cherokee 5.7 HEMI V8:2005:BENSIN:AUTOMAT:326",
                "Jeep Patriot:2008:BENSIN:MANUELL:170",
                "Jeep Compass:2012:DISEL:MANUELL:163",

                "Kia Sportage:2000:BENSIN:MANUELL:163",
                "Kia Picanto:2004:BENSIN:MANUELL:65",
                "Kia Carens:2005:BENSIN:AUTOMAT:139",
                "Kia Sorento:2005:DISEL:AUTOMAT:140",
                "Kia Magentis:2005:BENSIN:AUTOMAT:136",
                "Kia Cee´d:2007:DISEL:MANUELL:114",
                "Kia Soul:2009:DISEL:AUTOMAT:59",
                "Kia Sorento 2,5 CRDi:2008:DISEL:AUTOMAT:170",

                "Mazda 323:1984:BENSIN:MANUELL:72",
                "Mazda 626:2000:BENSIN:MANUELL:116",
                "Mazda Demio:2000:BENSIN:MANUELL:72",
                "Mazda PREMACY:2001:BENSIN:MANUELL:131",
                "Mazda 6:2007:DISEL:MANUELL:143",
                "Mazda 2:2007:BENSIN:MANUELL:75",
                "Mazda 3:2008:BENSIN:MANUELL:260",

                "Lexus RX 400:2006:EL-BENSIN:AUTOMAT:201",
                "Lexus NX 300:2015:EL-BENSIN:AUTOMAT:155",

                "Mercedes 230:2083:BENSIN:AUTOMAT:125",
                "Mercedes A:1999:BENSIN:MANUELL:102",
                "Mercedes 200 C:2001:BENSIN:AUTOMAT:163",
                "Mercedes 450 SL:1974:BENSIN:AUTOMAT:224",
                "Mercedes 350 E:2005:BENSIN:AUTOMAT:272",

                "MINI Cooper S 1.6L:2003:BENSIN:MANUELL:163",
                "MINI Cooper S Cabriolet:2005:BENSIN:MANUELL:170",
                "MINI Cooper S Clubman:2009:BENSIN:MANUELL:174",
                "MINI Countryman SD:2012:DISEL:MANUELL:143",
                "MINI One D:2010:DISEL:MANUELL:90",

                "Mitsubishi 1.6 LANCER:1997:BENSIN:MANUELL:113",
                "Mitsubishi Carisma:1998:BENSIN:AUTOMAT:125",
                "Mitsubishi Space Wagon:2000:BENSIN:AUTOMAT:150",
                "Mitsubishi Galant:2001:BENSIN:AUTOMT:160",
                "Mitsubishi Pajero:2002:BENSIN:AUTOMAT:114",
                "Mitsubishi Outlander:2003:BENSIN:MANUELL:136",

                "Nissan Primera:1996:BENSIN:MANUELL:125",
                "Nissan Micra:2000:BENSIN:MANUELL:75",
                "Nissan Almera:2001:BENSIN:MANUELL:114",
                "Nissan X-Trail:2003:BENSIN:MANUELL:165",
                "Nissan Patrol:2004:DISEL:AUTOMT:158",
                "Nissan NOTE:2006:BENSIN:MANUELL:88",

                "Opel Vectra:1998:BENSIN:AUTOMAT:136",
                "Opel Astra:1999:BENSIN:MANUELL:101",
                "Opel Corsa:2004:BENSIN:AUTOMT:75",
                "Opel Zafira:2005:BENSIN:AUTOMAT:147",
                "Opel Meriva:2007:BENSIN:MANUELL:105",
                "Opel Insignia:2009:DISEL:AUTOMAT:160",
                "Opel Combo Tour:2012:DISEL:MANUELL:105",

                "Peugeot 406:1998:BENSIN:MANUELL:132",
                "Peugeot 206:2000:BENSIN:MANUELL:88",
                "Peugeot 306:2001:BENSIN:MANUELL:110",
                "Peugeot 307:2003:BENSIN:MANUELL:136",
                "Peugeot 807:2003:BENSIN:MANUELL:158",
                "Peugeot 607:2005:DISEL:AUTOMAT:204",
                "Peugeot 407:2006:DISEL:AUTOMAT:204",
                "Peugeot 207:2007:BENSIN:MANUELL:105",

                "Porsche 911:1974:BENSIN:MANUELL:150",
                "Porsche 928:1097:BENSIN:AUTOMAT:241",
                "Porsche 944:1990:BENSIN:MANUELL:211",

                "Renault Laguna:1998:BENSIN:MANUELL:113",
                "Renault Megane:1999:BENSIN:MANUELL:113",
                "Renault Clio:1999:BENSIN:MANUELL:75",
                "Renault Kangoo:2001:DISEL:MANUELL:64",
                "Renault Scenic:2002:BENSIN:MANUELL:137",
                "Renault Twingo:2002:BENSIN:MANUELL:75",
                "Renault Espace:2004:BENSIN:AUTOMAT:163",
                "Renault Koleos:2013:DISEL:MANUELL:250",
                "Renault Zoe:2014:EL:AUTOMAT:58",

                "Saab 900:1994:BENSIN:AUTOMAT:170",
                "Saab 9-3:1999:BENSIN:MANUELL:154",
                "Saab 9-5:2000:BENSIN:MANUELL:150",
                "Saab 9-5 Combi:2001:ETANOL-BENSIN:MANUELL:185",

                "Seat Toledo:1996:BENSIN:MANUELL:90",
                "Seat Vario:1998:BENSIN:MANUELL:101",
                "Seat Cordoba:1999:DISEL:MANUELL:90",
                "Seat Ibiza:2002:BENSIN:MANUELL:64",
                "Seat Leon:2007:DISEL:MANUELL:140",
                "Seat Altea:2007:DISEL:AUTOMAT:140",
                "Seat Alhambra:2012:DISEL:AUTOMAT:140",
                "Seat Exeo:2012:DISEL:AUTOMAT:143",
                "Seat Mii:2013:BENSIN:MANUELL:75",
                "Seat Ateca:2017:BENSIN:MANUELL:150",

                "Skoda Octavia:1999:BENSIN:MANUELL:75",
                "Skoda Fabia:2000:BENSIN:MANUELL:68",
                "Skoda Superb:2002:BENSIN:AUTOMAT:193",
                "Skoda Roomster:2007:DISEL:MANUELL:105",
                "Skoda Yeti:2010:BENSIN:MANUELL:105",
                "Skoda Citigo:2013:BENSIN:MANUELL:60",
                "Skoda Rapid:2015:DISEL:MANUELL:90",
                "Karoq Style:2019:DISEL:AUTOMAT:116",

                "Smart FORTWO:2005:BENSIN:AUTOMAT:61",
                "Smart Coupé:1999:BENSIN:AUTOMAT:45",
                
                "Ssangyong Kyron:2008:DISEL:AUTOMAT:165",
                "Ssangyong RODIUS:2013:DISEL:AUTOMAT:155",

                "Subaru Forester:2014:DISEL:MANUELL:145",
                "Subaru Legacy:2000:BENSIN:MANUELL:125",
                "Subaru impreza:2000:BENSIN:MANUELL:123",
                "Subaru Outback:2000:BENSIN:MANUELL:156",
                "Subaru Outback 2,5i:2006:BENSIN:AUTOMAT:172",
                "Subaru Legacy 2.5:2008:BENSIN:AUTOMAT:172",

                "Suzuki Swift:2019:BENSIN:MANUELL:90",
                "Suzuki Vitara:1997:BENSIN:MANUELL:96",

                "Tesla Model S P85:2013:EL:AUTOMAT:241",
                "Tesla Model S 75:2016:EL:AUTOMAT:387",

                "Toyota Corolla:1987:BENSIN:MANUELL:77",
                "Toyota Yaris:2000:BENSIN:MANUELL:85",
                "Toyota Celica:2000:BENSIN:MANUELL:142",
                "Toyota Avensis:2001:BENSIN:MANUELL:129",
                "Toyota Starlett:1987:BENSIN:MANUELL:74",
                "Toyota Carina:1989:BENSIN:MANUELL:121",
                "Toyota Camry:1996:BENSIN:MANUELL:136",
                "Toyota Aygo:2006:BENSIN:MANUELL:68",
                "Toyota Rav4:2003:BENSIN:MANUELL:125",
                "Toyota Prius:2007:EL-HYBROD-BENSIN:AUTOMAT:77",
                "Toyota Auris:2013:DISEL:MANUELL:89",

                "Volkswagen Golf:1990:BENSIN:AUTOMAT:69",
                "Volkswagen 1300:1973:BENSIN:MANUELL:50",
                "Volkswagen Passat:1997:BENSIN:MANUELL:150",
                "Volkswagen Polo:1998:BENSIN:MANUELL:75",
                "Volkswagen Beetle:1999:BENSIN:MANUELL:116",
                "Volkswagen Bora:1999:BENSIN:MANUELL:101",
                "Volkswagen Caddy:2003:DISEL:MANUELL:150",
                "Volkswagen Touran:2005:BENSIN:AUTOMAT:150",
                "Volkswagen Phaeton:2003:BENSIN:AUTOMAT:420",
                "Volkswagen Eos:2006:BENSIN:MANUELL:150",
                "Volkswagen Tiguan:2010:DISEL:AUTOMAT:140",
                "Volkswagen Sharan:2011:DISEL:MANUELL:140",
                "Volkswagen Touareg:2011:DISEL:AUTOMAT:239",

                "Volvo V90 D3:2017:DISEL:AUTOMAT:170",
                "Volvo V90 D4:2017:DISEL:AUTOMAT:190",
                "Volvo V90 D5:2017:DISEL:AUTOMAT:235",
                "Volvo V90 T4:2017:BENSIN:AUTOMAT:190",
                "Volvo V90 T5:2017:BENSIN:AUTOMAT:250",
                "Volvo V90 T4:2017:BENSIN:MANUELL:190",
                "Volvo V90 T5:2017:BENSIN:MANUELL:250",
                "Volvo V90 D3:2018:DISEL:AUTOMAT:170",
                "Volvo V90 D4:2018:DISEL:AUTOMAT:190",
                "Volvo V90 D5:2018:DISEL:AUTOMAT:235",
                "Volvo V90 T4:2018:BENSIN:AUTOMAT:190",
                "Volvo V90 T5:2018:BENSIN:AUTOMAT:250",
                "Volvo V90 T4:2018:BENSIN:MANUELL:190",
                "Volvo V90 T5:2018:BENSIN:MANUELL:250",
                "Volvo V90 CC D4:2017:DISEL:AUTOMAT:190",
                "Volvo V90 CC D5:2017:DISEL:AUTOMAT:235",
                "Volvo V90 CC D4:2018:DISEL:AUTOMAT:190",
                "Volvo V90 CC D5:2018:DISEL:AUTOMAT:235",
                "Volvo V90 CC T5:2017:BENSIN:AUTOMAT:250",
                "Volvo XC90 D5:2017:DISEL:AUTOMAT:235",
                "Volvo XC90 D4:2017:DISEL:AUTOMAT:190",
                "Volvo XC90 D5:2018:DISEL:AUTOMAT:235",
                "Volvo XC90 D4:2018:DISEL:AUTOMAT:190",
                "Volvo XC90 D5:2010:DISEL:AUTOMAT:235",
                "Volvo XC90 D4:2010:DISEL:AUTOMAT:190",

                "Volvo XC60 D3:2016:DISEL:AUTOMAT:150",
                "Volvo XC60 D3:2017:DISEL:AUTOMAT:150",
                "Volvo XC60 D4:2016:DISEL:MANNUELL:190",
                "Volvo XC60 D4:2017:DISEL:MANNUELL:190",
                "Volvo XC60 D5:2016:DISEL:AUTOMAT:220",
                "Volvo XC60 D5:2017:DISEL:AUTOMAT:220",
                "Volvo XC60 T5:2011:BENSIN:AUTOMAT:241",
                "Volvo XC60 T5:2013:BENSIN:AUTOMAT:241",
                "Volvo XC60 T6:2014:BENSIN:AUTOMAT:329",
                "Volvo XC60 T6:2015:BENSIN:AUTOMAT:329",

                "Volvo V60 D3:2010:DISEL:AUTOMAT:163",
                "Volvo V60 D5:2011:DISEL:AUTOMAT:205",
                "Volvo V60 T3:2011:BENSIN:MANUELL:150",
                "Volvo V60 T4:2011:BENSIN:AUTOMAT:179",
                "Volvo V60 T6:2011:BENSIN:AUTOMAT:305",
                "Volvo V60 D2:2012:DISEL:MANUELL:114",

                "Volvo S60 D3:2010:DISEL:AUTOMAT:163",
                "Volvo S60 D5:2011:DISEL:AUTOMAT:205",
                "Volvo S60 T3:2011:BENSIN:MANUELL:150",
                "Volvo S60 T4:2011:BENSIN:AUTOMAT:179",
                "Volvo S60 T6:2011:BENSIN:AUTOMAT:305",
                "Volvo S60 D2:2012:DISEL:MANUELL:114",

                "Volvo V50 2.0 D:2006:DISEL:MANUELL:136",
                "Volvo V50 2.4:2004:BENSIN:MANUELL:170",
                "Volvo V50 T5:2004:BENSIN:MANUELL:220",
                "Volvo V50 1.6D:2005:DISEL:MANUELL:110",
                "Volvo V50 D5:2007:DISEL:AUTOMAT:179",
                "Volvo V50 D2:2012:DISEL:MANUELL:114",
                "Volvo V50 D4:2012:DISEL:AUTOMAT:177",

                "Volvo S40 2.0T:2000:BENSIN:MANUELL:160",
                "Volvo S40 T4:2003:BENSIN:MNUELL:200",
                "Volvo S40 T5:2004:BENSIN:MANUELL:220",
                "Volvo S40 1.8F:2006:BENSIN:mNUELL:125",
                "Volvo S40 D3:2012:DISEL:AUTOMAT:150",

                "Volvo V40 2.0T:2000:BENSIN:MANUELL:160",
                "Volvo V40 T4:2003:BENSIN:MNUELL:200",
                "Volvo V40 T5:2004:BENSIN:MANUELL:220",
                "Volvo V40 1.8F:2006:BENSIN:mNUELL:125",
                "Volvo V40 D3:2012:DISEL:AUTOMAT:150",

                "Volvo XC40 D4:2018:DISEL:AUTOMAT:190",
                "Volvo XC40 D5:2018:DISEL:AUTOMAT:220",
                "Volvo XC40 T5:2018:BENSIN:AUTOMAT:241",
                "Volvo XC40 T8:2018:EL-BENSIN:AUTOMAT:420",

                "Volvo C30 T5:2007:BENSIN:AUTOMAT:220",
                "Volvo C30 D5:2007:DISEL:AUTOMAT:179",

                "Volvo S70 2.4:1998:BENSIN:AUTOMAT:144",
                "Volvo S70 2.5:1999:BENSIN:MANUELL:170",
                "Volvo S70 XC:2001:BENSIN:MANUELL:193",
                "Volvo S70 D5:2008:DISEL:AUTOMAT:185",
                "Volvo S70 D3:2010:DISEL:AUTOMAT:163",
                "Volvo S70 D4:2014:DISEL:AUTOMAT:181",

                "Volvo V70 2.4:1997:BENSIN:AUTOMAT:144",
                "Volvo V70 2.5:1998:BENSIN:MANUELL:170",
                "Volvo V70 XC:2000:BENSIN:MANUELL:193",
                "Volvo V70 D5:2007:DISEL:AUTOMAT:185",
                "Volvo V70 D3:2011:DISEL:AUTOMAT:163",
                "Volvo V70 D4:2015:DISEL:AUTOMAT:181",

                "Volvo C70 2.4T:2002:BENSIN:MANUELL:193",
                "Volvo C70 T5:2006:BENSIN:AUTOMAT:220",

                "Volvo 142:1969:BENSIN:MANUELL:75",
                "Volvo 144:1970:BENSIN:MANUELL:100",
                "Volvo 145:1971:BENSIN:MANUELL:100",
                "Volvo 164:1974:BENSIN:MANUELL:124",

                "Volvo 242:1975:BENSIN:MANUELL:100",
                "Volvo 244:1980:BENSIN:MANUELL:100",
                "Volvo 245:1988:BENSIN:MANUELL:120",
                "Volvo 264:1990:BENSIN:MANUELL:155",

                "Volvo Amazon:1959:BENSIN:MANUELL:118",
                "Volvo PV444:1960:BENSIN:MANUELL:75",
                "Volvo Duett:1959:BENSIN:MANUELL:75",

                "Volvo 66:1977:BENSIN:AUTOMAT:50",

                "Volvo 340:1988:BESIN:MANUELL:110",
                "Volvo 360:1989:BENSIN:MANUELL:110",
                "Volvo 440:1994:BENSIN:MANUELL:110",
                "Volvo 460:1995:BENSIN:MANUELL:110",
                "Volvo 480:1088:BENSIN:MANUELL:119",
                "Volvo 850:1997:BENSIN:MANUELL:144",
                "Volvo 940:1997:BENSIN:MANUELL:134",
                "Volvo 960:1994:BENSIN:MANUELL:204",
                "Volvo P1800:1961:BENSIN:MANUELL:100",
                "Volvo PV544:1960:BENSIN:MANUELL:59",
                "Volvo S80:2000:BENSIN:MANUELL:140"
        };

        String retVal = "-1";
        switch (typ) {
            case 0:  retVal = se.nhpj.testdata.RndData.rndFrom(brand); break;
            case 1:  retVal = se.nhpj.testdata.RndData.rndFrom(mc);    break;
            default: retVal = se.nhpj.testdata.RndData.rndFrom(brand); break;
        }
        return retVal;
    }
}

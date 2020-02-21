package se.nhpj.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonToken {

    @JsonProperty("personnummer")
    private String personnummer;
    @JsonProperty("fornamn")
    private String fornamn;
    @JsonProperty("mellannamn")
    private String mellannamn;
    @JsonProperty("efternamn")
    private String efternamn;
    @JsonProperty("adress")
    private AdressToken adress;
}

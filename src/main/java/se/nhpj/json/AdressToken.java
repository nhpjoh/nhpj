package se.nhpj.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdressToken {

    @JsonProperty("gatauadress")
    private String gatauadress;
    @JsonProperty("postnummer")
    private String postnummer;
    @JsonProperty("postort")
    private String postort;
    @JsonProperty("land")
    private String land;

}

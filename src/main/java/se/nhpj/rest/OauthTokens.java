package se.nhpj.rest;

/**
 * Denna klass representerar ett oAuth token som används av eHälsomyndigheten:s säkerhetslösning
 * @author nhpj
 */

public class OauthTokens {
    private String access_token;
    private String expires_in;
    private String token_type;
    private String refresh_token;
    private String error;
    private String error_description;

    /**
     * Special formaterad utsskrift för toString
     * @return Formaterad sträng
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***********************************************************************");
        sb.append("\n* access_token: ").append(access_token);
        sb.append("\n* expires_in: ").append(expires_in);
        sb.append("\n* token_type: ").append(token_type);
        sb.append("\n* refresh_token: ").append(refresh_token);
        sb.append("\n* error: ").append(error);
        sb.append("\n* error_description: ").append(error_description);
        sb.append("\n*********************************************************************");
        return sb.toString();
    }
    /**
     * Denna returnerar alla värden på en rad för t.ex. loggning till fil
     * @return en sträng
     */
    public String toLog(){
        StringBuilder sb = new StringBuilder();
        sb.append("access_token: ").append(access_token);
        sb.append(", expires_in: ").append(expires_in);
        sb.append(", token_type: ").append(token_type);
        sb.append(", refresh_token: ").append(refresh_token);
        sb.append(", error: ").append(error);
        sb.append(", error_description: ").append(error_description);
        return sb.toString();
    }

    public String getAccess_token() {    return access_token; }
    public void setAccess_token(String access_token) { this.access_token = access_token; }
    public String getExpires_in() { return expires_in; }
    public void setExpires_in(String expires_in) { this.expires_in = expires_in; }
    public String getToken_type() { return token_type; }
    public void setToken_type(String token_type) { this.token_type = token_type; }
    public String getRefresh_token() { return refresh_token; }
    public void setRefresh_token(String refresh_token) { this.refresh_token = refresh_token; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getError_description() { return error_description; }
    public void setError_description(String error_description) { this.error_description = error_description; }
    
}

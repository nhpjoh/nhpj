package se.nhpj.soap.services;

import se.nhpj.soap.utils.CurrentDateTime;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class GodkannUttag extends BaseXML {
    
    public GodkannUttag(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/GodkannUttag.properties");
    }
   
    public GodkannUttag() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/GodkannUttag.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
        
	    retVal = retVal + response.getTagCount("*//faultstring");
		
        try {
            if (retVal == 0 ) {
                System.out.println( "GodkannUttag: " + response.getTagValue("*//resultat"));
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getXML() + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/apisp/GodkannUttagResponder/V7";

    private String XML =    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:GodkannUttag xmlns:urn=\"urn:riv:se.apotekensservice:or:GodkannUttagResponder:7\">\n" +
                            "         <urn:anvandare></urn:anvandare>\n" +
                            "         <urn:apoteksaktorensExpeditionsId></urn:apoteksaktorensExpeditionsId>\n" +
                            "         <urn:dosunderlagsversion></urn:dosunderlagsversion>\n" +
                            "         <urn:expeditionsId></urn:expeditionsId>\n" +
                            "         <urn:expeditionsdatum></urn:expeditionsdatum>\n" +
                            "         <urn:expeditionsunderlag>\n" +
                            "            <urn1:dispenseratuttag xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "               <urn1:artikelinformation>\n" +
                            "                  <urn1:nplId></urn1:nplId>\n" +
                            "                  <urn1:nplPackId></urn1:nplPackId>\n" +
                            "                  <urn1:utbyttArtikel></urn1:utbyttArtikel>\n" +
                            "                  <urn1:extension/>\n" +
                            "               </urn1:artikelinformation>\n" +
                            "               <urn1:expedieradMangd></urn1:expedieradMangd>\n" +
                            "               <urn1:expeditionsradId></urn1:expeditionsradId>\n" +
                            "               <urn1:farmaceutsNotering></urn1:farmaceutsNotering>\n" +
                            "               <urn1:formansval></urn1:formansval>\n" +
                            "               <urn1:uttagsdosering>\n" +
                            "                  <urn1:doseringstext1></urn1:doseringstext1>\n" +
                            "                  <urn1:doseringstext2></urn1:doseringstext2>\n" +
                            "                  <urn1:extension/>\n" +
                            "               </urn1:uttagsdosering>\n" +
                            "               <urn1:uttagstyp></urn1:uttagstyp>\n" +
                            "               <urn1:extension/>\n" +
                            "            </urn1:dispenseratuttag>\n" +
                            "            <urn1:helforpackningsuttag xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "               <urn1:artikelinformation>\n" +
                            "                  <urn1:nplId></urn1:nplId>\n" +
                            "                  <urn1:nplPackId></urn1:nplPackId>\n" +
                            "                  <urn1:utbyttArtikel></urn1:utbyttArtikel>\n" +
                            "                  <urn1:extension/>\n" +
                            "               </urn1:artikelinformation>\n" +
                            "               <urn1:expedieradMangd></urn1:expedieradMangd>\n" +
                            "               <urn1:expedieratAntalForpackningar></urn1:expedieratAntalForpackningar>\n" +
                            "               <urn1:expeditionsradId></urn1:expeditionsradId>\n" +
                            "               <urn1:farmaceutsNotering></urn1:farmaceutsNotering>\n" +
                            "               <urn1:formansval></urn1:formansval>\n" +
                            "               <urn1:startforpackning></urn1:startforpackning>\n" +
                            "               <urn1:uttagsdosering>\n" +
                            "                  <urn1:doseringstext1></urn1:doseringstext1>\n" +
                            "                  <urn1:doseringstext2></urn1:doseringstext2>\n" +
                            "                  <urn1:extension/>\n" +
                            "               </urn1:uttagsdosering>\n" +
                            "               <urn1:uttagstyp></urn1:uttagstyp>\n" +
                            "               <urn1:extension/>\n" +
                            "            </urn1:helforpackningsuttag>\n" +
                            "            <urn1:ingenDygnsdos xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:ingenDygnsdos>\n" +
                            "            <urn1:korrigeraDoseringstext xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:korrigeraDoseringstext>\n" +
                            "            <urn1:maxDygnsdos xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:maxDygnsdos>\n" +
                            "            <urn1:ordinationsId xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:ordinationsId>\n" +
//                            "            <urn1:slutexpedierad xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"></urn1:slutexpedierad>\n" +
                            "            <urn1:varningarAccepteradeAvFarmaceut xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\">\n" +
                            "               <urn1:extension/>\n" +
                            "            </urn1:varningarAccepteradeAvFarmaceut>\n" +
                            "            <urn1:extension xmlns:urn1=\"urn:riv:se.apotekensservice:or:7\"/>\n" +
                            "         </urn:expeditionsunderlag>\n" +
                            "         <urn:personnummer></urn:personnummer>\n" +
                            "         <urn:underlagsversion></urn:underlagsversion>\n" +
                            "         <urn:yrkeskod></urn:yrkeskod>\n" +
                            "         <urn:efternamn></urn:efternamn>\n" +
                            "         <urn:fornamn></urn:fornamn>\n" +
                            "         <urn:befattningskod></urn:befattningskod>\n" +
                            "         <urn:extension/>\n" +
                            "      </urn:GodkannUttag>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setUnderlagsversion(String underlagsversion) {
        this.setTagValue("*//GodkannUttag/underlagsversion", underlagsversion);
    }

    public void setOrdinationsId(String ordinationsid) {
        this.setTagValue("*//GodkannUttag/expeditionsunderlag/ordinationsId", ordinationsid);
    }

    public void setPersonnummer(String personnummer) {
        this.setTagValue("*//GodkannUttag/personnummer", personnummer);
    }
    
    public void setExpeditionsdatumNow() {
        this.setTagValue("*//GodkannUttag/expeditionsdatum", CurrentDateTime.getTodaysDate() + "T00:00:00.000+00:00");
    }
    
    public void setExpeditionId(){
        Random rnd = new Random();
        String value = "";
        for (int i=0;i<12;i++){
           value = value + rnd.nextInt(9);
        }
        this.setTagValue("*//GodkannUttag/expeditionsId", value);
        this.setTagValue("*//GodkannUttag/apoteksaktorensExpeditionsId", value);
    }
    
    public void setExpeditionId( String value){
        this.setTagValue("*//GodkannUttag/expeditionsId", value);
        this.setTagValue("*//GodkannUttag/apoteksaktorensExpeditionsId", value);
    }
    
    public void setNplid (String nplid) {
        this.setTagValue("*//dispenseratuttag/artikelinformation/nplId",nplid);
        this.setTagValue("*//helforpackningsuttag/artikelinformation/nplId",nplid);
    }
    
    public void setNplPackid (String nplPackid) {
        this.setTagValue("*//dispenseratuttag/artikelinformation/nplPackId",nplPackid);
        this.setTagValue("*//helforpackningsuttag/artikelinformation/nplPackId",nplPackid);
    }
    
    public void setExpidieradMangd(String expedieradMangd) {
        this.setTagValue("*//helforpackningsuttag/expedieradMangd",expedieradMangd);
    }
}

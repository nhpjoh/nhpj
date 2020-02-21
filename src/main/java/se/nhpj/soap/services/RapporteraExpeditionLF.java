package se.nhpj.soap.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;


/**
 * @author nhpj
 */
public class RapporteraExpeditionLF extends BaseXML {
    
    public RapporteraExpeditionLF(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/RapporteraExpeditionLF.properties");
    }
   
    public RapporteraExpeditionLF() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/RapporteraExpeditionLF.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en underlagsversion returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("RapporteraExpeditionLF OK ");
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//faultstring") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/apisp/RapporteraExpeditionLFResponder/V5";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:RapporteraExpeditionLF xmlns:urn=\"urn:riv:se.apotekensservice:lf:RapporteraExpeditionLFResponder:5\">\n" +
                            "         <urn:expedition>\n" +
                            "            <urn1:aktorsExpeditionsid xmlns:urn1=\"urn:riv:se.apotekensservice:lf:5\"></urn1:aktorsExpeditionsid>\n" +
                            "            <urn1:expedierandeApotek xmlns:urn1=\"urn:riv:se.apotekensservice:lf:5\"></urn1:expedierandeApotek>\n" +
                            "            <urn1:expeditionsdatum xmlns:urn1=\"urn:riv:se.apotekensservice:lf:5\"></urn1:expeditionsdatum>\n" +
                            "            <urn1:expeditionsid xmlns:urn1=\"urn:riv:se.apotekensservice:lf:5\"></urn1:expeditionsid>\n" +
                            "            <urn1:patient xmlns:urn1=\"urn:riv:se.apotekensservice:lf:5\">\n" +
                            "               <urn2:namn xmlns:urn2=\"urn:riv:se.apotekensservice:lf:4\"></urn2:namn>\n" +
                            "               <urn2:personnummer xmlns:urn2=\"urn:riv:se.apotekensservice:lf:4\"></urn2:personnummer>\n" +
                            "            </urn1:patient>\n" +
                            "            <urn1:receptexpeditionsrader xmlns:urn1=\"urn:riv:se.apotekensservice:lf:5\">\n" +
                            "               <urn1:antalForpackningar></urn1:antalForpackningar>\n" +
                            "               <urn1:arbetsplatskod></urn1:arbetsplatskod>\n" +
                            "               <urn1:artikelinformation>\n" +
                            "                  <urn2:nplPackid xmlns:urn2=\"urn:riv:se.apotekensservice:lf:4\"></urn2:nplPackid>\n" +
                            "               </urn1:artikelinformation>\n" +
                            "               <urn1:dosdispenserad></urn1:dosdispenserad>\n" +
                            "               <urn1:doseringstext></urn1:doseringstext>\n" +
                            "               <urn1:forskrivararbetsplatsnamn></urn1:forskrivararbetsplatsnamn>\n" +
                            "               <urn1:forskrivararbetsplatsort></urn1:forskrivararbetsplatsort>\n" +
                            "               <urn1:forskrivarkod></urn1:forskrivarkod>\n" +
                            "               <urn1:forskrivarnamn></urn1:forskrivarnamn>\n" +
                            "               <urn1:forskrivarpostadress></urn1:forskrivarpostadress>\n" +
                            "               <urn1:forskrivarpostnummer></urn1:forskrivarpostnummer>\n" +
                            "               <urn1:radnummer></urn1:radnummer>\n" +
                            "               <urn1:utfardarkategori></urn1:utfardarkategori>\n" +
                            "            </urn1:receptexpeditionsrader>\n" +
                            "         </urn:expedition>\n" +
                            "         <urn:klientinformation>\n" +
                            "            <urn2:anvandare xmlns:urn2=\"urn:riv:se.apotekensservice:lf:4\"></urn2:anvandare>\n" +
                            "            <urn2:session xmlns:urn2=\"urn:riv:se.apotekensservice:lf:4\"></urn2:session>\n" +
                            "            <urn2:system xmlns:urn2=\"urn:riv:se.apotekensservice:lf:4\"></urn2:system>\n" +
                            "         </urn:klientinformation>\n" +
                            "      </urn:RapporteraExpeditionLF>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

    public void setPersonnummer(String personnummer) {
        this.setTagValue("*//patient/personnummer", personnummer);
    }
    public void setNamn(String fornamn, String efternamn) {
        this.setTagValue("*//patient/namn", fornamn + " " + efternamn);
    }
}

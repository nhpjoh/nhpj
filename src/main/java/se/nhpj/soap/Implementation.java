package se.nhpj.soap;

import se.nhpj.soap.services.SoapResponseXML;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
public class Implementation extends BaseXML {
    
    public Implementation(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/<ClassName>.properties");
    }
   
    public Implementation() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/<ClassName>.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
	retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("<XXXXXXXXX> hämtat OK ");
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

    private String soapEndpointUrl = "<serviceEndpoint>";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
            "               <bas>" +
            "                   <person>" +
            "                       <fnamn>Adam</fnamn>" +
            "                   </person>" +
            "                   <person>" +
            "                       <fnamn>Bertil</fnamn>" +
            "                       <enamn>Json</enamn>" +
            "                   </person>" +
            "               </bas>";


}
